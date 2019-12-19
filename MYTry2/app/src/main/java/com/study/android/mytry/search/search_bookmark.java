package com.study.android.mytry.search;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileLoadConnection;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.Login;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.study.android.mytry.login.Intro.local_url;
import static com.study.android.mytry.main.Fragment_Search.bookuserid;


public class search_bookmark extends AppCompatActivity {
    private static final String TAG ="lecture";
    public static Activity search_bookmark;
    AdView mAdView;
    int position;
    GridView gridView;
    searchmain_singerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        search_bookmark=search_bookmark.this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bookmark);
        gridView = findViewById(R.id.book_list);

        adapter = new searchmain_singerAdapter(search_bookmark.this);

        String url = local_url + "/uzinee/public_bookmark";
        String msg = "?member_id="+bookuserid;
        url = url +msg;
        Log.d("public_bookmark", url);
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        Log.d("lecture", "챌린지 모든 리스트 출력:"+url);

    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            Log.d("done_money1231", "통과통과4");
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }
        @Override
        protected void onPostExecute(String json) {

            super.onPostExecute(json);

            try {
                if (json != null) {
                    JSONObject jsonObject = new JSONObject(json);
                    Log.d("ssssssettext2", "들어옴");
                    Log.d("ssssssettext2", json);
                    JSONArray ListArray = jsonObject.getJSONArray("search_list");

                    searchmain_SingerItem[] singermainItems = new searchmain_SingerItem[ListArray.length()];

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        String url = local_url+"/" + ListObject.getString("challenge_first_image")+".jpg";

                        Log.d("testtxtx",url);
                        FileUploadNetworkTask fileUploadNetworkTask = new FileUploadNetworkTask(url, null);
                        fileUploadNetworkTask.execute();

                        singermainItems[i] =
                                new searchmain_SingerItem(
                                        String.valueOf(ListObject.getInt("challenge_num")),
                                        ListObject.getString("challenge_title"),
                                        ListObject.getString("challenge_category"),
                                        ListObject.getString("challenge_type"),
                                        ListObject.getString("challenge_frequency"),
                                        ListObject.getString("challenge_start"),
                                        ListObject.getString("challenge_end"),
                                        String.valueOf(ListObject.getInt("challenge_fee")),
                                        ListObject.getString("challenge_time"),
                                        ListObject.getString("challenge_detail"),
                                        ListObject.getString("challenge_first_image"),
                                        ListObject.getString("challenge_state"),
                                        ListObject.getString("challenge_public"),
                                        String.valueOf(ListObject.getInt("challenge_exp")),
                                        //      ListObject.getString("challenge_date"),
                                        ListObject.getString("challenge_host"),
                                        ListObject.getInt("bookmake_exist"));
                        adapter.addItem(singermainItems[i]);
                        Log.d("ssssssettext2", "들어옴111");
                    }
                    gridView.setAdapter(adapter);
                    Log.d("ssssssettext2", "들어옴222");

                } else {

                    TextView challenge_name =  findViewById(R.id.challenge_name1);
                    TextView challenge_start =  findViewById(R.id.challenge_start1);
                    TextView challenge_end = findViewById(R.id.challenge_end1);

                    challenge_name.setText("");
                    challenge_start.setText("");
                    challenge_end.setText("");

                    Log.d("ssssssettext2", "null임");

                }

            } catch (JSONException e) {
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
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

            Log.d("testtxtx","resilt   "+result.toString());

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap1) {

            super.onPostExecute(bitmap1);
            Log.d("testtxtx","bitmap"+bitmap1.toString());
        }
    }

}




