/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModel;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class CompleteFundingViewModel extends ViewModel implements FundingViewModel {
    @Override
    public List<GameVO> getFundingList() {
        String uri = "http://117.17.102.139:8080/game?list=done";
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
}
