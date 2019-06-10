package kr.ac.jejunu.giftapplication.myinvestment;

import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

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
import java.util.concurrent.ExecutionException;

import kr.ac.jejunu.giftapplication.home.viewmodel.GetFundingTask;
import kr.ac.jejunu.giftapplication.vo.GameVO;
import kr.ac.jejunu.giftapplication.vo.PayInfoVO;

public class MyInvestMentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public List<PayInfoVO> getFundTask(String userSeqId) {
        List<PayInfoVO> payInfoVOList;
        final String url = "http://117.17.102.139:8080/game/my/" + userSeqId;
        try {
            payInfoVOList = new MyInvestMentTask(url).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            payInfoVOList = new ArrayList<>();
        }
        return payInfoVOList;
    }

    private class MyInvestMentTask extends AsyncTask<Void, Void, List<PayInfoVO>> {
        private final String uri;

        public MyInvestMentTask(String url) {
            this.uri = url;
        }

        @Override
        protected List<PayInfoVO> doInBackground(Void... voids) {
            List<PayInfoVO> payInfoVOList = new ArrayList<>();
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
                System.out.println(builder.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<PayInfoVO>>() {
                }.getType();
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(builder.toString()).getAsJsonObject().get("data");
                payInfoVOList = gson.fromJson(element, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return payInfoVOList;
        }
    }
}
