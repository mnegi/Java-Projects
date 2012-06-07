// NAME
//      $RCSfile: OneNTPrintQBean.java,v $
// DESCRIPTION
//      [given below in javadoc format]
// DELTA
//      $Revision: 1.10 $
// CREATED
//      $Date: 2002/10/10 15:13:56 $
// COPYRIGHT
//      Westhawk Ltd
// TO DO
//

/*
 * Copyright (C) 1998, 1999, 2000, 2001, 2002 by Westhawk Ltd (www.westhawk.co.uk)
 *
 * Permission to use, copy, modify, and distribute this software
 * for any purpose and without fee is hereby granted, provided
 * that the above copyright notices appear in all copies and that
 * both the copyright notice and this permission notice appear in
 * supporting documentation.
 * This software is provided "as is" without express or implied
 * warranty.
 * author <a href="mailto:snmp@westhawk.co.uk">Tim Panton</a>
 */

import java.awt.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.beans.*;

/**
 * <p>
 * This bean collects information about one NT print queue installed
 * on a NT server. The NT mib is described in the 
 *
 * <a href="http://premium.microsoft.com/msdn/library/winresource/dnwinnt/f1d/d25/s86a2.htm">LAN Manager MIB II for Windows NT Objects</a> .
 *
 * You will have to register to the MSDN before accessing this page.
 * </p>
 *
 * <p>
 * The properties in the parent classes should be set, before calling
 * the action() method. Via a PropertyChangeEvent the application/applet
 * will be notified.
 * </p>
 *
 * @see SNMPBean#setHost
 * @see SNMPBean#setPort
 * @see SNMPBean#setCommunityName
 * @see SNMPRunBean#setUpdateInterval
 * @see #setIndex(String)
 * @see SNMPBean#addPropertyChangeListener
 * @see SNMPBean#action
 * @see GetPdu_vec
 * @see NTPrintQBean
 * 
 * @author <a href="mailto:snmp@westhawk.co.uk">Birgit Arkesteijn</a>
 * @version $Revision: 1.10 $ $Date: 2002/10/10 15:13:56 $
 */
public class OneNTPrintQBean extends SNMPRunBean implements Observer 
{
    private static final String     version_id =
        "@(#)$Id: OneNTPrintQBean.java,v 1.10 2002/10/10 15:13:56 birgit Exp $ Copyright Westhawk Ltd";

    private GetPdu_vec pdu;

    private boolean isPduInFlight;
    private Date lastUpdateDate = null;

    private String index = "";
    private String name = "";
    private int jobs = 0;

    private final static int NR_OID = 2;
    private final static String svPrintQName = 
            "1.3.6.1.4.1.77.1.2.29.1.1";
    private final static String svPrintQNumJobs =
            "1.3.6.1.4.1.77.1.2.29.1.2";

/**
 * The default constructor.
 */
public OneNTPrintQBean() 
{
}

/**
 * The constructor that will set the host and the port no.
 *
 * @param h the hostname
 * @param p the port no
 * @see SNMPBean#setHost
 * @see SNMPBean#setPort
 */
public OneNTPrintQBean(String h, int p) 
{
    this();
    setHost(h);
    setPort(p);
}

/**
 * Sets the index of the NT print queue that will be requested.
 * @param ind the index
 * @see #getIndex()
 * @see NTPrintQBean#getIndex(String)
 */
public void setIndex(String ind)
{
    if (ind != null && ind.length() > 0)
    {
        index = ind;
    }
}

/**
 * Returns the index of the NT print queue.
 * @return the index
 * @see #setIndex(String)
 */
public String getIndex()
{
    return index;
}

/**
 * Returns the name of the NT print queue.
 * @return the name
 */
public String getName()
{
    return name;
}

/**
 * Returns the number of jobs currently in this NT print queue.
 * @return the number of jobs
 */
public int getNumJobs()
{
    return jobs;
}

/**
 * Returns the date of the moment when this bean was last updated.
 * This might be null when the first time the update was not finished.
 *
 * @return the last update date
 */
public Date getLastUpdateDate()
{
    return lastUpdateDate;
}

/**
 * This method starts sending the SNMP request. All properties should be
 * set before this method is called.
 *
 * The actual sending will take place in the run method.
 * It makes a new snmp context and initialises all variables before
 * starting.
 */
public void action() 
{
    if (isHostPortReachable())
    {
        lastUpdateDate = new Date();
        isPduInFlight = false;
        setRunning(true);
    }
}


/**
 * The run method according to the Runnable interface.
 * This method will send the Pdu request, if the previous one is not
 * still in flight.
 * @see SNMPRunBean#isRunning()
 */
public void run()
{
    while (context != null && isRunning())
    {
        String ind = getIndex();
        if (isPduInFlight == false && ind != null && ind.length() > 0)
        {
            isPduInFlight = true;
            pdu = new GetPdu_vec(context, NR_OID);
            pdu.addObserver(this);
            pdu.addOid(svPrintQName + "." + ind);
            pdu.addOid(svPrintQNumJobs + "." + ind);
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

/**
 * The update method according to the Observer interface, it will be
 * called when the Pdu response is received.
 * The property change event is fired.
 *
 * @see SNMPBean#addPropertyChangeListener
 */
public void update(Observable obs, Object ov)
{
    pdu = (GetPdu_vec) obs;
    varbind [] varbinds = (varbind []) ov;

    if (pdu.getErrorStatus() == AsnObject.SNMP_ERR_NOERROR)
    {
        name = ((AsnOctets) varbinds[0].getValue()).getValue();
        jobs = ((AsnInteger) varbinds[1].getValue()).getValue();

        lastUpdateDate = new Date();
        isPduInFlight = false;
        firePropertyChange("NTService", null, null);
    }
}


}