
import java.sql.*;
import java.io.IOException;
import java.io.Serializable;
import java.lang.Class;

public class emp extends person  implements Serializable
{
Connection cn=null;
Statement st=null;
ResultSet rs=null;

	
int empno;
double salary;
String  salGrade;
String dept;
String designation;
public emp()
{

}


public emp(int empno,String ename,
		String address,
		String dept,
		String designation,
		int age,
		String gender,
		String m,
		String color,
		String sun,
		double sal,
		String salg
		) throws InstantiationException, IllegalAccessException
{
	this.empno=empno;
	this.ename=ename;
	this.address= address;
	this.dept=dept;
	this.designation= designation;
	this.age=age;
	this.gender=gender;
	this.maritalStatus= m;
	this.preferredColors= color;
	this.sunSign=sun;
	this.salary= sal;
	this.salGrade= salg;
	
}

public void getDetails() throws IOException
{
	super.getDetails();

	System.out.print("Enter the employee number : ");
	empno=Integer.parseInt(console.readLine());	

	System.out.print("Enter the salary : ");
	salary=Double.parseDouble(console.readLine());

	System.out.print("Enter salary grade : ");
 	salGrade = console.readLine();
 	
 	System.out.print("Enter dept : ");
 	dept = console.readLine();
 	
 	System.out.print("Enter designation : ");
 	designation = console.readLine();
 	

}

public void display()
{
	System.out.println("");
	System.out.println("Employee details ");
	System.out.println("************************************************************************");
	System.out.println("");
	
	super.display();
	//System.out.println("Ename 				" + ename);
	System.out.println("Employee number 	"+ empno);
	System.out.println("Employee dept		"+ dept);
	System.out.println("Designation 		"+ designation);
	System.out.println("Employee salary 	"+ salary);
	System.out.println("Employee sal grade  	"+ salGrade);

}


public void calculateTax(double tax)
{
	System.out.println("Employee Salary 	: "+salary);
	System.out.println("Tax %			: "+tax);
	
	System.out.println("Tax Amount		: "+(salary*tax)/100);
}


public void allocateGrade()
{
	System.out.print("Employee Salary Grade : ");
	if(salary>20000)
	{
		System.out.println("High");
	}
	else if(salary>10000)
	{
		System.out.println("Medium");
	}
	else if(salary>5000)
	{
		System.out.println("Average");
	}
	else 
	{
		System.out.println("Low");
	}
}

}



