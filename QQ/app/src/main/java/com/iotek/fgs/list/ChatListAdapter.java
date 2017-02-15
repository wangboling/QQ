package com.iotek.fgs.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.fgs.entity.Msg;
import com.iotek.fgs.qq.R;
import com.iotek.fgs.qq.SetHeadIc;

import java.util.List;

/**
 * Created by fgs on 2016/1/2.
 */
public class ChatListAdapter extends BaseAdapter{
    List<Msg> msgList ;
    int reUid;
    Context context;
    public ChatListAdapter(Context context, List<Msg> msgList,int reUid){
        this.msgList = msgList;
        this.reUid = reUid;
        this.context = context;
    }
    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (msgList.get(position).getMessage_recipiendtId() == reUid) {

            view = LayoutInflater.from(context).inflate(R.layout.sendmsgitem, parent,false);
            TextView tvSendMsg ,tvSendTime;
            ImageView ivSendHeadIc;
            tvSendTime = (TextView)view.findViewById(R.id.sendmsg_tv_time);
            tvSendMsg = (TextView)view.findViewById(R.id.sendmsgitem_tv_msg);
            tvSendTime.setText((msgList.get(position).getMessage_temes()));
            tvSendMsg.setText(msgList.get(position).getMessage_content());
            ivSendHeadIc = (ImageView)view.findViewById(R.id.sendmsgitem_iv_head);
                SetHeadIc.setHead(msgList.get(position).getMessage_senderId(),ivSendHeadIc);

            }else {
                view = LayoutInflater.from(context).inflate(R.layout.receivemsgitem, parent,false);
                TextView tvReceiveMsg,tvReTime ;
                ImageView ivReHeadIc ;
                tvReTime = (TextView)view.findViewById(R.id.rece_tv_time);
                tvReceiveMsg = (TextView)view.findViewById(R.id.receivemsgitem_tv_msg);
                ivReHeadIc = (ImageView)view.findViewById(R.id.receivemsgitem_iv_head);
                tvReTime.setText(msgList.get(position).getMessage_temes());
                tvReceiveMsg.setText(msgList.get(position).getMessage_content());
                SetHeadIc.setHead(msgList.get(position).getMessage_recipiendtId(),ivReHeadIc);

            }
        return view;
    }
}
