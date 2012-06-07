
import java.util.*;
public class TrapReceivedSupport 
{
   
    private Object source;
    private transient Vector trapListeners;

public TrapReceivedSupport(Object src)
{
    source = src;
}

public synchronized void empty()
{
    if (trapListeners != null)
    {
        trapListeners.removeAllElements();
    }
}
public synchronized int getListenerCount()
{
    int c=0;
    if (trapListeners != null)
    {
        c = trapListeners.size();
    }
    return c;
}

public synchronized void addTrapListener(TrapListener listener)
{
    if (trapListeners == null)
    {
        trapListeners = new Vector (5);
    }
    if (trapListeners.contains(listener) == false)
    {
        trapListeners.addElement(listener);
    }
}

public synchronized void removeTrapListener(TrapListener listener)
{
    if (trapListeners != null)
    {
        trapListeners.removeElement(listener);
    }
}

public boolean fireTrapReceived(int version, String hostAddress, byte [] message)
{
    boolean isConsumed = false;
    Vector copyOfListeners = null;
    if (trapListeners != null)
    {
        synchronized (trapListeners)
        {
            copyOfListeners = (Vector) trapListeners.clone();
        }
    }

    if (copyOfListeners != null)
    {
        int l = message.length;
        int sz = copyOfListeners.size();
        for (int i=sz-1; i>=0 && isConsumed == false; i--)
        {
            TrapListener listener = (TrapListener) copyOfListeners.elementAt(i);

            byte [] copyOfMessage = new byte[l];
            System.arraycopy(message, 0, copyOfMessage, 0, l);
            TrapEvent evt = new TrapEvent(source, version, hostAddress, copyOfMessage);
            listener.trapReceived(evt);
            isConsumed = (evt.isConsumed());
        }
    }
    return isConsumed;
}

public void fireTrapReceived(Pdu pdu)
{
    Vector copyOfListeners = null;
    if (trapListeners != null)
    {
        synchronized (trapListeners)
        {
            copyOfListeners = (Vector) trapListeners.clone();
        }
    }

    if (copyOfListeners != null)
    {
        int sz = copyOfListeners.size();
        for (int i=sz-1; i>=0; i--)
        {
            TrapListener listener = (TrapListener) copyOfListeners.elementAt(i);

            TrapEvent evt = new TrapEvent(source, pdu);
            listener.trapReceived(evt);
        }
    }
}

}
