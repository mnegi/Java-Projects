

import java.awt.*; 
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;

public class set_req extends JComponent
     implements Observer, ActionListener
{
    public final static String sysContact = "1.3.6.1.2.1.1.4.0";

    private String host;
    private int port;
    private String community;
    private String oid;
    private String value;
    private String socketType;

    private SnmpContext context;
    private JTextField   thost, toid, tcom, tport, tvalue;
    private JButton      getButton, setButton, getNextButton;
    private JLabel       lmessage;

    private Pdu         pdu;
    private boolean     pduInFlight;
    private Util        util;


public set_req(String propertiesFilename)
{
    util = new Util(propertiesFilename, this.getClass().getName());
}
public set_req()
{
    
}

public void init (String obhost,String obid) 
{

    host = obhost;//util.getHost();
    port =161;// util.getPort(SnmpContextBasisFace.DEFAULT_PORT);
    socketType ="Standard";// util.getSocketType();
    oid =obid;// util.getOid(sysContact);
    community = "public";//util.getCommunity();

    pduInFlight = false;
    makeLayout(host, oid, port, community);
    sendGetRequest(host, port, community, oid);
}

public void actionPerformed(ActionEvent evt)
{
    Object src = evt.getSource();
    host = thost.getText();
    port = Integer.valueOf(tport.getText()).intValue();
    community = tcom.getText();
    oid = toid.getText();

    if (context != null)
    {
        context.destroy();
    }
    try
    {
        context = new SnmpContext(host, port, socketType);
        context.setCommunity(community);

        if (src == getButton)
        {
            pdu = new OneGetPdu(context);
            pdu.addOid(oid);
        }
        else if (src == getNextButton)
        {
            pdu = new OneGetNextPdu(context);
            pdu.addOid(oid);
        }
        else if (src == setButton)
        {
            OneSetPdu setPdu = new OneSetPdu(context);
            String value = tvalue.getText();
            AsnObject obj;
            if (Util.isNumber(value))
            {
                obj = new AsnInteger(Util.getNumber(value));
            }
            else
            {
                obj = new AsnOctets(value);
                //testSubOid(oid, (AsnOctets) obj, false);
                //testSubOid2(oid);
            }
            setPdu.addOid(oid, obj);

            pdu = setPdu;
        }
        sendRequest(pdu);
    }
    catch (Exception exc)
    {
        exc.printStackTrace();
        setErrorMessage("Exception: " + exc.getMessage());
    }
}

private void testSubOid(String oid, AsnOctets obj, boolean length_implied)
{
    long [] subOid = obj.toSubOid(length_implied);
    System.err.println("SubOid of '" + obj.toString() 
        + "' (length_implied = " + length_implied + "):");
    for (int i=0; i<subOid.length; i++)
    {
        if (i > 0)
        {
            System.err.print(".");
        }
        System.err.print(subOid[i]);
    }
    System.err.println();

    AsnObjectId asnOID = new AsnObjectId(oid);
    asnOID.add(subOid);
    System.err.println(asnOID);
}

private void testSubOid2(String oid)
{
    try
    {
        InetAddress inetA = InetAddress.getLocalHost();
        byte [] ip = inetA.getAddress();

        AsnOctets obj = new AsnOctets(ip, SnmpConstants.IPADDRESS);
        testSubOid(oid, obj, true);
    }
    catch (java.net.UnknownHostException exc)
    {
        System.err.println(exc.getMessage());
    }
}

private void sendGetRequest(String host, int port, String community, 
  String oid)
{
    if (context != null)
    {
        context.destroy();
    }
    try
    {
        context = new SnmpContext(host, port, socketType);
        context.setCommunity(community);

        pdu = new OneGetPdu(context);
        pdu.addOid(oid);
        sendRequest(pdu);
    }
    catch (java.io.IOException exc)
    {
        exc.printStackTrace();
        setErrorMessage("IOException: " + exc.getMessage());
    }
}

private void sendRequest(Pdu pdu)
{
    boolean hadError = false;

    setButton.setEnabled(false);
    getButton.setEnabled(false);
    getNextButton.setEnabled(false);
    try
    {
        if (!pduInFlight)
        {
            pduInFlight = true;
            setMessage("Sending request ..: ");

            tvalue.setText("");
            pdu.addObserver(this);
            pdu.send();
        }
        else
        {
            setErrorMessage("Pdu still in flight");
        }
    }
    catch (PduException exc)
    {
        exc.printStackTrace();
        setErrorMessage("PduException: " + exc.getMessage());
        hadError = true;
    }
    catch (java.io.IOException exc)
    {
        exc.printStackTrace();
        setErrorMessage("IOException: " + exc.getMessage());
        hadError = true;
    }

    if (hadError == true)
    {
        pduInFlight = false;
        setButton.setEnabled(true);
        getButton.setEnabled(true);
        getNextButton.setEnabled(true);
    }
}


/**
 * Implementing the Observer interface. Receiving the response from 
 * the Pdu.
 *
 * @param obs the Pdu variable
 * @param ov the varbind
 *
 * @see uk.co.westhawk.snmp.pdu.OneGetPdu
 * @see uk.co.westhawk.snmp.pdu.OneSetPdu
 * @see uk.co.westhawk.snmp.stack.varbind
 */
public void update(Observable obs, Object ov)
{
    pduInFlight = false;

    ScreenOnUpdate upd = new ScreenOnUpdate(obs, ov);
    SwingUtilities.invokeLater(upd);
}

public void setErrorMessage(String message)
{
    setMessage(message, true);
}

public void setMessage(String message)
{
    setMessage(message, false);
}

public void setMessage(String message, boolean isError)
{
    lmessage.setText(message);
    Color c = Color.white;
    if (isError)
    {
        c = Color.red;
    }
    lmessage.setBackground(c);
}

private void makeLayout(String host, String oid, int port, String com)
{
    JLabel lhost, loid, lport, lcom, lvalue;

    lhost   = new JLabel("Host: ");
    lport   = new JLabel("Port: ");
    lcom    = new JLabel("Community: ");
    loid    = new JLabel("OID: ");
    lvalue  = new JLabel("Value: ");
    lmessage = new JLabel("");
    lmessage.setOpaque(true);

    thost   = new JTextField(host);
    tport   = new JTextField(String.valueOf(port));
    tcom    = new JTextField(com);
    toid    = new JTextField(oid);
    tvalue  = new JTextField();

    setButton = new JButton ("Set");
    getButton = new JButton ("Get");
    getNextButton = new JButton ("GetNext");

    this.setLayout(new GridLayout(7, 2));

    add(lhost); add(thost);
    add(lport); add(tport);
    add(lcom);  add(tcom);
    add(loid);  add(toid);
    add(lvalue); add(tvalue);
    add(setButton); add(getButton);
    add(getNextButton); add(lmessage);

    setButton.addActionListener(this);
    getButton.addActionListener(this);
    getNextButton.addActionListener(this);
    lmessage.setBackground(Color.white);
}

public void opensetframe(String h,String obid)
{
    String propFileName = null;
 
    set_req application = new set_req(propFileName);
    application.init(h,obid);

    JFrame frame = new JFrame();
    frame.setTitle(application.getClass().getName());
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(application, BorderLayout.CENTER);

    frame.addWindowListener(new WindowAdapter()
    {
        public void windowClosing(WindowEvent e)
        {
           return;
        }
    });
    frame.setBounds(50, 50, 500, 200);
    frame.setVisible(true);

}

class ScreenOnUpdate implements Runnable 
{
    Observable obs;
    Object ov;

    public ScreenOnUpdate(Observable ob, Object obj)
    {
        obs = ob;
        ov = obj;
    }

    public void run()
    {
        setMessage("Received answer");
        if (pdu.getErrorStatus() != AsnObject.SNMP_ERR_NOERROR)
        {
            setErrorMessage(pdu.getErrorStatusString());
        }
        else
        {
            varbind var = (varbind) ov;
            if (var != null)
            {
                AsnObjectId oid = var.getOid();
                toid.setText(oid.toString());

                AsnObject obj = var.getValue();
                tvalue.setText(obj.toString());
            }
        }
        setButton.setEnabled(true);
        getButton.setEnabled(true);
        getNextButton.setEnabled(true);
    }
}

}
