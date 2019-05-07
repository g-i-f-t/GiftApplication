package kr.ac.jejunu.giftapplication.signup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kr.ac.jejunu.giftapplication.R;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionBar;

    EditText et_email, et_pw, et_pw_chk, et_name;
    String email, password, password_chk, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_email = (EditText) findViewById(R.id.login_id_editText);
        et_pw = (EditText) findViewById(R.id.login_pwd_editText);
        et_pw_chk = (EditText) findViewById(R.id.login_pwd_chk_editText);
        et_name = (EditText) findViewById(R.id.login_name_editText);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = findViewById(R.id.login_toolbar);

        setToolBar();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
