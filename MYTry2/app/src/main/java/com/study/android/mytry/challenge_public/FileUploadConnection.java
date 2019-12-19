package com.study.android.mytry.challenge_public;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploadConnection {

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    DataOutputStream dos = null;
    FileInputStream mFileInputStream = null;

    public String request(String _url, String imaPath,String fileName){
        try {
            // 절대경로
            Log.d("name", imaPath);
            //파일 이름
            Log.d("name", fileName);

            mFileInputStream = new FileInputStream(imaPath);
            Log.d("name", "mFileInputStream  is " + mFileInputStream);

            URL connectUrl = new URL(_url);

            // open connection
            HttpURLConnection con = (HttpURLConnection) connectUrl.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // write data
            dos = new DataOutputStream(con.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadFile\";filename=\"" + imaPath + "\"" + lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Type: application/octet-stream\r\n\r\n");

            Log.d("name", "mFileInputStream  is " + dos);

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("name", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        // close streams
            Log.e("name", "File is written");
            mFileInputStream.close();
            dos.flush(); // finish upload...

            // get response
            int ch;
            InputStream is = con.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.e("name", "result = " + s);
            dos.close();

            return "success";
        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }finally {
            try {
                if (mFileInputStream != null)
                    mFileInputStream.close();
                if (dos != null)
                    dos.close();
            } catch (IOException e) {
                // handle the exception
            }
        }
        return "faild";
    }

}
