package kr.ac.jejunu.giftapplication.gamedetail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kr.ac.jejunu.giftapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class InvestmentAgreementActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private WebView webview;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_agreement);
        openWebView();
        button.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        Intent intent = new Intent(this, PurchaseActivity.class);
        Long gameId = getIntent().getLongExtra("id", 0);
        intent.putExtra("id", gameId);
        startActivity(intent);
    }

    private void openWebView() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webview.loadUrl("https://aerain.github.io/2019/05/01/%ED%88%AC%EC%9E%90%EA%B3%A0%EC%A7%80%EC%9C%84%ED%97%98.html");
    }

    private void setToolbar() {
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.investment_risk_title));
        }

        @Override
        public void setContentView(int layoutResID) {
            super.setContentView(layoutResID);
            actionBar = getSupportActionBar();
            toolbar = findViewById(R.id.investment_toolbar);
            webview = findViewById(R.id.agreement_web_view);
            button = findViewById(R.id.investment_agree_button);
            setToolbar();
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
