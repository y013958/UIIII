package com.study.android.mytry.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class search_filter_Adapter extends BaseAdapter {
    private static final String TAG="lecture";

    Context filter_context;
    ArrayList<search_filter_item> items=new ArrayList<>();

    public search_filter_Adapter(Context context){

        this.filter_context=context;
    }

    public void addItem(search_filter_item item){
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
        search_filter_view view=null;

        if(convertView==null){
            view=new search_filter_view(filter_context);
        }else {
            view=(search_filter_view) convertView;
        }

        final search_filter_item item=items.get(position);

        view.setFilter_items(item.getInterest_context());


        view.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(filter_context, search_filer_main.class);
                intent.putExtra("sendData", item.getInterest_context());// 이 메서드를 통해 데이터를 전달합니다.


                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                filter_context.startActivity(intent);

            }
        });

        return view;
    }


}
