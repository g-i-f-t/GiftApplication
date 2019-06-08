package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;

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
                    String url = elem.select("td.left.name > .content > .title > a").attr("href");
                    String image = elem.select("img[class=banner]").attr("src");
                    String[] categorySplit = elem.select("span[class=info]").text().split(" \\| ");
                    String category = categorySplit[0].trim(); //카테고리
                    String writer = categorySplit[1].trim(); // 기자
                    String date = categorySplit[2].trim(); //기재날짜

                    NewsVO news = new NewsVO();
//                    System.out.println("테스트: " + title);
//                    System.out.println("테스트: " + url);
//                    System.out.println("URLTest " + image);
                    news.setTitle(title);
                    news.setImage(image);
                    news.setUrl(url);
                    news.setCategory(category+" l");
                    news.setWriter(writer);
                    news.setDate(date);
                    newsList.add(news);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsList;

        }
    }
}
