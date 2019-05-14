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
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.Room.AppDatabase;
import kr.ac.jejunu.giftapplication.Room.UserDao;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.introduction.IntroductionActivity;
import kr.ac.jejunu.giftapplication.vo.AuthVO;
import kr.ac.jejunu.giftapplication.vo.LoginVO;
import kr.ac.jejunu.giftapplication.vo.Profile;
import kr.ac.jejunu.giftapplication.vo.User;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = new User();
        UserDao roomUserDao = AppDatabase.getInstance(this).roomUserDao();
        AccessDBTask task = new AccessDBTask(roomUserDao);
        //TODO Room에서 get, Token이 있을경우 서버로 로그인 요청
        String LoginKey = null;
        try {
            LoginKey = task.execute(user).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (LoginKey != null) {
            //TODO server와 통신
            String url = "http://117.17.102.139:8080/account/"+LoginKey;
            NetWorkTask netWorkTask = new NetWorkTask(url);
            LoginVO result = null;
            try {
                result = netWorkTask.execute().get();
                if(result.getCode() == 200){
                    Profile profile = new Profile();
                    profile.setName(result.getName());
                    profile.setEmail(result.getEmail());

                    ((GiftApplication) getApplication()).setUserInfo(profile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    private class AccessDBTask extends AsyncTask<User, Void, String> {
        private final UserDao roomUserDao;

        public AccessDBTask(UserDao roomUserDao) {
            this.roomUserDao = roomUserDao;
        }

        @Override
        protected String doInBackground(User... users) {
//            long id = 1;
//            for(User user: users)
//                roomUserDao.get(id);
            String result = null;
            try {
                result = roomUserDao.get(0).getUserSeqNo();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
