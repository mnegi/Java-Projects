import java.io.*;
class hostfile
{
public String hname[],hip[];
public int readhost()
{
	DataInputStream in=null;
	try
	{
			 in = new DataInputStream(new  FileInputStream("filelist.txt"));	
	}
	catch(FileNotFoundException exi){System.out.println("FiLE not found");}
	hname=new String[100];
	hip=new String[100];
	int i=-1;
	while(true)
	{	
		char ch;
		String s="";
		try
		{
			
			while((ch=in.readChar())!='\n')
		   	s=s+ch;
		   	i++;
			hname[i]=s;
			
			s="";
			while((ch=in.readChar())!='\n')
		   	s=s+ch;
			hip[i]=s;
			//System.out.print(hname[i]+"\t");
			//System.out.println(hip[i]);
		}
		catch(EOFException e2)
		{
			try
			{
				in.close();
				 return i;
			}
			catch(IOException e3){}
		}
		catch(IOException e1)
		{	
			try
			{
				in.close();
				 return i;
			}
			catch(IOException e4){}
		}
		
	}
	
}
	

  

public void Inserthost(String hostname,String hostip)	
{
	System.out.println(hostname);
	try
	{
		DataOutputStream out = new DataOutputStream(new  FileOutputStream("filelist.txt",true));	
		out.writeChars(hostname);
            out.writeChar('\n');
           	out.writeChars(hostip);
            out.writeChar('\n');
            out.close();
            int i=readhost();
            System.out.print("total="+i);
	}
	catch(IOException ex){}
	//catch(FileNotFoundException exi){System.out.println("FiLE not found");}
	
}
}