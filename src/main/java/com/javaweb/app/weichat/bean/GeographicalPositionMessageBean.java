package com.javaweb.app.weichat.bean;

//地理位置消息 
public class GeographicalPositionMessageBean {
	
	public static final String TOUSERNAME = "ToUserName";
	public static final String FROMUSERNAME = "FromUserName";
	public static final String CREATETIME = "CreateTime";
	public static final String MSGTYPE = "MsgType";
	public static final String MEDIAID = "Location_X";
	public static final String THUMBMEDIAID = "Location_Y";
	public static final String SCALE = "Scale";
	public static final String LABEL = "Label";
	public static final String MSGID = "MsgId";
	
	private String toUserName;
	
	private String fromUserName;
	
	private String createTime;
	
	private String msgType;
	
	private String locationX;
	
	private String locationY;
	
	private String scale;
	
	private String label;
	
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

	public String getLocationX() {
		return locationX;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	public String getLocationY() {
		return locationY;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
