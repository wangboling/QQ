package com.iotek.fgs.entity;

/**
 * Created by fgs on 2016/1/1.
 */
public class Friend {
    private int Friends_uid;
    private int Friends_toUid;
    private int Friends_status;

    public Friend() {
    }

    public Friend(int friends_uid, int friends_toUid, int friends_status) {
        Friends_uid = friends_uid;
        Friends_toUid = friends_toUid;
        Friends_status = friends_status;
    }

    public int getFriends_uid() {
        return Friends_uid;
    }

    public int getFriends_toUid() {
        return Friends_toUid;
    }

    public int getFriends_status() {
        return Friends_status;
    }

    public void setFriends_uid(int friends_uid) {
        Friends_uid = friends_uid;
    }

    public void setFriends_toUid(int friends_toUid) {
        Friends_toUid = friends_toUid;
    }

    public void setFriends_status(int friends_status) {
        Friends_status = friends_status;
    }
}
