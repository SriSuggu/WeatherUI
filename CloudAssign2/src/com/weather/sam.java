package com.weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class sam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//conn c = new conn();
		try {
			String connectionURL = "jdbc:mysql://localhost:3306/weatherdb?verifyServerCertificate=false&useSSL=true";
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root", "password");
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM daily");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())  {
				SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd");
				String f = d.format(rs.getDate(1));
				System.out.println(f+"  "+rs.getDouble(2)+"  "+rs.getDouble(3));  
				
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
