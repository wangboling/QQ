package com.iotek.fgs.entity;

/**
 * Created by fgs on 2016/1/2.
 */
public class Msg {
    private int Message_senderId;
    private int Message_recipiendtId;
    private String Message_content;
    private String Message_temes;
    private int Message_state;
    private int Message_type;

    public Msg() {
    }

    public Msg(int message_senderId, int message_recipiendtId, String message_content, String message_temes, int message_state, int message_type) {
        Message_senderId = message_senderId;
        Message_recipiendtId = message_recipiendtId;
        Message_content = message_content;
        Message_temes = message_temes;
        Message_state = message_state;
        Message_type = message_type;
    }

    public int getMessage_senderId() {
        return Message_senderId;
    }

    public int getMessage_recipiendtId() {
        return Message_recipiendtId;
    }

    public String getMessage_content() {
        return Message_content;
    }

    public String getMessage_temes() {
        return Message_temes;
    }

    public int getMessage_state() {
        return Message_state;
    }

    public int getMessage_type() {
        return Message_type;
    }

    public void setMessage_senderId(int message_senderId) {
        Message_senderId = message_senderId;
    }

    public void setMessage_recipiendtId(int message_recipiendtId) {
        Message_recipiendtId = message_recipiendtId;
    }

    public void setMessage_content(String message_content) {
        Message_content = message_content;
    }

    public void setMessage_temes(String message_temes) {
        Message_temes = message_temes;
    }

    public void setMessage_state(int message_state) {
        Message_state = message_state;
    }

    public void setMessage_type(int message_type) {
        Message_type = message_type;
    }
}
