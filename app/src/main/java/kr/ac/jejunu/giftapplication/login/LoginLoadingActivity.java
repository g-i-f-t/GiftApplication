package kr.ac.jejunu.giftapplication.login;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

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
        new Handler().postDelayed(() -> {

        }, 1500);
        Snackbar.make(getWindow().getDecorView().getRootView(), "넌 틀려먹었어", Snackbar.LENGTH_SHORT).show();
        if(email.equals(tempEmail) && password.equals(tempPassword)) {
            // Todo 로그인 로직 구현하기! 이부분!
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            finish();
        }

    }

    private void getExtra() {
        Intent prevIntent = getIntent();
        email = prevIntent.getStringExtra("email");
        password = prevIntent.getStringExtra("password");
    }

    @Override
    public void onBackPressed() {
    }
}
