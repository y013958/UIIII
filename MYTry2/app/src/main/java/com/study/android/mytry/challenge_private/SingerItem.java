package com.study.android.mytry.challenge_private;

public class SingerItem {

    private int num;
    private String title;
    private String category;
    private String type;
    private String frequency;
    private String start;
    private String end;
    private String fee;
    // private String challenge_time;
    private String detail;
    private String first_image;
    private String state;
    private String pub;
    private String exp;
    private String alongday;
    private String host;

    public SingerItem(int num, String title, String category, String type, String frequency, String start, String end, String fee, String detail, String first_image, String state, String pub, String exp, String alongday, String host) {
        this.num = num;
        this.title = title;
        this.category = category;
        this.type = type;
        this.frequency = frequency;
        this.start = start;
        this.end = end;
        this.fee = fee;
        //    this.challenge_time = challenge_time;
        this.detail = detail;
        this.first_image = first_image;
        this.state = state;
        this.pub = pub;
        this.exp = exp;
        this.alongday=alongday;
        this.host = host;
    }

    public int getNum() {
        return num;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getAlongday() {
        return alongday;
    }

    public String getFee() {
        return fee;
    }

    public String getHost() {
        return host;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setFirst_image(String first_image) {
        this.first_image = first_image;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDetail() {
        return detail;
    }

    public String getFirst_image() {
        return first_image;
    }

    public String getPub() {
        return pub;
    }

    public String getExp() {
        return exp;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlongday(String alongday) {
        this.alongday = alongday;
    }

}

