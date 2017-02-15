package com.iotek.fgs.qq;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.iotek.fgs.connweb.Connection;
import com.iotek.fgs.entity.Friend;
import com.iotek.fgs.entity.Msg;
import com.iotek.fgs.entity.User;
import com.iotek.fgs.list.ChatListAdapter;
import com.iotek.fgs.list.ExpandAbleAdapter;
import com.iotek.fgs.list.MsgListAdapter;
import com.iotek.fgs.util.GenUtil;
import com.iotek.fgs.util.InfoFriend;
import com.iotek.fgs.util.InfoMsg;
import com.iotek.fgs.util.InfoUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
	SharedPreferences sp;
	Context context;
	ImageView ivHead,ivMenue;
	ListView lv;
	UpdateMsgTask upTask;
	QMsgTask qMsgTask;
	UserTask userTask;
	FriendTask fTask;
	int k = 0;
	int uid;
	User user1 = new User();
	List<Friend> fList = new ArrayList<>();
	List<User> uList = new ArrayList<User>();
	List<Msg> msgList = new ArrayList<Msg>();
	List<List<Msg>> lists = new ArrayList<List<Msg>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.messageview, container,false);
		
	}

	@Override
	public void onAttach(Context context) {
		this.context = context;
		super.onAttach(context);
	}

	@Override
	public void onViewCreated(final View view, Bundle savedInstanceState) {
		sp =context.getSharedPreferences("qqlogin",Context.MODE_APPEND);
		GetUserFromSp.getUser(sp,user1);
		ivHead = (ImageView)view.findViewById(R.id.message_iv_head);
		ivMenue = (ImageView)view.findViewById(R.id.message_iv_menue);
		lv = (ListView)view.findViewById(R.id.messageview_lv);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				uid = uList.get(position).getUser_id();
				String fUrl = "http://192.168.253.3:8080/qqServer/GetFriendsServlet.action?friends_uid=" + user1.getUser_id();
				if (fTask == null) {
					fTask = new FriendTask(fList);
					fTask.execute(fUrl);
					fTask = null;
				}
				String upUrl = "http://192.168.253.3:8080/qqServer/UpdateMessageStateServlet.action?message_state=1&message_senderId="+user1.getUser_id()+"&message_recipientId="+uid;
				String upUrl2 = "http://192.168.253.3:8080/qqServer/UpdateMessageStateServlet.action?message_state=1&message_senderId="+uid+"&message_recipientId="+user1.getUser_id();
				if (upTask == null){
					upTask = new UpdateMsgTask();
					upTask.execute(upUrl);
					upTask = null;
				}
				if (upTask == null){
					upTask = new UpdateMsgTask();
					upTask.execute(upUrl2);
					upTask = null;
				}
			}
		});

		switch (user1.getUser_id()){
			case 1:
				ivHead.setImageResource(R.drawable.ic_tab_mine);
				break;
			case 2:
				ivHead.setImageResource(R.drawable.ic_map_route_walk);
				break;
			default:
				ivHead.setImageResource(R.drawable.ic_tab_mine_active);
		}
		MyThread myThread = new MyThread();
		Thread thread = new Thread(myThread);
		thread.start();


		super.onViewCreated(view, savedInstanceState);
	}
	private class MyThread implements Runnable{

		@Override
		public void run() {
			while (true){
				String qMUrl = "http://192.168.253.3:8080/qqServer/GetMessageServletById.action?message_senderId="+user1.getUser_id()+"&message_recipientId="+user1.getUser_id();
				if (qMsgTask == null){
					qMsgTask = new QMsgTask(msgList);
					qMsgTask.execute(qMUrl);
					qMsgTask = null;
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lists = null;
				lists = new ArrayList<>();
				uList = null;
				uList = new ArrayList<>();
			}
		}
	}
	private class QMsgTask extends AsyncTask<String, Void, List<Msg>> {

		List<Msg> msgList;

		public QMsgTask(List<Msg> msgList) {
			this.msgList = msgList;

		}


		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected List<Msg> doInBackground(String... arg0) {

			try {
				String data = Connection.getData(arg0[0]);
				JSONObject obj = new JSONObject(data);
				JSONArray array = obj.getJSONArray(InfoUsers.OBJLIST);
				for (int i = 0; i < array.length(); i++) {
					obj = array.getJSONObject(i);
					Msg msg = new Msg();

					msg.setMessage_senderId(obj.getInt(InfoMsg.MSG_SID));
					msg.setMessage_recipiendtId(obj.getInt(InfoMsg.MSG_RID));
					msg.setMessage_content(obj.getString(InfoMsg.MSG_CONTENT));
					msg.setMessage_state(obj.getInt(InfoMsg.MSG_STATE));
					msg.setMessage_temes(obj.getString(InfoMsg.MSG_TIMES));
					msg.setMessage_type(obj.getInt(InfoMsg.MSG_TYPE));

					msgList.add(msg);

				}
			} catch (MalformedURLException e) {
				Log.i("Test", "MalFO");
				e.printStackTrace();
			} catch (IOException e) {
				Log.i("Test", "IOE");
				e.printStackTrace();
			} catch (JSONException e) {
				Log.i("Test", "Json");
				e.printStackTrace();
			}
			return msgList;
		}

		@Override
		protected void onPostExecute(List<Msg> msgList) {
			int uid;
			for (int i = 0; i < msgList.size(); i++) {
				List<Msg> list = new ArrayList<Msg>();
				for (int j = 0; j < msgList.size(); j++) {
					if (msgList.get(j).getMessage_senderId() == i || msgList.get(j).getMessage_recipiendtId() == i) {
						list.add(msgList.get(j));
					}
				}
				if(list.size()!=0) {
					lists.add(list);
				}
			}

			for (int m = 0; m < lists.size(); m++) {
				List<Msg> list = new ArrayList<>();
				list = lists.get(m);
					if (list.get(0).getMessage_recipiendtId() == user1.getUser_id()){
						uid = list.get(0).getMessage_senderId();
					}else {
						uid = list.get(0).getMessage_recipiendtId();
					}

				String furl = "http://192.168.253.3:8080/qqServer/GetFriendByToIdServlet.action?user_id=" + uid;

				if (userTask == null) {
					userTask = new UserTask();
					userTask.execute(furl);
					userTask =null;
				}
			}
			super.onPostExecute(msgList);
		}

	}
	private  class UserTask extends AsyncTask<String, Void, User> {
		User user =new User();
		@Override
		protected User doInBackground(String... arg0) {

			try {
				String data = Connection.getData(arg0[0]);
				JSONObject obj = new JSONObject(data);
				JSONArray array = obj.getJSONArray(InfoUsers.OBJLIST);
				for (int i = 0; i < array.length(); i++) {
					obj = array.getJSONObject(i);

					user.setUser_id(obj.getInt(InfoUsers.USER_ID));
					user.setUser_email(obj.getString(InfoUsers.USER_EMAIL));
					user.setUser_isVip(obj.getInt(InfoUsers.USER_ISVIP));
					user.setUser_webcam(obj.getInt(InfoUsers.USER_WEBCAM));
					user.setUser_nickName(obj.getString(InfoUsers.USER_NICKNAME));

					return user;

				}
			} catch (MalformedURLException e) {
				Log.i("Test", "MalFO");
				e.printStackTrace();
			} catch (IOException e) {
				Log.i("Test", "IOE");
				e.printStackTrace();
			} catch (JSONException e) {
				Log.i("Test", "Json");
				e.printStackTrace();
			}
			return  null;
		}

		@Override
		protected void onPostExecute(User user) {
			uList.add(user);
			k++;
			if(k == lists.size()) {
				lv.setAdapter(new MsgListAdapter(context,lists,uList));
				k=0;
			}
			super.onPostExecute(user);
		}
	}
	private class FriendTask extends AsyncTask<String, Void, List<Friend>> {
		List<Friend> list;
		public FriendTask(List<Friend> list){
			this.list = list;
		}
		@Override
		protected List<Friend> doInBackground(String... arg0) {

			try {
				String data = Connection.getData(arg0[0]);
				JSONObject obj = new JSONObject(data);
				JSONArray array = obj.getJSONArray(InfoUsers.OBJLIST);
				for(int i=0;i<array.length();i++){
					obj = array.getJSONObject(i);
					Friend friend = new Friend();
					friend.setFriends_uid(obj.getInt(InfoFriend.FRIENDS_UID));
					friend.setFriends_toUid(obj.getInt(InfoFriend.FRIENDS_TOUID));
					friend.setFriends_status(obj.getInt(InfoFriend.FRIENDS_STATUS));

					list.add(friend);
				}
			} catch (MalformedURLException e) {
				Log.i("Test", "MalFO");
				e.printStackTrace();
			} catch (IOException e) {
				Log.i("Test", "IOE");
				Intent intent = new Intent(GenUtil.CONNFAIL);
				startActivity(intent);
				e.printStackTrace();
			} catch (JSONException e) {
				Log.i("Test", "Json");
				e.printStackTrace();
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<Friend> list) {
			boolean flag = false;
			if(list!=null){
				for (int i=0;i<list.size();i++){
					Friend friend = list.get(i);
					int friendUid = friend.getFriends_toUid();
					if (friendUid == uid){
						flag = true;
					}
				}
				if (!flag){
					Intent intent = new Intent(GenUtil.REQUEST);
					intent.putExtra("request",uid);
					startActivity(intent);
				}else {
					Intent intent = new Intent(GenUtil.CHATVIEW);
					intent.putExtra("chatview",uid);
					startActivity(intent);
				}
			}

			super.onPostExecute(list);
		}
	}
	private  class UpdateMsgTask extends AsyncTask< String, Void, Boolean> {
		boolean result;
		@Override
		protected Boolean doInBackground(String... arg0) {
			String data ;
			try {
				data = Connection.getData(arg0[0]);
				JSONObject obj = new JSONObject(data);
				result = obj.getBoolean(InfoUsers.RESULT);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	}
}
