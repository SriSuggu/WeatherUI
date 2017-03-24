package com.weather;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.owlike.genson.stream.JsonType;

@Path("service")
public class service {
	connection s = new connection ();
	weatherVO weathervo = new weatherVO();

	@GET
	@Path("historical")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<weather> getMessage() throws Exception{


	
	return connection.getAllWeathers();
}

	@GET
    @Path("/historical/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dateClimate(@PathParam("date") int date) throws Exception {
    	return connection.getDate(date);
    } 
	
	@GET
    @Path("/forecast1/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dateClimate1(@PathParam("date") String date) throws Exception {
		
    	return connection.getDate1(date);
    }
	
	@GET
    @Path("/forecast5/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<weatherVO> dateClimate5(@PathParam("date") String date) throws Exception {
		System.out.println(date);
		
		weatherVO wea = new weatherVO();
	    List<weatherVO> list= new ArrayList<weatherVO>();
	    wea=s.getDate5(date);
	    System.out.println(wea);
	    list.add(wea);
	    System.out.println(list);
	    for (int ii=0;ii<4;ii++)
	    {
	    	
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));
		c.add(Calendar.DATE, 1);  // number of days to add
	     date= sdf.format(c.getTime()); 
	    wea= s.getDate5(date);
		list.add(wea);
		System.out.println(list);
	    
	    }
	
    	
    	return list;
    }
	@POST
    @Path("/historical")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addClimate (weatherVO weathervo)
	{
		return s.updateClimate(weathervo);
		
	}
	
	 
		//DELETE particular data method
	    @DELETE
	    @Path("/historical/{DATE}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteClimate(@PathParam("DATE") String DATE) {
	    	System.out.println(DATE);
	    	return s.deleteClimate(DATE);
	    }
	    
	    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      //@SuppressWarnings("unused")
			String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
			private static String readAll(Reader rd) throws IOException {
				StringBuilder sb = new StringBuilder();
				int cp;
				while ((cp = rd.read()) != -1) {
					sb.append((char) cp);
				}
				return sb.toString();
			}
		
		
		
			/*
			@GET
			@Path("/report/{DATE}")
			@Produces(MediaType.APPLICATION_JSON)
			public List<weatherVO> reportFiveDays(@PathParam("DATE") String DATE){
				return s.getReportforfivedays(DATE);
			 }
*/
		
		@GET
		@Path("/forecast/{dat}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response forcastClimate(@PathParam("dat") int dat) {
			//System.out.println(" "+dat);
			try{
				List<weatherVO> lt=new ArrayList<weatherVO>();
				String temp = Integer.toString(dat);
				char c[]=temp.toCharArray();
				String dt=""+c[0]+""+c[1]+""+c[2]+""+c[3]+"-"+c[4]+""+c[5]+"-"+c[6]+""+c[7];
				java.sql.Date dd = java.sql.Date.valueOf( dt );
				for(int i=0;i<7;i++)
				{

					String urrl="https://api.darksky.net/forecast/8016282c182ad757675e4b60a9604e68/39.103118,-84.512020,"+dd+"T12:00:00";
					JSONObject json=readJsonFromUrl(urrl);
					JSONObject jj=json.getJSONObject("daily");
					weatherVO d=new weatherVO();
					JSONArray jr4= jj.getJSONArray("data");
					JSONObject jo=jr4.getJSONObject(0);
					d.DATE=Integer.toString(dat);
			    	double tmax=jo.getLong("temperatureMax");
					d.TMAX=tmax;
			    	double tmin=jo.getLong("temperatureMin");
			    	d.TMIN=tmin;
			    	lt.add(d);
					dat++;
					dd = new Date(dd.getTime() + (1000 * 60 * 60 * 24));

				}
				if(lt!=null)
					return Response.status(Response.Status.OK).entity(lt).build();
				else
					return Response.status(Response.Status.BAD_REQUEST).build();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(Response.Status.BAD_REQUEST).build();
			}		
		}	
}
