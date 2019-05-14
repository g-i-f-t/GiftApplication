/**
 * Author Aerain
 * SSLAB
 * Jeju National University
 */

package kr.ac.jejunu.giftapplication.introduction;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.login.LoginActivity;
import kr.ac.jejunu.giftapplication.signup.SignUpActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;

public class IntroductionActivity extends AppCompatActivity {

    private CarouselView introductionCarousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        setCarouselView();
    }

    private void setCarouselView() {
        int mPageCount = 3;
        int[] layoutResourcesId = { R.layout.page_introduction_first, R.layout.page_introduction_second, R.layout.page_introduction_third };

        introductionCarousel.setPageCount(mPageCount);
        introductionCarousel.setViewListener(position -> {
            View view = getLayoutInflater().inflate(layoutResourcesId[position], null);
            switch(position) {
                case 0:

                    break;
                case 1:
                    break;
                case 2:
                    Button loginButton = view.findViewById(R.id.introduction_login);
                    loginButton.setOnClickListener(this::login);
                    TextView withoutLogin = view.findViewById(R.id.introduction_without_login);
                    withoutLogin.setOnClickListener(this::signUp);
                    break;
                default:
                    break;
            }
            return view;
        });
    }

    private void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void login(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        introductionCarousel = findViewById(R.id.introduction_carousel);
    }

    public void start(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
