/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.gamedetail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class GameDetail extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ImageView gameImage;
    private Intent prevIntent;
    private TextView gameTitle, gameDeveloper, gameIntroduction, investmentIntroduction, investmentCondition, companyIntroduction, goalPrice, currentPrice;
    private CarouselView carouselView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initView();
    }

    private void initView() {
        bindwithGameVO();
        setHeaderImage();
        setCarouselView();
    }

    private void bindwithGameVO() {
        GameVO gameVO = getGameVO();
        gameTitle.setText(gameVO.getName());
        gameDeveloper.setText(gameVO.getDeveloper());
        gameIntroduction.setText(gameVO.getGameInformation());
        investmentIntroduction.setText(gameVO.getInvestmentInformation());
        investmentCondition.setText(gameVO.getInvestmentCondition());
        companyIntroduction.setText(gameVO.getCompanyIntroduction());
        goalPrice.setText(String.format(Locale.KOREA, "%,d원", gameVO.getGoalPrice()));
        currentPrice.setText(String.format(Locale.KOREA, "%,d원 (%.0f %%)", gameVO.getCurrentPrice(), ((double) gameVO.getCurrentPrice()/ (double) gameVO.getGoalPrice()) * 100));
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

    private GameVO getGameVO() {
        // TODO: Network Call. 지금은 임시더미
        GameVO gameVO = new GameVO();
        gameVO.setName("게임 개발 힘들어요");
        gameVO.setDeveloper("기푸트");
        gameVO.setGameInformation("이 게임은 눈 앞에 보이는 적들을 마구마구 때리는 핵 앤 슬래시 게임이에요. 아니 이렇게 재밌는 게임이 있는데 안할 거라구요? 저희 굶어죽어요 ㅠ");
        gameVO.setInvestmentInformation("개발은 애자일하게 하구요. 그냥 하다보니 될 것 같아요 ㅎㅎ 마니 사랑해주세요 여러분");
        gameVO.setInvestmentCondition("1인당 최소 50000원부터 100000원 까지 투자 가능합니다.\n\n수익은 게임 발매후 6개월 동안, 자신이 배당받은 수익률만큼 1개월 주기로 지급됩니다.");
        gameVO.setCompanyIntroduction("안녕하세요 저희 회사 기푸트는 1인 개발자에오");
        gameVO.setGoalPrice(5000000L);
        gameVO.setCurrentPrice(4500000L);
        return gameVO;
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
