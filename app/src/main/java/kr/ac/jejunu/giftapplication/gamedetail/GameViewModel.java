package kr.ac.jejunu.giftapplication.gamedetail;

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
import java.util.List;
import java.util.concurrent.ExecutionException;

import kr.ac.jejunu.giftapplication.vo.GameVO;

public class GameViewModel {
    GameVO getGameVO(Long id) {
        String uri = "http://117.17.102.139:8080/game/" + id;
        GameDetailTask gameDetailTask = new GameDetailTask(uri);
        GameVO gameVO = null;
        try {
            gameVO = gameDetailTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        GameVO gameVO = new GameVO();
//        gameVO.setName("게임 개발 힘들어요");
//        gameVO.setDeveloper("기푸트");
//        gameVO.setGameInformation("이 게임은 눈 앞에 보이는 적들을 마구마구 때리는 핵 앤 슬래시 게임이에요. 아니 이렇게 재밌는 게임이 있는데 안할 거라구요? 저희 굶어죽어요 ㅠ");
//        gameVO.setInvestmentInformation("개발은 애자일하게 하구요. 그냥 하다보니 될 것 같아요 ㅎㅎ 마니 사랑해주세요 여러분");
//        gameVO.setInvestmentCondition("1인당 최소 50000원부터 100000원 까지 투자 가능합니다.\n\n수익은 게임 발매후 6개월 동안, 자신이 배당받은 수익률만큼 1개월 주기로 지급됩니다.");
//        gameVO.setCompanyIntroduction("안녕하세요 저희 회사 기푸트는 1인 개발자에오오");
//        gameVO.setGoalPrice(5000000L);
//        gameVO.setCurrentPrice(4500000L);
        return gameVO;
    }

    public class GameDetailTask extends AsyncTask<Void, Void, GameVO> {
        private final String uri;

        public GameDetailTask(String uri) {
            this.uri = uri;
        }

        @Override
        protected GameVO doInBackground(Void... voids) {
            GameVO gameVO = new GameVO();
            try {
                URL url = new URL(uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                String line = null;
                StringBuffer buffer = new StringBuffer();
                while((line = br.readLine()) != null) {
                    buffer.append(line);
                    buffer.append("\n");
                }
                Gson gson = new Gson();
                Type type = new TypeToken<GameVO>() {
                }.getType();
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(buffer.toString()).getAsJsonObject().get("data");
                gameVO = gson.fromJson(element, type);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gameVO;
        }
    }
}