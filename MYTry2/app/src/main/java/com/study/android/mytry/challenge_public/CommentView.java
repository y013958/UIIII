package com.study.android.mytry.challenge_public;;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class CommentView extends LinearLayout {

    TextView username;
    TextView content;
    TextView writeTime;
    Button likeTo;
    TextView likeCount;

    public CommentView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       inflater.inflate(R.layout.challenge_comment_item,this, true);

        username = (TextView) findViewById(R.id.public_comment_username);
        content = (TextView) findViewById(R.id.public_comment_content);
        writeTime = (TextView) findViewById(R.id.public_comment_time);
        likeTo = (Button) findViewById(R.id.public_comment_good);
        likeCount = (TextView) findViewById(R.id.public_comment_good_count);
    }

    public void setUsername(String str){
        username.setText(str);
    }
    public void setContent(String str){
        content.setText(str);
    }
    public void setWriteTime(String str){
        writeTime.setText(str);
    }
    public void setLikeCount(String str){
        likeCount.setText(str);
    }

}
