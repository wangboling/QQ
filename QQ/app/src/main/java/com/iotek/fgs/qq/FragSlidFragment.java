package com.iotek.fgs.qq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.fgs.entity.User;
import com.iotek.fgs.util.GenUtil;

/**
 * Created by fgs on 2016/1/10.
 */
public class FragSlidFragment extends Fragment{
    SharedPreferences sp;
    ImageView ivHead,ivPerson;
    TextView tvNick,tvName,tvGender;
    Context context;
    User user = new User();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragslid, container, false);
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ivHead = (ImageView)view.findViewById(R.id.fragslid_iv_headic);
        tvNick = (TextView)view.findViewById(R.id.fragslid_tv_nickName);
        tvName = (TextView)view.findViewById(R.id.fragslid_tv_name);
        tvGender = (TextView)view.findViewById(R.id.fragslid_tv_gender);
        ivPerson = (ImageView)view.findViewById(R.id.fragslid_iv_person);
        sp =context.getSharedPreferences("qqlogin", Context.MODE_APPEND);
        GetUserFromSp.getUser(sp, user);
        SetHeadIc.setHead(user.getUser_id(), ivHead);
        //SetPersonIc.setHead(user.getUser_id(),ivPerson);
        tvNick.setText(user.getUser_nickName());
        tvName.setText(user.getUser_name());
        tvGender.setText(user.getUser_gender());
//        ivPerson.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(GenUtil.PERSONIC);
//                startActivityForResult(intent,3);
//            }
//        });
        ivHead.setOnClickListener(new View.OnClickListener() {
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
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && resultCode == 2){
            GenUtil.PERSON = data.getIntExtra("person",1);
            SetPersonIc.setHead(GenUtil.PERSON,ivPerson);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
