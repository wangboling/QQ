package com.iotek.fgs.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.fgs.connweb.Connection;
import com.iotek.fgs.entity.Msg;
import com.iotek.fgs.entity.User;
import com.iotek.fgs.list.ChatListAdapter;
import com.iotek.fgs.util.GenUtil;
import com.iotek.fgs.util.InfoMsg;
import com.iotek.fgs.util.InfoUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgs on 2015/12/28.
 */
public class ChatViewTest extends Activity{
    SharedPreferences sp;
    ImageView ivFriendIc;
    TextView tvBack,tvNick;
    EditText etTest;
    Button btnSend;
    ListView lv;
    User user = new User();
    User user1 = new User();
    UserTask userTask;
    QMsgTask qMsgTask;

    AddMsgTask aTasK;
    Msg msg = new Msg();
    List<Msg> msgList = new ArrayList<Msg>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatview);
        Init();
        sp =getSharedPreferences("qqlogin", Context.MODE_APPEND);
        GetUserFromSp.getUser(sp, user1);
        Intent intent = getIntent();
        final int uid = intent.getIntExtra("chatview", -1);
        MyThread myThread = new MyThread(uid);
        Thread thread = new Thread(myThread);
        thread.start();


        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivFriendIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenUtil.DETAIL);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user",user);
                intent.putExtra("detail","friend");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgList = null;
                msgList = new ArrayList<Msg>();
                String st = etTest.getText().toString();
                if (!st.equals("")) {
                    Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_SHORT).show();
                    String addMsgUrl = "http://192.168.253.3:8080/qqServer/AddMessageServlet.action?message_senderId="+user1.getUser_id()+"&message_recipientId=" + uid + "&message_content=" +st+ "&message_times=2015-12-29&message_state=0";
                    if (aTasK == null) {
                        aTasK = new AddMsgTask();
                        aTasK.execute(addMsgUrl);
                        aTasK = null;
                    }
                    msgList = null;
                    msgList = new ArrayList<Msg>();
                    String qMUrl = "http://192.168.253.3:8080/qqServer/GetMessageServletTwo.action?message_senderId="+user1.getUser_id()+"&message_recipientId=" + uid;
                    if (qMsgTask == null) {
                        qMsgTask = new QMsgTask(msgList);
                        qMsgTask.execute(qMUrl);
                        qMsgTask = null;
                    }
                }else {
                    Toast.makeText(ChatViewTest.this, "不能为空！", Toast.LENGTH_SHORT).show();
                }
                etTest.setText("");
            }
        });
    }
    private class MyThread implements Runnable{
        int uid;
        public MyThread(int uid){
            this.uid = uid;
        }
        @Override
        public void run() {
            while (true){
                if (uid!=-1){
                    msgList = null;
                    msgList = new ArrayList<>();
                    String furl = "http://192.168.253.3:8080/qqServer/GetFriendByToIdServlet.action?user_id="+uid;
                    String qMUrl = "http://192.168.253.3:8080/qqServer/GetMessageServletTwo.action?message_senderId="+user1.getUser_id()+"&message_recipientId="+uid;


                    if (qMsgTask == null){
                        qMsgTask = new QMsgTask(msgList);
                        qMsgTask.execute(qMUrl);
                        qMsgTask = null;
                    }

                    if(userTask == null){

                        userTask = new UserTask(user);
                        userTask.execute(furl);

                        userTask = null;
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void Init(){
        tvNick = (TextView)findViewById(R.id.chatview_tv_nickname);
        tvBack =(TextView) findViewById(R.id.chatview_tv_back);
        ivFriendIc = (ImageView)findViewById(R.id.chatview_iv_friendic);
        etTest = (EditText)findViewById(R.id.chatview_et_text);
        btnSend = (Button)findViewById(R.id.chatview_btn_send);
        lv = (ListView)findViewById(R.id.chatview_lv);
    }
   private class UserTask extends AsyncTask<String, Void, User> {
        User user;

        public UserTask(User user) {

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
            tvNick.setText(user.getUser_nickName());
            SetHeadIc.setHead(user.getUser_id(),ivFriendIc);
            super.onPostExecute(user);
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
            return  msgList;
        }

        @Override
        protected void onPostExecute(List<Msg> msgList) {
            lv.setSelection(lv.getCount() - 1);
            lv.setAdapter(new ChatListAdapter(getApplicationContext(), msgList, user.getUser_id()));

            super.onPostExecute(msgList);
        }
    }
    private  class AddMsgTask extends AsyncTask< String, Void, Boolean> {
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

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"发送失败",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aBoolean);
        }
    }

}
