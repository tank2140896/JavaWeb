package com.javaweb.util.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiXSSFExcelUtil {
	
	//遍历所有sheet
	public static Object[] readAllExcelSheet(InputStream inputStream) throws IOException {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		int sheets = xssfWorkbook.getNumberOfSheets();
		Object[] objects = new Object[sheets];
		for(int i=0;i<sheets;i++){
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(i);
			objects[i] = readSheet(xssfSheet);
		}
		xssfWorkbook.close();
		return objects;
	}
	
	//根据sheet名字遍历单个sheet
	public static List<List<String>> readSingleExcelSheet(InputStream inputStream,String sheetName) throws IOException {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
		List<List<String>> list = readSheet(xssfSheet);
		xssfWorkbook.close();
		return list;
	}
	
	//根据sheet名字遍历单个sheet
	public static List<?> readSingleExcelSheet(InputStream inputStream,String sheetName,Map<Integer,String> map,Class<?> objectClass) throws Exception {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
		List<?> list = readSheet(xssfSheet,map,objectClass);
		xssfWorkbook.close();
		return list;
	}
	
	//根据sheet序号遍历单个sheet
	public static List<List<String>> readSingleExcelSheet(InputStream inputStream,int sheetIndex) throws IOException {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
		List<List<String>> list = readSheet(xssfSheet);
		xssfWorkbook.close();
		return list;
	}
	
	//根据sheet序号遍历单个sheet
	public static List<?> readSingleExcelSheet(InputStream inputStream,int sheetIndex,Map<Integer,String> map,Class<?> objectClass) throws Exception {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
		List<?> list = readSheet(xssfSheet,map,objectClass);
		xssfWorkbook.close();
		return list;
	}
	
	//写入Excel
	public static void writeExcelData(OutputStream outputStream,List<List<String>> data,String sheetName) throws IOException{
		XSSFWorkbook xssfWorkbook = writeSheetData(data,sheetName);
		xssfWorkbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		xssfWorkbook.close();
	}
	
	//写入Excel
	public static void writeExcelData(HttpServletResponse response,List<List<String>> data,String sheetName,String fileName) throws IOException{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		XSSFWorkbook xssfWorkbook = writeSheetData(data,sheetName);
		xssfWorkbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();
		xssfWorkbook.close();
	}
	
	//写入Excel
	public static void writeExcelObject(OutputStream outputStream,List<Object> data,String sheetName) throws Exception{
		XSSFWorkbook xssfWorkbook = writeSheetObject(data,sheetName);
		xssfWorkbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		xssfWorkbook.close();
	}
	
	//写入Excel
	public static void writeExcelObject(HttpServletResponse response,List<Object> data,String sheetName,String fileName) throws Exception{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		XSSFWorkbook xssfWorkbook = writeSheetObject(data,sheetName);
		xssfWorkbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();
		xssfWorkbook.close();
	}
	
	//读取每个sheet里的数据
	private static List<List<String>> readSheet(XSSFSheet xssfSheet){
		int rows = xssfSheet.getPhysicalNumberOfRows();
		List<List<String>> rowList = new ArrayList<>();
		for(int i=0;i<rows;i++){//遍历每一行
			XSSFRow row = xssfSheet.getRow(i);
			if(row==null){
				continue;
			}
			int cells = row.getPhysicalNumberOfCells();
			List<String> cellList = new ArrayList<>();
			for(int j=0;j<cells;j++){//遍历每一列
				XSSFCell cell = row.getCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//new Double("1.0").intValue()
				String cellValue = cell.getStringCellValue();
				cellList.add(cellValue);
			}
			rowList.add(cellList);
		}
		return rowList;
	}
	
	//读取每个sheet里的数据
	private static List<?> readSheet(XSSFSheet xssfSheet,Map<Integer,String> map,Class<?> objectClass) throws Exception{
		int rows = xssfSheet.getPhysicalNumberOfRows();
		List<Object> rowList = new ArrayList<>();
		for(int i=0;i<rows;i++){//遍历每一行
			Object target = objectClass.newInstance();
			XSSFRow row = xssfSheet.getRow(i);
			if(row==null){
				continue;
			}
			Set<Integer> set = map.keySet();
			for(Integer each:set){
				XSSFCell cell = row.getCell(each);
				if(cell==null){
					continue;
				}
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String fieldName = map.get(each);
				Class<?> fieldType = objectClass.getDeclaredField(fieldName).getType();
				fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
				Object value = "";
				try{
					if("java.lang.Double".equals(fieldType.getName())){
						value = new Double(cell.toString());
						target.getClass().getDeclaredMethod("set"+fieldName,Double.class).invoke(target, value);
					}else if("java.lang.Integer".equals(fieldType.getName())){
						value = new Double(cell.toString()).intValue();
						target.getClass().getDeclaredMethod("set"+fieldName,Integer.class).invoke(target, value);
					}else if("java.lang.Float".equals(fieldType.getName())){
						value = new Float(cell.toString());
						target.getClass().getDeclaredMethod("set"+fieldName,Float.class).invoke(target, value);
					}else{
						value = cell.toString();
						target.getClass().getDeclaredMethod("set"+fieldName,String.class).invoke(target, value);
					}
				}catch(Exception e){
					//do nothing
				}
			}
			rowList.add(target);
		}
		return rowList;
	}
	
	//写入每个sheet里的数据
	private static XSSFWorkbook writeSheetData(List<List<String>> data,String sheetName){
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		XSSFSheet xssfSheet = xssfWorkbook.createSheet(sheetName);
		XSSFRow[] rows = new XSSFRow[data.size()];
		for(int i=0;i<data.size();i++){
			List<String> columns = data.get(i);
			rows[i] = xssfSheet.createRow(i);
			//xssfSheet.setDefaultColumnWidth(columnWidth);//设置列的长度
			XSSFCell[] cells = new XSSFCell[columns.size()];
			for(int j=0;j<columns.size();j++){
				cells[j] = rows[i].createCell(j);
				//cells[j].setCellStyle(XSSFCellStyle);
				cells[j].setCellValue(columns.get(j));
			}
		}
		return xssfWorkbook;
	}
	
	//写入每个sheet里的数据
	private static XSSFWorkbook writeSheetObject(List<Object> data,String sheetName) throws Exception {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		XSSFSheet xssfSheet = xssfWorkbook.createSheet(sheetName);
		XSSFRow[] rows = new XSSFRow[data.size()];
		for(int i=0;i<data.size();i++){
			Object classes = data.get(i);
			Object target = classes.getClass().newInstance();
			Field[] fields = target.getClass().getDeclaredFields();
			rows[i] = xssfSheet.createRow(i);
			//xssfSheet.setDefaultColumnWidth(columnWidth);//设置列的长度
			XSSFCell[] cells = new XSSFCell[fields.length];
			for(int j=0;j<fields.length;j++){
				String fieldName = fields[j].getName();
				fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
				Method method = target.getClass().getDeclaredMethod("get"+fieldName,new Class[]{});
				cells[j] = rows[i].createCell(j);
				Object value = method.invoke(classes,new Object[]{});
				if(value instanceof Double){
					cells[j].setCellValue(new Double(value.toString()));
				}else if(value instanceof Integer){
					cells[j].setCellValue(new Double(value.toString()).intValue());
				}else if(value instanceof Float){
					cells[j].setCellValue(new Double(value.toString()));
				}else{
					cells[j].setCellValue(value.toString());
				}
				//cells[j].setCellStyle(XSSFCellStyle);
			}
		}
		return xssfWorkbook;
	}

}
