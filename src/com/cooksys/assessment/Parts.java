package com.cooksys.assessment;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Parts {
	
	@XmlElement(name = "component")
	private List<String> part;
	
    public List<String> getPart() {
		if(part == null){
			part = new ArrayList<String>();
		}
		return part; 
		}
    public void setPart(List<String> part) { this.part = part; }
}
