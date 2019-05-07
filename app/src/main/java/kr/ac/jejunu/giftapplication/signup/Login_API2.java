package kr.ac.jejunu.giftapplication.signup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.Room.AppDatabase;
import kr.ac.jejunu.giftapplication.vo.User;
import kr.ac.jejunu.giftapplication.Room.UserDao;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.vo.AuthVO;

public class Login_API2 extends AppCompatActivity {
    private TextView tv_outPut;

    //TODO form에서 입력했던 사용자 정보들을 Room에 저장하고 서버에 POST로 보내기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__api2);
        // 위젯에 대한 참조.
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);

        findViewById(R.id.nextBtn).setOnClickListener(goMain);

        Intent prevIntent = getIntent();
        final HashMap<String, String> extra = (HashMap<String, String>) prevIntent.getSerializableExtra("data");

        //        //URL 설정

        String url = "http://117.17.102.139:8080/addAccount";
        JsonObject params = new JsonObject();
        params.addProperty("password", extra.get("password"));
        params.addProperty("name", extra.get("name"));
        params.addProperty("email", extra.get("email"));
        params.addProperty("authCode", extra.get("code"));
        params.addProperty("scope", extra.get("scope"));

        NetWorkTask netWorkTask = null;
        netWorkTask = new NetWorkTask(url, new Gson().toJson(params));

        TextView authCode = findViewById(R.id.code);
        TextView scope = findViewById(R.id.scope);
        TextView clientInfo = findViewById(R.id.client_info);
        TextView email = findViewById(R.id.email);
        TextView password = findViewById(R.id.password);
        TextView name = findViewById(R.id.name);

        authCode.setText(extra.get("code"));
        scope.setText(extra.get("scope"));
        clientInfo.setText(extra.get("client_info"));
        email.setText(extra.get("email"));
        password.setText(extra.get("password"));
        name.setText(extra.get("name"));

        try {
            AuthVO result = netWorkTask.execute().get();
            if(result != null) {
                // Todo result에 user_seq_no 하고 access_token 들어있음.
                User user;
                UserDao roomUserDao = AppDatabase.getInstance(this).roomUserDao();
                user = new User();
                user.setUserSeqNo(result.getUser_seq_no());
                user.setAccessToken(result.getAccess_token());
                tv_outPut.setText(result.getAccess_token() + ", " + result.getUser_seq_no());
                AccessDBTask task = new AccessDBTask(roomUserDao);
                task.execute(user);

//                List<User> userList = roomUserDao.getAll();
//                System.out.println(userList.toString());
//                Intent intent = new Intent(Login_API2.this,  MainActivity.class);
//                startActivity(intent);
            } else {
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    Button.OnClickListener goMain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Login_API2.this,  MainActivity.class);
            startActivity(intent);
        }
    };

    public class NetWorkTask extends AsyncTask<Void, Void, AuthVO> {
        private final String stringifiedJson;
        private String url;

        public NetWorkTask(String url, String stringifiedJson) {
            this.url = url;
            this.stringifiedJson = stringifiedJson;
        }
        //execute한 후에 백그라운드 쓰레드에서 호출됨

        @Override
        protected AuthVO doInBackground(Void... params) {
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
                OutputStream os = connection.getOutputStream();
                os.write(stringifiedJson.getBytes(StandardCharsets.UTF_8));
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Login_API2.this, result, Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }
}

