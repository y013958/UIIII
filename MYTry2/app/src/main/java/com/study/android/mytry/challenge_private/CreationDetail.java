package com.study.android.mytry.challenge_private;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileLoadConnection;

import java.util.HashMap;

import static com.study.android.mytry.login.Intro.local_url;

public class CreationDetail extends AppCompatActivity {

    HashMap<String, String> map = new HashMap<>();

    private TextView textTitle;
    private TextView textCategory;
    private TextView textType;
    private TextView textFrequency;
    private TextView textCategory2;
    private TextView textFee;
    private TextView textDetail;
    private ImageView First_image;
    private TextView textExp;
    private TextView textAlongday;
    private TextView textHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_detail);

        Intent intent = getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        textTitle = findViewById(R.id.private_detail_title);
        textCategory = findViewById(R.id.private_detail_category);
        textCategory2= findViewById(R.id.private_detail_category_textView2);
        textType= findViewById(R.id.private_detail_type_textView);
        textFrequency= findViewById(R.id.private_detail_frequency_textView);
        textFee= findViewById(R.id.private_detail_fee);
        First_image= findViewById(R.id.private_detail_imageVIew);
        textExp= findViewById(R.id.private_detail_reward_textView3);
        textAlongday= findViewById(R.id.private_detail_term);
      //  textHost= findViewById(R.id.private_detail_title);
        // 시간 : private_detail_time_textView

        textTitle.setText(map.get("title"));
        textCategory.setText(map.get("category"));
        textCategory2.setText(map.get("category"));
        textType.setText(map.get("type"));
        textFrequency.setText(map.get("frequency"));
        textFee.setText(map.get("fee"));
        textExp.setText(map.get("exp"));
        textAlongday.setText(map.get("start")+" ~ "+map.get("end"));


        String url = local_url+"/";
        url= url+map.get("first_image")+".jpg";

        FileUploadNetworkTask fileUploadNetworkTask = new FileUploadNetworkTask(url,null);
        fileUploadNetworkTask.execute();
    }

    // 참가하기 버튼
    public void onClicked(View v){

    }

    public void onBackPressed(View v){
        finish();
    }

    public class FileUploadNetworkTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ContentValues values;

        public FileUploadNetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap result; // 요청 결과를 저장할 변수.
            FileLoadConnection requestHttpURLConnection = new FileLoadConnection();
            result = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap json) {
            super.onPostExecute(json);
            First_image.setImageBitmap(json);
        }
    }

}
