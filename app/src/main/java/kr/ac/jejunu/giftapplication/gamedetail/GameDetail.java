/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.gamedetail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.synnapps.carouselview.CarouselView;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class GameDetail extends AppCompatActivity {
    private GameViewModel gameViewModel;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ImageView gameImage;
    private Intent prevIntent;
    private TextView gameTitle, gameDeveloper, gameIntroduction, investmentIntroduction, investmentCondition, companyIntroduction, goalPrice, currentPrice;
    private CarouselView carouselView;
    private Button mButton;
    private GameVO gameVO;
    private ScrollView gameDetailMainView;
    private ProgressBar fundingProgress;
    private boolean isBottom = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameViewModel = new GameViewModel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initView();
    }

    private void initView() {
        bindwithGameVO();
        setHeaderImage();
        setCarouselView();
        setButtonTransition();
        mButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, InvestmentAgreementActivity.class);
            startActivity(intent);
        });

    }

    private void setButtonTransition() {
        gameDetailMainView.getViewTreeObserver().addOnScrollChangedListener(this::onScrollchanged);
    }

    private void onScrollchanged() {
        if (gameDetailMainView.getChildAt(0).getBottom()
                == (gameDetailMainView.getHeight() + gameDetailMainView.getScrollY()) ) {
            ConstraintLayout.MarginLayoutParams layoutParams = (ConstraintLayout.MarginLayoutParams) mButton.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            mButton.requestLayout();
            //scroll view is at bottom
        } else {
            ConstraintLayout.MarginLayoutParams layoutParams = (ConstraintLayout.MarginLayoutParams) mButton.getLayoutParams();
            layoutParams.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics()),
                    0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
            layoutParams.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
            mButton.requestLayout();
            //scroll view is not at bottom
        }
    }

    private void bindwithGameVO() {
        HashMap<String, Object> params = (HashMap<String, Object>) prevIntent.getSerializableExtra("params");
        gameVO = gameViewModel.getGameVO((Long) params.get("id"));
        gameTitle.setText((String) params.get("name"));
        gameDeveloper.setText((String) params.get("developer"));
        gameIntroduction.setText(gameVO.getGameInformation());
        investmentIntroduction.setText(gameVO.getInvestmentInformation());
        investmentCondition.setText(gameVO.getInvestmentCondition());
        companyIntroduction.setText(gameVO.getCompanyIntroduction());
        goalPrice.setText(String.format(Locale.KOREA, "목표 금액 %,d원", gameVO.getGoalPrice()));
        fundingProgress.setProgress((int) ((double) gameVO.getCurrentPrice()/ (double) gameVO.getGoalPrice()) * 100);
        currentPrice.setText(String.format(Locale.KOREA, "현재 %,d원 (%.1f %%)", gameVO.getCurrentPrice(), ((double) gameVO.getCurrentPrice()/ (double) gameVO.getGoalPrice()) * 100));
    }

    private void setHeaderImage() {
        File filePath = getFileStreamPath(prevIntent.getStringExtra("fileName"));
        Drawable drawable = Drawable.createFromPath(filePath.toString());
        gameImage.setImageDrawable(drawable);
    }

    private void setCarouselView() {
        carouselView.setPageCount(5);
        carouselView.setImageListener(((position, imageView) -> {
            imageView.setImageResource(R.drawable.icon);
        }));
    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = findViewById(R.id.detail_toolbar);
        gameImage = findViewById(R.id.detail_game_image);
        gameTitle = findViewById(R.id.game_title);
        gameDeveloper = findViewById(R.id.game_developer);
        carouselView = findViewById(R.id.detail_carousel_view);
        gameIntroduction = findViewById(R.id.game_introduction_content);
        investmentIntroduction = findViewById(R.id.investment_introduction_content);
        investmentCondition = findViewById(R.id.investment_condition_content);
        companyIntroduction = findViewById(R.id.company_introduction_content);
        goalPrice = findViewById(R.id.goal_price);
        currentPrice = findViewById(R.id.current_price_and_percnetage);
        mButton = findViewById(R.id.investment_button);
        gameDetailMainView = findViewById(R.id.game_detail_main_view);
        fundingProgress = findViewById(R.id.progressBar);
        prevIntent = getIntent();
        setToolbar();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
