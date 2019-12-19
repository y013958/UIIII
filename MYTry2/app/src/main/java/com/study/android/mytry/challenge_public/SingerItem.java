package com.study.android.mytry.challenge_public;

import android.widget.Button;
import android.widget.TextView;

public class SingerItem {

    private int num;
    private String category;
    private String title;
    private int likecount;
    private int likeExist;


    public SingerItem(int num, String category, String title, int likecount, int likeExist){
        this.num = num;
        this.category=category;
        this.title=title;
        this.likecount=likecount;
        this.likeExist=likeExist;
    }

    public int getNum() {
        return num;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public int getLikecount() {
        return likecount;
    }

    public int getLikeExist() {
        return likeExist;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

}
