/**
 * Author Aerain
 * SSLAB
 * Jeju National University
 */

package kr.ac.jejunu.giftapplication.splash;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.Room.AppDatabase;
import kr.ac.jejunu.giftapplication.Room.UserDao;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.introduction.IntroductionActivity;
import kr.ac.jejunu.giftapplication.vo.LoginVO;
import kr.ac.jejunu.giftapplication.vo.User;

public class SplashActivity extends AppCompatActivity {
    private ProfileManager profileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileManager = new ProfileManager();
        LogSearch();
    }

    private void LogSearch() {
        String LoginKey = profileManager.getLoginKey(this);
        if (LoginKey != null) {
            profileManager.getProfile(LoginKey, getApplication());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, IntroductionActivity.class);
            startActivity(intent);
            finish();
        }
    }



    public static class NetWorkTask extends AsyncTask<Void, Void, LoginVO> {
        private String url;

        public NetWorkTask(String url) {
            this.url = url;
        }
        //execute한 후에 백그라운드 쓰레드에서 호출됨

        @Override
        protected  LoginVO doInBackground(Void... params) {
            LoginVO result = null;
            try {
                URL uri = new URL(url);

                HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("charset", "UTF-8");
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
}
