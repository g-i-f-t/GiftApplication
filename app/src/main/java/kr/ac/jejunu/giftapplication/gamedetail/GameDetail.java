package kr.ac.jejunu.giftapplication.gamedetail;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.jejunu.giftapplication.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.HashMap;

public class GameDetail extends AppCompatActivity {
    private ImageView gameImage;
    private HashMap<String, Object> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
//        gameImage.setImageDrawable((Drawable) params.get("imageURI"));
    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        gameImage = findViewById(R.id.detail_game_image);
        params = (HashMap<String, Object>) getIntent().getSerializableExtra("params");
    }
}
