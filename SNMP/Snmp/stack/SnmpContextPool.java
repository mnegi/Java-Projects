


import java.util.*;

public class SnmpContextPool implements SnmpContextFace
{
    protected static Hashtable contextPool;

    protected SnmpContext context = null;
    protected String hostAddr, socketType;
    protected int hostPort;
    protected String community = SnmpContext.Default_Community;

public SnmpContextPool(String host, int port) throws java.io.IOException
{
    this(host, port, STANDARD_SOCKET);
}

public SnmpContextPool(String host, int port, String typeSocket) 
throws java.io.IOException
{
    this(host, port, SnmpContext.Default_Community, typeSocket);
}

public SnmpContextPool(String host, int port, String comm, String typeSocket) 
throws java.io.IOException
{
    initPools();
    hostAddr = host;
    hostPort = port;
    community = comm;
    socketType = typeSocket;

    context = getMatchingContext();
}

private static synchronized void initPools()
{
    if (contextPool == null)
    {
        contextPool = new Hashtable(5);
    }
}

public int getVersion()
{
    return AsnObject.SNMP_VERSION_1;
}

public String getHost()
{
    return hostAddr;
}

public int getPort()
{
    return hostPort;
}

public String getTypeSocket()
{
    return socketType;
}

public String getCommunity()
{
    return community;
}

public void setCommunity(String newCommunity)
{
    if (newCommunity != null
            && 
        newCommunity.equals(community) == false)

    {
        community = newCommunity;
        try
        {
            context = getMatchingContext();
        }
        catch (java.io.IOException exc) { }
    }
}

public boolean addPdu(Pdu pdu)
throws java.io.IOException, PduException
{
    try
    {
        if (context == null)
        {
            context = getMatchingContext();
        }
    }
    catch (java.io.IOException exc) {}
    return context.addPdu(pdu);
}

/**
 * Removes a pdu. 
 *
 * @param rid the Pdu request id
 * @return whether the pdu has been successfully removed
 */
public boolean removePdu(int requestId)
{
    boolean res = false;
    if (context != null)
    {
        res = context.removePdu(requestId);
    }
    return res;
}

/**
 * Encodes a pdu packet. 
 */
public byte[] encodePacket(byte msg_type, int rId, int errstat, 
      int errind, Enumeration ve) 
      throws java.io.IOException, EncodingException
{
    byte[] res = null;
    if (context != null)
    {
        res = context.encodePacket(msg_type, rId, errstat, errind, ve);
    }
    return res;
}

public void sendPacket(byte[] packet)
{
    if (context != null)
    {
        context.sendPacket(packet);
    }
}

/**
 * Releases the resources held by this context. This method will
 * decrement the reference counter. When the reference counter reaches
 * zero the actual context is removed from the pool and destroyed.
 */
public void destroy()
{
    String hashKey = getHashKey();
    synchronized(contextPool)
    {
        int count = 0;
        SnmpContextPoolItem item = (SnmpContextPoolItem) contextPool.get(hashKey);
        if (item != null)
        {
            count = item.getCounter();
            count--;
            item.setCounter(count);
        }

        if (count <= 0)
        {
            contextPool.remove(hashKey);
            if (context != null)
            {
                context.destroy();
                context = null;
            }
        }
    }
}

protected SnmpContext getMatchingContext() throws java.io.IOException
{
    SnmpContextPoolItem item = null;
    SnmpContext newContext = null;
    String hashKey = getHashKey();

    synchronized(contextPool)
    {
        int count=0;
        if (contextPool.containsKey(hashKey))
        {
            item = (SnmpContextPoolItem) contextPool.get(hashKey);
            newContext = (SnmpContext) item.getContext();
            count = item.getCounter();
        }
        else
        {
            newContext = new SnmpContext(hostAddr, hostPort, socketType);
            newContext.setCommunity(community);
            item = new SnmpContextPoolItem(newContext);
            contextPool.put(hashKey, item);
        }
        count++;
        item.setCounter(count);
    }
    return newContext;
}

/**
 * Dumps the pool of contexts. This is for debug purposes.
 * @param title The title of the dump
 */
public void dumpContexts(String title)
{
    System.out.println(title + " " + contextPool.size());
    Enumeration keys = contextPool.keys();
    int i=0;
    Object c = null;
    while (keys.hasMoreElements())
    {
        String key = (String) keys.nextElement();
        SnmpContextPoolItem item = (SnmpContextPoolItem) contextPool.get(key);
        if (item != null)
        {
            int count = item.getCounter();
            SnmpContext cntxt = (SnmpContext) item.getContext();

            System.out.println("\tcontext: " + key + ", count: " + count
                + ", index: " + i);
            if (cntxt == context)
            {
                System.out.println("\t\tcurrent context");
            }
            i++;
        }
    }
}

/**
 * Returns the hash key. This key is built out of all properties. It
 * serves as key for the hashtable of (v1) contexts.
 *
 * @return The hash key
 */
public String getHashKey()
{
    String str = hostAddr
          + "_" + hostPort
          + "_" + socketType
          + "_" + community
          + "_v" + getVersion();
    return str;
}

public void addTrapListener(TrapListener l) throws java.io.IOException
{
    if (context != null)
    {
        context.addTrapListener(l);
    }
}

/**
 * Removes the specified trap listener. The listener will be removed
 * from the current context, <em>not</em> from all the contexts in the
 * hashtable.
 *
 * @see SnmpContext#removeTrapListener
 */
public void removeTrapListener(TrapListener l) throws java.io.IOException
{
    if (context != null)
    {
        context.removeTrapListener(l);
    }
}

/**
 * Processes the incoming trap with the current context.
 *
 * @see SnmpContext#processIncomingTrap
 */
public Pdu processIncomingTrap(byte [] message)
throws DecodingException, java.io.IOException
{
    Pdu pdu = null;
    if (context != null)
    {
        pdu = context.processIncomingTrap(message);
    }
    return pdu;
}

/**
 * Returns a string representation of the object.
 * @return The string
 */
public String toString()
{
    String res = "";
    if (context != null)
    {
        res = context.toString();
    }
    return res;
}

}
