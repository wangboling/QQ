package com.iotek.fgs.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
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
 * Created by fgs on 2015/12/29.
 */
public class SearchActivity extends Activity {
    TabHost tab;
    EditText etPerson,etGroup;
    Button btnSearchPerson,btnSearchGroup;
    TextView tvBack;
    UserTask userTask;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        Init();
        tab.setup();

        tab.addTab(tab.newTabSpec("找人")
                .setIndicator("找人")
                .setContent(R.id.search_tab1));
        tab.addTab(tab.newTabSpec("找群")
                .setIndicator("找群")
                .setContent(R.id.search_tab2));
        tab.addTab(tab.newTabSpec("找公众号")
                    .setIndicator("找公众号")
                    .setContent(R.id.search_tab3));
        btnSearchPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = etPerson.getText().toString();
                if (userId.equals("")) {
                    Toast.makeText(getApplication(), "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Test", userId + "@@");
                    String url = "http://192.168.253.3:8080/qqServer/GetFriendByToIdServlet.action?user_id=" + userId;
                    if (userTask == null) {
                        userTask = new UserTask(user);
                        userTask.execute(url);
                        userTask = null;
                    }

                }
            }
        });
        btnSearchGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenUtil.DETAIL);
                intent.putExtra(GenUtil.DETAIL,"search");
                startActivity(intent);
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void Init(){
        tvBack = (TextView)findViewById(R.id.search_tv_back);
        tab = (TabHost)findViewById(R.id.search_tab);
        etPerson = (EditText)findViewById(R.id.search_et_person);
        etGroup = (EditText)findViewById(R.id.search_et_group);
        btnSearchPerson = (Button)findViewById(R.id.search_btn_person);
        btnSearchGroup = (Button)findViewById(R.id.search_btn_group);
    }
    private  class UserTask extends AsyncTask<String, Void, User> {
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
                boolean result = obj.getBoolean(InfoUsers.RESULT);
                if(result){
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
            if(user!=null) {
                Log.i("Test",user.getUser_id()+"");
                Intent intent = new Intent(GenUtil.DETAIL);
                Bundle bundle = new Bundle();
                intent.putExtra("detail", "search");
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"没有要查找的人",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(user);
        }
    }
}
