package com.javaweb.util.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.javaweb.entity.common.ColumnInfo;
import com.javaweb.entity.common.GenerateConfigInfo;
import com.javaweb.entity.common.TableInfo;
import com.javaweb.util.self.Constant;

public class GenerateUtil {
	
	public static String getRandomUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static void main(String[] args) throws Exception {
		generateZipFile(null);
	}
	
	public static void generateZipFile(String tableNames[]) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		//for(String tableName : tableNames){
		    TableInfo tableInfo = new TableInfo();
		    tableInfo.setTableName("sys_user");
		    tableInfo.setTableComment("系统用户表");
		    tableInfo.setEngine("InnoDB");
		    tableInfo.setCreateTime(DateUtil.getDefaultDate());
		    List<ColumnInfo> tableColumns = new ArrayList<ColumnInfo>();
		    ColumnInfo columnInfo1 = new ColumnInfo();ColumnInfo columnInfo2 = new ColumnInfo();
		    columnInfo1.setColumnName("id");columnInfo2.setColumnName("address");
		    columnInfo1.setDataType("int");columnInfo2.setDataType("varchar");
		    columnInfo1.setColumnComment("主键ID");columnInfo2.setColumnComment("地址");
		    columnInfo1.setColumnKey("PRI");columnInfo2.setColumnKey("");
		    columnInfo1.setExtra("额外");columnInfo2.setExtra("");
		    tableColumns.add(columnInfo1);tableColumns.add(columnInfo2);
		    GenerateConfigInfo generateConfigInfo = new GenerateConfigInfo();
		    generateConfigInfo.setAuthor("张三");
		    generateConfigInfo.setEmail("abc@test.com");
		    generateConfigInfo.setPackageName("com.javaweb");
		    generateConfigInfo.setTablePrefix("");
			generatorCode(tableInfo, tableColumns, generateConfigInfo, zip);
		//}
		IOUtils.closeQuietly(zip);
		byte[] bytes = outputStream.toByteArray();
		OutputStream zipOutputStream = new FileOutputStream(new File("F:/a.zip"));
		zipOutputStream.write(bytes);
		zipOutputStream.close();
	}
	
	private static void generatorCode(TableInfo tableInfo,
									 List<ColumnInfo> tableColumns,
									 GenerateConfigInfo generateConfigInfo,
									 ZipOutputStream zip) {
		tableColumns.stream().forEach(each->{
			String dataType = each.getDataType();
			each.setAttrType(Constant.MYSQL_COLUMN_TYPE_MAPPER.get(dataType));
			each.setAttrName(getAttrName(each.getColumnName()));
			each.setAttrNameForTitleCase(getAttrNameForTitleCase(each.getAttrName()));
		});
		tableInfo.setClassName(getAttrNameForTitleCase(getAttrName(tableInfo.getTableName())));
		
		//设置velocity资源加载器
		Properties properties = new Properties();  
		properties.put("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");  
		Velocity.init(properties);
		
		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("hasBigDecimal",true);
		map.put("tableComment",tableInfo.getTableComment());
		map.put("packageName",generateConfigInfo.getPackageName());
		map.put("author",generateConfigInfo.getAuthor());
		map.put("email",generateConfigInfo.getEmail());
		map.put("dateTime",DateUtil.getDefaultDate());
		map.put("className",tableInfo.getClassName());
		map.put("columns", tableColumns);
		
		VelocityContext velocityContext = new VelocityContext(map);
		//获取模板列表
		List<String> templates = getTemplates();
		for(String template:templates){
			//渲染模板
			StringWriter stringWriter = new StringWriter();
			Template tpl = Velocity.getTemplate(template,"UTF-8");
			tpl.merge(velocityContext,stringWriter);
			try {
				//添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template,tableInfo.getClassName(),generateConfigInfo.getPackageName())));  
				IOUtils.write(stringWriter.toString(),zip,"UTF-8");
				IOUtils.closeQuietly(stringWriter);
				zip.closeEntry();
			} catch (IOException e) {
				System.out.println("渲染模板失败，表名：["+tableInfo.getTableName()+"]，异常信息为："+e.getMessage());
			}
		}
	}
	
	private static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("template/Entity.java.vm");
		return templates;
	}
	
	private static String getFileName(String template,String className,String packageName){
		String packagePath = packageName.replace(".",File.separator) + File.separator;
		if(template.contains("Entity.java.vm")){
			return packagePath + "entity" + File.separator + className + "Entity.java";
		}
		return null;
	}
	
	private static String getAttrName(String columnName){
		String str[] = columnName.split("_");
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			String each = str[i];
			if(i%2!=0){
				stringBuffer.append(each.substring(0,1).toUpperCase())
							.append(each.substring(1,each.length()));
			}else{
				stringBuffer.append(each);
			}
		}
		return stringBuffer.toString();
	}
	
	private static String getAttrNameForTitleCase(String attrName){
		return attrName.substring(0,1).toUpperCase()+
			   attrName.substring(1,attrName.length());
	}
	
}
