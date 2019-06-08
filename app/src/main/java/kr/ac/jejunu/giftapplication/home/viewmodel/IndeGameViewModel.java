package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import kr.ac.jejunu.giftapplication.vo.CafeVO;

public class IndeGameViewModel extends ViewModel {
    public List<CafeVO> getCafe() {

        String url = "https://cafe.naver.com/indiedev";
        CafeTask task = new CafeTask(url);
        List<CafeVO> indegameList   = new ArrayList<>();

        indegameList = task.execute().get();


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
            return null;
        }
    }
}
