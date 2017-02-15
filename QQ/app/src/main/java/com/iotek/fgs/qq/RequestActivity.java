package com.iotek.fgs.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.fgs.connweb.Connection;
import com.iotek.fgs.entity.Friend;
import com.iotek.fgs.entity.User;
import com.iotek.fgs.list.ExpandAbleAdapter;
import com.iotek.fgs.util.GenUtil;
import com.iotek.fgs.util.InfoUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by fgs on 2016/1/8.
 */
public class RequestActivity extends Activity {
    TextView tvBack,tvNick,tvContent;
    Button btnAgree,btnDisagree;
    ImageView ivHead;
    SharedPreferences sp;
    User user1 = new User();
    AddTask aTask;
    UserTask uTask;
    UpdateMsgTask upTask;
    int groupId,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request);
        Init();
        sp =getSharedPreferences("qqlogin", Context.MODE_APPEND);
        GetUserFromSp.getUser(sp, user1);
        Intent intent = getIntent();
        uid = intent.getIntExtra("request",-1);

        if (uid != -1){
            String userUrl = "http://192.168.253.3:8080/qqServer/GetFriendByToIdServlet.action?user_id="+uid;
            if (uTask == null){
                uTask = new UserTask();
                uTask.execute(userUrl);
                uTask = null;
            }
        }
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GenUtil.SELGROUP);
                startActivityForResult(intent1, 0);
                String upUrl = "http://192.168.253.3:8080/qqServer/UpdateMessageStateServlet.action?message_state=1&message_senderId="+uid+"&message_recipientId="+user1.getUser_id();
                if (upTask == null){
                    upTask = new UpdateMsgTask();
                    upTask.execute(upUrl);
                    upTask = null;
                }
            }
        });
        btnDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upUrl = "http://192.168.253.3:8080/qqServer/UpdateMessageStateServlet.action?message_state=1&message_senderId="+uid+"&message_recipientId="+user1.getUser_id();
                if (upTask == null){
                    upTask = new UpdateMsgTask();
                    upTask.execute(upUrl);
                    upTask = null;
                }

            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 1){
            groupId = data.getIntExtra("groupId",1);
            String addUrl = "http://192.168.253.3:8080/qqServer/AddFriendsServlet.action?friends_uid="+uid+"&friends_toUid="+user1.getUser_id()+"&friends_status="+groupId;
            if (aTask == null){
                aTask = new AddTask();
                aTask.execute(addUrl);
                aTask = null;
            }
            String addUrl2 = "http://192.168.253.3:8080/qqServer/AddFriendsServlet.action?friends_uid="+user1.getUser_id()+"&friends_toUid="+uid+"&friends_status="+groupId;
            if (aTask == null){
                aTask = new AddTask();
                aTask.execute(addUrl2);
                aTask = null;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void Init(){
        tvBack = (TextView)findViewById(R.id.request_tv_back);
        tvNick = (TextView)findViewById(R.id.requestitem_tv_name);
        tvContent = (TextView)findViewById(R.id.requestitem_tv_msg);
        btnAgree = (Button)findViewById(R.id.requestitem_btn_agree);
        btnDisagree = (Button)findViewById(R.id.requestitem_btn_disagree);
        ivHead = (ImageView)findViewById(R.id.requestitem_iv_headic);
    }
    private  class UserTask extends AsyncTask<String, Void, User> {
        User user = new User();
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
            SetHeadIc.setHead(user.getUser_id(),ivHead);
            tvNick.setText(user.getUser_nickName());

            super.onPostExecute(user);
        }
    }
    private  class AddTask extends AsyncTask< String, Void, Boolean> {
        boolean result;

        @Override
        protected Boolean doInBackground(String... arg0) {
            String data;
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

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                Toast.makeText(RequestActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(RequestActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aBoolean);
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
