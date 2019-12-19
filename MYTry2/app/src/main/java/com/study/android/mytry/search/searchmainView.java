package com.study.android.mytry.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;

import static com.study.android.mytry.login.Intro.local_url;

public class searchmainView extends LinearLayout {
    TextView challenge_name;
    TextView challenge_start;
    TextView challenge_end;
    ImageView imageViewsearch;
    String test="";

    public searchmainView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_main_item,this, true);

        challenge_start =  findViewById(R.id.challenge_start);
        challenge_name =  findViewById(R.id.challenge_name);
        challenge_end = findViewById(R.id.challenge_end);
        imageViewsearch =findViewById(R.id.imageViewsearch);

    }

    public void setChallenge_name(String str){ challenge_name.setText(str);}
    public void setChallenge_start(String str) {challenge_start.setText(str);}
    public void setChallenge_end(String str){ challenge_end.setText(str);}
    public void setChallenge_image(String str){ test=str;

        String url = local_url+"/"+test+".jpg";
        Picasso.with(getContext()).load(url).into(imageViewsearch);

    }
    public void setButtonstar(int likeExist){

    }
}



