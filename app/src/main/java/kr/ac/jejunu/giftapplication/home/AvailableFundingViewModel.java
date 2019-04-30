package kr.ac.jejunu.giftapplication.home;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class AvailableFundingViewModel extends ViewModel implements FundingViewModel {
    @Override
    public List<GameVO> getFundingList() {
        List<GameVO> gameVOList = new ArrayList<>();
        gameVOList.add(new GameVO() {{ setName("해보쉴?"); }});
        gameVOList.add(new GameVO() {{ setName("아니오");}});
        gameVOList.add(new GameVO() {{ setName("아니오");}});
        gameVOList.add(new GameVO() {{ setName("아니오");}});
        gameVOList.add(new GameVO() {{ setName("아니오");}});
        gameVOList.add(new GameVO() {{ setName("아니오");}});
        return gameVOList;
    }
    // TODO: Implement the ViewModel
}
