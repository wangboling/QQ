package com.iotek.qq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.iotek.qq.bean.Friends;

public class FriendsDao extends BaseDao{
	/**
	 * ��Ӻ�����Ϣ
	 */
	public Map<String, Object> addFriends(Friends friends){
		String sql = "insert into FRIENDS_INFO(friends_uid,friends_toUid,friends_status) values(?,?,?)";
		Map map = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		params.add(friends.getFriends_uid());
		params.add(friends.getFriends_toUid());
		params.add(friends.getFriends_status());
		Boolean boo = this.operUpdate(sql, params);
		map.put("result", boo);
		return map;	
	}
	/**
	 * ��Ӻ�����Ϣ
	 */
	public Map<String, Object> delFriends(Friends friends){
		String sql = "delete from FRIENDS_INFO where friends_toUid=? and friends_uid=? or friends_uid=? and friends_toUid=?";
		Map map = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
	
		params.add(friends.getFriends_toUid());
		params.add(friends.getFriends_uid());
		params.add(friends.getFriends_toUid());
		params.add(friends.getFriends_uid());
		Boolean boo = this.operUpdate(sql, params);
		map.put("result", boo);
		return map;	
	}
	
	/**
	 * �鿴������Ϣ
	 */
	public Map<String, String> selectFriends(Friends friends){
		List<Friends> uList = new ArrayList<Friends>();
		String boo = "false";
		Map map = new HashMap<String, String>();
		String sql = "select friends_uid,friends_toUid,friends_status from FRIENDS_INFO where friends_uid = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(friends.getFriends_uid());
		try {
			uList = this.operQuery(sql, params, Friends.class);
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
	

}
