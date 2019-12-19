package com.study.android.mytry.search;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.study.android.mytry.login.Intro.local_url;

public class Search_detail1 extends AppCompatActivity {
    private static final String tag = "certificate";
    String i_num;
    String i_title;
    String i_img;
    String i_start;
    String i_end;
    Button join_btn;
    String url;
    String userid;
    String i_category;
    String i_type;
    String i_frequency;
    String i_time;
    String i_time_term;

    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    ArrayList<Movie> movieList;
    LinearLayout movie_list;

    TextView search_content_pulbic;
    TextView search_content_title;
    TextView search_content_term;
    ImageView search_content_main_imageview;
    TextView search_contnet_day;
    TextView search_content_total_money;  //모인금액
    TextView search_content_total_challenger;  //신청자수
    TextView search_frequency;      //인증빈도
    TextView search_content_time;   //인증가능시간
    TextView search_content_type;  //인증시간
    TextView search_content_time_term; //챌린지 시작 시간 계산
//로그인 전
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail1);

        search_content_pulbic = (TextView) findViewById(R.id.search_content_public_title1);
        search_content_title = (TextView) findViewById(R.id.search_content_title);
        search_content_term=(TextView)findViewById(R.id.search_content_term);
        search_content_main_imageview=findViewById(R.id.search_content_main_imageVIew);
        search_contnet_day=findViewById(R.id.search_content_day);
        search_frequency =findViewById(R.id.search_content_frequency_textView);
        search_content_time=findViewById(R.id.search_content_time_textView);
        search_content_type=findViewById(R.id.search_content_type_textView);
        search_content_time_term = findViewById(R.id.search_content_time_term);
        GlobalApplication myApp = (GlobalApplication) getApplication();
        userid = myApp.getGlobalString();

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        i_num = intent.getStringExtra("challenge_num");
        i_title = intent.getStringExtra("challenge_title");
        i_img = intent.getStringExtra("challenge_first_image");
        i_category = intent.getStringExtra("challenge_category");
        i_type = intent.getStringExtra("challenge_type");
        i_start =intent.getStringExtra("challenge_start");
        i_end=intent.getStringExtra("challenge_end");
        i_frequency=intent.getStringExtra("challenge_frequency");
        i_time = intent.getStringExtra("challenge_time");

        if (i_type.equals("영화")) {

            Log.d(tag , i_type);
            Log.d(tag , "num, title : " + i_num + ", " + i_title);


            movie_list=findViewById(R.id.movie_list_before);
            recyclerView = (RecyclerView)findViewById(R.id.recycler_view_before);
            movieList = new ArrayList<Movie>();

            //Asynctask - OKHttp
            MyAsyncTask mAsyncTask = new MyAsyncTask();
            mAsyncTask.execute();

            //LayoutManager
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            movie_list.setVisibility(View.VISIBLE);


        }

        search_content_pulbic.setText(i_title);
        search_content_title.setText(i_title);
        search_content_term.setText(i_start+"-"+i_end);
        search_contnet_day.setText(i_frequency);
        search_frequency.setText(i_frequency);
        search_content_time.setText(i_time);
        search_content_type.setText(i_type);
        String url = local_url+"/"+i_img+".jpg";
        Picasso.with(Search_detail1.this).load(url).into(search_content_main_imageview);

        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {

                            @Override
                            public void run()
                            {
                                getCurrentTime(i_start);

                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        }).start();

    }

    public void  getCurrentTime(String mday) {

        //   try {
        long time = System.currentTimeMillis();
        Date d;
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd hh:mm:ss");

        String str = dayTime.format(new Date(time));
        Log.d("테스트", "현재시간" + str);

        SimpleDateFormat original_format = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyyMMdd hh:mm:ss");

        try {
            // 문자열 타입을 날짜 타입으로 변환한다.
            Date original_date = original_format.parse(mday);
            // 날짜 형식을 원하는 타입으로 변경한다.
            String new_date = new_format.format(original_date);

            Log.d("테스트", "챌린지 시작시간" + new_date);

            Date todate_date = dayTime.parse(str);
            Date tostart_date = dayTime.parse(new_date);

            Log.d("테스트", "현재date" + todate_date);
            Log.d("테스트", "챌린지 시작시간date" + tostart_date);

            int diff = (int) (tostart_date.getTime() - todate_date.getTime() - (60 * 60 * 1000) * 8);
            Log.d("테스트", "챌린지 시간계산" + diff);

            int mDay = diff / (24 * 60 * 60 * 1000);
            int mHour = (diff - mDay * 24 * 60 * 60 * 1000) / (60 * 60 * 1000) % 24;
            int mMinute = (diff - mDay * 24 * 60 * 60 * 1000 - mHour * (60 * 60 * 1000) % 24) / (60 * 1000) % 60;
            int diffSeconds = (diff - mDay * 24 * 60 * 60 * 1000 - mHour * (60 * 60 * 1000) % 24 - mMinute * (60 * 1000) % 60) / 1000 % 60;

           search_content_time_term.setText("챌린지 시작 까지 " + mDay + "일 " + mHour + "시 " + mMinute + "분 " + diffSeconds + "초");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

        // 통신
    class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url , ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url , values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //tv_outPut.setText(s);
        }
    }

    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(Search_detail1.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            //show dialog
            progressDialog.show();
        }

        @Override
        protected Movie[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/upcoming?api_key=249f29394c3815c9af3e36c4e6c5c7de&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Movie[] posts = gson.fromJson(rootObject, Movie[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Movie[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList에 차례대로 집어 넣는다.
            if(result.length > 0){
                for(Movie p : result){
                    movieList.add(p);
                }
            }

            //어답터 설정
            adapter = new MyRecyclerViewAdapter(Search_detail1.this, movieList);
            recyclerView.setAdapter(adapter);
        }

        public void onBackPressed(View v) {
            finish();
        }

    }
}
