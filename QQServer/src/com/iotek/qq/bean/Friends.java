package com.iotek.qq.bean;

public class Friends {
	private int friends_uid;//主动方用户id
	private int friends_toUid;//被动方用户ID
	private int friends_status;//关系状态
	
	public Friends() {
		super();
	}

	public Friends(int friends_uid, int friends_toUid, int friends_status) {
		super();
		this.friends_uid = friends_uid;
		this.friends_toUid = friends_toUid;
		this.friends_status = friends_status;
	}

	public int getFriends_uid() {
		return friends_uid;
	}

	public void setFriends_uid(int friends_uid) {
		this.friends_uid = friends_uid;
	}

	public int getFriends_toUid() {
		return friends_toUid;
	}

	public void setFriends_toUid(int friends_toUid) {
		this.friends_toUid = friends_toUid;
	}

	public int getFriends_status() {
		return friends_status;
	}

	public void setFriends_status(int friends_status) {
		this.friends_status = friends_status;
	}

	@Override
	public String toString() {
		return "Friends [friends_uid=" + friends_uid + ", friends_toUid="
				+ friends_toUid + ", friends_status=" + friends_status + "]";
	}
	
	
	
	
}
