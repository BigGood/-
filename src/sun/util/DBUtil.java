package sun.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class DBUtil {
	private Connection con = null;
	public Connection getMySqlConnection()
    {
        try
        {

            InitialContext ic = new InitialContext();
            Context envContext  = (Context)ic.lookup("java:/comp/env");
            DataSource dataSource = (DataSource)envContext.lookup("jdbc/mysql");     
        	if(dataSource != null){
        		con = dataSource.getConnection();
                 if (con != null)
                 {
                     System.out.println("Connect succeed from MySql!");
                     return con;
                 }
                 else
                 {
                     System.out.println("new Connection error ...");
                     return null;
                 }
        		
        	}else{
        	   System.out.println("new DataSource error ...");
               return null;
        	}
        
        }
        catch (Throwable e)
        {
            System.out.println("look for jndi name error ...");
            e.printStackTrace();
            return null;
        }
    }
	public boolean closeMySqlConnection(){
		if(con!=null){
			try {
				con.close();
				System.out.println("Connection is closed...");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("Connection is not build...");
			return false;
		}
		return false;
	}
}
