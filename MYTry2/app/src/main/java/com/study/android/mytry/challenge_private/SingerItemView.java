package com.study.android.mytry.challenge_private;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class SingerItemView extends LinearLayout {

    TextView state;
    TextView title;
    TextView alongday;


    public SingerItemView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.challenge_private_item,this, true);


        state = (TextView) findViewById(R.id.private_item_state);
        title = (TextView) findViewById(R.id.private_item_name);
        alongday = (TextView) findViewById(R.id.private_item_alongday);

    }

    //public void setState( imgNum){linearLayout.setBackground(imgNum);}
    public void setState(String str){
        state.setText(str);
    }
    public void setTitle(String str){
        title.setText(str);
    }
    public void setAlongday(String str){
        alongday.setText(str);
    }

}