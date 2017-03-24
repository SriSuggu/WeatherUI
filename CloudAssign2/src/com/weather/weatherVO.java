package com.weather;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class weatherVO {

	@XmlElement public String DATE;
	@XmlElement  public double TMAX;
	@XmlElement public double  TMIN;
	
	
	
	public weatherVO() {
		super();
	}
	
	public weatherVO(String DATE, double TMAX, double TMIN) {
		super();
		this.DATE = DATE;
		this.TMAX = TMAX;
		this.TMIN = TMIN;
	}
	
	
	
}
