

import java.awt.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.beans.*;


public class NTServiceNamesBean extends SNMPRunBean implements Observer
{

    public final static String svSvcName = "1.3.6.1.2.1.25.4.2.1.1.1";//"1.3.6.1.4.1.77.1.2.3.1.1";

    private int           svSvcName_len;
    private OneGetNextPdu pdu;
    private Hashtable     serviceHash;

    private boolean       isGetNextInFlight;
    private Date          lastUpdateDate = null;



public NTServiceNamesBean() 
{
    serviceHash = new Hashtable();
    svSvcName_len = svSvcName.length();
}

public NTServiceNamesBean(String h, int p) 
{
    this();
    setHost(h);
    setPort(p);
}


public Date getLastUpdateDate()
{
    return lastUpdateDate;
}


public Enumeration getIndices()
{
    return serviceHash.elements();
}

public String getIndex(String name)
{
    String ret = null;
    if (name != null)
    {
        ret = (String) serviceHash.get(name);
    }
    return ret;
}


public Enumeration getNames()
{
    return serviceHash.keys();
}


public synchronized int getCount()
{
    return serviceHash.size();
}


public void action()
{
    if (isHostPortReachable())
    {
        serviceHash.clear();
        lastUpdateDate = new Date();
        isGetNextInFlight = false;
        setRunning(true);
    }
}


public void run()
{
    while (context != null && isRunning())
    {
        if (isGetNextInFlight == false)
        {
            // start the GetNext loop again
            isGetNextInFlight = true;
            pdu = new OneGetPdu(context);
            pdu.addObserver(this);
            pdu.addOid(svSvcName);
            try
            {
                pdu.send();
            }
            catch (PduException exc)
            {
                System.out.println("PduException " + exc.getMessage());
            }
            catch (IOException exc)
            {
                System.out.println("IOException " + exc.getMessage());
            }
        }

        try
        {
            Thread.sleep(interval);
        } 
        catch (InterruptedException ix)
        {
            ;
        }
    }
}


public void update(Observable obs, Object ov)
{
    varbind var;
    String hashKey;
    String oid, index, name;

    pdu = (OneGetPdu) obs;
    if (pdu.getErrorStatus() == AsnObject.SNMP_ERR_NOERROR)
    {
        var = (varbind) ov;
        oid = var.getOid().toString();
        if (oid.startsWith(svSvcName))
        {
            // index is the part of the oid AFTER the svSvcName
            index = oid.substring(svSvcName_len+1);

            name = ((AsnOctets) var.getValue()).getValue();

            // update the hashtable with the new answer
            serviceHash.put(name, index);

            // perform the GetNext on the just received answer
            pdu = new OneGetNextPdu(context);
            pdu.addObserver(this);
            pdu.addOid(oid);
            try
            {
                pdu.send();
            }
            catch (PduException exc)
            {
                System.out.println("PduException " + exc.getMessage());
            }
            catch (IOException exc)
            {
                System.out.println("IOException " + exc.getMessage());
            }
        }
        else
        {
            // the GetNext loop has ended
            lastUpdateDate = new Date();
            isGetNextInFlight = false;
            firePropertyChange("serviceNames", null, null);
        }
    }
    else
    {
        // the GetNext loop has ended
        lastUpdateDate = new Date();
        isGetNextInFlight = false;
        firePropertyChange("serviceNames", null, null);
    }
}


}
