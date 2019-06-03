package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import kr.ac.jejunu.giftapplication.vo.GameVO;

public class GetFundingTask extends AsyncTask<Void, Void, List<GameVO>> {
    private final String uri;

    public GetFundingTask(String uri) {
        this.uri = uri;
    }

    @Override
    protected List<GameVO> doInBackground(Void... voids) {
        List<GameVO> gameVOList = new ArrayList<>();
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();

            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<GameVO>>() {
            }.getType();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(builder.toString()).getAsJsonObject().get("data");
            gameVOList = gson.fromJson(element, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameVOList;
    }
}