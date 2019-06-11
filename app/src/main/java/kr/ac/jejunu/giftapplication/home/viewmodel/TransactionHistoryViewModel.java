package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import kr.ac.jejunu.giftapplication.vo.GameVO;

public class TransactionHistoryViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public GameVO findHotGame() {
        final String url = "http://117.17.102.139:8080/game/hot";
        GameVO gameVO = null;
        try {
            gameVO = new HotTask(url).execute().get();
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return gameVO;
    }

    private class HotTask extends AsyncTask<Void, Void, GameVO> {

        private final String url;

        public HotTask(String url) {
            this.url = url;
        }

        @Override
        protected GameVO doInBackground(Void... voids) {
            GameVO result = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while((line = bufferedReader.readLine()) != null)
                    sb.append(line).append('\n');
                Gson gson = new Gson();
                Type type = new TypeToken<GameVO>() {}.getType();
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(sb.toString()).getAsJsonObject().get("data");
                result = gson.fromJson(element, type);
                System.out.println(result.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
