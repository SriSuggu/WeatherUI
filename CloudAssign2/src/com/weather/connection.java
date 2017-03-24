package com.weather;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;




public class connection {

	static String connectionURL = "jdbc:mysql://localhost:3306/weatherdb?verifyServerCertificate=false&useSSL=true";
	static Connection connection = null;
	
	//weatherVO weathervo = new weatherVO();
	public static weatherVO getweather(String date) throws Exception
	{
weatherVO weathervo = new weatherVO();
    	
		try {
			System.out.println(date);
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root", "password");
			SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd");
			PreparedStatement preparedstatement= connection.prepareStatement("select * from daily where date= ?");
			System.out.println(date);
			java.util.Date d=sm.parse(date);
			preparedstatement.setDate(1,(new java.sql.Date(d.getTime())));
			ResultSet rs=preparedstatement.executeQuery();
			
				
			
					while (rs.next())
					{
						SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMdd");
						String f = d1.format(rs.getDate(1));
						
					
						weathervo.DATE=f ;
						weathervo.TMAX=rs.getDouble(2);
						weathervo.TMIN=rs.getDouble(3);
					

				}
					System.out.println(weathervo.DATE);
					System.out.println(weathervo.TMAX);
					System.out.println(weathervo.TMIN);
				return weathervo;
		
			
			
		}
		
		catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
      
		
		
	}
	
	public static ArrayList<weather> getAllWeathers() throws Exception {

	Class.forName("com.mysql.jdbc.Driver").newInstance();
	connection = DriverManager.getConnection(connectionURL,"root", "password");
try {
		
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM daily");
		ResultSet rs = ps.executeQuery();
		ArrayList<weather> weatherList = new ArrayList<weather>();
		
		while (rs.next()) {
			weather weather= new weather();
			
			SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMdd");
			String f = d1.format(rs.getDate(1));
			
			weather.DATE=f;
		weatherList.add(weather);
		
		

		}
		return weatherList;
		} 
		catch (Exception e) {
		throw e;
		}
	}
	
	
	
	public static Response getDate(int date) throws Exception
	{
		weatherVO weathervo = new weatherVO();
    	
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root", "password");
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM daily where date="+date);
			ResultSet rs = ps.executeQuery();
			if(!rs.isBeforeFirst())
			{
				return Response.status(Status.NOT_FOUND).entity("").build();
			} 
			else{				
				
			
					while (rs.next())
					{
						SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMdd");
						String f = d1.format(rs.getDate(1));
						
					
						weathervo.DATE=f ;
						weathervo.TMAX=rs.getDouble(2);
						weathervo.TMIN=rs.getDouble(3);
					

				}
				return Response.status(Status.OK).entity(weathervo).build();
			}	
			
			
		}
		
		catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
      
	}			
		
	
	public Response updateClimate(weatherVO w) {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root", "password");
			
	    	//weatherVO weathervo = new weatherVO();
			SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd");
		    //Date d =  (Date) sm.parse(w.DATE);
			java.util.Date d=sm.parse(w.DATE);
			
	    	PreparedStatement ps = connection.prepareStatement("INSERT INTO daily(DATE,TMAX,TMIN) VALUES(?,?,?)");
	    	ps.setDate(1,new java.sql.Date(d.getTime()));
	    	ps.setDouble(2, w.TMAX);
	    	ps.setDouble(3, w.TMIN);
			 ps.executeUpdate();
			
		  weather we = new weather();
	    we.DATE=w.DATE;
	    
				System.out.println(we.DATE);
				return Response.status(Status.CREATED).entity(we).build();
					
			} 
			catch (Exception e) {
					e.printStackTrace();
					return Response.status(Status.CONFLICT).entity(e).build();
				}
	    
		
	}
	
	public Response deleteClimate(String DATE) {
	    
    	
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root", "password");
			
			SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd");
			PreparedStatement preparedstatement= connection.prepareStatement("select * from daily where date= ?");
			System.out.println(DATE);
			java.util.Date d=sm.parse(DATE);
			preparedstatement.setDate(1,(new java.sql.Date(d.getTime())));
			ResultSet rs=preparedstatement.executeQuery();
			boolean flag=rs.last();
			int r=-1;
			if(flag)
				r=rs.getRow();
			if(r==1)
			{
				PreparedStatement ps = connection.prepareStatement("delete from daily where date=?");
			
			ps.setDate(1,(new java.sql.Date(d.getTime())));
			ps.executeUpdate();
			//System.out.println(r);
			
			//System.out.println("Executed  "+a);
				return  Response.status(204).build();
			}
			else
				return Response.status(Response.Status.NOT_FOUND).build();
		} 
		catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.CONFLICT).entity(e).build();
			}
    }

	public static Response getDate1(String date) {
		
		try
		{
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(connectionURL, "root", "password");
		
		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd");
		PreparedStatement preparedstatement= connection.prepareStatement("select * from daily where date= ?");
		System.out.println(date);
		java.util.Date d=sm.parse(date);
		preparedstatement.setDate(1,(new java.sql.Date(d.getTime())));
		ResultSet rs=preparedstatement.executeQuery();
		boolean flag=rs.last();
		int r=-1;
		if(flag)
		{
			r=rs.getRow();
			int date1;
		date1=Integer.parseInt(date);
		
	
		 return getDate(date1);
		}
		else
		{
			String year,month,day;
			year=date.substring(0,4);
			month=date.substring(4,6);
			day=date.substring(6, 8);
			System.out.println(year);
			System.out.println(month);
			System.out.println(day);
			weatherVO weathervo = new weatherVO();
			String years[]=new String[4];
				years[0]="2013";
				years[1]="2014";
				years[2]="2015";
				years[3]="2016";
		
			String dates[]= new String[4];
			String newdates[]= new String[4];
			String newyear[]= new String[4];
			int i;
			/*
			dates=year.replaceAll(year, "2013");
			dates=year.replaceAll(year, "2014");
			dates=year.replaceAll(year, "2015");
			dates=year.replaceAll(year, "2016");*/
			
			for (i=0;i<years.length;i++ )
			{
				
				
				newyear[i]=year.replaceAll(year, years[i]);
				newdates[i]=newyear[i].concat(month).concat(day);
					System.out.println(newdates[i]);
				
			}
			
			System.out.println(newdates);
			
			//String newdates[]= {"date1","date2","date3","date4"};
			    weathervo=gettemp(newdates);
			    System.out.println(date);
			    System.out.println(weathervo.TMAX);
			    System.out.println(weathervo.TMIN);
			    weathervo.DATE=date;
			   // List<weatherVO> list1= new ArrayList<weatherVO>();
			   // list1=dates(date);
			    
			    System.out.println(weathervo.DATE);
			    System.out.println(weathervo.TMAX);
			    System.out.println(weathervo.TMIN);
			    return Response.status(Status.CREATED).entity(weathervo).build();
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(Response.Status.CONFLICT).entity(e).build();
		}
		
	}
	
	

	
	/*public static ArrayList<weatherVO> dates(String date) throws Exception
	{
		weatherVO weathervo = new weatherVO();
	    List<weatherVO> list= new ArrayList<weatherVO>();
	    weathervo=getdate1(date);
	    list.add(weathervo);
	    for (int ii=0;ii<4;ii++)
	    {
	    	
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));
		c.add(Calendar.DATE, 1);  // number of days to add
		weathervo.DATE= sdf.format(c.getTime()); 
	
	getdate1(date)
		list.add(weathervo);
	    
	    }
		return null;
		
	}*/

	public static weatherVO gettemp(String[] newdates) {
		weatherVO w = new weatherVO();
		double tmax=0,tmin=0,avg_tmax=0,avg_tmin=0;
		try
		{
			List<weatherVO> l= new ArrayList<weatherVO>();
			
	for (int i=0;i<newdates.length;i++)
	{
		
		w=getweather(newdates[i]);
		l.add(w);
		
		
	}
	//System.out.println(l);
	
	  for (weatherVO i:l)
	  {
		  System.out.println(i.DATE);
		  System.out.println(i.TMAX);
		  System.out.println(i.TMIN);
		 tmax=tmax+i.TMAX;
		 tmin=tmin+i.TMIN;
		 
		  
	  }
	  
	  avg_tmax=tmax/4;
	  avg_tmin=tmin/4;
	   
	  w.TMAX=avg_tmax;
	  w.TMIN=avg_tmin;
	  System.out.println(w.TMAX);
	  System.out.println(w.TMIN);
	  return w;
	  
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
		
	
	public static weatherVO getDate5(String date) {
		
		try
		{
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(connectionURL, "root", "password");
		
		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd");
		PreparedStatement preparedstatement= connection.prepareStatement("select * from daily where date= ?");
		System.out.println(date);
		java.util.Date d=sm.parse(date);
		preparedstatement.setDate(1,(new java.sql.Date(d.getTime())));
		ResultSet rs=preparedstatement.executeQuery();
		boolean flag=rs.last();
		int r=-1;
		if(flag)
		{
			r=rs.getRow();
		
		
	
		 return getweather(date);
		}
		else
		{
			String year,month,day;
			year=date.substring(0,4);
			month=date.substring(4,6);
			day=date.substring(6, 8);
			System.out.println(year);
			System.out.println(month);
			System.out.println(day);
			weatherVO weathervo = new weatherVO();
			String years[]=new String[4];
				years[0]="2013";
				years[1]="2014";
				years[2]="2015";
				years[3]="2016";
		
			String dates[]= new String[4];
			String newdates[]= new String[4];
			String newyear[]= new String[4];
			int i;
			/*
			dates=year.replaceAll(year, "2013");
			dates=year.replaceAll(year, "2014");
			dates=year.replaceAll(year, "2015");
			dates=year.replaceAll(year, "2016");*/
			
			for (i=0;i<years.length;i++ )
			{
				
				
				newyear[i]=year.replaceAll(year, years[i]);
				newdates[i]=newyear[i].concat(month).concat(day);
					System.out.println(newdates[i]);
				
			}
			
			System.out.println(newdates);
			
			//String newdates[]= {"date1","date2","date3","date4"};
			    weathervo=gettemp(newdates);
			    System.out.println(date);
			    System.out.println(weathervo.TMAX);
			    System.out.println(weathervo.TMIN);
			    weathervo.DATE=date;
			   // List<weatherVO> list1= new ArrayList<weatherVO>();
			   // list1=dates(date);
			    
			    System.out.println(weathervo.DATE);
			    System.out.println(weathervo.TMAX);
			    System.out.println(weathervo.TMIN);
			    return weathervo;
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
}


	

