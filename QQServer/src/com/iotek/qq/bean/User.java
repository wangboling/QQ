package com.iotek.qq.bean;

public class User {
	private int user_id;//用户id
	private String user_name;//用户名字
	private String user_passWord;//用户密码
	private String user_nickName;//用户昵称
	private String user_email;//用户邮箱
	private String user_gender;//用户性别
	private int user_isVip;//是否会员
	private String user_status;//在线状态
	private int user_webcam;//是否有摄像头

	public User() {
		super();
	}

	public User(int user_id, String user_name, String user_passWord,
			String user_nickName, String user_email, String user_gender,
			int user_isVip, String user_status, int user_webcam) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_passWord = user_passWord;
		this.user_nickName = user_nickName;
		this.user_email = user_email;
		this.user_gender = user_gender;
		this.user_isVip = user_isVip;
		this.user_status = user_status;
		this.user_webcam = user_webcam;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_passWord() {
		return user_passWord;
	}

	public void setUser_passWord(String user_passWord) {
		this.user_passWord = user_passWord;
	}

	public String getUser_nickName() {
		return user_nickName;
	}

	public void setUser_nickName(String user_nickName) {
		this.user_nickName = user_nickName;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}

	public int getUser_isVip() {
		return user_isVip;
	}

	public void setUser_isVip(int user_isVip) {
		this.user_isVip = user_isVip;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public int getUser_webcam() {
		return user_webcam;
	}

	public void setUser_webcam(int user_webcam) {
		this.user_webcam = user_webcam;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name
				+ ", user_passWord=" + user_passWord + ", user_nickName="
				+ user_nickName + ", user_email=" + user_email
				+ ", user_gender=" + user_gender + ", user_isVip=" + user_isVip
				+ ", user_status=" + user_status + ", user_webcam="
				+ user_webcam + "]";
	}


}
