package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kr.ac.jejunu.giftapplication.vo.CafeVO;

public class MobileIndeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public List<CafeVO> getCafe(int menuId, int page) {
        String url = "https://m.cafe.naver.com/ArticleList.nhn?search.clubid=28183931&search.menuid=" + menuId + "&search.boardtype=L&search.page="+page;
//      String url = "http://www.inven.co.kr";
        CafeTask task = new CafeTask(url);
        List<CafeVO> indegameList   = new ArrayList<>();
        try {
            indegameList = task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return indegameList;
    }

    private class CafeTask extends AsyncTask<Void, Void, List<CafeVO>> {

        private final String url;

        private CafeTask(String url) {
            this.url = url;
        }

        @Override
        protected List<CafeVO> doInBackground(Void... voids) {
            //TODO title , total_view 게시
            List<CafeVO> cafeList = new ArrayList<>();
            try {
                Connection.Response response = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .execute();
                Document document = response.parse();

                Elements IndeList = document.select("#articleListArea > ul > li");

                for (Element element : IndeList) {
                    String title = element.select("strong[class=tit]").text();
                    String image = element.select("div[class=thumb] > img").attr("src");
                    String url = "https://m.cafe.naver.com"+ element.select("a").attr("href");
                    String developer = element.select("span[class = nick] > span").text();
                    String total_view = "Total: "+ element.select("span[class = no]").text();
                    String date = element.select("span[class = time]").text();

                    CafeVO cafeVO = new CafeVO();
//                    System.out.println("url"+url);
                    cafeVO.setTitle(title);
                    cafeVO.setImage(image);
                    cafeVO.setUrl(url);
                    cafeVO.setTotal_view(total_view);
                    cafeVO.setDeveloper(developer);
                    cafeVO.setDate(date);
                    cafeList.add(cafeVO);

//                    Collections.sort(total_view.split(Total:));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cafeList;
        }
    }
}
