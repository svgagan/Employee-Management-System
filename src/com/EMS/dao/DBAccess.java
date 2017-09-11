package com.EMS.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {
	
	static Connection con = null;
	static  final String url = "jdbc:mysql://localhost:3306/employment";
	static  final String uname = "root";
	static  final String pass = "root";

	/*public static Connection getConnection(){
        if (con != null) return con;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url, uname, pass);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }*/
	
	private static void createConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,uname,pass);
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		//System.out.println("connection "+con);
		try {
			if(con == null || con.isClosed()) {
				//System.out.println("Inside IF");			
				createConnection();
				return con;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Outside IF");
		return con;
	}
}
