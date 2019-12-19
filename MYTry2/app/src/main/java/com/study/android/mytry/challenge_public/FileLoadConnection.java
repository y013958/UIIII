package com.study.android.mytry.challenge_public;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileLoadConnection {

    Bitmap bitmap;// 비트맵 객체


    public Bitmap request(String _url){
        URL url =null;
        try{
            // 스트링 주소를 url 형식으로 변환
            url =new URL(_url);
//            url =new URL("http://192.168.219.124:8081/IMG_20191007_071551.jpg.jpg");

            // url에 접속 시도
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            // 스트림 생성
            InputStream is = conn.getInputStream();
            // 스트림에서 받은 데이터를 비트맵 변환
            // 인터넷에서 이미지 가져올 때는 Bitmap을 사용해야함
            System.out.println(is);
            bitmap = BitmapFactory.decodeStream(is);

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
