package com.study.android.mytry.challenge_private;
import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileUploadConnection;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import static com.study.android.mytry.login.Intro.local_url;

public class CreationFifth extends AppCompatActivity {
    private static final String TAG = "lecture";

    HashMap<String, String> map = new HashMap<>();
    String userid;
    ImageView imageView;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 3;

    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private static final int MULTIPLE_PERMISSIONS = 101;
    private int CREATION_FINISH = 2;
    private static final int PRIVATE_CREATION_FINISH = 2;

    static String UploadImgPath;
    static String name_Str;

    String cash_user;
    String cash = "";
    int fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_fifth);

        imageView = (ImageView) findViewById(R.id.private_challenge_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();

                selectFromAlbum();
            }
        });

        Intent intent=getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();
        map.put("host",userid);

        myApp = (GlobalApplication) getApplication();
        cash_user = myApp.getGlobalString();

        String url = local_url + "/uzinee/cash_result";
        String msg = "?id=" + cash_user;
        Log.d("lecture", msg);
        url = url + msg;
        Log.d("lecture", url);

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

    }

    public void onFifthNext(View v) {

        if (Integer.parseInt(cash) < Integer.parseInt(map.get("fee")) * 10000) {
            Toast.makeText(CreationFifth.this, "소유하신 마일리지가 참가비보다 적습니다.", Toast.LENGTH_SHORT).show();
        } else {
            String url = local_url + "/yejin/private_create";
            String msg = "?title=" + map.get("title") +
                    "&category=" + map.get("category") +
                    "&type=" + map.get("type") +
                    "&frequency=" + map.get("frequency") +
                    "&start=" + map.get("start") +
                    "&end=" + map.get("end") +
                    "&time=" + map.get("time") +
                    "&fee=" + map.get("fee") +
                    "&first_image=" + name_Str +
                    "&detail=" + map.get("detail") +
                    "&host=" + userid;

            Log.d("lecture", msg);
            url = url + msg;
            Log.d("lecture", url);

            NetworkTask networkTask = new NetworkTask(url, null);
            networkTask.execute();

            fee = Integer.parseInt(map.get("fee")) * 10000;

            Toast.makeText(CreationFifth.this, "우리끼리 챌린지 신청이 완료되었습니다.", Toast.LENGTH_LONG).show();

            String url1 = local_url + "/uzinee/participation";
            String msg1 = "?id=" + cash_user + "&fee=" + fee;
            Log.d("lecture", msg1);
            url1 = url1 + msg1;
            Log.d("lecture", url1);

            networkTask = new NetworkTask(url1, null);
            networkTask.execute();

            Intent intent = new Intent(CreationFifth.this, CreationFourth.class);
            setResult(RESULT_OK, intent);

            finish();
        }
    }

    private boolean checkPermissions() {

        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;

    }

//    public void onAlbumClicked(View v) {
//        selectFromAlbum();
//    }

    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FROM_ALBUM) {
                try {
                    // 사진 절대 경로
                    String imagePath = getRealPathFromURI(data.getData());
                    Log.d("name", "절대 경로" + imagePath);

                    // 파일 이름
                    name_Str = getImageNameToUri(data.getData());
                    Log.d("name", "파일 이름" + name_Str);

                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    // 리사이징 : imageView 사진 올릴때 필요함 !!!
                    int height = bitmap.getHeight();
                    int width = bitmap.getWidth();

                    Bitmap src = BitmapFactory.decodeFile(imagePath);
                    Bitmap resized = Bitmap.createScaledBitmap(src, width / 4, height / 4, true);

                    // 비트맵을 jpeg로 바꿔서 따로 저장 후 통신해야함
                    saveBitmaptoJpeg(resized, "seatdot", name_Str);

                    Log.d("name", "사진크기 : " + resized);
                    Log.d("name", "사진크기 : " + height / 4);
                    Log.d("name", "사진크기 : " + width / 4);

                    if (bitmap != null) {
                        // imageView에 뿌려주는건 bitmap
                        imageView.setImageBitmap(bitmap);
                        imageView.invalidate();

                        // 스레드
                        String urlString = local_url + "/yejin/private_upload";
                        Log.d("name", urlString);
                        FilUploadTask networkTask = new FilUploadTask(urlString, null);
                        networkTask.execute();

                    } else {
                        Log.d(TAG, "BBB");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == PICK_FROM_CAMERA) {
                //getPictureFromCamera();
            }
        }
    }

    public static void saveBitmaptoJpeg(Bitmap bitmap, String folder, String name) {
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String foler_name = "/" + folder + "/";
        String file_name = name + ".jpg";
        String string_path = ex_storage + foler_name;
        UploadImgPath = string_path + file_name;

        File file_path;
        try {
            file_path = new File(string_path);
            if (!file_path.isDirectory()) {
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path + file_name);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        } catch (FileNotFoundException exception) {
            Log.e("FileNotFoundException", exception.getMessage());
        } catch (IOException exception) {
            Log.e("IOException", exception.getMessage());
        }
    }

    // 사진의 절대경로 구하기
    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    // Uri 에서 파일명을 추출하는 로직
    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        return imgName;
    }

    public void onBackPressed(View v){
        finish();
    }

    // 통신
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //   tv_outPut.setText(s);
            try{
                if(s != null) {
                    JSONObject json = new JSONObject(s);
                    cash = json.get("cash_total").toString();
                    Log.d("lecture", cash);
                }
            }catch(JSONException e){
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
    }

    // 통신
    public class FilUploadTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public FilUploadTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            FileUploadConnection requestHttpURLConnection = new FileUploadConnection();
            result = requestHttpURLConnection.request(url, UploadImgPath, name_Str); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
