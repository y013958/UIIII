package com.study.android.mytry.cash;

public class Cash_SingerItem {
    private String cash_content;
    private String cash_date;
    private String cash_check;

    public Cash_SingerItem(String cash_content,String cash_check, String cash_date){
        this.cash_content= cash_content;
        this.cash_check = cash_check;
        this.cash_date= cash_date;

    }

    public String getCash_content() {
        return cash_content;
    }

    public String getCash_date() {
        return cash_date;
    }

    public String getCash_check() {
        return cash_check;
    }
}
