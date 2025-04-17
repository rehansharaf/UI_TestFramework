package com.marina.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.marina.base.TestBase;

public class Database extends TestBase {
	
	public static final String dbClassName= "com.mysql.cj.jdbc.Driver";
    Connection myCon = null;
    Statement mySt,mySt1;
    PreparedStatement myPst,myPst1;
    ResultSet rs1,rs2,rs3,rs4;
    String myQuery1,myQuery2;
    String mySQL,myUName,myPswd;
	
	public Database() {
					
		 	mySQL  	= "jdbc:mysql://"+prop.getProperty("database_host")+":3306/"+prop.getProperty("database_name")+"?autoReconnect=true&useSSL=false";
	    	myUName = prop.getProperty("database_user");
	    	myPswd  = prop.getProperty("database_pass");
	    	
	    	try {
				Class.forName(dbClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    	try {
				myCon = DriverManager.getConnection(mySQL,myUName,myPswd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	
	


}
