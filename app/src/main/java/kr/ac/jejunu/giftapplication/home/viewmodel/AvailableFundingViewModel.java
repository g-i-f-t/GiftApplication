/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModel;

import kr.ac.jejunu.giftapplication.home.CategoryTask;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class AvailableFundingViewModel extends ViewModel implements FundingViewModel {
    @Override
    public List<GameVO> getFundingList() {
        String uri = "http://117.17.102.139:8080/game?list=available";
        List<GameVO> gameVOList = new ArrayList<>();
        try {
            gameVOList = new GetFundingTask(uri)
                    .execute()
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return gameVOList;
    }
    // TODO: Implement the ViewModel

    public List<String> getAllCategory() {
        String uri = "http://117.17.102.139:8080/game/category";
        List<String> categoryList = null;
        try {
            categoryList = new CategoryTask(uri)
                    .execute()
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
