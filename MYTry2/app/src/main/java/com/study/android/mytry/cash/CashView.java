package com.study.android.mytry.cash;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class CashView extends LinearLayout {
    TextView cash_date;
    TextView cash_name;
    TextView cash_content;

    public CashView(Context context){
      super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cash_list__item,this, true);

        cash_date =  findViewById(R.id.cash_date);
        cash_name =  findViewById(R.id.cash_name);
        cash_content = findViewById(R.id.cash_content);

    }

    public void setCash_date(String str){ cash_date.setText(str);}
    public void setCash_name(String str) {cash_name.setText(str);}
    public void setCash_content(String str){
        cash_content.setText(str);
    }

}
