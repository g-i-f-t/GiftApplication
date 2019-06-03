package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.vo.News;

public class NewsfeedViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public List<News> getNews() {
        String url = "http://www.inven.co.kr/webzine/news/?site=indie";
        NewsFeedTask task = new NewsFeedTask(url);
        List<News> newsfeedList = new ArrayList<>();
        try {
            newsfeedList = task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newsfeedList;
    }

    private class NewsFeedTask extends AsyncTask<Void, Void, List<News>> {
        private final String url;

        public NewsFeedTask(String url) {
            this.url = url;
        }

        @Override
        protected List<News> doInBackground(Void... voids) {
            List<News> News = null;
            try {
                Connection.Response response = Jsoup.connect("http://www.inven.co.kr/webzine/news/?site=indie")
                        .method(Connection.Method.GET)
                        .execute();
                Document document = response.parse();

               Elements news = document.select("#webzineNewsListF1 > div.list > div > table > tbody > tr");

                System.out.println(news.size());

                for(Element elem : news){
                    String title = elem.select("td.left.name > .content > .title > a").text();
                    System.out.println(title);
                }
                System.out.println(news.first().html());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return News;

        }
    }
}
