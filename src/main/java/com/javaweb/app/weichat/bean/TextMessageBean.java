package com.javaweb.app.weichat.bean;

//文本消息
public class TextMessageBean {
	
	public static final String TOUSERNAME = "ToUserName";
	public static final String FROMUSERNAME = "FromUserName";
	public static final String CREATETIME = "CreateTime";
	public static final String MSGTYPE = "MsgType";
	public static final String CONTENT = "Content";
	public static final String MSGID = "MsgId";
	
	private String toUserName;
	
	private String fromUserName;
	
	private String createTime;
	
	private String msgType;
	
	private String content;
	
	private String msgId;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
