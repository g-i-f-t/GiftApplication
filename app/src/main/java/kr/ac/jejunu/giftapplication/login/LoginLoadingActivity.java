package kr.ac.jejunu.giftapplication.login;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
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

        // Todo 로그인 로직 구현
        String url = "http://117.17.102.139:8080/validateAccount";
        LoginTask task = new LoginTask(url, email, password);
        try {
            int resultCode = task.execute().get();
            if(resultCode != 200) {
                System.out.println("안돼" + resultCode);
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

    private void getExtra() {
        Intent prevIntent = getIntent();
        email = prevIntent.getStringExtra("email");
        password = prevIntent.getStringExtra("password");
    }

    @Override
    public void onBackPressed() {
    }

    public class LoginTask extends AsyncTask<Void, Void, Integer> {
        private final String url;
        private final String password;
        private final String email;

        public LoginTask(String url, String email, String password) {
            this.url = url;
            this.email = email;
            this.password = password;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Integer responseCode = null;
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
                body.addProperty("password", HashService.sha256(password));
                OutputStream os = connection.getOutputStream();
                os.write(gson.toJson(body).getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();
                responseCode = connection.getResponseCode();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return responseCode != null ? responseCode : 404;
        }


    }
}
