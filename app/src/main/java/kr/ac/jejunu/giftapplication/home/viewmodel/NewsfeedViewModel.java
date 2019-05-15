package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModel;
import kr.ac.jejunu.giftapplication.vo.News;

public class NewsfeedViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public String getNews() {
        String url = "http://www.inven.co.kr/webzine/news/?site=indie";
        NewsFeedTask task = new NewsFeedTask(url);
        try {
            List<News> newsfeedlist = task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class NewsFeedTask extends AsyncTask<Void, Void, List<News>> {
        private final String url;

        public NewsFeedTask(String url) {
            this.url = url;
        }

        @Override
        protected List<News> doInBackground(Void... voids) {
            List<News> News = null;
//            try {
//                URL uri = new URL(url);
//
//                HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
//                connection.setRequestMethod("GET");
////                connection.setRequestProperty("Content-Type", "application/json");
////                connection.setRequestProperty("charset", "UTF-8");
////                connection.setUseCaches(false);
//
//                InputStream is = connection.getInputStream();
//
//                StringBuilder builder = new StringBuilder();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    builder.append(line);
//                    builder.append('\n');
//                }
//                News = new Gson().fromJson(builder.toString(), List<News>.class);
//                connection.disconnect();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {
                Connection.Response response = Jsoup.connect("http://www.inven.co.kr/webzine/news/?site=indie")
                        .method(Connection.Method.GET)
                        .execute();
                Document document = response.parse();

               Elements news = document.select("#webzineNewsListF1 > div.list > div > table > tbody > tr");
                System.out.println(news.size());
                System.out.println(news.first().html());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return News;

        }
    }
}
