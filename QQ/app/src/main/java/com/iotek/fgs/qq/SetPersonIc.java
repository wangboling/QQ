package com.iotek.fgs.qq;

import android.widget.ImageView;

/**
 * Created by fgs on 2016/1/1.
 */
public class SetPersonIc {
    public static void setHead(int uid,ImageView ivPerson){
        switch (uid){
            case 0:
                ivPerson.setImageResource(R.drawable.shaosiming1);
                break;
            case 1:
                ivPerson.setImageResource(R.drawable.shaosiming2);
                break;
            case 2:
                ivPerson.setImageResource(R.drawable.shaosiming3);
                break;
            case 3:
                ivPerson.setImageResource(R.drawable.shaosiming4);
                break;
            case 4:
                ivPerson.setImageResource(R.drawable.shaosiming5);
                break;

            default:
                ivPerson.setImageResource(R.drawable.shaosiming5);
                break;
        }
    }
}
