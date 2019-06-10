package kr.ac.jejunu.giftapplication.home;

import android.os.AsyncTask;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CategoryTask extends AsyncTask<Void, Void, List<String>> {
    private final String uri;

    public CategoryTask(String uri) {
        this.uri = uri;
    }
    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> result = new ArrayList<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();

            String line;
            while((line = reader.readLine()) != null)
                sb.append(line).append('\n');

            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {}.getType();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(sb.toString()).getAsJsonObject().get("data");
            result = gson.fromJson(element, type);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
