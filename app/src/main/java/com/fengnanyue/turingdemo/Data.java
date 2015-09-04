package com.fengnanyue.turingdemo;

/**
 * Created by Fernando on 15/9/3.
 */
public class Data {

    public static final int SEND = 1;
    public static final int RECEIVER = 2;
    private int flag;
    private String content;
    private String time;

    public Data(String content,int flag,String time){
        setContent(content);
        setFlag(flag);
        setTime(time);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


