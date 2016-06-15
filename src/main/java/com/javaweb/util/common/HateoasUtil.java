package com.javaweb.util.common;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HateoasUtil {
	
	private static HttpHeaders getHttpHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("X-FRAME-OPTIONS", "DENY");
		return headers;
	}
	
	private static Resource<Object> getResource(String result,Link link){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode onode = mapper.createObjectNode();
		onode.putPOJO("health", HttpStatus.OK);
		onode.putPOJO("status", HttpStatus.OK.value());
		onode.putPOJO("data", result);
		Resource<Object> resource = new Resource<Object>(onode);
		resource.add(link);
		return resource;
	}
	
	public static ResponseEntity<Resource<Object>> getResponseEntity(String result,Link link){
		HttpHeaders headers = getHttpHeaders();
		Resource<Object> resource = getResource(result,link);
		return new ResponseEntity<Resource<Object>>(resource, headers, HttpStatus.OK);
	}

}
