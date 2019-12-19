package com.study.android.mytry.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class search_filter_view extends LinearLayout {
    TextView filter_items;

    public search_filter_view(Context context){
      super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_filter_item,this, true);

        filter_items =  findViewById(R.id.filter_items);


    }

    public void setFilter_items(String str){filter_items.setText(str);}
}
