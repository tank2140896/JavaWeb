package com.gloudtek.util.common;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FileUtil {

    //递归创建文件夹
    public static void makeDirs(File file){
    	if(file.getParentFile().exists()){
    		file.mkdir();
    	}else{
    		makeDirs(file.getParentFile());
    		file.mkdir();//递归收尾
    	}
    }
    
	//读取文件名
	public static List<String> readFileName(String filePath){
		List<String> fileNameList = new ArrayList<String>();
		File f = new File(filePath);
		File file[] = f.listFiles();
		for(File ff:file){
			fileNameList.add(ff.getName());
		}
		return fileNameList;
	}
    
	//写文件
	public static void writeFile(InputStream is,Path path) throws Exception{
		OutputStream os = Files.newOutputStream(path);
		int bytesRead = 0;
		byte[] buffer = new byte[1024];//1KB
		while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		if(os!=null){
			os.close();
		}
		if(is!=null){
			is.close();
		}
	}
	
	//下文件
	public static void downloadFile(OutputStream os,Path path) throws Exception{
		InputStream is = Files.newInputStream(path);
		int bytesRead = 0;
		byte[] buffer = new byte[1024];//1KB
		while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		if(os!=null){
			os.close();
		}
		if(is!=null){
			is.close();
		}
	}
	
	//追加文件并设置编码
	public static void writeFileAppend(String context,File f) throws Exception{
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),"UTF-8"));  
		bw.write(context);
		bw.close();
	}
	
	//序列化输出
	//实体类的属性加上transient表示不会被序列化,如:private transient String name;
	public static void writeSerialize(String filePath,Object obj) throws Exception{
		Path p = Paths.get(filePath);
		if(!Files.exists(p)){
			Files.createFile(p);
		}
		BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(p));
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		/** 简单序列化加密
		Student s = (Student)obj;
		int age = s.getAge();
		age = age << 2;
		s.setAge(age);
		oos.writeObject(s);
		*/
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		bos.flush();
		bos.close();
	}
	
	//序列化读取
	public static Object readSerialize(String filePath) throws Exception{
		Path p = Paths.get(filePath);
		if(Files.isReadable(p)){
			BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(p));
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object obj = ois.readObject();
			ois.close();
			bis.close();
			return obj;
		}
		return null;
	}
	
	//读取XML文件
	public static Map<String,String> getSqlXmlContent(String filePath) throws Exception{
		SAXReader sr = new SAXReader();
		Document d = sr.read(new File(filePath));
		Element e = d.getRootElement();
		Iterator<?> i = e.elementIterator();
		Map<String,String> map = new HashMap<String,String>();
		while(i.hasNext()){
			Element e2 = (Element)i.next();
			Iterator<?> i2 = e2.elementIterator();
			while(i2.hasNext()){
				String key = ((Element)i2.next()).getStringValue();
				String value = ((Element)i2.next()).getStringValue();
				map.put(key, value);
			}
		}
		return map;
	}
	
	//读Excel
	public static void readExcel(String filePth) throws Exception {
		InputStream is = new FileInputStream(filePth);
		//创建工作薄
		//XSSFWorkbook hwb = new XSSFWorkbook(is);
		HSSFWorkbook hwb = new HSSFWorkbook(new POIFSFileSystem(is));
		//得到sheet
		for (int i = 0; i < hwb.getNumberOfSheets(); i++) {
			HSSFSheet sheet = hwb.getSheetAt(i);
			int rows = sheet.getPhysicalNumberOfRows();
			//遍历每一行
			for (int j = 0; j < rows; j++) {
				HSSFRow hr = sheet.getRow(j);
				Iterator<?> it = hr.iterator();
				while(it.hasNext()){
					String context = it.next().toString();
					System.out.println(context);
				}
			}
		}
		hwb.close();
	}
	
	//写Excel
	public static void writeExcel(HttpServletResponse response,List<String> list) throws Exception {
		response.setContentType("application/vnd.ms-excel");//文件格式,此处设置为excel
		response.setHeader("Content-Disposition","attachment;filename=file.xls");//此处设置了下载文件的默认名称
		ServletOutputStream sos = response.getOutputStream();
	    //创建一个新的excel
		XSSFWorkbook wb = new XSSFWorkbook();//XSSFWorkbook
		/**
		 * 采用现成Excel模板
		 * 用这种方式得先保证每个cell有值,不然会报空指针
		 * 有时我们用row.getCell(i)会得到null,那么此时就要用Iterator<Cell> it = row.cellIterator();
		 * XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File("D://a.xlsx")));
		 * XSSFSheet sheet = wb.getSheet("Sheet1");
		 * row[i] = sheet.getRow(i);
		 * headerCell[j] = row[i].getCell(j);
		 */
		//创建sheet页
		XSSFSheet sheet = wb.createSheet("sheet1");//sheet名
		//创建行数
		XSSFRow[] row = new XSSFRow[list.size()];
		//插入数据
		for (int i = 0; i < row.length; i++) {
			row[i] = sheet.createRow(i);
			sheet.setDefaultColumnWidth(30);//设置列的长度
			String info[] = list.get(i).split(",");
			XSSFCell[] headerCell = new XSSFCell[info.length];
			for (int j = 0; j < headerCell.length; j++) {
				headerCell[j] = row[i].createCell(j);
				headerCell[j].setCellValue(new XSSFRichTextString(info[j]));
				/**设置模板样式*/
				//headerCell[j].setCellStyle(setStyle(wb));
			}
		}
		wb.write(sos);
		wb.close();
	    sos.flush();
	    sos.close();
	    response.flushBuffer();
	}
	
	//设置Excel模板
	public static XSSFCellStyle setStyle(XSSFWorkbook workbook) {  
        //设置字体;  
        XSSFFont font = workbook.createFont();  
        //设置字体大小;  
        font.setFontHeightInPoints((short) 20);  
        //设置字体名字;  
        font.setFontName("Courier New");  
        //font.setItalic(true);  
        //font.setStrikeout(true);  
        //设置样式;  
        XSSFCellStyle style = workbook.createCellStyle();  
        //设置底边框;  
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
        //设置底边框颜色;  
        style.setBottomBorderColor(new XSSFColor(Color.BLACK));  
        //设置左边框;  
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
        //设置左边框颜色;  
        style.setLeftBorderColor(new XSSFColor(Color.BLACK));  
        //设置右边框;  
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
        //设置右边框颜色;  
        style.setRightBorderColor(new XSSFColor(Color.BLACK));  
        //设置顶边框;  
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
        //设置顶边框颜色;  
        style.setTopBorderColor(new XSSFColor(Color.BLACK));  
        //在样式用应用设置的字体;  
        style.setFont(font);  
        //设置自动换行;  
        style.setWrapText(false);  
        //设置水平对齐的样式为居中对齐;  
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
        //设置垂直对齐的样式为居中对齐;  
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
        return style;  
    }
	
}
