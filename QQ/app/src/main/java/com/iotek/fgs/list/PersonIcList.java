package com.iotek.fgs.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.iotek.fgs.qq.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgs on 2016/1/11.
 */
public class PersonIcList extends BaseAdapter {
    List<Integer> list = new ArrayList<>();
    Context context;
    public PersonIcList(List<Integer> list,Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
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
        PersonIcWapper pWapper;
        View view;
        view = convertView;
       if (view == null){
           view = LayoutInflater.from(context).inflate(R.layout.personic,null);
       }
        ImageView ivPerson ;
        ivPerson = (ImageView)view.findViewById(R.id.per_iv_person);
        ivPerson.setImageResource(list.get(position));
        return view;
    }
}
