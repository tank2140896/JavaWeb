package com.gloudtek.util.common;

import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class JsonAndXmlUtil {
	
	public static String object2Json(Object obj) throws Exception{
		if(obj==null){
			return "";
		}
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		JsonFactory jf = new JsonFactory();
		JsonGenerator jg = jf.createGenerator(sw);
		mapper.writeValue(jg, obj);
		jg.close();
		return sw.toString();
	}
	
	public static <T> T json2Object(String json,Class<T> type) throws Exception{
		if(json==null){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory jf = new JsonFactory();
		JsonParser jp = jf.createParser(json);
		return mapper.readValue(jp, type);
	}
	
	public static String object2Xml(Object obj) throws Exception{
		XStream xs = new XStream(new DomDriver());
		xs.alias(obj.getClass().getSimpleName(), obj.getClass());
		return xs.toXML(obj);
	}
	
	public static Object xml2Object(String xml) throws Exception{
		XStream xs = new XStream(new DomDriver());
		return xs.fromXML(xml);
	}
	
}
