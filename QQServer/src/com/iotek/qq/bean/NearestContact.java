package com.iotek.qq.bean;
/**
 * �����ϵ��
 * @author xx
 *
 */
public class NearestContact {
	private int sum_message;//δ����Ϣ����
	private int message_senderId;//������id
	private String user_nickName;//�������ǳ�
	private int message_state;//��Ϣ״̬
	
	public NearestContact() {
		super();
	}

	public NearestContact(int sum_message, int message_senderId,
			String user_nickName, int message_state) {
		super();
		this.sum_message = sum_message;
		this.message_senderId = message_senderId;
		this.user_nickName = user_nickName;
		this.message_state = message_state;
	}

	public int getSum_message() {
		return sum_message;
	}

	public void setSum_message(int sum_message) {
		this.sum_message = sum_message;
	}

	public int getMessage_senderId() {
		return message_senderId;
	}

	public void setMessage_senderId(int message_senderId) {
		this.message_senderId = message_senderId;
	}

	public String getUser_nickName() {
		return user_nickName;
	}

	public void setUser_nickName(String user_nickName) {
		this.user_nickName = user_nickName;
	}

	public int getMessage_state() {
		return message_state;
	}

	public void setMessage_state(int message_state) {
		this.message_state = message_state;
	}

	@Override
	public String toString() {
		return "NearestContact [sum_message=" + sum_message
				+ ", message_senderId=" + message_senderId + ", user_nickName="
				+ user_nickName + ", message_state=" + message_state + "]";
	}
	
	
	

}
