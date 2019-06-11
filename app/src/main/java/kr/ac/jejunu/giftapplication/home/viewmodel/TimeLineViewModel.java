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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kr.ac.jejunu.giftapplication.vo.PayInfoVO;

public class TimeLineViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public List<PayInfoVO> getLatestTransaction() {
        final String uri = "http://117.17.102.139:8080/pay/latest";
        List<PayInfoVO> payInfoVOList = new ArrayList<>();
        try {
            payInfoVOList = new TimeLineTask(uri).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return payInfoVOList;
    }

    private class TimeLineTask extends AsyncTask<Void, Void, List<PayInfoVO>> {
        private final String uri;

        public TimeLineTask(String uri) {
            this.uri = uri;
        }

        @Override
        protected List<PayInfoVO> doInBackground(Void... voids) {
            List<PayInfoVO> result = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
                connection.setRequestMethod("GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while((line = br.readLine()) != null)
                    sb.append(line).append('\n');

                Type type = new TypeToken<List<PayInfoVO>>(){}.getType();
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(sb.toString()).getAsJsonObject().get("data").getAsJsonObject().get("content");
                result = gson.fromJson(element, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
