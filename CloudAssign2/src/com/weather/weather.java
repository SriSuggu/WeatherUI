package com.weather;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class weather {

	@XmlElement public String DATE;
	
	public weather()
	{
		
	}
	
	public weather(String DATE)
	{
		this.DATE=DATE;
	}
}
