package com.iotek.qq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.iotek.qq.bean.NearestContact;
import com.iotek.qq.bean.User;

public class NearestContactDao extends BaseDao {
	/**
	 * 查看未读消息
	 */
	public Map<String, String> selectUnreadMessage(User user) {
		List<NearestContact> uList = new ArrayList<NearestContact>();
		String boo = "false";
		Map map = new HashMap<String, String>();
		String sql = "select count(message_senderId)as sum_message ,a.* from (select message_senderId,b.user_nickName as user_nickName, " +
				"a.message_state from MESSAGE_INFO a,(select * from USER_INFO a,(select b.friends_toUid from USER_INFO a , FRIENDS_INFO b " +
				"where a.user_id=? and a.user_id = b.friends_uid) b where a.user_id = b.friends_toUid) b  where b.friends_toUid = a.message_senderId " +
				"and a.message_recipientId=?) a where message_state=0 group by message_senderId ";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUser_id());
		params.add(user.getUser_id());
		try {
			uList = this.operQuery(sql, params, NearestContact.class);
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
