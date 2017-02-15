package com.iotek.fgs.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.fgs.connweb.Connection;
import com.iotek.fgs.entity.User;
import com.iotek.fgs.util.GenUtil;
import com.iotek.fgs.util.InfoUsers;
import com.iotek.fgs.util.MessageGen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by fgs on 2015/12/28.
 */
public class EditDetailActivity extends Activity {
    TextView tvCancle,tvFinish;
    EditText etNick;
    SetNickTask setNickTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdetail);
        Init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final User user = bundle.getParcelable("user");
        etNick.setText(user.getUser_nickName());

        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String  nickName = etNick.getText().toString();
                if (!nickName.equals("")){
                    String url = "http://192.168.253.3:8080/qqServer/UpdateNickNameServlet.action?user_nickName="+nickName+"&user_id="+user.getUser_id()+"&user_passWord=123";
                    if(setNickTask == null) {
                        setNickTask = new SetNickTask();
                        setNickTask.execute(url);
                        setNickTask = null;
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"不能为空",Toast.LENGTH_SHORT).show();
                }
            }
         });


    }
    private void Init(){
        etNick = (EditText)findViewById(R.id.editdetail_et_nickname);
        tvCancle = (TextView)findViewById(R.id.editdetail_tv_cancle);
        tvFinish = (TextView)findViewById(R.id.editdetail_tv_finish);
    }

    private  class SetNickTask extends AsyncTask< String, Void, Boolean> {
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
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT).show();
            }
            finish();
            super.onPostExecute(aBoolean);
        }
    }
}
