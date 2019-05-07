package kr.ac.jejunu.giftapplication.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.signup.SignUpActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private EditText email, password;
    private LinearLayout loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton.setOnClickListener(this::tryLogin);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = findViewById(R.id.login_toolbar);
        loginButton = findViewById(R.id.login_login_button);
        email = findViewById(R.id.login_id_editText);
        password = findViewById(R.id.login_pwd_editText);
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

    public void tryLogin(View v) {
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();

        if (!emailString.equals("") && !passwordString.equals("")) {
            Intent intent = new Intent(this, LoginLoadingActivity.class);
            intent.putExtra("email", emailString);
            intent.putExtra("password", passwordString);
            startActivity(intent);
        } else {
            AlertDialog.Builder alert = showAlert();
            alert.setMessage("양식에 맞게 입력해주세요.");
            alert.show();
        }

    }
    private AlertDialog.Builder showAlert() {
            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();     //닫기
                }
            });
            return alert;
    }

}
