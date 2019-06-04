package kr.ac.jejunu.giftapplication.gamedetail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.Room.UserDao;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.selectbank.SelectBankActivity;
import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.splash.RoomLog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

public class PurchaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private EditText priceEditText;
    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        buttonLayout.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        // Todo Network Call 구매관련.
        Intent intent = new Intent(this, SelectBankActivity.class);
        intent.putExtra("id", getIntent().getLongExtra("id", 0));
        intent.putExtra("price", Long.parseLong(priceEditText.getText().toString()));
        startActivity(intent);
//        ProfileManager profileManager = new ProfileManager();
//        String willPurchasePrice = priceEditText.getText().toString();
//        String userSeqNo = profileManager.getLoginKey(this);
//        String url = "http://117.17.102.139:8080/game/" + getIntent().getLongExtra("id", 0);
//        new PurchaseTask(url, userSeqNo).execute(willPurchasePrice);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("onPurchased", true);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
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
        priceEditText = findViewById(R.id.purchase_price);
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

    private class PurchaseTask extends AsyncTask<String, Void, Void> {
        private final String uri;
        private final String userSeqNo;

        public PurchaseTask(String uri, String userSeqNo) {
            this.uri = uri;
            this.userSeqNo = userSeqNo;
        }

        @Override
        protected Void doInBackground(String... prices) {
            for (String price: prices) fundGame(price);
            return null;
        }

        private void fundGame(String price) {
            try {
                URL url = new URL(uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("charset", "UTF-8");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("price", price);
                jsonObject.put("userSeqNo", userSeqNo);

                OutputStream os = conn.getOutputStream();
                os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuffer buffer = new StringBuffer();

                String line = null;
                while((line = br.readLine()) != null)
                    buffer.append(line).append('\n');

                String result = buffer.toString().trim();
                System.out.println(result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
