
import java.awt.*; 
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class set_one implements Observer
{
   

    public final static String sysContact = "1.3.6.1.2.1.1.4.0";

    private String host;
    private int port;
    private String community;
    private String oid;
    private String value;
    private String socketType;
    private int version = SnmpConstants.SNMP_VERSION_2c;

    private SnmpContextFace context;
    private Choice      snmpVersion;

    private Pdu         pdu;
    private boolean     pduInFlight;
    private Util util;
    public DataInputStream in;
    public String obname,obid,obvalue;
    public boolean isrw,got,tout;

/*public set_one(String propertiesFilename)
{
    util = new Util(propertiesFilename, this.getClass().getName());
}*/



public void init (String hostname) 
{
    //AsnObject.setDebug(15);

    host = hostname;//util.getHost();
    port = 161;//util.getPort(SnmpContextBasisFace.DEFAULT_PORT);
    socketType ="Standard";// util.getSocketType();
//    oid = util.getOid(sysContact);
    community ="public";// util.getCommunity();

    pduInFlight = false;
 version = SnmpConstants.SNMP_VERSION_1;

  
}

private void sendGetNextRequest(String host, int port, String community, 
  String oid, int version)
{
    try
    {
        context = new SnmpContextPool(host, port, socketType);
        context.setCommunity(community);
        pdu = new OneGetNextPdu(context);
        pdu.addOid(oid);
        sendRequest(pdu);
        if(pdu.isTimedOut()==true)
        {
	        tout=true;
	        got=true;
        return;
    }
    }
    catch (java.io.IOException exc)
    {
        exc.printStackTrace();
       // setErrorMessage("IOException: " + exc.getMessage());
    }
}


private void sendGetRequest(String host, int port, String community, 
  String oid, int version)
{
    try
    {
        context = new SnmpContextPool(host, port, socketType);
        context.setCommunity(community);
        pdu = new OneGetPdu(context);
        pdu.addOid(oid);
        sendRequest(pdu);
        if(pdu.isTimedOut()==true)
        {
	        tout=true;
	        got=true;
        return;
    }
    }
    catch (java.io.IOException exc)
    {
        exc.printStackTrace();
       // setErrorMessage("IOException: " + exc.getMessage());
    }
}

private void sendRequest(Pdu pdu)
{
    boolean hadError = false;

    try
    {
        if (!pduInFlight)
        {
            pduInFlight = true;
      

        //    tvalue.setText("");
            pdu.addObserver(this);
            pdu.send();
            if(tout==true)
            {
	           System.out.println("Prasad1");
            return;
        }
        }
       
    }
    catch (PduException exc)
    {
        exc.printStackTrace();
    //    setErrorMessage("PduException: " + exc.getMessage());
        hadError = true;
    }
    catch (java.io.IOException exc)
    {
        exc.printStackTrace();
      //  setErrorMessage("IOException: " + exc.getMessage());
        hadError = true;
    }

    if (hadError == true)
    {
        pduInFlight = false;
      
    }
}

public void update(Observable obs, Object ov)
{
    pduInFlight = false;

    // TODO: invokeLater
//    setMessage("Received answer");
tout=false;
    if (pdu.getErrorStatus() == AsnObject.SNMP_ERR_NOERROR)
    {
        varbind var = (varbind) ov;
        if (var != null)
        {
            AsnObjectId oid = var.getOid();
            //toid.setText(oid.toString());
obid=oid.toString();

            AsnObject obj = var.getValue();
//            tvalue.setText(obj.toString());
obvalue=obj.toString();
 //System.out.println(obid);
   // System.out.println(obvalue);
   got=true;
   //System.out.println("obid=" + obid);
    //System.out.println("obvalue"+obvalue);
        }
    }
  else
  {
	  tout=true;
	  got=true;
	  System.out.println("prasad");
	  
//	 getobjectvalue();
	 	//System.exit(1);

  }
}


public int makeconnection(int i)
{
	String fname="sysgroup.txt";
	switch(i)
	{
		
		case 1: fname="intgroup.txt"; break;
		case 2: fname="ipgroup.txt"; break;
		case 3: fname="icmpgroup.txt"; break;
		case 4: fname="tcpgroup.txt"; break;
		case 5: fname="udpgroup.txt"; break;
		case 6: fname="snmpgroup.txt"; break;
	}
	System.out.println(fname);
	

try
{
in=new DataInputStream(new FileInputStream("unms\"+fname));
}
catch(FileNotFoundException e){return 0;}
return 1;
}


public int getobjectvalue()
{
   String s="";
char ch;
   try
   {
	   
		   	s=in.readLine();
      obname=s;
      	s="";
         	   	s=in.readLine();
      oid=s;
      
      s="";
         	   	s=in.readLine();
      if(s!=null)
      {
         	   	if(s.equals("false"))
        isrw=false;
      else
    if(s.equals("true"))
        isrw=true;
    }
        got=false;
        if(oid==null) return 0;
        tout=false;
    sendGetRequest(host,port,community,oid,version);
    
    if(tout==true)
    return 1;

    
   while(got==false);
   if(tout==true)
    return 1;
   
   System.out.println("obname=" + obname);
   System.out.println("obid=" + obid);
   System.out.println("obvalue="+obvalue);
   

  }
  catch(EOFException e){return 0;}
  catch(IOException e){return 0;}
  
   
  return 1;
}

    
  public int getnextobject()   
  {
	  String oldid1=obid,oldid2=obid;
	  if(tout==true || obid==null)
	  {
	  int status=getobjectvalue();
	  System.out.println("retry get");
	  return status;
     }
     tout=got=false;
	  sendGetNextRequest(host,port,community,obid,version);
	  while(got==false);
  
   
 
   System.out.println("obid=" + obid);
   System.out.println("obvalue="+obvalue);
	  
	  /*if(tout==true)
	  {
	  int status=getobjectvalue();
	  System.out.println("retry get");
	  return status;
     }*/
     int ind1=oldid1.indexOf('.',14);
     System.out.println("Index==============================="+oldid1.substring(0,ind1));
     
	  oldid1=oldid1.substring(0,ind1);
	  
	  int ind2=oldid2.indexOf('.',12);
	  oldid2=oldid2.substring(0,ind2);
	  System.out.println(oldid1);
	  System.out.println(oldid2);
	 // System.exit(1);
	  
	  if(!oldid2.equals(obid.substring(0,ind2)))
	  {
		  
		  return 0;
	  }
	  if(oldid1.equals(obid.substring(0,ind1)))
	  {
		  System.out.println("obid.substring(0,ind1)="+obid.substring(0,ind1-1));
		  return 1;
	  }
	  String s="";
	  char ch;
   	try
   {
	   
		   	s=in.readLine();
      obname=s;
      	s="";
         	   	s=in.readLine();
         	   	System.out.println("S="+s);
         	   	
      oid=s;
      
      s="";
         	   	s=in.readLine();
      if(s!=null)
      {
         	   	if(s.equals("false"))
        isrw=false;
      else
    if(s.equals("true"))
        isrw=true;
    }
     }
  catch(EOFException e){return 0;}
  catch(IOException e){return 0;}
    if(oid==null)
        return 0;
    System.out.println("oid="+oid.substring(0,ind1));
    System.out.println("oldid="+oldid1);
    
    if(oldid1.equals(oid.substring(0,ind1))==false)
    {
     
     System.out.println("Peasad");
     return 1;
	}
    return 0;
     
  }


}
