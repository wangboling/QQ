package com.iotek.fgs.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.fgs.qq.R;

/**
 * Created by fgs on 2016/1/6.
 */
public class MsgWapper {
    private ImageView ivHead;
    private TextView tvName,tvMsg,tvTime,tvCount;
    private View row;
    public MsgWapper(View view){
        row = view;
    }
    public ImageView getIvHead(){
        if (ivHead == null){
            ivHead = (ImageView)row.findViewById(R.id.messageitem_iv_headic);
        }return ivHead;
    }
    public TextView getTvName(){
        if(tvName == null){
            tvName = (TextView)row.findViewById(R.id.messageitem_tv_name);
        }return tvName;
    }
    public TextView getTvMsg() {
        if (tvMsg == null) {
            tvMsg = (TextView) row.findViewById(R.id.messageitem_tv_msg);
        }return tvMsg;
    }
    public TextView getTvTime(){
        if(tvTime == null){
            tvTime = (TextView) row.findViewById(R.id.messageitem_tv_time);
        }return tvTime;
    }
    public TextView getTvCount(){
        if(tvCount == null){
            tvCount = (TextView)row.findViewById(R.id.messageitem_tv_count);
        }return tvCount;
    }

}
