package com.study.android.mytry.certification;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class CertificateItemView extends LinearLayout {

    TextView name;
    TextView term;
    TextView frequency;
    TextView time;
    TextView rate;

    ImageView back_image;
    ImageView over_image;
    TextView over_text;

    public CertificateItemView(Context context) {
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.certification_main_item, this, true);

        name = findViewById(R.id.certification_item_title);
        term = findViewById(R.id.certification_item_term);
        frequency = findViewById(R.id.certification_item_day);
        time = findViewById(R.id.certification_item_time);
        rate = findViewById(R.id.certification_item_rate);

        back_image = findViewById(R.id.certification_item_main_imageVIew1);
        over_image = findViewById(R.id.certification_item_over_imageView);
        over_text = findViewById(R.id.certification_item_over_textView);
    }

    public void setName(String cName) {
        name.setText(cName);
    }

    public void setTerm(String start, String end) {
        term.setText(start + " - " + end);
    }

    public void setFrequency(String cFrequency) {
        frequency.setText(cFrequency);
    }

    public void setTime(String cTime) {
        time.setText(cTime);
    }

    public void setRate(int all, int success) {
        double cRate = success/all*100;
        rate.setText("달성률 : "+String.format("%.1f", cRate)+"%");
    }
}
