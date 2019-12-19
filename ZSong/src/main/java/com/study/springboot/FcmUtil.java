package com.study.springboot;


import java.io.FileInputStream;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;


@Component
public class FcmUtil {
    public void send_FCM(String tokenId, String title, String content, String topic) {
        try {    
            //본인의 json 파일 경로 입력
//        	D:/finalProject/workspace-spring-tool-suite-4-4.4.1.RELEASE/Song/src/main/resources/static/project-71fd1-firebase-adminsdk-9c4gq-a51140e11e.json
            FileInputStream refreshToken = new FileInputStream("D:/finalProject/ZSong/src/main/resources/static/project-71fd1-firebase-adminsdk-9c4gq-14ba520bf7.json");	   
            
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://project-71fd1.firebaseio.com")
                    .build();
            
            //Firebase 처음 호출시에만 initializing 처리
            if(FirebaseApp.getApps().isEmpty()) { 
                FirebaseApp.initializeApp(options);
            }
            
            //String registrationToken = 안드로이드 토큰 입력;
            String registrationToken = tokenId;

            if(topic.length() == 0)
            {
            	//message 작성
                Message msg = Message.builder()
                        .setAndroidConfig(AndroidConfig.builder()
                            .setTtl(3600 * 1000) // 1 hour in milliseconds
                            .setPriority(AndroidConfig.Priority.NORMAL)
                            .setNotification(AndroidNotification.builder()
                                .setTitle(title)
                                .setBody(content)
                                .setIcon("stock_ticker_update")
                                .setColor("#0051ff")
                                .build())
                            .build())
                        .setToken(registrationToken)
                        .build();

                //메세지를 FirebaseMessaging 에 보내기
                String response = FirebaseMessaging.getInstance().send(msg);
                //결과 출력
                System.out.println("Successfully sent message: " + response);
            }
            else
            {
            	//message 작성
                Message msg = Message.builder()
                        .setAndroidConfig(AndroidConfig.builder()
                            .setTtl(3600 * 1000) // 1 hour in milliseconds
                            .setPriority(AndroidConfig.Priority.NORMAL)
                            .setNotification(AndroidNotification.builder()
                                .setTitle(title)
                                .setBody(content)
                                .setIcon("stock_ticker_update")
                                .setColor("#0051ff")
                                .build())
                            .build())
                        .setTopic(topic)
                        .build();

                //메세지를 FirebaseMessaging 에 보내기
                String response = FirebaseMessaging.getInstance().send(msg);
                //결과 출력
                System.out.println("Successfully sent message: " + response);
            }
                 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

///topics/ALL
