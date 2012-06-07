/*
 * Created on Sep 25, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class dataAccess {
	Connection cn=null;
	PreparedStatement pst=null;
	Statement st = null;
	
	public dataAccess() throws InstantiationException, IllegalAccessException, SQLException
	{
		try
		{
			
			// Load Sun's jdbc-odbc driver
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
//			 Create a URL that identifies database
			String url = "jdbc:odbc:mani";

//			 Now attempt to create a database connection
			cn = 
			DriverManager.getConnection (url);
			//			
			//System.out.println(cn);
			st = cn.createStatement();
			
			
		}
		catch (ClassNotFoundException cnfe) // driver not found
		{
			System.err.println ("Unable to load database driver");
			System.err.println ("Details : mani");
			System.exit(0);
		}

	
	}
	public emp showAll()
	{emp e1=null;
		try
		{
		String str="select * from emp";
		ResultSet result = st.executeQuery(str);
		
//		 While more rows exist, print them
		while (result.next() )
		{
			e1=new emp(result.getInt(1),result.getString(2),result.getString(3)
					,result.getString(4),result.getString(5),
					result.getInt(6),result.getString(7),result.getString(8),
					result.getString(9),result.getString(10),result.getDouble(11),result.getString(12));
			e1.display();
			
			
		}
		
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
		return e1;
	}

public emp search(int empno)
{emp e1=null;
	try
	{
	String str="select * from emp where eno="+empno;
	ResultSet result = st.executeQuery(str);
	
//	 While more rows exist, print them
	while (result.next() )
	{
		e1=new emp(result.getInt(1),result.getString(2),result.getString(3)
				,result.getString(4),result.getString(5),
				result.getInt(6),result.getString(7),result.getString(8),
				result.getString(9),result.getString(10),result.getDouble(11),result.getString(12));
		
		
		
	}
	
	}

	catch(Exception e)
	{
		e.printStackTrace();
	}
	return e1;
}
	
	
public void add(emp e1) throws SQLException
{
	
	try
	{
		String str="insert into emp values("+
		e1.empno+",'"+	
		e1.ename+"','"+
		e1.address+"','"+
		e1.dept+"','"+
		e1.designation+"',"+
		e1.age+",'"+
		e1.gender+"','"+
		e1.maritalStatus+"','"+
		e1.preferredColors+"','"+
		e1.sunSign+"',"+
		e1.salary+",'"+
		e1.salGrade+"')";
		
		System.out.println(str);
		
		PreparedStatement st=cn.prepareStatement(str);
		int x=st.executeUpdate();
		if(x!=0)
		{
			System.out.println("inserted to datbase");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}


public void delete(int e) throws SQLException
{
	
	try
	{
		String str="delete from emp where eno="+e;
		PreparedStatement st=cn.prepareStatement(str);
		
		System.out.println(str);
		
		int x=st.executeUpdate();
		if(x!=0)
		{
			System.out.println("Deleted from database");
		}
	}
	catch(Exception e111)
	{
		e111.printStackTrace();
	}
}

public void update(int eno,String field,String value) throws SQLException
{
	
	try
	{
		String str="update emp set "+field+"="+value+" where eno="+eno;
		System.out.println(str);
		
		
		PreparedStatement st=cn.prepareStatement(str);
		int x=st.executeUpdate();
		if(x!=0)
		{
			System.out.println("updated");
		}
	}
	catch(Exception e111)
	{
		e111.printStackTrace();
	}
}
}
