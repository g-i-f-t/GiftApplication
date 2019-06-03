package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;

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
import kr.ac.jejunu.giftapplication.vo.NewsVO;

public class NewsfeedViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public List<NewsVO> getNews() {
        String url = "http://www.inven.co.kr/webzine/news/?site=indie";
        NewsFeedTask task = new NewsFeedTask(url);
        List<NewsVO> newsfeedList = new ArrayList<>();
        try {
            newsfeedList = task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newsfeedList;
    }

    private class NewsFeedTask extends AsyncTask<Void, Void, List<NewsVO>> {
        private final String url;

        public NewsFeedTask(String url) {
            this.url = url;
        }

        @Override
        protected List<NewsVO> doInBackground(Void... voids) {
            List<NewsVO> newsList = new ArrayList<>();
            try {
                Connection.Response response = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .execute();
                Document document = response.parse();

                Elements newseles = document.select("#webzineNewsListF1 > div.list > div > table > tbody > tr");

                for (Element elem : newseles) {
                    String title = elem.select("td.left.name > .content > .title > a").text();

                    NewsVO news = new NewsVO();
                    System.out.println("테스트: " + title);
                    news.setTitle(title);
                    news.setDeveloper("테스트개발자");
                    news.setImage("http://static.inven.co.kr/column/2019/06/03/news/thumb/n16172648351.png");
                    newsList.add(news);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsList;

        }
    }
}
