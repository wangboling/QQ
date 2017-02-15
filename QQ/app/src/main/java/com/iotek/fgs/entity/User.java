package com.iotek.fgs.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fgs on 2015/12/31.
 */
public class User implements Parcelable{
    private int User_id;
    private String User_name;
    private String User_passWord;
    private  String User_nickName;
    private String User_email;
    private String User_gender;
    private int User_isVip;
    private String User_status;
    private int User_webcam;

    public User() {
    }

    public User(String user_name, String user_passWord, String user_nickName, String user_email,
                String user_gender, int user_isVip, String user_status, int user_webcam, int user_id) {
        User_name = user_name;
        User_passWord = user_passWord;
        User_nickName = user_nickName;
        User_email = user_email;
        User_gender = user_gender;
        User_isVip = user_isVip;
        User_status = user_status;
        User_webcam = user_webcam;
        User_id = user_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public String getUser_passWord() {
        return User_passWord;
    }

    public String getUser_nickName() {
        return User_nickName;
    }

    public String getUser_email() {
        return User_email;
    }

    public String getUser_gender() {
        return User_gender;
    }

    public int getUser_isVip() {
        return User_isVip;
    }

    public String getUser_status() {
        return User_status;
    }

    public int getUser_webcam() {
        return User_webcam;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public void setUser_passWord(String user_passWord) {
        User_passWord = user_passWord;
    }

    public void setUser_nickName(String user_nickName) {
        User_nickName = user_nickName;
    }

    public void setUser_email(String user_email) {
        User_email = user_email;
    }

    public void setUser_gender(String user_gender) {
        User_gender = user_gender;
    }

    public void setUser_isVip(int user_isVip) {
        User_isVip = user_isVip;
    }

    public void setUser_status(String user_status) {
        User_status = user_status;
    }

    public void setUser_webcam(int user_webcam) {
        User_webcam = user_webcam;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    protected User(Parcel in){
        User_id = in.readInt();
        User_name = in.readString();
        User_passWord = in.readString();
        User_nickName = in.readString();
        User_email = in.readString();
        User_gender = in.readString();
        User_isVip = in.readInt();
        User_status = in.readString();
        User_webcam = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(User_id);
        dest.writeString(User_name);
        dest.writeString(User_passWord);
        dest.writeString(User_nickName);
        dest.writeString(User_email);
        dest.writeString(User_gender);
        dest.writeInt(User_isVip);
        dest.writeString(User_status);
        dest.writeInt(User_webcam);


    }
    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };







}
