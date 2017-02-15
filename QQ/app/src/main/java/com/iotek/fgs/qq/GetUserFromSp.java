package com.iotek.fgs.qq;

import android.content.SharedPreferences;

import com.iotek.fgs.entity.User;
import com.iotek.fgs.util.InfoUsers;

/**
 * Created by fgs on 2015/12/31.
 */
public class GetUserFromSp {
    SharedPreferences sp;

    public GetUserFromSp(SharedPreferences sp){
        this.sp = sp;

    }
    public static  void getUser(SharedPreferences sp,User user){
        user.setUser_id(sp.getInt(InfoUsers.USER_ID, -1));
        user.setUser_email(sp.getString(InfoUsers.USER_EMAIL, "没有邮箱"));
        user.setUser_isVip(sp.getInt(InfoUsers.USER_ISVIP, 0));
        user.setUser_status(sp.getString(InfoUsers.USER_STATUS, "不在线"));
        user.setUser_name(sp.getString(InfoUsers.USER_NAME,"没有名字"));
        user.setUser_gender(sp.getString(InfoUsers.USER_GENDER, "男"));
        user.setUser_webcam(sp.getInt(InfoUsers.USER_WEBCAM, 0));
        user.setUser_nickName(sp.getString(InfoUsers.USER_NICKNAME, "快来设置你的个性昵称吧"));
    }
}
