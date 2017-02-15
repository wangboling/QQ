package com.iotek.fgs.list;

import android.view.View;
import android.widget.ImageView;

import com.iotek.fgs.qq.R;


/**
 * Created by fgs on 2016/1/11.
 */
public class PersonIcWapper {
    private ImageView ivPerson;
    private View row;
    public PersonIcWapper(View view){
        row = view;
    }
    public ImageView getIvPerson(){
        if (ivPerson == null){
            ivPerson = (ImageView)row.findViewById(R.id.per_iv_person);
        }return ivPerson;

    }
}
