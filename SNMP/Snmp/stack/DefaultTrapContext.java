
import java.io.*;
import java.util.*;

public class DefaultTrapContext implements Runnable
{
    public final static int DEFAULT_TRAP_PORT = 162;
    private static DefaultTrapContext current = null;

    private ContextSocketFace  soc;
    private Thread          me;
    private String          basename;
    protected int           maxRecvSize;
    transient private TrapReceivedSupport trapSupport, unhandledSupport;

/**
 * Constructor.
 * The Standard socket type will be used.
 *
 * @param port The local port where traps are received
 * @see SnmpContextBasisFace#STANDARD_SOCKET
 */
protected DefaultTrapContext(int port) throws java.io.IOException
{
    this(port, SnmpContextBasisFace.STANDARD_SOCKET);
}

/**
 * Constructor.
 *
 * The typeSocket will indicate which type of socket to use. This way
 * different handlers can be provided for Netscape or Standard JVM.
 * The Netscape implementation will make the necessary security calls
 * to access connections that are not the applet's webserver. The KVM
 * version will be for small device support (e.g. Palm Pilot).
 *
 * @param port The local port where traps are received
 * @param typeSocketA The type of socket to use.
 *
 * @see SnmpContextBasisFace#STANDARD_SOCKET
 * @see SnmpContextBasisFace#NETSCAPE_SOCKET
 * @see SnmpContextBasisFace#KVM_SOCKET
 */
protected DefaultTrapContext(int port, String typeSocketA)
throws java.io.IOException
{
    basename = ""+port;
    trapSupport = new TrapReceivedSupport(this);
    unhandledSupport = new TrapReceivedSupport(this);
    maxRecvSize = SnmpContextBasisFace.MSS;

    soc = AbstractSnmpContext.getSocket(typeSocketA);
    soc.create(port);
}


/**
 * Returns the instance of DefaultTrapContext. It will create the
 * instance if it didn't exists.
 * See <a href=#note>the note</a> above.
 */
public static synchronized DefaultTrapContext getInstance(int port) 
throws java.io.IOException 
{
    if (current == null)
    {
        current = new DefaultTrapContext(port);
    }
    return current;
}

/**
 * Returns the instance of DefaultTrapContext. It will create the
 * instance if it didn't exists.
 * See <a href=#note>the note</a> above.
 */
public static synchronized DefaultTrapContext getInstance(int port, String typeSocketA) 
throws java.io.IOException 
{
    if (current == null)
    {
        current = new DefaultTrapContext(port, typeSocketA);
    }
    return current;
}

/**
 * Starts listening for traps if we didn't do so already.
 * @see #addTrapListener
 */
protected void startListening()
{
    if (me == null)
    {
        me = new Thread(this, basename+"_Receive");
        me.setPriority(me.NORM_PRIORITY);
        me.start();
    }
}

/**
 * Returns the maximum number of bytes this context will read from the
 * socket. By default this will be set to <code>MSS</code> (i.e. 1300).
 *
 * @since 4_12
 * @see SnmpContextBasisFace#MSS
 * @see #setMaxRecvSize(int)
 * @see AbstractSnmpContext#setMaxRecvSize(int)
 * @return The number 
 */
public int getMaxRecvSize()
{
    return maxRecvSize;
}

/**
 * Sets the maximum number of bytes this context will read from the
 * socket. By default this will be set to <code>MSS</code> (i.e. 1300).
 *
 * @since 4_12
 * @see SnmpContextBasisFace#MSS
 * @see AbstractSnmpContext#getMaxRecvSize()
 * @param no The new number 
 */
public void setMaxRecvSize(int no)
{
    maxRecvSize = no;
}

/**
 * This method will stop the thread listening for Traps. 
 * It closes the socket. The traplisteners are removed just before 
 * the run() finishes.
 *
 * <p>
 * Note that by calling this method the whole stack will stop listening
 * for traps! The listeners added via the SnmpContext classes are
 * effected as well.
 * </p>
 *
 * <p>
 * Thanks to Balakrishnan (bala_tbn@yahoo.com) for pointing out that
 * there was no way of stopping the listener.
 * </p>
 */
public synchronized void destroy()
{
    if (me != null)
    {
        me = null;
    }
    soc.close();

    if (AsnObject.debug > 12)
    {
        System.out.println("DefaultTrapContext.destroy(): Socket closed ");
    }
}

/**
 * We wait for any incoming traps and fire a trap received (undecoded) event 
 * if we do.
 
 * <p>
 * The undecoded events are fired to all normal listeners (added via 
 * addTrapListener()), unless one of them
 * consumes it. The SnmpContext classes will consume the event if it
 * matches their configuration.
 * </p>
 *
 * <p>
 * If none of them consume the event, the undecoded events
 * are fired to all unhandled trap listeners (added via
 * addUnhandledTrapListener()), unless one of them
 * consumes it.
 * </p>
 * @see TrapReceivedSupport#fireTrapReceived(int, String, byte [])
 * @see #addTrapListener(TrapListener)
 * @see #addUnhandledTrapListener(TrapListener)
 */
public void run()
{
    while (me != null)
    {
        // block for incoming packets
        me.yield(); 
        try
        {
            ByteArrayInputStream in;
            String hostAddress;

            if (me == null)
            {
                break;
            }
            in = soc.receive(maxRecvSize); 
            hostAddress = soc.getHostAddress();

            // read the bytes of the input stream into bu
            byte [] bu = null;
            int nb = in.available();
            bu = new byte[nb];
            in.read(bu);
            in.close();

            if (AsnObject.debug > 10)
            {
                 SnmpUtilities.dumpBytes("DefaultTrapContext.run(): "
                      + "Received packet", bu);
            }

            in = new ByteArrayInputStream(bu);
            AsnDecoder rpdu = new AsnDecoder();
            AsnSequence asnTopSeq = rpdu.getAsnSequence(in);
            int version = rpdu.getSNMPVersion(asnTopSeq);

            boolean isConsumed = trapSupport.fireTrapReceived(version, hostAddress, bu);
            if (isConsumed == false)
            {
                unhandledSupport.fireTrapReceived(version, hostAddress, bu);
            }
        }
        catch (java.io.IOException exc)
        {
            if (exc instanceof InterruptedIOException)
            {
                if (AsnObject.debug > 15)
                {
                    System.out.println("DefaultTrapContext.run(): Idle recv " + exc.getMessage());
                }
            }
            else
            {
                if (AsnObject.debug > 0)
                {
                    System.out.println("DefaultTrapContext.run(): IOException: " + exc.getMessage());
                }
            }
        }
        catch (DecodingException exc)
        {
            if (AsnObject.debug > 1)
            {
                System.out.println("DefaultTrapContext.run(): DecodingException: " + exc.getMessage());
            }
        }
        catch (Exception exc)
        {
            if (AsnObject.debug > 0)
            {
                System.out.println("DefaultTrapContext.run(): Exception: " + exc.getMessage());
                exc.printStackTrace();
            }
        }
    }

    trapSupport.empty();
    unhandledSupport.empty();
}

/**
 * Adds the specified trap listener to receive the undecoded traps.
 * When a trap is received the trap received event is fired to all 
 * listeners, unless one of them consumes it.
 *
 * <p>
 * All the SnmpContext objects use this method to listen for traps. When
 * a SnmpContext object decodes the trap succesfully, it will consume
 * it.
 * </p>
 *
 * @see TrapReceivedSupport#fireTrapReceived(int, String, byte [])
 * @see AbstractSnmpContext#addTrapListener(TrapListener)
 * @see #addUnhandledTrapListener(TrapListener)
 */
public void addTrapListener(TrapListener listener)
{
    trapSupport.addTrapListener(listener);
    startListening();
}

/**
 * Removes the specified trap listener.
 */
public void removeTrapListener(TrapListener listener)
{
    trapSupport.removeTrapListener(listener);
}

/**
 * Adds the specified trap listener to receive the undecoded traps when
 * it was not handled (ie not consumed) by any of the trap listeners in
 * addTrapListener().
 *
 * @see #addTrapListener(TrapListener)
 */
public void addUnhandledTrapListener(TrapListener listener)
{
    unhandledSupport.addTrapListener(listener);
    startListening();
}

/**
 * Removes the specified unhandled trap listener.
 */
public void removeUnhandledTrapListener(TrapListener listener)
{
    unhandledSupport.removeTrapListener(listener);
}

}
