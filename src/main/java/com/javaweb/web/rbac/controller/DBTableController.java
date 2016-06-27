package com.javaweb.web.rbac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaweb.web.common.service.CommonWebService;

@Controller
@RequestMapping(value="/web/tools/dbTable")
public class DBTableController {
	
	@Autowired
	private CommonWebService commonWebService;
	
	@GetMapping(value="/descTable",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String descTable(HttpServletRequest request, 
			  			    HttpServletResponse response) {
		JSONArray ja = new JSONArray();
		try{
			List<String> tableList = commonWebService.showTables();
			for(int i=0;i<tableList.size();i++){
				JSONObject jo = new JSONObject();
				String table = tableList.get(i);
				jo.put("table", table);
				jo.put("list", commonWebService.descTable(table));
				//特殊处理null字段,不然转换时会报错
				ja.add(jo.toString().replaceAll("null", "isNull"));
			}
		}catch(Exception e){
			//do nothing
		}
		return ja.toString();
	}
	
}
