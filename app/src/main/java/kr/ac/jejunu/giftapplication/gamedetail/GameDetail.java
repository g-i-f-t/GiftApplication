/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.gamedetail;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import kr.ac.jejunu.giftapplication.DownloadImageTask;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.GameDescribeImagesVO;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class GameDetail extends AppCompatActivity {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initView();
    }

    private void initView() {
        bindwithGameVO();
        setHeaderImage();

        setButtonTransition();
        mButton.setOnClickListener(v -> {
            Boolean hasHistory = hasHistory();
            System.out.println(hasHistory);
            if(hasHistory) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("기록 존재")
                        .setMessage("이미 해당 게임에 투자하셨습니다.")
                        .create();

                dialog.show();
                return;
            }
            Intent intent = new Intent(this, InvestmentAgreementActivity.class);
            intent.putExtra("id", gameVO.getGameId());
            startActivity(intent);
        });

    }

    private boolean hasHistory() {
        final String url = "http://117.17.102.139:8080/game/lookup/" + gameVO.getGameId();
        Boolean hasHistory = false;
        try {
            hasHistory = new HistoryTask(url).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hasHistory;
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
        gameVO = (GameVO) prevIntent.getSerializableExtra("gameVO");
//        HashMap<String, Object> params = (HashMap<String, Object>) prevIntent.getSerializableExtra("params");
        // 이미지 리스트 생성
        List<String> gameDetailImages = new ArrayList<>();
        for(GameDescribeImagesVO describeImagesVO : gameVO.getGameDescribeImages()) {
            gameDetailImages.add(describeImagesVO.getDescribeImage());
        }

        double progress = (((double) gameVO.getCurrentPrice()/ (double) gameVO.getGoalPrice()) * 100);
        gameTitle.setText(gameVO.getName());
        gameDeveloper.setText(gameVO.getDeveloper().getName());
//        gameTitle.setText((String) params.get("name"));
//        gameDeveloper.setText((String) params.get("developer"));
        gameIntroduction.setText(gameVO.getGameInformation());
        investmentIntroduction.setText(gameVO.getInvestmentInformation());
        investmentCondition.setText(gameVO.getInvestmentCondition());
        companyIntroduction.setText(gameVO.getCompanyIntroduction());
        goalPrice.setText(String.format(Locale.KOREA, "목표 금액 %,d원", gameVO.getGoalPrice()));
        fundingProgress.setProgress((int) progress);
        currentPrice.setText(String.format(Locale.KOREA, "현재 %,d원 (%.0f %%)", gameVO.getCurrentPrice(), progress));
        setCarouselView(gameDetailImages);
    }

    private void setHeaderImage() {
        File filePath = getFileStreamPath(prevIntent.getStringExtra("fileName"));
        Drawable drawable = Drawable.createFromPath(filePath.toString());
        gameImage.setImageDrawable(drawable);
    }

    private void setCarouselView(List<String> describeImageList) {
        carouselView.setPageCount(describeImageList.size());
        carouselView.setImageListener(((position, imageView) -> {
            new DownloadImageTask(imageView).execute(describeImageList.get(position));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            imageView.setImageResource(R.drawable.icon);
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
