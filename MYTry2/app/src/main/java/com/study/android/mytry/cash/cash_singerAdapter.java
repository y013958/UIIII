package com.study.android.mytry.cash;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

import java.util.ArrayList;

public class cash_singerAdapter extends BaseAdapter {
    private static final String TAG="lecture";

    Context cash_context;
    ArrayList<Cash_SingerItem> items=new ArrayList<>();

    public cash_singerAdapter(Context context){

        this.cash_context=context;
    }

    public void addItem(Cash_SingerItem item){
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

        CashView view=null;
//        ViewHolder wrapper = null;

        RecyclerView.ViewHolder holder = null;

        if(convertView==null){
            view=new CashView(cash_context);
        }else {
            view=(CashView) convertView;
        }

        final Cash_SingerItem item=items.get(position);



        view.setCash_date(item.getCash_date());
        Log.d("ssssssettext2","포지션 "+items.get(position));
        Log.d("ssssssettext2","이고 "+item.getCash_check());
        if(item.getCash_check().equals("0")) {

            view.setCash_name("입금");
            view.setCash_content("+"+item.getCash_content());
         //   view.setBackgroundColor(cash_context.getResources().getColor(R.color.cash_red));

        }
        else if(item.getCash_check().equals("1")) {
            view.setCash_name("출금");
            view.setCash_content("-"+item.getCash_content());
        //    view.setBackgroundColor(cash_context.getResources().getColor(R.color.cash_blue));

        } else if(item.getCash_check().equals("2")) {
            view.setCash_name("상금");
           view.setCash_content("+"+item.getCash_content());

        }else if(item.getCash_check().equals("3")) {
            view.setCash_name("상금전환");
            view.setCash_content("+"+item.getCash_content());
        }else if(item.getCash_check().equals("4")){
            view.setCash_name("챌린지 참가");
            view.setCash_content("-"+item.getCash_content());
        }else if(item.getCash_check().equals("6")){
            view.setCash_name("챌린지 취소 ");
            view.setCash_content("+"+item.getCash_content());
        }

        return view;
    }


}
