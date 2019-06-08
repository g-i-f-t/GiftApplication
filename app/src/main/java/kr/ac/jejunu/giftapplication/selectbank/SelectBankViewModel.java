package kr.ac.jejunu.giftapplication.selectbank;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModel;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.vo.BankAccountVO;
import kr.ac.jejunu.giftapplication.vo.BankVO;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class SelectBankViewModel extends ViewModel {
    public List<BankAccountVO> getBankAccountList(ProfileManager profileManager, Context context) {
        Map<String, String> auth = profileManager.getLoginKey(context);
        List<BankAccountVO> bankVOList = null;
        try {
            bankVOList = new BankListTask(auth.get("accessToken"), auth.get("userSeqNo")).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bankVOList;
    }

    public class BankListTask extends AsyncTask<Void, Void, List<BankAccountVO>> {
        private String uri, accessToken, userSeqNo;

        public BankListTask(String accessToken, String userSeqNo) {
            uri = "http://117.17.102.139:8080/bank";
            this.accessToken = accessToken;
            this.userSeqNo = userSeqNo;
        }

        @Override
        protected List<BankAccountVO> doInBackground(Void... voids) {
            List<BankAccountVO> bankAccountVOList = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
                // 헤더 설정
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                // body
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("accessToken", accessToken);
                jsonObject.put("userSeqId", userSeqNo);

                OutputStream os = connection.getOutputStream();
                os.write(jsonObject.toString().getBytes("UTF-8"));
                os.flush();

                // 결과 받아옴
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuffer sb = new StringBuffer();

                String line = null;
                while((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                }

                System.out.println(sb.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<BankAccountVO>>() {
                }.getType();
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(sb.toString()).getAsJsonObject().get("data");
                bankAccountVOList = gson.fromJson(element, type);
                System.out.println(bankAccountVOList.get(0).getBankName());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return bankAccountVOList;
        }
    }
}
