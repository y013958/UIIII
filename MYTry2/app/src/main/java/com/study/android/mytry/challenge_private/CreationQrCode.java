package com.study.android.mytry.challenge_private;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;

import static com.study.android.mytry.login.Intro.local_url;

public class CreationQrCode extends AppCompatActivity {

    ImageView qrView;
    String json;
    HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_qr_code);

        Intent intent = getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        qrView = (ImageView) findViewById(R.id.private_qrcode_imgview);

       // String json = "{\"num\" : \""+map.get("num")+"\"}";

        String json = "{\"num\" : \""+map.get("num")+"\"," +
                " \"title\" : \""+map.get("title")+"\"," +
                "\"category\" : \""+map.get("category")+"\"," +
                "\"type\" : \""+map.get("type")+"\"," +
                "\"frequency\" : \""+map.get("frequency")+"\"," +
                "\"start\" : \""+map.get("start")+"\"," +
                "\"end\" : \""+map.get("end")+"\", " +
                "\"fee\" : \""+map.get("fee")+"\"," +
                "\"frequency\" : \""+map.get("frequency") +"\"," +
                "\"detail\" : \""+map.get("detail") +"\"," +
                "\"first_image\" :\""+map.get("first_image") +"\"," +
                "\"state\" : \""+map.get("state") +"\"," +
                "\"public\" : \""+map.get("public") +"\"," +
                "\"exp\" : \""+map.get("exp")+"\", " +
                "\"along\" : \""+map.get("along") +"\"," +
                "\"host\" : \""+map.get("host") +"\"}";


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            BitMatrix bitMatrix = multiFormatWriter.encode(json, BarcodeFormat.QR_CODE, 200, 200,hints);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrView.setImageBitmap(bitmap);
        } catch (Exception e) {
        }


    }

    public void onFinalNext(View v) {
        Intent intent = new Intent(CreationQrCode.this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onBackPressed(View v) {
        finish();
    }

}
