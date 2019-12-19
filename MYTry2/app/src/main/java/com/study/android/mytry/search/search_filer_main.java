package com.study.android.mytry.search;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileLoadConnection;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;
import static com.study.android.mytry.main.Fragment_Search.bookuserid;

public class search_filer_main extends AppCompatActivity {

   TextView filer_main;
    Button clear;
    GridView gridView1;
    searchmain_singerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_filer_main);
        adapter = new searchmain_singerAdapter(search_filer_main.this);
        gridView1 =findViewById(R.id.filer_list);
        filer_main=findViewById(R.id.filer_main);
        clear =findViewById(R.id.clear);

        Intent intent = getIntent();
        String receiveStr = intent.getExtras().getString("sendData");// 전달한 값을 받을 때
        filer_main.setText(receiveStr);

        Log.d("filer",receiveStr);

        String url = local_url + "/uzinee/serach_filer_main";
        String msg = "?member_id="+bookuserid +"&filer="+receiveStr;
        url = url +msg;
        Log.d("filer", url);
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();


        //초기화
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(search_filer_main.this, search_filer.class);
                startActivity(intent);
                finish();
            }

        });

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
                    gridView1.setAdapter(adapter);
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

