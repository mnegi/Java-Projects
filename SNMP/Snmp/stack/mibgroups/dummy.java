public int getobjectvalue()
{
   String s="";
char ch;
   try
   {
	   
  	/*while((ch=in.readLine())!='\n')
		   	s=s+ch;*/
		   	s=in.readLine();
      obname=s;
      	s="";
	/*while((ch=in.readChar())!='\n')
         s=s+ch;*/
         	   	s=in.readLine();
      oid=s;
      
      s="";
      /*while((ch=in.readChar())!='\n')
         s=s+ch;
         System.out.println("prasad3");*/
         	   	s=in.readLine();
      if(s!=null)
      {
         	   	if(s.equals("false"))
        isrw=false;
      else
    if(s.equals("true"))
        isrw=true;
    }
       // System.out.println("host="+host);
        //System.out.println("port="+port);
        //System.out.println("community="+community);
       // System.out.println("oid="+oid);
       // System.out.println("version="+version);
      // Get_Request application = new Get_Request(host,port,oid,community, socketType); 
//application.init(); 
 //application.start(); 
        got=false;
        System.out.println("Accessing....got="+got);
        if(oid==null) return 0;
        tout=false;
    sendGetRequest(host,port,community,oid,version);
    System.out.println("tout=="+tout);
    
    if(tout==true)
    return 1;
     //System.out.println("context="+context.toString());
    System.out.println("obname=" + obname);
   while(got==false);
   
   System.out.println("obid=" + obid);
   System.out.println("obvalue="+obvalue);
   

  }
  catch(EOFException e){System.out.println("prasad4");return 0;}
  catch(IOException e){return 0;}
  return 1;
}
