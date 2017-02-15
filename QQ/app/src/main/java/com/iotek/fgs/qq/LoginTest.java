package com.iotek.fgs.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iotek.fgs.connweb.Connection;
import com.iotek.fgs.entity.User;
import com.iotek.fgs.util.GenUtil;
import com.iotek.fgs.util.InfoUsers;
import com.iotek.fgs.util.MessageGen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by fgs on 2015/12/28.
 */
public class LoginTest extends Activity {
    EditText etName, etPass ;
    Button btnLogin ;
    ImageView imBack;
    CheckBox cb;
    User user = null;
    String uName, uPass,user_id;
    int count = 0;
    MyAsyncTask asyncTask;
    SharedPreferences sp;

    private void intentFinish(String uname){
        if (uname.equals(uName)) {
//            Intent intent = new Intent(GenUtil.MAINACTIVITY);
//            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "用户名或密码错误!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        asyncTask = null;
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Init();
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   SharedPreferences.Editor et = sp.edit();
                    et.putString("etPass",etPass.getText().toString());
                    et.putString("etName",etName.getText().toString());
                    et.commit();
                }
            }
        });

            etName.setText(sp.getString("etName",null));
            etPass.setText(sp.getString("etPass",null));

        boolean tag = JudjeConnStatus();
        if(tag){
            btnLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    uName = etName.getText().toString();
                    uPass = etPass.getText().toString();
                    String userUrl = "http://192.168.253.3:8080/qqServer/LoginServlet.action?user_id="+uName+"&user_passWord="+uPass;
                    if(asyncTask == null){
                        asyncTask = new MyAsyncTask();
                        asyncTask.execute(userUrl);
                        asyncTask = null;
                    }

                }
            });
        }else{
            Toast.makeText(LoginTest.this, "网络无连接，请检查网络状态", Toast.LENGTH_SHORT).show();

            btnLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Toast.makeText(LoginTest.this, "网络无连接，请检查网络状态", Toast.LENGTH_SHORT).show();
                }
            });

        }


        imBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();

            }
        });
    }

    private boolean JudjeConnStatus() {
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if(info != null){
            return true;
        }else {
            return false;
        }
    }

    protected void startConnFail() {
        Intent intent = new Intent(GenUtil.CONNFAIL);
        startActivity(intent);

    }
    private void Init() {
        etName = (EditText) findViewById(R.id.login_et_name);
        etPass = (EditText) findViewById(R.id.login_et_pass);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        imBack = (ImageView) findViewById(R.id.login_im_back);
        cb = (CheckBox)findViewById(R.id.login_cb);
        sp = getSharedPreferences("qqlogin", MODE_APPEND);
    }

   private class MyAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... arg0) {
            boolean result=false;

            String data;
            try {
                data = Connection.getData(arg0[0]);
               JSONObject obj = new JSONObject(data);
                Log.i("Test", obj.toString());
                result = obj.getBoolean(InfoUsers.RESULT);
                if(result){
                    JSONArray array = obj.getJSONArray(InfoUsers.OBJLIST);
                    obj = array.getJSONObject(0);
                    user = new User();
                    user.setUser_id(obj.getInt(InfoUsers.USER_ID));
                    user.setUser_email(obj.getString(InfoUsers.USER_EMAIL));
                    user.setUser_isVip(obj.getInt(InfoUsers.USER_ISVIP));
                    user.setUser_webcam(obj.getInt(InfoUsers.USER_WEBCAM));
                    user.setUser_nickName(obj.getString(InfoUsers.USER_NICKNAME));

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(InfoUsers.USER_ID, user.getUser_id());
                    editor.putString(InfoUsers.USER_NICKNAME, user.getUser_nickName());
                    editor.putString(InfoUsers.USER_EMAIL, user.getUser_email());
                    editor.putInt(InfoUsers.USER_ISVIP, user.getUser_isVip());
                    editor.putInt(InfoUsers.USER_WEBCAM, user.getUser_webcam());
                    editor.commit();
                    return user.getUser_id()+"";
                }
            } catch (MalformedURLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                startConnFail();
                e1.printStackTrace();
            }catch (JSONException e) {
                Log.i("Test", "aaaa");
                e.printStackTrace();
            }

            return null;
        }

       @Override
       protected void onPostExecute(String s) {
           if (s == null){
               Toast.makeText(getApplicationContext(), "登录失败!",
                       Toast.LENGTH_SHORT).show();
           }else {
               Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();
               intentFinish(s);
           }
           super.onPostExecute(s);
       }
   }


}

