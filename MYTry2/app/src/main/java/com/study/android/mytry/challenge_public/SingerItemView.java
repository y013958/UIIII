package com.study.android.mytry.challenge_public;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class SingerItemView extends LinearLayout {

    TextView category;
    TextView title;
    Button likebtn;

    public SingerItemView(Context context){
      super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.challenge_public_item,this, true);


        category = (TextView) findViewById(R.id.public_item_category);
        title = (TextView) findViewById(R.id.public_item_name);
        likebtn = (Button) findViewById(R.id.public_challenge_btn);
    }

    public void setCategory(String str){
        category.setText(str);
    }
    public void setTitle(String str){
        title.setText(str);
    }
    public void setLikeBtn(int likeExist){
        likebtn.setText(likeExist);
    }
}
