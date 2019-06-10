package kr.ac.jejunu.giftapplication.gamedetail;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class HistoryTask extends AsyncTask<Void, Void, Boolean> {
    private final String url;

    public HistoryTask(String url) {
        this.url = url;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean result = false;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while((line = bufferedReader.readLine()) != null)
                sb.append(line).append('\n');
            JSONObject jsonObject = new JSONObject(sb.toString());
            result = (Boolean) jsonObject.get("data");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
