package com.iotek.qq.bean;

public class Message {
	private int message_senderId;//发送人id
	private int message_recipientId;//接收人id
	private String message_content;//消息内容
	private String message_times;//发送时间
	private int message_state;//消息状态
	private int message_type;//消息类型
	
	public Message() {
		super();
	}

	public Message(int message_senderId, int message_recipientId,
			String message_content, String message_times, int message_state,
			int message_type) {
		super();
		this.message_senderId = message_senderId;
		this.message_recipientId = message_recipientId;
		this.message_content = message_content;
		this.message_times = message_times;
		this.message_state = message_state;
		this.message_type = message_type;
	}

	public int getMessage_senderId() {
		return message_senderId;
	}

	public void setMessage_senderId(int message_senderId) {
		this.message_senderId = message_senderId;
	}

	public int getMessage_recipientId() {
		return message_recipientId;
	}

	public void setMessage_recipientId(int message_recipientId) {
		this.message_recipientId = message_recipientId;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public String getMessage_times() {
		return message_times;
	}

	public void setMessage_times(String message_times) {
		this.message_times = message_times;
	}

	public int getMessage_state() {
		return message_state;
	}

	public void setMessage_state(int message_state) {
		this.message_state = message_state;
	}

	public int getMessage_type() {
		return message_type;
	}

	public void setMessage_type(int message_type) {
		this.message_type = message_type;
	}

	@Override
	public String toString() {
		return "Message [message_senderId=" + message_senderId
				+ ", message_recipientId=" + message_recipientId
				+ ", message_content=" + message_content + ", message_times="
				+ message_times + ", message_state=" + message_state
				+ ", message_type=" + message_type + "]";
	}

}
