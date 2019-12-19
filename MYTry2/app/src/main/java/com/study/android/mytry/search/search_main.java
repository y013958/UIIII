package com.study.android.mytry.search;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
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

import static com.study.android.mytry.login.Intro.local_url;

import static com.study.android.mytry.main.Fragment_Search.bookuserid;


//로그인 전
public class search_main  extends AppCompatActivity {
    private static final String TAG ="lecture";

    private FloatingActionButton fab;
    AdView mAdView;
    Button buttonguide;
    GridView gridView;
    search_singerAdapter adapter;
    GlobalApplication myApp = (GlobalApplication) getApplication();
    String cash_user = myApp.getGlobalString();

    Button all;
    Button hobby;
    Button life;
    Button capability;
    Button health;
    Button realtionship;
    Button asset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main1);
        gridView = findViewById(R.id.search_list1);


        adapter = new search_singerAdapter(search_main.this);

        Intent intent = new Intent(search_main.this, PopupActivity.class);
        startActivity(intent); // 다음화면으로 넘어가기

        fab = findViewById(R.id.loginfab);
        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(search_main.this, Login.class);
                startActivity(intent); // 다음화면으로 넘어가기
            }
        });
        buttonguide = findViewById(R.id.buttonguide);
        buttonguide.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(search_main.this, guide.class);
                startActivity(intent); // 다음화면으로 넘어가기
            }
        });

        all = findViewById(R.id.search_main_category_all1);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/serach_public_list";
                String msg = "?member_id="+bookuserid;
                url = url +msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });

        life =findViewById(R.id.search_main_category_life1);
        life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_life";
                String msg = "?member_id="+bookuserid;
                url = url +msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });

        hobby =findViewById(R.id.search_main_category_hobby1);
        hobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_hobby";
                String msg = "?member_id="+bookuserid;
                url = url +msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });


        capability = findViewById(R.id.search_main_category_capability1);
        capability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_capability";
                String msg = "?member_id="+bookuserid;
                url = url +msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });


        health = findViewById(R.id.search_main_category_health1);
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_health";
                String msg = "?member_id="+bookuserid;
                url = url +msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });


        realtionship = findViewById(R.id.search_main_category_relationship1);
        realtionship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_relationship";
                String msg = "?member_id="+bookuserid;
                url = url +msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });


        asset = findViewById(R.id.search_main_category_asset1);
        asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_asset";
                String msg = "?member_id="+bookuserid;
                url = url +msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

            }
        });



        mAdView = findViewById(R.id.adView);
        String bannerid = getResources().getString(R.string.ad_unit_id_1);
        MobileAds.initialize(getApplicationContext(), bannerid);



        // 다음의 리스너는 구현하지 않아도 된다.
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("aderror", "b:"+errorCode);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        // 테스트 광고 부르기

        AdRequest adRequest = new AdRequest
                .Builder()
                .addTestDevice("HASH_DEVICE_ID") //테스트
                .build();
        mAdView.loadAd(adRequest);

        String url = local_url + "/uzinee/serach_public_list";
        String msg = "?member_id="+bookuserid;
        url = url +msg;
        Log.d("done_money1231", url);
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();



        Log.d("lecture", "챌린지 모든 리스트 출력:"+url);
//        //그래드 뷰 클릭
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),"눌림"+i,Toast.LENGTH_LONG).show();
//                Log.d("dfasdf",adapter.getItem(i).toString());
//
//
//            }
//        });


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

                    search_SingerItem[] singerItems = new search_SingerItem[ListArray.length()];
                    adapter = new search_singerAdapter(search_main.this);
                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        String url = local_url+"/" + ListObject.getString("challenge_first_image")+".jpg";

                        Log.d("testtxtx",url);
                        FileUploadNetworkTask fileUploadNetworkTask = new FileUploadNetworkTask(url, null);
                        fileUploadNetworkTask.execute();

                        singerItems[i] =
                                new search_SingerItem(
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
                           //     ListObject.getString("challenge_date"),
                                ListObject.getString("challenge_host"));
                        adapter.addItem(singerItems[i]);
                        Log.d("ssssssettext2", "들어옴111");
                    }
                    gridView.setAdapter(adapter);
                    Log.d("ssssssettext2", "들어옴222");

                } else {
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View view = inflater.inflate(R.layout.search_main_item, null);

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




