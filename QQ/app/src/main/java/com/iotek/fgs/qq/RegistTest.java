package com.iotek.fgs.qq;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.iotek.fgs.connweb.Connection;
import com.iotek.fgs.util.GenUtil;
import com.iotek.fgs.util.InfoUsers;
import com.iotek.fgs.util.MessageGen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.iotek.fgs.util.MessageGen.REGISTE_FAIL;
import static com.iotek.fgs.util.MessageGen.REGISTE_SUCCESS;
import static com.iotek.fgs.util.MessageGen.USER_NAME_NULL;

/**
 * Created by fgs on 2015/12/28.
 */
public class RegistTest extends Activity {

    EditText etName, etPass, etConfig;
    Button btnRegist;
    ImageView imBack;
    RegisteTask reTask;
    String uName, uPass, uPassConfig,user_name;
    int count = 0;



    private void intentFinish(String uname){
        if (user_name.equals(uName)) {
            Intent data = getIntent();
            data.putExtra("name", uName);
            setResult(GenUtil.RESULT_LOGIN_BACK, data);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "用户名或密码错误!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        Init();
        boolean tag = JudjeConnStatus();
        if(tag){
            btnRegist.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    uName = etName.getText().toString();
                    uPass = etPass.getText().toString();
                    if(etName.equals("")==true || uPass.equals("")==true){
                        Toast.makeText(getApplicationContext(), "请输入用户名，密码", Toast.LENGTH_LONG).show();
                    }else{
                        uPassConfig = etConfig.getText().toString();

                        if (uPass.equals(uPassConfig)) {
                            String registerUrl = "http://192.168.253.3:8080/qqServer/RegisterServlet.action?user_passWord="+uPass+"&user_nickName="+uName+"&user_email=2958365570@qq.com";
                            if(reTask == null) {
                                reTask = new RegisteTask();
                                reTask.execute(registerUrl);
                                reTask = null;
                            }
                            count = 0;
                        } else {
                            Toast.makeText(getApplicationContext(), "密码不一致！",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
        }else{
            Toast.makeText(RegistTest.this, "检查网络连接状态", Toast.LENGTH_SHORT).show();
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
        etName = (EditText) findViewById(R.id.regist_et_name);
        etPass = (EditText) findViewById(R.id.regist_et_pass);

        imBack = (ImageView) findViewById(R.id.regist_im_back);
        btnRegist = (Button) findViewById(R.id.regist_btn_register);
        etConfig = (EditText) findViewById(R.id.regist_et_config_pass);


    }


    private  class RegisteTask extends AsyncTask< String, Void, Boolean> {
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
                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GenUtil.LOGIN_CODE);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
                finish();
            }
            super.onPostExecute(aBoolean);
        }
    }

}

