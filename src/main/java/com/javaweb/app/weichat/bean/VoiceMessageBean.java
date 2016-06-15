package com.javaweb.app.weichat.bean;

//语音消息
public class VoiceMessageBean {
	
	public static final String TOUSERNAME = "ToUserName";
	public static final String FROMUSERNAME = "FromUserName";
	public static final String CREATETIME = "CreateTime";
	public static final String MSGTYPE = "MsgType";
	public static final String MEDIAID = "MediaId";
	public static final String FORMAT = "Format";
	public static final String MSGID = "MsgId";
	
	private String toUserName;
	
	private String fromUserName;
	
	private String createTime;
	
	private String msgType;
	
	private String mediaId;
	
	private String format;
	
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

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
