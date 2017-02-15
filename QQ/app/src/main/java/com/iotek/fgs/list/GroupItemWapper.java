package com.iotek.fgs.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.fgs.qq.R;

/**
 * Created by fgs on 2015/12/30.
 */
public class GroupItemWapper {
    private ImageView ivHead;
    private TextView tvName,tvMsg;
    private View view;
    public GroupItemWapper(View view){
        this.view = view;
    }
    public ImageView getIvHead(){
        if(ivHead == null){
            ivHead = (ImageView)view.findViewById(R.id.group_item_iv_headic);
        }return ivHead;
    }
    public TextView getTvName(){
        if(tvName == null){
            tvName = (TextView)view.findViewById(R.id.group_item_name);
        }return tvName;
    }
    public TextView getTvMsg(){
        if(tvMsg == null){
            tvMsg = (TextView)view.findViewById(R.id.group_item_message);
        }return tvMsg;
    }
}
