package com.study.android.mytry.certification;

public class CertificateItem {

    private String num;
    private String check;
    private String type;
    private String name;
    private String start;
    private String end;
    private String frequency;
    private String time;
    private String image;
    private int all_count;
    private int check_count;

    public CertificateItem(String num , String check , String type , String name , String start , String end , String frequency , String time , String image , int all_count , int check_count) {
        this.num = num;
        this.check = check;
        this.type = type;
        this.name = name;
        this.start = start;
        this.end = end;
        this.frequency = frequency;
        this.time = time;
        this.image = image;
        this.all_count = all_count;
        this.check_count = check_count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAll_count() {
        return all_count;
    }

    public void setAll_count(int all_count) {
        this.all_count = all_count;
    }

    public int getCheck_count() {
        return check_count;
    }

    public void setCheck_count(int check_count) {
        this.check_count = check_count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}