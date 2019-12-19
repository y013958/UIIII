package com.study.android.mytry.mypage;

public class SingerItem {

    private int challenge_num;
    private String title;
    private String content;
    private String date;


    public SingerItem(int challenge_num, String title, String content, String date) {
        this.challenge_num=challenge_num;
        this.title=title;
        this.content=content;
        this.date=date;
    }

    public int getChallenge_num() {
        return challenge_num;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setChallenge_num(int challenge_num) {
        this.challenge_num = challenge_num;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

