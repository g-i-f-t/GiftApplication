package kr.ac.jejunu.giftapplication.login;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.vo.AuthVO;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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

        // Todo 로그인 로직 구현
        String url = "http://117.17.102.139:8080/validateAccount";
        LoginTask task = new LoginTask(url, email, password);
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
                // Todo 똑같이 AuthVO임. Room에 담을 것.
                Intent intent = new Intent(this, MainActivity.class);
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
                body.addProperty("password", HashService.sha256(password));
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
}