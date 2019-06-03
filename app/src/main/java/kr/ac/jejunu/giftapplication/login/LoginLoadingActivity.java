package kr.ac.jejunu.giftapplication.login;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.Room.AppDatabase;
import kr.ac.jejunu.giftapplication.Room.UserDao;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.introduction.IntroductionActivity;
import kr.ac.jejunu.giftapplication.signup.Login_API2;
import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.splash.RoomLog;
import kr.ac.jejunu.giftapplication.splash.SplashActivity;
import kr.ac.jejunu.giftapplication.vo.AuthVO;
import kr.ac.jejunu.giftapplication.vo.LoginVO;
import kr.ac.jejunu.giftapplication.vo.Profile;
import kr.ac.jejunu.giftapplication.vo.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class LoginLoadingActivity extends AppCompatActivity {
    private String email;
    private String password;

    // 이거는 임시 이메일 패스워드;
    private final String tempEmail = "foo@ex";
    private final String tempPassword = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_loading);
        getExtra();

        Intent resultIntent = new Intent();

        String url = "http://117.17.102.139:8080/validateAccount";
        LoginTask task = new LoginTask(url, email, HashService.sha256(password));
        try {
            AuthVO resultCode = task.execute().get();
            if(resultCode == null || resultCode.getCode() != 200) {
                if(resultCode != null)
                    System.out.println("안돼" + resultCode.getMessages());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.login_task_dialog_title)
                        .setMessage(R.string.login_task_dialog_content)
                        .setCancelable(false)
                        .setPositiveButton("확인", this::onClickDialog);
                builder.create().show();
            } else {
                //why ADD? 앱이 삭제됐을 경우를 가정, Room에는 data가 존재하지 않기 때문!
                addDB(resultCode); //room
                ProfileManager profileManager = new ProfileManager();
                String loginKey = profileManager.getLoginKey(this);
                profileManager.getProfile(loginKey, this);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //        new Handler().postDelayed(() -> {
//            Snackbar.make(getWindow().getDecorView().getRootView(), "넌 틀려먹었어", Snackbar.LENGTH_SHORT).show();
//            if(email.equals(tempEmail) && password.equals(tempPassword)) {
//                // Todo 로그인 로직 구현하기! 이부분!
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            } else {
//                finish();
//            }
//        }, 3000);
    }

    private void addDB(AuthVO resultCode) {
        User user = new User();
        user.setAccessToken(resultCode.getAccess_token());
        user.setUserSeqNo(resultCode.getUser_seq_no());
        UserDao roomUserDao = AppDatabase.getInstance(this).roomUserDao();
        try {
            new RoomLog.addDBTask(roomUserDao).execute(user).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void onClickDialog(DialogInterface dialogInterface, int i) {
        finish();
    }

    private void getExtra() {
        Intent prevIntent = getIntent();
        email = prevIntent.getStringExtra("email");
        password = prevIntent.getStringExtra("password");
    }

    @Override
    public void onBackPressed() {
    }

    public class LoginTask extends AsyncTask<Void, Void, AuthVO> {
        private final String url;
        private final String password;
        private final String email;

        public LoginTask(String url, String email, String password) {
            this.url = url;
            this.email = email;
            this.password = password;
        }

        @Override
        protected AuthVO doInBackground(Void... voids) {
            AuthVO result = null;
            try {
                URL uri = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset",  "UTF-8");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Gson gson = new Gson();
                JsonObject body = new JsonObject();

                body.addProperty("email", email);
                body.addProperty("password", password);
                OutputStream os = connection.getOutputStream();
                os.write(gson.toJson(body).getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                InputStream is = connection.getInputStream();

                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append('\n');
                }

                result = new Gson().fromJson(builder.toString(), AuthVO.class);
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
    private class AccessDBTask extends AsyncTask<User, Void, Void> {
        private final UserDao roomUserDao;

        public AccessDBTask(UserDao roomUserDao) {
            this.roomUserDao = roomUserDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            for(User user: users)
                roomUserDao.add(user);
            final String result = roomUserDao.getAll().get(0).getAccessToken();

            return null;
        }
    }

}