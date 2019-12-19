package com.study.android.mytry.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;

import static com.study.android.mytry.login.Intro.local_url;

public class searchView extends LinearLayout {
    TextView challenge_name;
    TextView challenge_start;
    TextView challenge_end;
    ImageView imageViewsearch;


    String test="";

    public searchView(Context context){
      super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_main_item1,this, true);

        challenge_start =  findViewById(R.id.challenge_start1);
        challenge_name =  findViewById(R.id.challenge_name1);
        challenge_end = findViewById(R.id.challenge_end1);
        imageViewsearch =findViewById(R.id.imageViewsearch1);

    }

    public void setChallenge_name(String str){ challenge_name.setText(str);}
    public void setChallenge_start(String str) {challenge_start.setText(str);}
    public void setChallenge_end(String str){
        challenge_end.setText(str);
    }
    public void setChallenge_image(String str){ test=str;

        String url = local_url+"/"+test+".jpg";
        Picasso.with(getContext()).load(url).into(imageViewsearch);

    }

}