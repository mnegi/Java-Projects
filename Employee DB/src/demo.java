/*
 * Created on Sep 25, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class demo {
	static int choice;
	
	public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{		
		dataAccess d1=new dataAccess();
		emp es=new emp();
		
		System.out.println();
		System.out.println("Main Menu");
		
		System.out.println("1	Search an employee");
		System.out.println("2	Add new employee");
		System.out.println("3	Delete an employee");
		System.out.println("4	Update employee");
		System.out.println("5	Show All Employee");
		System.out.println("6	Calculate Interest");
		System.out.println("7	Salary Grade");
		System.out.println("8	Exit");
		
		System.out.println();
		

		InputStreamReader s=new InputStreamReader(System.in);
		BufferedReader c =new BufferedReader(s);
		
		System.out.print("Please enter your choice : ");
		choice=Integer.parseInt(c.readLine());
		
		switch(choice)
		{
		case 1:
		{
			System.out.print("Please enter employee number : ");
			int en=Integer.parseInt(c.readLine());			
			es=d1.search(en);
				if(es!=null)
				{
					es.display();
				}
				else
				{
					System.out.println("Employee not found");
				}
			break;
		}
		case 2:
		{
			es.getDetails();
			d1.add(es);
			break;
		}
		case 3:
		{
			System.out.print("Please enter employee number : ");
			int en=Integer.parseInt(c.readLine());			
			d1.delete(en);
			
			break;
			
		}
		case 4:
		{
			System.out.print("Please enter employee number : ");
			int en=Integer.parseInt(c.readLine());	
			
			System.out.print("Please enter field to be updated : ");
			String field=c.readLine();
			
			System.out.print("Please enter value of field : ");
			String val=c.readLine();
			
			d1.update(en,field,val);
			break;
			
		}
		case 5:
		{
			d1.showAll();
			break;
		}
		case 6:
		{
			System.out.print("Please enter employee number : ");
			int en=Integer.parseInt(c.readLine());	
			
			System.out.print("Please enter tax % : ");
			double t=Double.parseDouble(c.readLine());	
			
			es=d1.search(en);
				if(es!=null)
				{
					es.calculateTax(t);
				}
				else
				{
					System.out.println("Employee not found");
				}
			break;	
			
		}
		case 7:
		{
			System.out.print("Please enter employee number : ");
			int en=Integer.parseInt(c.readLine());			
			es=d1.search(en);
				if(es!=null)
				{
					es.allocateGrade();
				}
				else
				{
					System.out.println("Employee not found");
				}
			break;	
			
		}
		case 8:
		{
			System.exit(1);		
			
		}
		default:
		{
			System.out.println("Invalid choice");
			break;
		}
		}
		main(null);
		//es.getDetails();
		//d1.add(es);
		
		//d1.delete(es);
		
		//es=d1.search(1);
		//es.display();
		
		//d1.update(1,"ename","'manohar'");
	
		
		//d1.showAll();
	}

}
