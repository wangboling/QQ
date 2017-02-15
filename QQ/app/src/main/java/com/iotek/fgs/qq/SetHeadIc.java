package com.iotek.fgs.qq;

import android.widget.ImageView;

/**
 * Created by fgs on 2016/1/1.
 */
public class SetHeadIc {
    public static void setHead(int uid,ImageView ivHead){
        switch (uid){
            case 1:
                ivHead.setImageResource(R.drawable.ic_tab_mine);
                break;
            case 2:
                ivHead.setImageResource(R.drawable.ic_map_route_walk);
                break;
            default:
                ivHead.setImageResource(R.drawable.ic_tab_mine_active);
                break;
        }
    }
}
