/**
 * Author Aerain
 * SSLAB
 * Jeju National University
 */

package kr.ac.jejunu.giftapplication.splash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.introduction.IntroductionActivity;

public class SplashActivity extends AppCompatActivity {
    private ProfileManager profileManager;
    private AlertDialog.Builder alert;
    private Timer limitLoadingTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileManager = new ProfileManager();
        limitLoadingTimer = new Timer();
        makeAlert();
        LogSearch();
    }

    private void makeAlert() {
        alert = new AlertDialog.Builder(this);
        alert.setTitle("알림");
        alert.setMessage("GIFT서버와 연결이 되지 않습니다!");
        alert.setPositiveButton("확인", (dialog, which) -> {
            dialog.dismiss();     //닫기
        });
    }

    private void LogSearch() {
        String LoginKey = profileManager.getLoginKey(this);


            if (LoginKey != null) { // 한번이라도 접속한 적이 있을때. 자동로그인 시도
                limitLoadingTimer.schedule(new TimerTask() { // 이때 타이머를 재. 왜잴까? 너무 로딩시간이 길면 접속 끊을라고.
                    public void run() {
                        profileManager.stopTask();
                        runOnUiThread(() -> alert.show());
                    }
                }, 5000); // 5초가 마지노선.
                //Room에 유저 정보가 있다면 goMain!
                profileManager.getProfile(LoginKey, this, () -> {
                    limitLoadingTimer.cancel();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
            else  {
                Intent intent = new Intent(this, IntroductionActivity.class);
                startActivity(intent);
                finish();
            }


    }
}
