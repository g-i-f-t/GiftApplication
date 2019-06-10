package kr.ac.jejunu.giftapplication.selectbank;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WithDrawTask extends AsyncTask<Void, Void, Void> {
    private final String fintechUseNum, url, access_token, userSeqNo;
    private final long id;
    private final long price;

    public WithDrawTask(String fintechUseNum, long id, long price, String accessToken, String userSeqNo) {
        this.url = "http://117.17.102.139:8080/bank/withdraw";
        this.fintechUseNum = fintechUseNum;
        this.id = id;
        this.price = price;
        this.access_token = accessToken;
        this.userSeqNo = userSeqNo;

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fintechUseNum", fintechUseNum);
            jsonObject.put("gameId", id);
            jsonObject.put("price", price);
            jsonObject.put("accessToken", access_token);
            jsonObject.put("userSeqId", userSeqNo);

            OutputStream os = connection.getOutputStream();
            os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null)
                sb.append(line).append('\n');

            JSONObject result = new JSONObject(sb.toString());
            System.out.println(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
