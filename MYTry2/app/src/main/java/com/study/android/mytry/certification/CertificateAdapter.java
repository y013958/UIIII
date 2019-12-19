package com.study.android.mytry.certification;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.android.mytry.R;

import java.security.cert.Certificate;
import java.util.ArrayList;


public class CertificateAdapter extends BaseAdapter {
    private static final String tag="certificate";

    Context context;
    int layout;
    ArrayList<CertificateDto> items;
    LayoutInflater inf;

    public CertificateAdapter(Context context, int layout, ArrayList<CertificateDto> items) {
        this.context = context;
        this.layout = layout;
        this.items = items;
        inf = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inf.inflate(layout, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.certification_item_title);
        TextView term = (TextView)convertView.findViewById(R.id.certification_item_term);
        TextView frequency = (TextView)convertView.findViewById(R.id.certification_item_day);
        TextView time = (TextView)convertView.findViewById(R.id.certification_item_time);
        TextView rate = (TextView)convertView.findViewById(R.id.certification_item_rate);

        ImageView back_image = (ImageView)convertView.findViewById(R.id.certification_item_main_imageVIew1);
        ImageView over_image = (ImageView)convertView.findViewById(R.id.certification_item_over_imageView);
        TextView over_text = (TextView)convertView.findViewById(R.id.certification_item_over_textView);

        CertificateDto m = items.get(position);
        name.setText(m.title);
        term.setText(m.start + " - " + m.end);
        frequency.setText(m.frequency);
        time.setText(m.time);

        double cRate;
        if(m.check_count != 0) {
            cRate = (m.check_count/m.count)*100;
        } else {
            cRate = 0;
        }

        rate.setText("달성률 : "+String.format("%.1f", cRate)+"%");

        if(m.check.equals("1")) {
            over_image.setImageResource(R.drawable.challenge_liketo);
            over_text.setText("오늘 끝!");
        } else {
            over_image.setImageResource(R.drawable.login_egg);
            over_text.setText("인증하기");
        }

        return convertView;
    }
}