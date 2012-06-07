

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

class person implements Serializable
{
public String ename;
public String gender;
public String address;
public int age;
public String maritalStatus;
public int noOfKids;
public int []ageOfKids;
public String preferredColors;
public String sunSign;

InputStreamReader stdin=new InputStreamReader(System.in);
BufferedReader console =new BufferedReader(stdin);

public void getDetails() throws IOException
{try
	{

		System.out.print("Enter the name : ");
 		ename = console.readLine();

		System.out.print("Enter the gender : ");
 		gender = console.readLine();
 		
 		System.out.print("Enter the address : ");
 		address = console.readLine();
 		
		System.out.print("Enter the age : ");
		age=Integer.parseInt(console.readLine());
		
		System.out.print("Enter marital status : ");
 		maritalStatus= console.readLine();

	/*	System.out.print("Enter the no. Of Kids");
		noOfKids=Integer.parseInt(console.readLine());

		ageOfKids=new int[noOfKids];

		for(int i=0;i<noOfKids;i++)
		{
			System.out.print("Enter the age of kid " + (i+1));
			ageOfKids[i]=Integer.parseInt(console.readLine());			
		}
*/
		System.out.print("Enter preferred color : ");
 		preferredColors=console.readLine();

		System.out.print("Enter sunsign : ");
 		sunSign=console.readLine();
 		
	}
catch(Exception e)
{
	e.printStackTrace();
}
}


public void display()
{

	System.out.println("Ename 			" + ename);
	System.out.println("Gender 			" + gender);
	System.out.println("Address 		" + address);
	System.out.println("Age 			" + age);
	System.out.println("Married 		" + maritalStatus);
	/*System.out.print("Number Of kids = " + noOfKids);

	
	for(int i=0;i<noOfKids;i++)
	{			
	 	System.out.print("Age Of kid"+(i+1)+ " = "+ageOfKids[i]);			
	}

*/	System.out.println("Prefered color 		"+ preferredColors);

}

}