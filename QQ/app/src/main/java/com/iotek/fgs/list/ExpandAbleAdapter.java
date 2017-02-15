package com.iotek.fgs.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iotek.fgs.qq.R;

import java.util.List;

/**
 * Created by fgs on 2015/12/30.
 */
public class ExpandAbleAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupList;
    private List<List<String>> itemList;
    public ExpandAbleAdapter(Context context,List<String> groupList,List<List<String>> itemList) {
        this.context = context;
        this.groupList = groupList;
        this.itemList = itemList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.i("Test",itemList.get(groupPosition).size()+"");
        return itemList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.i("Test",itemList.get(groupPosition).get(childPosition));
        return itemList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupWapper groupWapper;
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.group,null);
            groupWapper = new GroupWapper(view);
            view.setTag(groupWapper);
        }else{
            groupWapper = (GroupWapper)view.getTag();
        }

        TextView tvGroupName = groupWapper.getTvGroupName();
        TextView tvGroupCount = groupWapper.getTvGroupCount();

        tvGroupName.setText(getGroup(groupPosition).toString());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        GroupItemWapper itemWapper;
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.group_item,null);
            itemWapper = new GroupItemWapper(view);
            view.setTag(itemWapper);
        }else {
            itemWapper = (GroupItemWapper)view.getTag();
        }
         ImageView ivHead = itemWapper.getIvHead();
         TextView tvName = itemWapper.getTvName();
        TextView tvMsg = itemWapper.getTvMsg();

        tvName.setText(itemList.get(groupPosition).get(childPosition));


        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
