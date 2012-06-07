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
public JButton hsubmit;
//for accessing mib
public set_one hopcon;
DFrame dummy;
//For selectiong the view type
  public Choice viewtype;
  public JButton viewok;
  public JLabel viewlabel;
  public JButton vobname[],vobid[],vobvalue[];
  public JButton voi[],voset[],gotonext;
  
  
  // for displaying the status of the host connection
  JLabel hstname,hstip,hstreqtime,hstreptime;
  JLabel hstname1,hstip1,hstreqtime1,hstreptime1,hststatus1;
  
  
 public void sethoststatus(String hn,String hip,String hreq,String hrep,String hst)
 {
	  if(hn!=null)
	  {
		  hstname1.setText(hn);
		  hstip1.setText(hip);
		  hstreqtime1.setText(hreq);
		  hstreptime1.setText(hrep);
		  hststatus1.setText(hst);
	  }
	  else
	  {
		  hststatus1.setText("No Hosts Connected");
	  }
  }
	
  public void showhoststatus(int i)
  {
	  if(i==0)
	  {
		  hststatus1.setVisible(true);
		  return;
	  } 
	  hstname1.setVisible(true);
	   hstip1.setVisible(true);
	   hstreqtime1.setVisible(true);
	   hstreptime1.setVisible(true);
	   hststatus1.setVisible(true);
	   
	   hstname.setVisible(true);
	   hstip.setVisible(true);
	   hstreqtime.setVisible(true);
	   hstreptime.setVisible(true);
   }	  
		  
public void hidehoststatus()
  {
	   hstname1.setVisible(false);
	   hstip1.setVisible(false);
	   hstreqtime1.setVisible(false);
	   hstreptime1.setVisible(false);
	   hststatus1.setVisible(false);
	   
	   hstname.setVisible(false);
	   hstip.setVisible(false);
	   hstreqtime.setVisible(false);
	   hstreptime.setVisible(false);
   }	  
   public void createstatusframes()
   {
	  
	   
	   hstname1=new JLabel();
	   hstip1=new JLabel();
	   hstreqtime1=new JLabel();
	   hstreptime1=new JLabel();
	   hststatus1=new JLabel();
	   
	   hstname.setBounds(10,400,65,30);
	   hstip.setBounds(10,430,65,30);
	   hstreqtime.setBounds(10,460,65,30);
	   hstreptime.setBounds(10,490,650,30);
	   
	   hstname1.setBounds(150,400,50,30);
	   hstip1.setBounds(150,430,50,30);
	   hstreqtime1.setBounds(150,460,50,30);
	   hstreptime1.setBounds(150,490,50,30);
	   hststatus1.setBounds(75,520,50,30);
	
	   
	   
	   getContentPane().add(hstname1);	   
	   getContentPane().add(hstip1);	   
	   getContentPane().add(hstreqtime1);	   
	   getContentPane().add(hstreptime1);	   
	   getContentPane().add(hststatus1);	   
   }
	 	
	   
	   
	   
	   
	   
	   
   

 public DFrame()
  {
     setSize(800,580);
     setTitle("Output Window- Logic Design");
     getContentPane().setLayout(null);

    snhost=new Choice();
    sihost=new Choice();
    stype=new Choice();
gotonext = new JButton(">>>");
    gotonext.setBounds(735,510,60,20);
gotonext.setVisible(false);
getContentPane().add(gotonext);
gotonext.addActionListener(this);
typeok = new JButton("OK");
chok = new JButton("OK");

sltype =new JLabel("Selct The Host By..");
slnhost =new JLabel("Selct The Host Name..");
slihost =new JLabel("Selct The Host Address..");

 	   hstname=new JLabel("Hostname: ");
  	   hstip=new JLabel("IP Address: ");
	   hstreqtime=new JLabel("Con.Req.Time: ");
	   hstreptime=new JLabel("Con.Rep.Time: ");
	   
		hstname.setBounds(10,400,65,30);
		hstip.setBounds(10,430,68,30);
	   hstreqtime.setBounds(10,460,85,30);
	   hstreptime.setBounds(10,490,85,30);
	   
	   
	   getContentPane().add(hstip);	   
	   getContentPane().add(hstreqtime);	   
	   getContentPane().add(hstreptime);
	   getContentPane().add(hstname);	   

	   hstname1=new JLabel("Hostname: ");
  	   hstip1=new JLabel("IP Address: ");
	   hstreqtime1=new JLabel("Con.Req.Time: ");
	   hstreptime1=new JLabel("Con.Rep.Time: ");
	   
		hstname1.setBounds(100,400,65,30);
		hstip1.setBounds(100,430,68,30);
	   hstreqtime1.setBounds(100,460,85,30);
	   hstreptime1.setBounds(100,490,85,30);
	   
	   hststatus1=new JLabel();
	   hststatus1.setBounds(75,520,150,30);
	   
	   getContentPane().add(hstip1);	   
	   getContentPane().add(hstreqtime1);	   
	   getContentPane().add(hstreptime1);
	   getContentPane().add(hstname1);	   
	   getContentPane().add(hststatus1);	   
JLabel stimg=new JLabel(new ImageIcon("stimg.jpg"));
stimg.setBounds(0,0,220,600);

	   
sltype.setBounds(30,30,150,50);
stype.setBounds(45,70,159,30);
typeok.setBounds(60,100,60,30);
 
stype.add("Name");
stype.add("IP Address");

slnhost.setBounds(30,150,150,50);
snhost.setBounds(45,190,159,30);




slihost.setBounds(30,150,150,50);
sihost.setBounds(45,190,159,30);
chok.setBounds(60,220,60,30);


 viewtype=new Choice();
 viewok=new JButton("Go");
 viewlabel =new JLabel("Select The MIB Group");
 viewtype.add("System Group");
 viewtype.add("Interface Group");
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
vobname[0].setBounds(225,120,160,30);
vobid[0].setBounds(380,120,160,30);
vobvalue[0].setBounds(535,120,160,30);
vobname[0].setBackground(Color.white);
vobid[0].setBackground(Color.white);
vobvalue[0].setBackground(Color.white);
getContentPane().add(vobname[0]);
getContentPane().add(vobid[0]);
getContentPane().add(vobvalue[0]);
for(int i=0;i<19;i++)
{
	voi[i]=new JButton(new ImageIcon("int1.jpg"));
	voset[i]=new JButton(new ImageIcon("pen.jpg"));
	voi[i].setBounds(695,130+((i+1)*20),20,20);
	voset[i].setBounds(715,130+((i+1)*20),20,20);
getContentPane().add(voi[i]);	
getContentPane().add(voset[i]);
voset[i].addActionListener(this);

//voset[i].setEnabled(false);
}
	
for(int i=1;i<20;i++)
{
vobname[i]=new JButton();
vobid[i]=new JButton();
vobvalue[i]=new JButton("prasad");

vobname[i].setBounds(225,130+(i*20),160,20);
vobid[i].setBounds(380,130+(i*20),160,20);
vobvalue[i].setBounds(535,130+(i*20),160,20);

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


slnhost.setVisible(false);
snhost.setVisible(false);
slihost.setVisible(false);
sihost.setVisible(false);
chok.setVisible(false);
typeok.addActionListener(this);


/*for adding the host*/

hname=new JLabel("Host Name:");
hadd=new JLabel("Host Address:");

thname=new JTextField(20);
thadd=new JTextField(20);

hsubmit=new JButton("Add");

hname.setBounds(20,300,80,30);
thname.setBounds(104,305,100,20);
getContentPane().add(hname);
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
JLabel img= new JLabel(new ImageIcon("back.jpg"));
img.setBounds(-20,0,850,550);
//getContentPane().add(stimg);
getContentPane().add(img);
hidehoststatus();
 
}

public void setallvisible()
{
	for(int i=1;i<20;i++)
	{
		vobname[i].setVisible(true);
		vobid[i].setVisible(true);
		vobvalue[i].setVisible(true);
		voi[i-1].setVisible(true);
		voset[i-1].setVisible(true);
		//voset[i-1].setEnabled(true);
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
		catch(UnknownHostException ex){System.out.println("Host not found");}
		}  
		else
			hostname=snhost.getSelectedItem();	
		showset.opensetframe(hostname,vobid[i+1].getText());
		
	}
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
		catch(UnknownHostException ex){System.out.println("Host not found");}
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
		catch(UnknownHostException ex){System.out.println("Host not found");}
		}
		
		/*store the host entry*/
		if(hostname!=null && hostip!=null)
		{
		hostfile h=new hostfile();
		h.Inserthost(hostname,hostip);	
		fillhosts(h);	
	}
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
		catch(UnknownHostException un){sethoststatus(null,null,null,null,"Host");showhoststatus(0);return; }
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
if(a==null){sethoststatus(null,null,null,null,"Host");showhoststatus(0);return;}

String selip=a.toString();
Date d2=new Date();
String ds1=d1.toString();
ds1=ds1.substring(11,19);
String ds2=d2.toString();
ds2=ds2.substring(11,19);
sethoststatus(selhname,selip.substring(selip.indexOf('/')+1),ds1,ds2,"Host Connected");
showhoststatus(1);
}	

if(ob==viewok)
{
	setallvisible();
	if((hopcon.makeconnection(viewtype.getSelectedIndex()))==1)
	{
		int i=1;
			
		
		
			int ststus=hopcon.getobjectvalue();
			if(ststus==0)
			{
				System.out.println("Can not possible to read from mib");
			
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
		
		
		while(i<20 && ststus!=0)
		{
			System.out.println("entered to get next\n\n\n");
			
			ststus=hopcon.getnextobject();
			if(ststus==0)
			{
				System.out.println("Can not possible to read from mib");
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
	
	}
}

if(ob==gotonext)
{
setallvisible();	
	int i=1;
	int ststus=1;		
	
		
		while(i<20 && ststus!=0)
		{
			System.out.println("entered to get next\n\n\n");
			
			ststus=hopcon.getnextobject();
			if(ststus==0)
			{
				System.out.println("Can not possible to read from mib");
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
	
	
}


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