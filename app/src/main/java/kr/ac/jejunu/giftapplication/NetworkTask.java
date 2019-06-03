package kr.ac.jejunu.giftapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

import kr.ac.jejunu.giftapplication.splash.SplashActivity;
import kr.ac.jejunu.giftapplication.vo.LoginVO;

public class NetworkTask extends AsyncTask<Void, Void, LoginVO> {
    private String url;
    private Activity activity;

    public NetworkTask(String url, Activity activity) {
        this.url = url;
        this.activity = activity;
    }

    //execute한 후에 백그라운드 쓰레드에서 호출됨

    @Override
    protected  LoginVO doInBackground(Void... params) {
        LoginVO result = null;
        try {
            URL uri = new URL(url);
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) uri.openConnection();
            } catch (Exception e) {
//                activity.runOnUiThread(new Runnable() {
////                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
////                    @Override
////                    public void run() {
////                        alert.setTitle("알림");
////                        alert.setMessage("GIFT서버와 연결이 되지 않습니다!");
////                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                dialog.dismiss();     //닫기
////                            }
////                        });
////                        alert.show();
////                    }
////               });
                  e.printStackTrace();
            }
            connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset", "UTF-8");
//                connection.setUseCaches(false);
            InputStream is = connection.getInputStream();

            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }

            result = new Gson().fromJson(builder.toString(), LoginVO.class);
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}