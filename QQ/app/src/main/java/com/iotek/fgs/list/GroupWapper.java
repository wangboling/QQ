package com.iotek.fgs.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.fgs.qq.R;

import junit.framework.Test;

/**
 * Created by fgs on 2015/12/30.
 */
public class GroupWapper {

    private TextView tvGroupName,tvGroupCount;
    private View view;
    public GroupWapper(View view){
        this.view = view;
    }

    public TextView getTvGroupName(){
        if(tvGroupName == null){
            tvGroupName = (TextView)view.findViewById(R.id.group_tv_groupname);
        }return tvGroupName;
    }
    public TextView getTvGroupCount(){
        if(tvGroupCount == null){
            tvGroupCount = (TextView)view.findViewById(R.id.group_tv_groupcount);
        }return tvGroupCount;
    }
}
