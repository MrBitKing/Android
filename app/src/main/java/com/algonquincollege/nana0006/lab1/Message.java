package com.algonquincollege.nana0006.lab1;

public class Message {
    private String content;
    private boolean isMine;

    public Message(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}
