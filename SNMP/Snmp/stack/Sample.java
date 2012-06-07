import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.*;



class DFrame extends JFrame implements ActionListener
{
  public Choice snhost,stype,sihost;
public JLabel sltype,slnhost,slihost;
public JButton typeok,chok;

public JLabel hname,hadd;
public JTextField thname,thadd;
public JButton hsubmit,ifclose;
public String hostconnected;
public JButton vbservice,vbinterface,vbprintq;
//for accessing mib
public set_one hopcon;
DFrame dummy;
//For selectiong the view type
  public Choice viewtype;
  public JButton viewok;
  public JLabel viewlabel,lb1,lb2;
  public JButton vobname[],vobid[],vobvalue[];
  public JButton voi[],voset[],gotonext,backst;
  
  
  // for displaying the status of the host connection
  JLabel hstname,hstip,hstreqtime,hstreptime;
  JLabel hstname1,hstip1,hstreqtime1,hstreptime1;
  JTextField hststatus1;
  JTextField ifob[];
  String ifreq[],ifrep[],ifdesc[];
  JDialog ifobject;
  JTextArea ifobta;
  
  //menus declaration
  JMenu optionsmenu,viewmenu;
  JMenuBar menubar;
  JMenuItem maddhost,mnew,mexit,mvservice,mvuser,mvprocess,mvinterface,mvprint,mvprograms;
  //menus declaration end
  
  //to show and hide the add host area
  public void setaddhoststatus(boolean st)
  {
	  lb1.setVisible(st);
	  hname.setVisible(st);
	  hadd.setVisible(st);
	  thname.setVisible(st);
	  thadd.setVisible(st);
	  hsubmit.setVisible(st);
  }
  public void shownewwindow()
  {
	  
	  setaddhoststatus(false);  
	  slnhost.setVisible(false);
	  slihost.setVisible(false);
	  snhost.setVisible(false);
	  sihost.setVisible(false);
	  chok.setVisible(false);
	  viewtype.setVisible(false);
	  viewok.setVisible(false);
	  viewlabel.setVisible(false);
	  backst.setVisible(false);
	  hidehoststatus();
	  for(int i=0;i<20;i++)
	  {
		  vobname[i].setVisible(false);
		  vobid[i].setVisible(false);
		  vobvalue[i].setVisible(false);
		  if(i<19)
		  {
			  voi[i].setVisible(false);
			  voset[i].setVisible(false);
		  }
	  }
  }
  
  
  public void showselectgroup() 
  {

	  viewtype.setVisible(true);
	  viewok.setVisible(true);
	  viewlabel.setVisible(true);
	  for(int i=0;i<20;i++)
	  {
		  vobname[i].setVisible(false);
		  vobid[i].setVisible(false);
		  vobvalue[i].setVisible(false);
		  if(i<19)
		  {
			  voi[i].setVisible(false);
			  voset[i].setVisible(false);
		  }
	  }
  }
  
  public void addmenubar()
  {
  menubar=new JMenuBar();
	getRootPane().setMenuBar(menubar);
	optionsmenu=new JMenu("Options");
	menubar.add(optionsmenu);
	
	JMenu dummy=new JMenu("                                       ");
	menubar.add(dummy);
	dummy.setEnabled(false);
	
	viewmenu=new JMenu("View");
	menubar.add(viewmenu);
	viewmenu.setEnabled(false);
	
	mnew=new JMenuItem("Reset Application");
	mnew.addActionListener(this);
	optionsmenu.add(mnew);
	
	maddhost=new JMenuItem("Add Host");
	maddhost.addActionListener(this);
	optionsmenu.add(maddhost);
	
	mexit=new JMenuItem("Close");
	mexit.addActionListener(this);
	optionsmenu.add(mexit);
	
	mvinterface=new JMenuItem("Network Interfaces");
	mvinterface.addActionListener(this);
	viewmenu.add(mvinterface);
	
	mvservice=new JMenuItem("Services");
	mvservice.addActionListener(this);
	viewmenu.add(mvservice);
	
	mvprocess=new JMenuItem("Processes");
	mvprocess.addActionListener(this);
	viewmenu.add(mvprocess);

	mvprograms=new JMenuItem("Application Softwares");
	mvprograms.addActionListener(this);
	viewmenu.add(mvprograms);
	
	mvuser=new JMenuItem("Users");
	mvuser.addActionListener(this);
	viewmenu.add(mvuser);
	
	mvprint=new JMenuItem("Print Queue");
	mvprint.addActionListener(this);
	viewmenu.add(mvprint);
	
}

  
  
  public boolean checkhostsnmp(String hname)
  {
	  set_one chsample=new set_one();
	  chsample.init(hname);
	  chsample.got=false;
        String oid="1.3.6.1.2.1.1.1.0";
         chsample.sendGetRequest(chsample.host,chsample.port,chsample.community,oid,chsample.version);
    
    if(chsample.tout==true)
    return false;

    
   while(chsample.got==false);
   if(chsample.tout==true)
    return false;
    return true;
}
  
  
 public void setmainstatus(String hst)
 {
	  hststatus1.setText("    Status:  "+hst);
	  
 }
  
 public void sethoststatus(String hn,String hip,String hreq,String hrep,String hst)
 {
		  hstname1.setText(hn);
		  hstip1.setText(hip);
		  hstreqtime1.setText(hreq);
		  hstreptime1.setText(hrep);
		  hostconnected=hn;
		  hstip.setForeground(Color.white);
		  hstreqtime.setForeground(Color.white);
		  hstreptime.setForeground(Color.white);
		  hstname.setForeground(Color.white);
		  
  }
	
  public void showhoststatus()
  {
	 
	  hstname1.setVisible(true);
	   hstip1.setVisible(true);
	   hstreqtime1.setVisible(true);
	   hstreptime1.setVisible(true);
	   
	   
	   hstname.setVisible(true);
	   hstip.setVisible(true);
	   hstreqtime.setVisible(true);
	   hstreptime.setVisible(true);
	   /*vbservice.setVisible(true);
	   vbinterface.setVisible(true);
	   vbprintq.setVisible(true);*/
	   backst.setVisible(true);
   }	  
		  
public void hidehoststatus()
  {
	   hstname1.setVisible(false);
	   hstip1.setVisible(false);
	   hstreqtime1.setVisible(false);
	   hstreptime1.setVisible(false);
	   /*vbservice.setVisible(false);
	   vbinterface.setVisible(false);
	   vbprintq.setVisible(false);*/
	   hstname.setVisible(false);
	   hstip.setVisible(false);
	   hstreqtime.setVisible(false);
	   hstreptime.setVisible(false);
	   backst.setVisible(false);
   }	  
     
	   
	   
   

 public DFrame()
  {
     setSize(800,580);
     setTitle("Unified Network Management");
     getContentPane().setLayout(null);
     addmenubar();

    snhost=new Choice();
    sihost=new Choice();
    stype=new Choice();
gotonext = new JButton(">>>");
    gotonext.setBounds(735,490,60,20);
gotonext.setVisible(false);
getContentPane().add(gotonext);
gotonext.addActionListener(this);
typeok = new JButton("OK");
chok = new JButton("OK");
hostconnected=null;
sltype =new JLabel("Selct The Host By..");
slnhost =new JLabel("Selct The Host Name..");
slihost =new JLabel("Selct The Host Address..");
backst=new JButton(new ImageIcon("mibgroups/back.jpg"));
backst.setBounds(5,390,215,150);

 	   hstname=new JLabel("Hostname: ");
  	   hstip=new JLabel("IP Address: ");
	   hstreqtime=new JLabel("Con.Req.Time: ");
	   hstreptime=new JLabel("Con.Rep.Time: ");
	   
		hstname.setBounds(10,395,65,30);
		hstip.setBounds(10,425,68,30);
	   hstreqtime.setBounds(10,455,85,30);
	   hstreptime.setBounds(10,485,85,30);
	   
	   
	   getContentPane().add(hstip);	   
	   getContentPane().add(hstreqtime);	   
	   getContentPane().add(hstreptime);
	   getContentPane().add(hstname);	   

	   hstname1=new JLabel("Hostname: ");
  	   hstip1=new JLabel("IP Address: ");
	   hstreqtime1=new JLabel("Con.Req.Time: ");
	   hstreptime1=new JLabel("Con.Rep.Time: ");
	   
		hstname1.setBounds(100,395,85,30);
		hstip1.setBounds(100,425,68,30);
	   hstreqtime1.setBounds(100,455,85,30);
	   hstreptime1.setBounds(100,485,85,30);

	   	hstname1.setForeground(Color.blue);
		hstip1.setForeground(Color.blue);
	   hstreqtime1.setForeground(Color.blue);
	   hstreptime1.setForeground(Color.blue);
	   
	   hststatus1=new JTextField("No Hosts Connected");
	   hststatus1.setBounds(0,510,800,20);
	   hststatus1.setEditable(false);
	   hststatus1.setHorizontalAlignment(JTextField.CENTER);
	   
	   getContentPane().add(hstip1);	   
	   getContentPane().add(hstreqtime1);	   
	   getContentPane().add(hstreptime1);
	   getContentPane().add(hstname1);	   
	   getContentPane().add(hststatus1);	   
	   
	   getContentPane().add(backst);
	   backst.addActionListener(this);
	   
	   ifreq=new String[19];
	   ifrep=new String[19];
	   ifdesc=new String[19];
	   
	   //for the object information
	   
	   ifobject=new JDialog(this,"Object information");
	   ifobject.setBounds(250,150,400,250);
	   ifobject.getContentPane().setLayout(null);
	    ifobject.getContentPane().setBackground(Color.white);
	   //ifobject.getContentPane().setBackground(Color.white);
	Color c=new Color(206,255,255);
	   ifob=new JTextField[11];
	   for(int ifc=0;ifc<6;ifc++)
	   {
		   ifob[ifc]=new JTextField("---");
		   if(ifc==5)
		   ifob[ifc].setBounds(0,(ifc*25),150,50);
		   else
		   ifob[ifc].setBounds(0,(ifc*25),150,25);
		   
		   ifobject.getContentPane().add(ifob[ifc]);
		   ifob[ifc].setBackground(c);
		    ifob[ifc].setEditable(false);
		    ifob[ifc].setHorizontalAlignment(JTextField.CENTER);
		    ifob[ifc].setFont(new Font("BookMan Old Style",Font.BOLD,14));
		    ifob[ifc].setForeground(Color.blue);
	   }
	    
	   for(int ifc=6;ifc<11;ifc++)
	   {
		   ifob[ifc]=new JTextField("---");
		   ifob[ifc].setBounds(150,(ifc-6)*25,250,25);
		   ifobject.getContentPane().add(ifob[ifc]);
		      ifob[ifc].setBackground(c);
		      ifob[ifc].setEditable(false);
		       ifob[ifc].setFont(new Font("Times New Roman",Font.PLAIN,14));
		    ifob[ifc].setForeground(Color.magenta);
	   }
	   ifobta=new JTextArea(null,null,4,90);
	   	   ifobta.setBounds(151,1+(5*25),250,48);
	   	   ifobta.setLineWrap(true);
	   	    ifobta.setWrapStyleWord(true);
	   	    ifobta.setEditable(false);
	   	    ifobta.setFont(new Font("Times New Roman",Font.PLAIN,16));
		    ifobta.setForeground(Color.magenta);
		     ifobta.setBackground(c);
		   ifobject.getContentPane().add(ifobta);
	   
	ifclose=new JButton("Close");
	ifclose.setBounds(140,180,100,30);
		   ifobject.getContentPane().add(ifclose);
		   ifclose.addActionListener(this);
	       ifobject.hide();	
		ifob[0].setText("Object Name");   
		ifob[1].setText("Object ID");
		ifob[2].setText("Object Value");
		ifob[3].setText("Request Time");
		ifob[4].setText("Response Time");
		ifob[5].setText("Description");
		 ifobta.setText("prasad");
	  // getContentPane().add(ifobject);
	   
	   
	   //------------------------------------------------------------
JLabel stimg=new JLabel(new ImageIcon("stimg.jpg"));
stimg.setBounds(0,0,220,600);

	   
sltype.setBounds(30,30,150,50);
stype.setBounds(45,70,120,30);
typeok.setBounds(60,100,60,30);
 
stype.add("Name");
stype.add("IP Address");

slnhost.setBounds(30,150,150,50);
snhost.setBounds(45,190,159,30);


//---------------------------

/*vbservice=new JButton("View Services");
vbinterface=new JButton("View Inteface Properties");
vbprintq=new JButton("View Print Queue Status");

vbservice.setBounds(600,30,200,20);
vbinterface.setBounds(600,50,200,20);
vbprintq.setBounds(600,70,200,20);*/


slihost.setBounds(30,150,150,50);
sihost.setBounds(45,190,159,30);
chok.setBounds(60,220,60,30);


 viewtype=new Choice();
 viewok=new JButton("Go");
 viewlabel =new JLabel("Select The MIB Group");
 viewtype.add("System Group");
// viewtype.add("Interface Group");
 viewtype.add("IP Group");
 viewtype.add("ICMP Group");
 viewtype.add("TCP Group");
 viewtype.add("UDP Group");
viewtype.add("SNMP Group");
 
//-------------------
vobname=new JButton[20];
vobid=new JButton[20];
vobvalue=new JButton[20];
//vobname[]=new JLabel[15];
vobname[0]=new JButton("Object Name");
vobid[0]=new JButton("Object ID");
vobvalue[0]=new JButton("Object Value");
voi=new JButton[19];
voset=new JButton[19];
vobname[0].setBounds(225,100,160,30);
vobid[0].setBounds(380,100,160,30);
vobvalue[0].setBounds(535,100,160,30);
vobname[0].setBackground(Color.white);
vobid[0].setBackground(Color.white);
vobvalue[0].setBackground(Color.white);
getContentPane().add(vobname[0]);
getContentPane().add(vobid[0]);
getContentPane().add(vobvalue[0]);
for(int i=0;i<19;i++)
{
	voi[i]=new JButton(new ImageIcon("mibgroups/int1.jpg"));
	voset[i]=new JButton(new ImageIcon("mibgroups/pen.jpg"));
	voi[i].setBounds(695,110+((i+1)*20),20,20);
	voset[i].setBounds(715,110+((i+1)*20),20,20);
getContentPane().add(voi[i]);	
getContentPane().add(voset[i]);
voset[i].addActionListener(this);
voi[i].addActionListener(this);

//voset[i].setEnabled(false);
}
	
for(int i=1;i<20;i++)
{
vobname[i]=new JButton();
vobid[i]=new JButton();
vobvalue[i]=new JButton();

vobname[i].setBounds(225,110+(i*20),160,20);
vobid[i].setBounds(380,110+(i*20),160,20);
vobvalue[i].setBounds(535,110+(i*20),160,20);

vobname[i].setBackground(Color.lightGray);
vobid[i].setBackground(Color.lightGray);
vobvalue[i].setBackground(Color.lightGray);

vobname[i].setForeground(Color.white);
vobid[i].setForeground(Color.blue);
vobvalue[i].setForeground(Color.white);

/*vobname[i].setEnabled(false);
vobid[i].setEnabled(false);
vobvalue[i].setEnabled(false);*/

getContentPane().add(vobname[i]);
getContentPane().add(vobid[i]);
getContentPane().add(vobvalue[i]);

}


viewlabel.setBounds(300,30,150,30);
viewtype.setBounds(425,35,150,30);
viewok.setBounds(440,60,50,30);
getContentPane().add(viewlabel);
getContentPane().add(viewtype);
getContentPane().add(viewok);


getContentPane().add(slnhost);
getContentPane().add(snhost);

getContentPane().add(slihost);
getContentPane().add(sihost);

getContentPane().add(typeok);
getContentPane().add(chok);
getContentPane().add(stype);
getContentPane().add(sltype);

/*getContentPane().add(vbservice);
getContentPane().add(vbinterface);
getContentPane().add(vbprintq);

vbservice.addActionListener(this);
vbinterface.addActionListener(this);
vbprintq.addActionListener(this);*/

slnhost.setVisible(false);
snhost.setVisible(false);
slihost.setVisible(false);
sihost.setVisible(false);
chok.setVisible(false);
typeok.addActionListener(this);


/*for adding the host*/
lb1 =new JLabel("Add Host..");
hname=new JLabel("Host Name:");
hadd=new JLabel("Host Address:");

thname=new JTextField(20);
thadd=new JTextField(20);

hsubmit=new JButton("Add");
lb1.setBounds(15,280,80,30);
hname.setBounds(20,300,80,30);
thname.setBounds(104,305,100,20);
getContentPane().add(hname);
getContentPane().add(lb1);
getContentPane().add(thname);


hadd.setBounds(20,330,80,30);
thadd.setBounds(104,335,100,20);
getContentPane().add(hadd);
getContentPane().add(thadd);

hsubmit.setBounds(90,370,60,30);
getContentPane().add(hsubmit);

hsubmit.addActionListener(this);
viewok.addActionListener(this);
chok.addActionListener(this);
//addMouseListener(this);
JLabel img= new JLabel(new ImageIcon("mibgroups/back.jpg"));
img.setBounds(-20,0,850,550);
//getContentPane().add(stimg);
getContentPane().add(img);
hidehoststatus();
shownewwindow();
}

public void setallvisible()
{
	for(int i=0;i<20;i++)
	{
		vobname[i].setVisible(true);
		vobid[i].setVisible(true);
		vobvalue[i].setVisible(true);
		if(i<19)
		{
		voi[i].setVisible(true);
		voset[i].setVisible(true);
		voset[i].setEnabled(false);
		voset[i].setEnabled(false);
}
	}
	gotonext.setVisible(false);
}
		

public void fillhosts(hostfile h)
{
	int count=snhost.getItemCount();
	while(snhost.getItemCount()>0)
		snhost.remove(snhost.getItem(0));
	count=sihost.getItemCount();
	while(sihost.getItemCount()>0)
		sihost.remove(sihost.getItem(0));
		int m=h.readhost();
		for(int i=0;i<=m;i++)
			sihost.add(h.hip[i]);
		for(int i=0;i<=m;i++)
			snhost.add(h.hname[i]);
}





 public void actionPerformed(ActionEvent e)
{
   Object ob=e.getSource();
   
   for(int i=0;i<19;i++)
   {
	    set_req showset=new set_req();
	   if(ob==voset[i])
	   {
		   String hostname=null,hostip=null;
		if(stype.getSelectedItem()=="IP Address")
		{	
			try
		{
			InetAddress iadd=InetAddress.getByName(sihost.getSelectedItem());
			hostname=iadd.getHostName();	 
		}
		catch(UnknownHostException ex){setmainstatus("Host not found");return;}
		}  
		else
			hostname=snhost.getSelectedItem();	
		showset.opensetframe(hostname,vobid[i+1].getText());
		
	}
}
   
   
if(ob==mnew)
{
	shownewwindow();
	
}

if(ob==maddhost)
{
	setaddhoststatus(true);
	
}

if(ob==mexit)
{
	System.exit(0);
	
}

if(ob==mvservice)
{
	testService t=new testService();
	t.initialize(hostconnected);
}

if(ob==mvprocess)
{
	testprocess t=new testprocess();
	t.initialize(hostconnected);
}

if(ob==mvuser)
{
	testuser t=new testuser();
	t.initialize(hostconnected);
}

if(ob==mvprograms)
{
	testprograms t=new testprograms();
	t.initialize(hostconnected);
}

if(ob==mvinterface)
{
	testInterface t=new testInterface();
	t.initialize(hostconnected);
}

if(ob==mvprint)
{
	testPrintQ t=new testPrintQ();
	t.initialize(hostconnected);
}
/*if(ob==vbservice)
{
	//testService t=new testService();
	testuser t=new testuser();
	t.initialize(hostconnected);
}*/


if(ob==backst)
{
	hidehoststatus();
	showhoststatus();
}

/*if(ob==vbprintq)
{
	testPrintQ t=new testPrintQ();
	t.initialize(hostconnected);
}

if(ob==vbinterface)
{
	testInterface t=new testInterface();
	t.initialize(hostconnected);
}*/

for(int i=0;i<19;i++)
{
	if(ob==voi[i])
	{
		ifob[6].setText(vobname[i+1].getText());
		ifob[7].setText(vobid[i+1].getText());
		ifob[8].setText(vobvalue[i+1].getText());
		ifob[9].setText(ifreq[i]);
		ifob[10].setText(ifrep[i]);
		ifobta.setText(ifdesc[i]);
		ifobject.show();
	}
}
if(ob==ifclose)
{
	ifobject.hide();
}
	    
  
	if(ob==typeok)
	{
		
		fillhosts(new hostfile());
 		if(stype.getSelectedItem()=="Name")
		{
//Graphics g=getContentPane().getGraphics();
//g.drawLine(300,300,400,400);

						//System.out.println("prasad");
			slnhost.setVisible(true);
			snhost.setVisible(true);
			slihost.setVisible(false);
			sihost.setVisible(false);

		}
		else
		{
			slihost.setVisible(true);
			sihost.setVisible(true);
			slnhost.setVisible(false);
			snhost.setVisible(false);

		}
		chok.setVisible(true);
	}
/*when add host button is clicked*/

	if(ob==hsubmit)
	{ 
		String hostname=null,hostip=null;
		
		  if((thname.getText()).length()>=1)
		{
		try
		{
			InetAddress iadd=InetAddress.getByName(thname.getText());
			hostname=thname.getText();
			byte[] ipadd=iadd.getAddress();
			String s="";
			for(int i=0;i<ipadd.length;i++)
			{
				s=s + ( ipadd[i] < 0 ? ipadd[i]+256 : ipadd[i]);
				if(i<ipadd.length-1)
				s= s + ".";
			}		
			hostip=s;	 
			thadd.setText(s);//iadd.toString());
		}
		catch(UnknownHostException ex){setmainstatus("Host not found");return;}
		}
		else 
		if((thadd.getText()).length()>=1)
		{	
			try
		{
			InetAddress iadd=InetAddress.getByName(thadd.getText());
			hostip=thadd.getText();
			hostname=iadd.getHostName();	 
			
			thname.setText(hostname);//iadd.toString());
		}
		catch(UnknownHostException ex){setmainstatus("Host not found");return;}
		}
		
		/*store the host entry*/
		if(hostname!=null && hostip!=null)
		{
		hostfile h=new hostfile();
		h.Inserthost(hostname,hostip);	
		fillhosts(h);	
	}
	setaddhoststatus(false);
	}
	

	
	
	//when the host was selected.
if(ob==chok)
{
	Date d1=new Date();
	hopcon=new set_one();
	String selhname=null;
	if(stype.getSelectedItem()=="Name")
	    selhname=snhost.getSelectedItem();
	else
	{
		InetAddress iadd=null;
		try
		{
			iadd=InetAddress.getByName(sihost.getSelectedItem());
		}
		catch(UnknownHostException un){setmainstatus("Not connected");return; }
		selhname=iadd.getHostName();
	}
	hopcon.init(selhname);
	/*if((hopcon.makeconnection())==1)
	{	//System.out.println("Accessing......"); 
	if((hopcon.getobjectvalue())==0)
	{
		System.out.println("Can not possible to read from mib");
	}
}*/
InetAddress a=null;
try
  {
	   a=InetAddress.getByName(selhname);
  
}catch(UnknownHostException sel){}
if(a==null){setmainstatus("Not connected");return;}

String selip=a.toString();

String ds1=d1.toString();
ds1=ds1.substring(11,19);
//check for the snmp service
boolean snmpok=checkhostsnmp(selhname);
Date d2=new Date();
String ds2=d2.toString();
ds2=ds2.substring(11,19);
if(snmpok==false)
{
	JOptionPane.showMessageDialog(chok,"Host cannot Be Manageble! Check SNMP Service in the Host","Snmp Verifier",JOptionPane.WARNING_MESSAGE);
	hidehoststatus();
	return;
}
sethoststatus(selhname,selip.substring(selip.indexOf('/')+1),ds1,ds2,"Host Connected");
showhoststatus();
setmainstatus("Host connected");
viewmenu.setEnabled(true);
showselectgroup();
}	

if(ob==viewok)
{
	setallvisible();
	if((hopcon.makeconnection(viewtype.getSelectedIndex()))==1)
	{
		int i=1;
			int ststus=1;
			
			while(i<20 && ststus!=0)
			{
			if(hopcon.getnext==false)
			{
				Date dreq=new Date();
				String dsreq=dreq.toString();
				ifreq[i-1]=dsreq.substring(11,19);
							
		 					ststus=hopcon.getobjectvalue();
		 		Date drep=new Date();
				String dsrep=drep.toString();
				ifrep[i-1]=dsrep.substring(11,19);
			if(ststus==0)
			{
				setmainstatus("Finished Reading from the MIB Group");
			
			}
			else
				if(hopcon.tout!=true)
				{
					vobname[i].setText(hopcon.obname);
					vobid[i].setText(hopcon.obid);
					vobvalue[i].setText(hopcon.obvalue);
					ifdesc[i-1]=hopcon.obdesc;
					if(hopcon.isrw==false)
						 voset[i-1].setEnabled(false);
					if(hopcon.isrw==true)
						 voset[i-1].setEnabled(true);
					
					/*System.out.println("obname="+ vobname[i].getText());
					System.out.println("obid="+vobid[i].getText());
					System.out.println("obvalue="+vobvalue[i].getText());
					System.out.println("ob req="+ifreq[i-1]);
					System.out.println("ob rep="+ifreq[i-1]);
					System.out.println("ob description="+ifdesc[i-1]);*/
					i++;
				}			
			}	
			else
			{
			  ststus=hopcon.getnextobject();
			if(ststus==0)
			{
				setmainstatus("Finished Reading from the MIB Group");
			}
			else
				if(hopcon.tout!=true)
				{
					vobname[i].setText(hopcon.obname);
					vobid[i].setText(hopcon.obid);
					vobvalue[i].setText(hopcon.obvalue);
					if(hopcon.isrw==false)
						 voset[i-1].setEnabled(false);
					if(hopcon.isrw==true)
						 voset[i-1].setEnabled(true);
					i++;
				}			
			}
				
		}	
		
		if(i<19)
		{
			for(int z=i;z<20;z++)
			{
				vobname[z].setVisible(false);
				vobid[z].setVisible(false);
				vobvalue[z].setVisible(false);
				voi[z-1].setVisible(false);
				voset[z-1].setVisible(false);
			}
			gotonext.setVisible(false);
		}
		else
		{
			gotonext.setVisible(true);
		}
	}
}	
		
if(ob==gotonext)
{
setallvisible();	
	

	int i=1;
			int ststus=1;
			
			while(i<20 && ststus!=0)
			{
			if(hopcon.getnext==false)
			{
		 		ststus=hopcon.getobjectvalue();//System.out.println("Sample-------getobj");
			if(ststus==0)
			{
				setmainstatus("Finished Reading from the MIB Group");
			
			}
			else
				if(hopcon.tout!=true)
				{
					vobname[i].setText(hopcon.obname);
					vobid[i].setText(hopcon.obid);
					vobvalue[i].setText(hopcon.obvalue);
					if(hopcon.isrw==false)
						 voset[i-1].setEnabled(false);
					if(hopcon.isrw==true)
						 voset[i-1].setEnabled(true);
					i++;
					System.out.println("obname="+vobname[i].getText());
					System.out.println("obid="+vobid[i].getText());
					System.out.println("obvalue="+vobvalue[i].getText());
					System.out.println("ob req="+ifreq[i-1]);
					System.out.println("ob rep="+ifreq[i-1]);
					System.out.println("ob description="+ifdesc[i-1]);
				}			
			}	
			else
			{
				//System.out.println("Sample-------getobj");
			  ststus=hopcon.getnextobject();
			if(ststus==0)
			{
				setmainstatus("Finished Reading from the MIB Group");
			}
			else
				if(hopcon.tout!=true)
				{
					vobname[i].setText(hopcon.obname);
					vobid[i].setText(hopcon.obid);
					vobvalue[i].setText(hopcon.obvalue);
					if(hopcon.isrw==false)
						 voset[i-1].setEnabled(false);
					if(hopcon.isrw==true)
						 voset[i-1].setEnabled(true);
					i++;
				}			
			}
				
		}	
		
		if(i<19)
		{
			for(int z=i;z<20;z++)
			{
				vobname[z].setVisible(false);
				vobid[z].setVisible(false);
				vobvalue[z].setVisible(false);
				voi[z-1].setVisible(false);
				voset[z-1].setVisible(false);
			}
			gotonext.setVisible(false);
		}
		else
		{
			gotonext.setVisible(true);
		}
	}






/*








int i=1;
	int ststus=1;		
	
		
		while(i<20 && ststus!=0 && hopcon.getnext==true)
		{
			System.out.println("entered to get next\n\n\n");
			
			ststus=hopcon.getnextobject();
			if(ststus==0)
			{
				setmainstatus("Finished Reading from the MIB Group");
				break;
			}
			else
				if(hopcon.tout!=true)
				{
					vobname[i].setText(hopcon.obname);
					vobid[i].setText(hopcon.obid);
					vobvalue[i].setText(hopcon.obvalue);
					if(hopcon.isrw==false)
						 voset[i-1].setEnabled(false);
					if(hopcon.isrw==true)
						 voset[i-1].setEnabled(true);
					i++;
				}
		}
		
		
		
		if(i<19)
		{
			for(int z=i;z<20;z++)
			{
				vobname[z].setVisible(false);
				vobid[z].setVisible(false);
				vobvalue[z].setVisible(false);
				voi[z-1].setVisible(false);
				voset[z-1].setVisible(false);
			}
			gotonext.setVisible(false);
		}
		else
		{
			gotonext.setVisible(true);
		}
	
	
}*/


}
}




public class Sample
{
 public static void main(String s[])
{
  DFrame df=new DFrame();
  df.addWindowListener(new WindowAdapter()
    {
        public void windowClosing(WindowEvent e)
        {
            System.exit(0);
        }
    });

	      df.show();
}
}