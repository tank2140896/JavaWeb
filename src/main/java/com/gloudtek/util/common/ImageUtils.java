package com.gloudtek.util.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static final String GIF  = "gif";//图形交换格式
	public static final String JPG  = "jpg";//联合照片专家组
	public static final String JPEG = "jpeg";//联合照片专家组
	public static final String BMP  = "bmp";//英文Bitmap(位图)的简写,它是Windows操作系统中的标准图像文件格式
	public static final String PNG  = "png";//可移植网络图形
	public static final String PSD  = "psd";//Photoshop的专用格式Photoshop


	/**
	 * 缩放图像(按比例缩放)
	 * @param srcImg 源图片路径
	 * @param outImg 缩放后输出的图片路径
	 * @param scale 缩放倍数,如2就是2倍
	 * @param flag 缩放选择:true 放大;false 缩小
	 * @param imageType 图片类型
	 */
	public static void scale(File srcImg, File outImg, int scale, boolean flag, String imageType) throws Exception {
		BufferedImage src = ImageIO.read(srcImg);//读入文件
		int width = src.getWidth();//得到源图宽
		int height = src.getHeight();//得到源图长
		if (flag) {//放大
			width = width * scale;
			height = height * scale;
		} else {//缩小
			width = width / scale;
			height = height / scale;
		}
		Image image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null);//绘制缩小后的图
		g.dispose();
		ImageIO.write(tag, imageType, outImg);//输出到文件流
	}

	/**
	 * 缩放图像(按高度和宽度缩放)
	 * 不按比例缩放,图片会失真
	 * @param srcImg 源图像文件
	 * @param outImg 缩放后的图像
	 * @param height 缩放后的高度
	 * @param width 缩放后的宽度
	 * @param flag 比例不对时是否需要补白:true为补白;false为不补白
	 * @param imageType 图片类型
	 */
	public static void scale(File srcImg, File outImg, int height, int width, boolean flag, String imageType) throws Exception {
		double ratio = 0.0;//缩放比例
		BufferedImage bi = ImageIO.read(srcImg);
		Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		//计算比例
		if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
			if (bi.getHeight() > bi.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / bi.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / bi.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			itemp = op.filter(bi, null);
		}
		if (flag) {//补白
			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			if (width == itemp.getWidth(null)){
				g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
			}else{
				g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
			}
			g.dispose();
			itemp = image;
		}
		ImageIO.write((BufferedImage) itemp, imageType,outImg);
	}

	//根据指定宽度压缩图片
	public final static void scaleByWidth(File srcImg, File outImg,int width, String imageType) throws Exception {
		BufferedImage bi = ImageIO.read(srcImg);
		int imageWidth = bi.getWidth();
		float sacle = (width * 1.0f) / (imageWidth * 1.0f);
		int height = (int) (bi.getHeight() * sacle);
		Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		//宽度缩放比
		double widthRatio = (new Integer(width)).doubleValue() / bi.getWidth();
		//高度缩放比
		double heightRatio = (new Integer(height)).doubleValue() / bi.getHeight();
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(widthRatio, heightRatio),null);
		itemp = op.filter(bi, null);
		ImageIO.write((BufferedImage) itemp, imageType, outImg);
	}

	//缩放图像(按高度和宽度缩放)(true为补白;false为不补白)
	public static byte[] scale(byte[] img, int height, int width,boolean flag,String imageType) throws Exception{
		double ratio = 0.0; //缩放比例
		InputStream inputStream = new ByteArrayInputStream(img);
		BufferedImage bi = ImageIO.read(inputStream);
		Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		//计算比例
		if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
			if (bi.getHeight() > bi.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / bi.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / bi.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			itemp = op.filter(bi, null);
		}
		if (flag) {//补白
			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			if (width == itemp.getWidth(null)){
				g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),Color.white, null);
			}else{
				g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,itemp.getWidth(null), itemp.getHeight(null),Color.white, null);
			}
			g.dispose();
			itemp = image;
		}
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		ImageIO.write((BufferedImage) itemp, imageType, outputstream);
		return outputstream.toByteArray();
	}

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * @param srcImg 源图像地址
	 * @param outImg 切片后的图像地址
	 * @param x 目标切片起点坐标X
	 * @param y 目标切片起点坐标Y
	 * @param width 目标切片宽度
	 * @param height 目标切片高度
	 * @param imageType 图片类型
	 */
	public static void cut(File srcImg, File outImg, int x, int y, int width, int height,String imageType) throws Exception{
		//读取源图像
		BufferedImage bi = ImageIO.read(srcImg);
		int srcWidth = bi.getHeight();//源图宽度
		int srcHeight = bi.getWidth();//源图高度
		if (srcWidth > 0 && srcHeight > 0) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_DEFAULT);
			//四个参数分别为图像起点坐标和宽高
			//即:CropImageFilter(int x,int y,int width,int height)
			ImageFilter cropFilter = new CropImageFilter(x, y, width,height);
			Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, width, height, null);//绘制切割后的图
			g.dispose();
			//输出为文件
			ImageIO.write(tag, imageType, outImg);
		}
	}

	/**
	 * 图像切割(指定切片的行数和列数)
	 * @param srcImage 源图像地址
	 * @param outImg 切片目标地址
	 * @param rows 目标切片行数.默认2,必须是范围 [1,20]之内
	 * @param cols 目标切片列数.默认2,必须是范围 [1,20]之内
	 * @param imageType 图片类型
	 */
	public static void cut2(String srcImage, String outImg, int rows, int cols,String imageType) throws Exception{
		if (rows <= 0 || rows > 20)
			rows = 2;//切片行数
		if (cols <= 0 || cols > 20)
			cols = 2;//切片列数
		//读取源图像
		BufferedImage bi = ImageIO.read(new File(srcImage));
		int srcWidth = bi.getHeight();//源图宽度
		int srcHeight = bi.getWidth();//源图高度
		if (srcWidth > 0 && srcHeight > 0) {
			Image img;
			ImageFilter cropFilter;
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			int destWidth = srcWidth;//每张切片的宽度
			int destHeight = srcHeight;//每张切片的高度
			// 计算切片的宽度和高度
			if (srcWidth % cols == 0) {
				destWidth = srcWidth / cols;
			} else {
				destWidth = (int) Math.floor(srcWidth / cols) + 1;
			}
			if (srcHeight % rows == 0) {
				destHeight = srcHeight / rows;
			} else {
				destHeight = (int) Math.floor(srcWidth / rows) + 1;
			}
			//循环建立切片
			//改进的想法:是否可用多线程加快切割速度
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					//四个参数分别为图像起点坐标和宽高
					//即:CropImageFilter(int x,int y,int width,int height)
					cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
					img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(),cropFilter));
					BufferedImage tag = new BufferedImage(destWidth,destHeight, BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(img, 0, 0, null);//绘制缩小后的图
					g.dispose();
					//输出为文件
					ImageIO.write(tag, imageType, new File(outImg));
				}
			}
		}
	}

	/**
	 * 图像切割(指定切片的宽度和高度)
	 * @param srcImage 源图像地址
	 * @param outImage 切片目标地址
	 * @param destWidth 目标切片宽度。默认200
	 * @param destHeight 目标切片高度。默认150
	 * @param imageType 图片类型
	 */
	public static void cut3(String srcImage, String outImage, int destWidth, int destHeight,String imageType) throws Exception{
		if (destWidth <= 0)
			destWidth = 200;//切片宽度
		if (destHeight <= 0)
			destHeight = 150;//切片高度
		//读取源图像
		BufferedImage bi = ImageIO.read(new File(srcImage));
		int srcWidth = bi.getHeight();//源图宽度
		int srcHeight = bi.getWidth();//源图高度
		if (srcWidth > destWidth && srcHeight > destHeight) {
			Image img;
			ImageFilter cropFilter;
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			int cols = 0;//切片横向数量
			int rows = 0;//切片纵向数量
			// 计算切片的横向和纵向数量
			if (srcWidth % destWidth == 0) {
				cols = srcWidth / destWidth;
			} else {
				cols = (int) Math.floor(srcWidth / destWidth) + 1;
			}
			if (srcHeight % destHeight == 0) {
				rows = srcHeight / destHeight;
			} else {
				rows = (int) Math.floor(srcHeight / destHeight) + 1;
			}
			//循环建立切片
			//改进的想法:是否可用多线程加快切割速度
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					//四个参数分别为图像起点坐标和宽高
					//即:CropImageFilter(int x,int y,int width,int height)
					cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
					img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(),cropFilter));
					BufferedImage tag = new BufferedImage(destWidth,destHeight, BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(img, 0, 0, null);//绘制缩小后的图
					g.dispose();
					//输出为文件
					ImageIO.write(tag, imageType, new File(outImage));
				}
			}
		}
	}

	/**
	 * 图像类型转换:GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * @param srcImage 源图像地址
	 * @param formatName 包含格式非正式名称的 String:如JPG、JPEG、GIF等
	 * @param outImage 目标图像地址
	 */
	public static void convert(String srcImage, String formatName,String outImage) throws Exception{
		File f = new File(srcImage);
		f.canRead();
		f.canWrite();
		BufferedImage src = ImageIO.read(f);
		ImageIO.write(src, formatName, new File(outImage));
	}

	/**
	 * 彩色转为黑白
	 * @param srcImage 源图像地址
	 * @param outImage 目标图像地址
	 */
	public static void gray(String srcImage, String outImage) throws Exception{
		BufferedImage src = ImageIO.read(new File(srcImage));
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		src = op.filter(src, null);
		ImageIO.write(src, "JPEG", new File(outImage));
	}

	/**
	 * 给图片添加文字水印
	 * @param pressText 水印文字
	 * @param srcImage 源图像地址
	 * @param outImage 目标图像地址
	 * @param fontName 水印的字体名称
	 * @param fontStyle 水印的字体样式
	 * @param color 水印的字体颜色
	 * @param fontSize 水印的字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度:alpha 必须是范围 [0.0, 1.0] 之内(包含边界值)的一个浮点数字
	 * @param imageType 图片类型
	 */
	public static void pressText(String pressText, String srcImageFile,String destImageFile, 
			                     String fontName, int fontStyle, Color color,int fontSize, 
			                     int x, int y, float alpha,String imageType) throws Exception{
		File img = new File(srcImageFile);
		Image src = ImageIO.read(img);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		//在指定坐标绘制水印文字
		g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
		g.dispose();
		ImageIO.write((BufferedImage) image, imageType,new File(destImageFile));// 输出到文件流
	}

	/**
	 * 给图片添加文字水印
	 * @param pressText 水印文字
	 * @param srcImage 源图像地址
	 * @param outImage 目标图像地址
	 * @param fontName 字体名称
	 * @param fontStyle 字体样式
	 * @param color 字体颜色
	 * @param fontSize 字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度:alpha 必须是范围 [0.0, 1.0] 之内(包含边界值)的一个浮点数字
	 * @param imageType 图片类型
	 */
	public static void pressText2(String pressText, String srcImageFile,String destImageFile, 
			                            String fontName, int fontStyle, Color color,int fontSize, 
			                            int x, int y, float alpha,String imageType) throws Exception{
		File img = new File(srcImageFile);
		Image src = ImageIO.read(img);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		//在指定坐标绘制水印文字
		g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
		g.dispose();
		ImageIO.write((BufferedImage) image, imageType, new File(destImageFile));
	}

	/**
	 * 给图片添加图片水印
	 * @param pressImg 水印图片
	 * @param srcImage 源图像地址
	 * @param outImage 目标图像地址
	 * @param x 修正值.默认在中间
	 * @param y 修正值.默认在中间
	 * @param alpha 透明度:alpha 必须是范围 [0.0, 1.0] 之内(包含边界值)的一个浮点数字
	 * @param imageType 图片类型
	 */
	public static void pressImage(String pressImg, String srcImageFile,String destImageFile, 
								  int x, int y, float alpha,String imageType) throws Exception {
		File img = new File(srcImageFile);
		Image src = ImageIO.read(img);
		int wideth = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, wideth, height, null);
		//水印文件
		Image src_biao = ImageIO.read(new File(pressImg));
		int wideth_biao = src_biao.getWidth(null);
		int height_biao = src_biao.getHeight(null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
		//水印文件结束
		g.dispose();
		ImageIO.write((BufferedImage) image, imageType, new File(destImageFile));
	}

	//text的长度(一个中文算两个字符)
	private static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
	
}