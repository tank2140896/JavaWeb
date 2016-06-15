package com.javaweb.exceptions;

//本项目中所有自定义的异常都将继承本类
public class JavaWebException extends Exception {

	private static final long serialVersionUID = 1L;

	public JavaWebException(){
		super();
	}
	
	public JavaWebException(String message){
		super(message);
	}
	
	public JavaWebException(Throwable cause) {
		super(cause);
	}
	
	public JavaWebException(String message, Throwable cause) {
		super(message, cause);
	}

}
