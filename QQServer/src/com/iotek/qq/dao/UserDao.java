package com.iotek.qq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iotek.qq.bean.User;

public class UserDao extends BaseDao {
	/**
	 * ע���û�
	 */
	public boolean insertUser(User user) {
		String sql = "insert into USER_INFO(user_passWord,user_nickName,user_email)values(?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUser_passWord());
		params.add(user.getUser_nickName());
		params.add(user.getUser_email());
		boolean boo = this.operUpdate(sql, params);
		return boo;
	}
	
	/**
	 * ����id���
	 */
	public Map<String, String> maxUserId(User user){
		List<User> uList = new ArrayList<User>();
		String boo = "false";
		Map map = new HashMap<String, String>();
		String sql = "select max(user_id) as user_id from USER_INFO";
		System.out.println(sql);
		try {
			uList = this.operQuery(sql, null,User.class);
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
	 * ��¼����
	 * @param user
	 * @return
	 */
	public Map<String, String> loginUser(User user) {
		List<User> uList = new ArrayList<User>();
		String boo = "false";
		Map map = new HashMap<String, String>();
		String sql = "select user_id,user_name,user_nickName,user_email,user_gender,user_isVip,user_status,user_webcam  from  USER_INFO  " +
				"where user_id = ? and user_passWord = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUser_id());
		params.add(user.getUser_passWord());
		try {
			uList = this.operQuery(sql, params, User.class);
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
	 * ��¼����
	 * @param user
	 * @return
	 */
	public Map<String, String> getFriendByToId(User user) {
		List<User> uList = new ArrayList<User>();
		String boo = "false";
		Map map = new HashMap<String, String>();
		String sql = "select user_id,user_name,user_nickName,user_email,user_gender,user_isVip,user_status,user_webcam  from  USER_INFO  " +
				"where user_id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUser_id());
		
		try {
			uList = this.operQuery(sql, params, User.class);
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
	 * �޸��û��ǳ�
	 */
	public Map<String, String> updateNickName(User user){
		Map<String,String> map = new HashMap<String, String>();
		String sql = "update USER_INFO set user_nickName = ? where user_id = ? and user_passWord = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUser_nickName());
		params.add(user.getUser_id());
		params.add(user.getUser_passWord());
		boolean boo = this.operUpdate(sql, params);
		map.put("result", boo+"");
		return map;		
	}
}
