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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.fgs.connweb.Connection;
import com.iotek.fgs.entity.Friend;
import com.iotek.fgs.entity.User;
import com.iotek.fgs.list.ExpandAbleAdapter;
import com.iotek.fgs.util.GenUtil;
import com.iotek.fgs.util.InfoFriend;
import com.iotek.fgs.util.InfoUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
	RefreshableView refreshableView;
	ExpandableListView elv;
	Context context;
	ImageView ivHead;
	TextView tvAdd;
	FriendTask ftask;
	UserTask userTask;
	SharedPreferences sp;
	int j=0;
	User user = new User();
	private List<Friend> fList = new ArrayList<Friend>();
	private List<User> userList = new ArrayList<User>();
	private List<User> uList1 = new ArrayList<User>();
	private List<User> uList2 = new ArrayList<User>();
	private List<User> uList0 = new ArrayList<User>();
	private List<String> groupList = new ArrayList<String>();
	private List<List<String>> itemList = new ArrayList<List<String>>();

	@Override
	public void onAttach(Context context) {
		this.context = context;
		super.onAttach(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.contactsview, container,false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		sp =context.getSharedPreferences("qqlogin",Context.MODE_APPEND);
		ivHead = (ImageView)view.findViewById(R.id.conntactsview_iv_head);
		GetUserFromSp.getUser(sp, user);
		final int uid = user.getUser_id();

		SetHeadIc.setHead(uid, ivHead);
		String url = "http://192.168.253.3:8080/qqServer/GetFriendsServlet.action?friends_uid="+uid;
		if(ftask == null){
			ftask = new FriendTask(fList);
			ftask.execute(url);
			ftask = null;
		}



		tvAdd = (TextView)view.findViewById(R.id.conntactsview_tv_add);

		tvAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GenUtil.SEARCH);
				Bundle bundle = new Bundle();
				bundle.putParcelable(GenUtil.SEARCH, user);
				intent.putExtras(bundle);
				intent.putExtra(GenUtil.DETAIL, "search");
				startActivity(intent);
			}
		});

		elv = (ExpandableListView)view.findViewById(R.id.list_view);

		elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			int uid = -1;

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
										int arg3, long arg4) {
				Toast.makeText(getActivity(), groupList.get(arg2) + "***" + itemList.get(arg2).get(arg3)+userList.size(), Toast.LENGTH_SHORT).show();
				for(int i = 0;i<userList.size();i++){
					if(userList.get(i).getUser_nickName().equals(itemList.get(arg2).get(arg3))){
						uid = userList.get(i).getUser_id();
					}
				}
				Intent intent = new Intent(GenUtil.CHATVIEW);
				intent.putExtra("chatview",uid);
				startActivity(intent);
				return false;
			}
		});
		refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
		refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);
					MyThread myThread = new MyThread(uid);
					Thread thread = new Thread(myThread);
					thread.start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);
		super.onViewCreated(view, savedInstanceState);
	}
	private class MyThread implements Runnable{
		int uid;
		public MyThread(int uid){
			this.uid = uid;
		}
		@Override
		public void run() {
			groupList = null;
			groupList = new ArrayList<>();
			itemList=null;
			itemList = new ArrayList<>();
			uList0 = null;
			uList0 = new ArrayList<>();
			uList1 = null;
			uList1 = new ArrayList<>();
			uList2 = null;
			uList2 = new ArrayList<>();
			fList = null;
			fList = new ArrayList<>();
				String url = "http://192.168.253.3:8080/qqServer/GetFriendsServlet.action?friends_uid="+uid;
				if(ftask == null){
					ftask = new FriendTask(fList);
					ftask.execute(url);
					ftask = null;
				}
			}
		}

	private void InitData(){

		String[] groups = { "助教", "家人", "朋友" };
//		String[] farmilis = { "老爸", "老妈", "妹妹" };
//		String[] friends = { "小李", "张三", "李四" };
//		String[] colleagues = { "陈总", "李工", "李客户" };
		for (int i = 0; i < groups.length; i++) {
			groupList.add(groups[i]);
		}

			List<String> item1 = new ArrayList<String>();
			for (int i = 0; i < uList0.size(); i++) {
				item1.add(uList0.get(i).getUser_nickName());
			}

			List<String> item2 = new ArrayList<String>();
			for (int i = 0; i < uList1.size(); i++) {
				item2.add(uList1.get(i).getUser_nickName());
			}

			List<String> item3 = new ArrayList<String>();
			for (int i = 0; i < uList2.size(); i++) {
				item3.add(uList2.get(i).getUser_nickName());
			}
			itemList.add(item1);
			itemList.add(item2);
			itemList.add(item3);

	}
	protected  class FriendTask extends AsyncTask<String, Void, List<Friend>> {
		List<Friend> list;
		public FriendTask(List<Friend> list){
			this.list = list;
		}


		@Override
		protected void onPreExecute() {
			super.onPreExecute();

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
			if(list!=null){
				for (int i=0;i<list.size();i++){
					Friend friend = list.get(i);
					int friendUid = friend.getFriends_toUid();
					String furl = "http://192.168.253.3:8080/qqServer/GetFriendByToIdServlet.action?user_id="+friendUid;
					User user = new User();
					if(userTask == null){

						userTask = new UserTask(user,friend);
						userTask.execute(furl);

						userTask = null;
					}



				}
			}

			super.onPostExecute(list);
		}
	}
	private  class UserTask extends AsyncTask<String, Void, User> {
		User user;

		Friend friend;
		public UserTask(User user,Friend friend) {
			this.friend = friend;
			this.user = user;

		}


		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

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
					userList.add(user);
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
					if(friend.getFriends_status() == 0){
						uList0.add(user);
					}else if (friend.getFriends_status() == 1){
						uList1.add(user);
					}else if(friend.getFriends_status() == 2){
						uList2.add(user);
					}

			j++;
			if (j == fList.size()) {
				InitData();
				elv.setAdapter(new ExpandAbleAdapter(getActivity(), groupList, itemList));

				j=0;
			}

			super.onPostExecute(user);
		}
	}

}
