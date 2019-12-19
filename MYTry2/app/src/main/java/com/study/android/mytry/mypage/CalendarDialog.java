package com.study.android.mytry.mypage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.study.android.mytry.R;
import static com.study.android.mytry.main.Fragment_Mypage.singerItems;

public class CalendarDialog extends Dialog  {

    private Context mContext;
    private TextView textMonth;
    private TextView textDate;
    private TextView textDay;
    private ListView listView;
    private FloatingActionButton floatingMemo;

    String shortDay;

    public CalendarDialog(@NonNull Context context, String shortDay) {
        super(context);
        mContext = context;
        this.shortDay=shortDay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_alert_dialog);

        SingerAdapter singerAdapter = new SingerAdapter(mContext);
        for (int i = 0; i < singerItems.length; i++) {

           if (singerItems[i].getDate().equals(shortDay)) {
                singerAdapter.addItem(singerItems[i]);
            }
        }

        textDate = (TextView)findViewById(R.id.textview_alterdialog_title);
        textDate.setText(shortDay);

        floatingMemo = findViewById(R.id.alert_dialog_floating);
        floatingMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MemoInsert.class);
                mContext.startActivity(intent);
            }
        });
        listView= findViewById(R.id.listview_alterdialog_list);
        listView.setAdapter(singerAdapter);
    }
}
