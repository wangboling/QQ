package com.iotek.fgs.qq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.fgs.connweb.Connection;
import com.iotek.fgs.entity.Friend;
import com.iotek.fgs.entity.User;
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

/**
 * Created by fgs on 2015/12/28.
 */
public class DetailActivity extends Activity {
    SharedPreferences sp;
    SildingFinishLayout sildingFinishLayout;
    ImageView ivpic,ivHeadIc;
    Button btnEdit,btnAdd,btnDel,btnSetNick,btnPerson;
    TextView tvNick,tvBack,tvName,tvGender,tvEmail,tvIsVip,tvWebcam,tvStatus,tvId;
    LinearLayout llSelf,llFriend,llAddFriend,llHoll;
    User user;
    User user1 = new User();
    List<Friend> fList = new ArrayList<>();
    AddTask aTask;
    FriendTask fTask;
    RequestTask rTask;
    DelTask dTask;
    int groupId;
    int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Init();
        sp =getSharedPreferences("qqlogin", Context.MODE_APPEND);
        GetUserFromSp.getUser(sp, user1);
         Intent intent = getIntent();
        String type = intent.getStringExtra("detail");

        final Bundle bundle = intent.getExtras();
         user = bundle.getParcelable("user");

        setUserDetail(user);
        SetHeadIc.setHead(user.getUser_id(), ivHeadIc);
        //SetPersonIc.setHead(user1.getUser_id(),ivpic);
        switch (type){
            case "search":
                String searchUrl = "http://192.168.253.3:8080/qqServer/GetFriendsServlet.action?friends_uid="+user1.getUser_id();
                if (fTask == null){
                    fTask = new FriendTask(fList);
                    fTask.execute(searchUrl);
                    fTask = null;
                }
                break;
            case "self":
                llSelf.setVisibility(View.VISIBLE);
                break;
            case "friend":
                llFriend.setVisibility(View.VISIBLE);
                break;
        }

        sildingFinishLayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                finish();
            }
        });
        sildingFinishLayout.setTouchView(llHoll);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "HeHe", Toast.LENGTH_LONG).show();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Del", Toast.LENGTH_SHORT).show();
               new AlertDialog.Builder(DetailActivity.this)
                       .setMessage("确定删除此联系人吗？")
                       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               String DelUrl="http://192.168.253.3:8080/qqServer/DelFriendsServlet.action?friends_toUid="+user.getUser_id()+"&friends_uid="+user1.getUser_id();
                               if (dTask == null){
                                   dTask = new DelTask();
                                   dTask.execute(DelUrl);
                                   dTask = null;
                               }
                           }
                       })
                       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(DetailActivity.this, "取消", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (j == 0) {
                    j++;
                    Intent intent1 = new Intent(GenUtil.SELGROUP);
                    startActivityForResult(intent1, 0);
                    btnAdd.setText("发送请求");
                    Toast.makeText(getApplicationContext(), "AddFriend", Toast.LENGTH_SHORT).show();
                }else {
                    String requestUrl = "http://192.168.253.3:8080/qqServer/AddMessageRequestServlet.action?message_senderId="+user1.getUser_id()+"&message_recipientId="+user.getUser_id()+"&message_content=%E4%BD%A0%E5%A5%BD&message_times=2015-12-29&message_state=0&message_type=1";
                    if (rTask == null){
                        rTask = new RequestTask();
                        rTask.execute(requestUrl);
                        rTask = null;
                    }

//                    String addFriendUrl = "http://192.168.253.3:8080/qqServer/AddFriendsServlet.action?friends_uid=1&friends_toUid=" + user.getUser_id() + "&friends_status=" + groupId;
//                    if (aTask == null){
//                        aTask = new AddTask();
//                        aTask.execute(addFriendUrl);
//                        aTask = null;
//                    }
                    btnAdd.setText("添加好友");
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GenUtil.EDITDETAIL);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
        btnSetNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GenUtil.EDITDETAIL);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
//        btnPerson.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(GenUtil.PERSONIC);
//                startActivityForResult(intent2,1);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 1){
            groupId = data.getIntExtra("groupId",0);
        }
        if (requestCode == 1 && resultCode == 2){
            GenUtil.PERSON = data.getIntExtra("person",1);
            SetPersonIc.setHead(GenUtil.PERSON,ivpic);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUserDetail(User user) {

        if(user.getUser_webcam() == 1){
            tvWebcam.setText("有摄像头");
        }
        Log.i("Test","haha");
        if(user.getUser_isVip() == 1){
            tvIsVip.setText("是会员");
        }
        Log.i("Test",user.getUser_id()+"");
        tvId.setText(user.getUser_id() + "");
        tvEmail.setText(user.getUser_email());
        Log.i("Test",user.getUser_nickName());
        tvNick.setText(user.getUser_nickName());
    }

    private void Init(){
        llHoll = (LinearLayout)findViewById(R.id.detail_ll_holl);
        sildingFinishLayout = (SildingFinishLayout)findViewById(R.id.detail_silding);
        btnEdit = (Button)findViewById(R.id.detail_btn_edit);
        ivpic = (ImageView)findViewById(R.id.detail_iv_pic);
        ivHeadIc = (ImageView)findViewById(R.id.detail_iv_headic);
        tvBack = (TextView)findViewById(R.id.detail_iv_back);
        llAddFriend = (LinearLayout)findViewById(R.id.detail_ll_addfriend);
        llFriend = (LinearLayout)findViewById(R.id.detail_ll_friend);
        llSelf = (LinearLayout)findViewById(R.id.detail_ll_self);
        btnAdd = (Button)findViewById(R.id.detail_btn_addfriend);
        btnDel = (Button)findViewById(R.id.detail_btn_del);
        btnPerson = (Button)findViewById(R.id.detail_btn_person);
        btnSetNick = (Button)findViewById(R.id.detail_btn_setNickName);
        tvEmail = (TextView)findViewById(R.id.detail_tv_email);
        tvGender = (TextView)findViewById(R.id.detail_tv_gender);
        tvId = (TextView)findViewById(R.id.detail_tv_Id);
        tvIsVip = (TextView)findViewById(R.id.detail_tv_isVip);
        tvName = (TextView)findViewById(R.id.detail_tv_name);
        tvNick = (TextView)findViewById(R.id.detail_tv_nickName);
        tvStatus = (TextView)findViewById(R.id.detail_tv_status);
        tvWebcam = (TextView)findViewById(R.id.detail_tv_webcam);


    }
    private  class AddTask extends AsyncTask< String, Void, Boolean> {
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
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                finish();
            }
            super.onPostExecute(aBoolean);
        }
    }
    private   class FriendTask extends AsyncTask<String, Void, List<Friend>> {
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
            boolean flag = false;
            if(list!=null){
                for (int i=0;i<list.size();i++){
                    Friend friend = list.get(i);
                    int friendUid = friend.getFriends_toUid();
                    if (friendUid == user.getUser_id()){
                        flag =true;
                    }
                }
                if (flag){
                    llFriend.setVisibility(View.VISIBLE);
                }else {
                    llAddFriend.setVisibility(View.VISIBLE);
                }
            }

            super.onPostExecute(list);
        }
    }
    private  class RequestTask extends AsyncTask< String, Void, Boolean> {
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
                Toast.makeText(getApplicationContext(),"请求发送成功",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"请求发送失败",Toast.LENGTH_SHORT).show();
                finish();
            }
            super.onPostExecute(aBoolean);
        }
    }
    private  class DelTask extends AsyncTask< String, Void, Boolean> {
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
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_SHORT).show();
                finish();
            }
            super.onPostExecute(aBoolean);
        }
    }

}
