package com.example.firebase1;

public class ChattingData {
    public String msg;
    public String time;

    public ChattingData() {
        msg = new String();
        time = new String();
    }

    public ChattingData(String msg, String time)
    {
        this.msg = msg;
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
