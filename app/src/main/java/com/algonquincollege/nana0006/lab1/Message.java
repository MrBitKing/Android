package com.algonquincollege.nana0006.lab1;


//import android.arch.persistence.room.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Message {

    @PrimaryKey // (autoGenerate = true)
    private long id;
    private String content;
    private int isMine;

    public Message() {//{(String content, int isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setIsMine(int bool) {
        this.isMine = bool;
    }

    public int getIsMine() {
        return this.isMine;
    }


}
