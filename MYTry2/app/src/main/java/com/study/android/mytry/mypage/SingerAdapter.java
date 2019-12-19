package com.study.android.mytry.mypage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_private.CreationDetail;
import com.study.android.mytry.challenge_private.CreationFirst;
import com.study.android.mytry.challenge_private.CreationQrCode;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.study.android.mytry.challenge_private.CreationMain.private_listView;
import static com.study.android.mytry.login.Intro.local_url;


public class SingerAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<SingerItem> items = new ArrayList<>();
    LinearLayout linearLayout;

    HashMap<String, String> map = new HashMap<>();


    public SingerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(SingerItem item) {
        items.add(item);
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

        SingerItemView view = null;

        if (convertView == null) {
            view = new SingerItemView(context);
        } else {
            view = (SingerItemView) convertView;
        }

        final SingerItem item = items.get(position);
        view.setTitle(item.getTitle());
        view.setContent(item.getContent());
        view.setDate(item.getDate());

          return view;
    }


}
