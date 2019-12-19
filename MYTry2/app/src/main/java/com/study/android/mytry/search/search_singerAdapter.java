package com.study.android.mytry.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.cash.CashView;
import com.study.android.mytry.cash.Cash_SingerItem;

import java.util.ArrayList;

public class search_singerAdapter extends BaseAdapter {
    private static final String TAG="lecture";
    Context serach_context;
    ArrayList<search_SingerItem> items=new ArrayList<>();

    public search_singerAdapter(Context context){

        this.serach_context=context;
    }

    public void addItem(search_SingerItem item){
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

        searchView view=null;

        if(convertView==null){
            view=new searchView(serach_context);
        }else {
            view=(searchView) convertView;
        }

        final search_SingerItem item=items.get(position);

        view.setChallenge_name(item.getChallenge_title());
        view.setChallenge_start("시작일 "+item.getChallenge_start());
        view.setChallenge_end("종료일 "+item.getChallenge_end());
        view.setChallenge_image(item.getChallenge_first_image());
        Log.d("testtxtx","사진이름"+item.getChallenge_first_image());

        //그래드 뷰 클릭
        view.setOnClickListener(new Button.OnClickListener() {
          public void onClick(View v){
              Log.d("testtxtx",item.getChallenge_num());
              item.getChallenge_num();

              Intent intent = new Intent(serach_context, Search_detail1.class);

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
            //  intent.putExtra("challenge_date",item.getChallenge_date());
              intent.putExtra("challenge_host",item.getChallenge_host());

              serach_context.startActivity(intent);

            }
        });



        return view;
    }

}
