package com.weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class conn {
	
	public static ArrayList<weatherVO> getAllWeathers() throws Exception {

		String connectionURL = "jdbc:mysql://localhost:3306/weatherdb";
		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(connectionURL, "root", "password");
	try {
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM daily");
			ResultSet rs = ps.executeQuery();
			//weathervo.setDate(rs.getDate(1);
			ArrayList<weatherVO> weatherList = new ArrayList<weatherVO>();
			SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd");
			
			while (rs.next()) {
				weatherVO weathervo = new weatherVO();
				String f = d.format(rs.getDate(1));
				weathervo.DATE=f;
			//weathervo.setTmax(rs.getDouble(2));
			//weathervo.setTmin(rs.getDouble(3));
			weatherList.add(weathervo);
			//System.out.println(weathervo.getDATE());

			}
			return weatherList;
			} 
			catch (Exception e) {
			throw e;
			}
		}


}
