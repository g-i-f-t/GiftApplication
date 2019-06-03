/**
 * Author Aerain
 * SSLAB
 * Jeju National University
 */

package kr.ac.jejunu.giftapplication.splash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


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

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.introduction.IntroductionActivity;
import kr.ac.jejunu.giftapplication.signup.SignUpActivity;
import kr.ac.jejunu.giftapplication.vo.LoginVO;

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
                //Room에 유저 정보가 있다면 goMain!
                profileManager.getProfile(LoginKey, this);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else  {
                Intent intent = new Intent(this, IntroductionActivity.class);
                startActivity(intent);
                finish();
            }
    }
}
