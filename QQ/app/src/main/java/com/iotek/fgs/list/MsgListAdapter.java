package com.iotek.fgs.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.fgs.entity.Msg;
import com.iotek.fgs.entity.User;
import com.iotek.fgs.qq.R;
import com.iotek.fgs.qq.SetHeadIc;

import java.util.List;

/**
 * Created by fgs on 2016/1/6.
 */
public class MsgListAdapter extends BaseAdapter{
    private Context context;
    List<List<Msg>> mList;
    List<User> uList;
    List<Msg> list;
    public MsgListAdapter(Context context,List<List<Msg>> mList,List<User> uList){
        this.context = context;
        this.mList = mList;
        this.uList = uList;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        MsgWapper mWapper;
        View view;
        view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.messageitem,null);
            mWapper = new MsgWapper(view);
            view.setTag(mWapper);
        }else {
            mWapper = (MsgWapper)view.getTag();
        }
        ImageView ivHead;
        TextView tvName,tvMsg,tvTime,tvCount;
        tvCount = mWapper.getTvCount();
        ivHead = mWapper.getIvHead();
        tvName = mWapper.getTvName();
        tvMsg = mWapper.getTvMsg();
        tvTime = mWapper.getTvTime();
        int  type = mList.get(position).get(0).getMessage_type();
        if (type == 0) {
            tvCount.setText(mList.get(position).size() + "");
            SetHeadIc.setHead(uList.get(position).getUser_id(), ivHead);
            tvName.setText(uList.get(position).getUser_nickName());
            tvMsg.setText(mList.get(position).get(mList.get(position).size() - 1).getMessage_content() + "");
            tvTime.setText(mList.get(position).get(mList.get(position).size() - 1).getMessage_temes());
        }else {
            tvCount.setText(mList.get(position).size()+"");
            SetHeadIc.setHead(uList.get(position).getUser_id(), ivHead);
            tvName.setText("新朋友");
            tvMsg.setText(uList.get(position).getUser_nickName()+"请求加为好友");
            tvTime.setText(mList.get(position).get(mList.get(position).size() - 1).getMessage_temes());
        }
        return view;
    }
}
