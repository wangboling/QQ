package com.iotek.qq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.iotek.qq.bean.Message;


public class MessageDao extends BaseDao {
	/**
	 * �����Ϣ
	 * @param message
	 * @return
	 */

	public Map<String, Object> addMessage(Message message) {
		String sql = "insert into MESSAGE_INFO(message_senderId,message_recipientId,message_content,"
				+ "message_times,message_state)values(?,?,?,?,?)";
		Map map = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		params.add(message.getMessage_senderId());
		params.add(message.getMessage_recipientId());
		params.add(message.getMessage_content());
		params.add(message.getMessage_times());
		params.add(message.getMessage_state());
		Boolean boo = this.operUpdate(sql, params);
		map.put("result", boo);
		return map;
	}
	/**
	 * �����Ϣ
	 * @param message
	 * @return
	 */

	public Map<String, Object> addMessageRequest(Message message) {
		String sql = "insert into MESSAGE_INFO(message_senderId,message_recipientId,message_content,"
				+ "message_times,message_state,message_type)values(?,?,?,?,?,?)";
		Map map = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		params.add(message.getMessage_senderId());
		params.add(message.getMessage_recipientId());
		params.add(message.getMessage_content());
		params.add(message.getMessage_times());
		params.add(message.getMessage_state());
		params.add(message.getMessage_type());
		Boolean boo = this.operUpdate(sql, params);
		map.put("result", boo);
		return map;
	}
	
	/**
	 * �鿴��Ϣ
	 */
	public Map<String, String> selectMessage(Message message) {
		List<Message> uList = new ArrayList<Message>();
		String boo = "false";
		Map map = new HashMap<String, String>();
		String sql = "select message_senderId,message_recipientId,message_content,message_times,message_state,message_type  from  MESSAGE_INFO  " +
				"where message_senderId=? and message_recipientId=?";
		List<Object> params = new ArrayList<Object>();
		params.add(message.getMessage_senderId());
		params.add(message.getMessage_recipientId());
		try {
			uList = this.operQuery(sql, params, Message.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (uList == null || uList.size() < 1) {
			boo = "false";
			map.put("result", boo);
			map.put("ObjList", uList);
			return map;
		}
		boo = "true";
		map.put("result", boo);
		map.put("ObjList", uList);
		return map;
	}
	/**
	 * �鿴��Ϣ
	 */
	public Map<String, String> selectMessageTwo(Message message) {
		List<Message> uList = new ArrayList<Message>();
		String boo = "false";
		Map map = new HashMap<String, String>();
		String sql = "select message_senderId,message_recipientId,message_content,message_times,message_state,message_type  from  MESSAGE_INFO  " +
				"where message_senderId=? and message_recipientId=? or message_senderId=? and message_recipientId=?";
		List<Object> params = new ArrayList<Object>();
		params.add(message.getMessage_senderId());
		params.add(message.getMessage_recipientId());
		params.add(message.getMessage_recipientId());
		params.add(message.getMessage_senderId());
		
		try {
			uList = this.operQuery(sql, params, Message.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (uList == null || uList.size() < 1) {
			boo = "false";
			map.put("result", boo);
			map.put("ObjList", uList);
			return map;
		}
		boo = "true";
		map.put("result", boo);
		map.put("ObjList", uList);
		return map;
	}
	/**
	 * �鿴��Ϣ
	 */
	public Map<String, String> selectMessageById(Message message) {
		List<Message> uList = new ArrayList<Message>();
		String boo = "false";
		Map map = new HashMap<String, String>();
		String sql = "select message_senderId,message_recipientId,message_content,message_times,message_state,message_type  from  MESSAGE_INFO  " +
				"where message_senderId=? and message_state=0 or message_recipientId=? and message_state=0";
		List<Object> params = new ArrayList<Object>();
		params.add(message.getMessage_senderId());
		params.add(message.getMessage_senderId());
		try {
			uList = this.operQuery(sql, params, Message.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (uList == null || uList.size() < 1) {
			boo = "false";
			map.put("result", boo);
			map.put("ObjList", uList);
			return map;
		}
		boo = "true";
		map.put("result", boo);
		map.put("ObjList", uList);
		return map;
	}
	
	/**
	 * �޸���Ϣ״̬
	 */
	public Map<String, String> updateMessageType(Message message){
		Map<String,String> map = new HashMap<String, String>();
		String sql = "update MESSAGE_INFO set message_state = ? where message_senderId=? and message_recipientId=?";
		List<Object> params = new ArrayList<Object>();
		params.add(message.getMessage_state());
		params.add(message.getMessage_senderId());
		params.add(message.getMessage_recipientId());
		boolean boo = this.operUpdate(sql, params);
		map.put("result", boo+"");
		return map;		
	}
}
