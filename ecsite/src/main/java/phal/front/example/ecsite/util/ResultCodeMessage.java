package phal.front.example.ecsite.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public  class ResultCodeMessage{
	public Object result;
	public int code  = 1;
	public List<String> messages = new ArrayList<>();
}
