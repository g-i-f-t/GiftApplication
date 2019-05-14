package kr.ac.jejunu.giftapplication.gamedetail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kr.ac.jejunu.giftapplication.R;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class PurchaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        buttonLayout.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        // Todo Network Call 구매관련.
        Snackbar.make(getWindow().getDecorView().getRootView(), "구매 더미", Snackbar.LENGTH_SHORT).show();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.purchase_activity_title));
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        actionBar = getSupportActionBar();
        toolbar = findViewById(R.id.purchase_toolbar);
        buttonLayout = findViewById(R.id.purchase_layout_button);
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
