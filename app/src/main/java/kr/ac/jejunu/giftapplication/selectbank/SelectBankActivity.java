package kr.ac.jejunu.giftapplication.selectbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.jejunu.giftapplication.R;

public class SelectBankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bank_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SelectBankFragment.newInstance())
                    .commitNow();
        }
    }
}
