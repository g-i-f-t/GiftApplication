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
                    loginButton.setOnClickListener(v -> login());
                    TextView withoutLogin = view.findViewById(R.id.introduction_without_login);
                    withoutLogin.setOnClickListener(v -> start());
                    break;
                default:
                    break;
            }
            return view;
        });
    }

    private void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        introductionCarousel = findViewById(R.id.introduction_carousel);
    }

    public void start() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // TODO 액티비티 실행 후 다시는 이 액티비티를 안보이게 차단하게!
        finish();
    }
}
