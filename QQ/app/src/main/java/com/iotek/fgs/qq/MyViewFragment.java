package com.iotek.fgs.qq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.iotek.fgs.entity.User;
import com.iotek.fgs.util.GenUtil;

public class MyViewFragment extends Fragment {
	Button tvLogin,tvRegist;
	ImageView ivHeadic,ivhead;
	SharedPreferences sp;
	Context context;
	 User user = new User();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.myview, container, false);
	}

	@Override
	public void onAttach(Context context) {
		this.context = context;
		super.onAttach(context);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		ivHeadic = (ImageView)view.findViewById(R.id.myview_iv_headic);
		ivhead  = (ImageView)view.findViewById(R.id.myview_iv_head);
		sp =context.getSharedPreferences("qqlogin",Context.MODE_APPEND);
		GetUserFromSp.getUser(sp, user);
		SetHeadIc.setHead(user.getUser_id(),ivhead);
		SetHeadIc.setHead(user.getUser_id(),ivHeadic);
		ivHeadic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GenUtil.DETAIL);
				Bundle bundle = new Bundle();
				bundle.putParcelable("user",user);
				intent.putExtras(bundle);
				intent.putExtra("detail","self");
				startActivity(intent);
			}
		});
		//注册
		tvRegist = (Button) view.findViewById(R.id.myview_btn_regist);
		tvRegist.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {

				Intent intent = new Intent();
				intent.setAction(GenUtil.REGIST_CODE);
				startActivity(intent);
				return false;
			}
		});
		//登录
		tvLogin = (Button) view.findViewById(R.id.myview_btn_login);
		tvLogin.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Intent intent = new Intent();
				intent.setAction(GenUtil.LOGIN_CODE);
				startActivity(intent);

				return false;
			}
		});
		super.onViewCreated(view, savedInstanceState);
	}


}