package com.javaweb.util.common;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSendUtil {
	
	private static final String SMTP_SERVER = "smtp.sina.com";
	
	private static final String SEND_EMAIL_ADDRESS = "xxx@sina.com";
	
	private static final String SEND_EMAIL_PASSWORD = "xxx";
	
	//简单邮件发送
	public static void sendMail(String sendTo,String subject,String content){
		MailSendUtil mailSendUtil = new MailSendUtil();
		mailSendUtil.new SendThread(sendTo, subject, content).start();
	}
	
	private class SendThread extends Thread {
		
		private String sendTo;
		
		private String subject;
		
		private String content;
		
		public SendThread(String sendTo,String subject,String content){
			this.sendTo = sendTo;
			this.subject = subject;
			this.content = content;
		}
		
		public void run(){
			
			try{
				Properties properties = new Properties();
				//不同的邮件服务器有不同的写法
				properties.put("mail.smtp.host", SMTP_SERVER);
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.port", "25");
				//第三方登录时,有时不是密码而是授权码,具体要看各邮箱的要求
				MyAuthenticator myauth = new MailSendUtil().new MyAuthenticator(SEND_EMAIL_ADDRESS, SEND_EMAIL_PASSWORD);
				//根据属性新建一个邮件会话
				Session mailSession = Session.getDefaultInstance(properties, myauth);
				//是否是开发调试模式
				mailSession.setDebug(false);
				//建立消息对象
				MimeMessage mailMessage = new MimeMessage(mailSession);
				//发件人
				mailMessage.setFrom(new InternetAddress(SEND_EMAIL_ADDRESS));
				//收件人
				mailMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(sendTo));
				//主题
				mailMessage.setSubject(subject);
				//发信时间
				mailMessage.setSentDate(new Date());
				mailMessage.setContent(content, "text/html;charset=utf-8");
				//发送
				Transport.send(mailMessage);
			}catch(Exception e){
				//do nothing
			}
		}
		
	}
	
	private class MyAuthenticator extends Authenticator {
		
		private String username;
		
		private String password;

		public MyAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
		
	}

}
