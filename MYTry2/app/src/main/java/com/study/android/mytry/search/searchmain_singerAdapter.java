package com.study.android.mytry.search;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.cash.Kakao_cash;
import com.study.android.mytry.challenge_public.CommentView;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.study.android.mytry.login.Intro.local_url;
import static com.study.android.mytry.main.Fragment_Search.bookuserid;
import static com.study.android.mytry.main.Fragment_Search.gridView;

public class searchmain_singerAdapter extends BaseAdapter {
    private static final String TAG="lecture";
    Context serach_context;
    ArrayList<searchmain_SingerItem> items = new ArrayList<>();
    NetworkTask networkTask;
    GlobalApplication myApp;
    int count=0;



    public searchmain_singerAdapter(Context context){

        this.serach_context=context;
    }

    public void addItem(searchmain_SingerItem item){
        items.add(item);
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position,final View convertView, ViewGroup parent){

        searchmainView view=null;

        if(convertView==null){
            view=new searchmainView(serach_context);
        }else {
            view=(searchmainView) convertView;
        }

        final searchmain_SingerItem item = items.get(position);

        view.setChallenge_name(item.getChallenge_title());
        view.setChallenge_start("시작일 "+item.getChallenge_start());
        view.setChallenge_end("종료일 "+item.getChallenge_end());
        view.setChallenge_image(item.getChallenge_first_image());

        Log.d("testtxtx","사진이름"+item.getChallenge_first_image());



        //그래드 뷰 클릭
        view.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                Log.d("testtxtx",item.getChallenge_num());

                Log.d("testtxtx",""+item.getBookmaker_exist());

                Intent intent = new Intent(serach_context, Search_detail.class);

                intent.putExtra("challenge_num",item.getChallenge_num());
                intent.putExtra("challenge_title",item.getChallenge_title());
                intent.putExtra("challenge_category",item.getChallenge_category());
                intent.putExtra("challenge_type",item.getChallenge_type());
                intent.putExtra("challenge_frequency",item.getChallenge_frequency());
                intent.putExtra("challenge_start",item.getChallenge_start());
                intent.putExtra("challenge_end",item.getChallenge_end());
                intent.putExtra("challenge_fee",item.getChallenge_fee());
                intent.putExtra("challenge_time",item.getChallenge_time());
                intent.putExtra("challenge_detail",item.getChallenge_detail());
                intent.putExtra("challenge_first_image",item.getChallenge_first_image());
                intent.putExtra("challenge_state",item.getChallenge_state());
                intent.putExtra("challenge_public",item.getChallenge_public());
                intent.putExtra("challenge_exp",item.getChallenge_exp());
                intent.putExtra("challenge_host",item.getChallenge_host());
                intent.putExtra("bookmaker_exist",item.getBookmaker_exist());


                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                serach_context.startActivity(intent);




            }
        });

        count = item.getBookmaker_exist();

        final ImageButton bookmaker=view.findViewById(R.id.imageButtonstar);

//        if(item.getBookmaker_exist()==0) {
//            bookmaker.setBackgroundResource(R.drawable.search_unstar);
//        }
//        else{
//            bookmaker.setBackgroundResource(R.drawable.search_instar);
//        }
        if(count==0) {
            bookmaker.setBackgroundResource(R.drawable.search_unstar);
        }
        else{
            bookmaker.setBackgroundResource(R.drawable.search_instar);
        }


        bookmaker.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){
                Log.d("바뀐 정보",""+item.getBookmaker_exist());
                if(count==0){
                    Toast.makeText(serach_context,"북마크에 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                    bookmaker.setBackgroundResource(R.drawable.search_instar);
                    count=1;


                }else if(count==1){
                    Toast.makeText(serach_context,"북마크가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                    bookmaker.setBackgroundResource(R.drawable.search_unstar);
                    count=0;
                }



                String str = "content: "+item.getChallenge_num();
                Log.d("bookmaker",str);
                String url1 = local_url+"/uzinee/bookmake";
                String msg1 = "?challenge_num="+item.getChallenge_num()+
                        "&member_id="+bookuserid;
                Log.d("lecture", msg1);
                url1 = url1 +msg1;
                Log.d("lecture", url1);

                networkTask = new NetworkTask(url1, null);
                networkTask.execute();

//                if(item.getChallenge_category().equals("취미")) {
//
//                    String url = local_url + "/uzinee/category_hobby";
//                    String msg = "?member_id=" + bookuserid;
//                    url = url + msg;
//                    Log.d("done_money1231", url);
//                    networkTask = new NetworkTask(url, null);
//                    networkTask.execute();
//                }
            }


        });

        return view;
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
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                if(s != null) {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray ListArray = jsonObject.getJSONArray("search_list");
                    searchmain_SingerItem[] singermainItems = new searchmain_SingerItem[ListArray.length()];

                    searchmain_singerAdapter adapter  = new searchmain_singerAdapter(serach_context);
                    for (int i = 0; i < ListArray.length(); i++) {
                        JSONObject ListObject = ListArray.getJSONObject(i);
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

                }
            }   catch (JSONException e) {
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
    }


}
