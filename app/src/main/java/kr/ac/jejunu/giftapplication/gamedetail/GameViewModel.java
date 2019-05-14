package kr.ac.jejunu.giftapplication.gamedetail;

import android.os.AsyncTask;

import java.net.URL;

import kr.ac.jejunu.giftapplication.vo.GameVO;

public class GameViewModel {
    GameVO getGameVO() {
        // TODO: Network Call. 지금은 임시더미
        GameVO gameVO = new GameVO();
        gameVO.setName("게임 개발 힘들어요");
        gameVO.setDeveloper("기푸트");
        gameVO.setGameInformation("이 게임은 눈 앞에 보이는 적들을 마구마구 때리는 핵 앤 슬래시 게임이에요. 아니 이렇게 재밌는 게임이 있는데 안할 거라구요? 저희 굶어죽어요 ㅠ");
        gameVO.setInvestmentInformation("개발은 애자일하게 하구요. 그냥 하다보니 될 것 같아요 ㅎㅎ 마니 사랑해주세요 여러분");
        gameVO.setInvestmentCondition("1인당 최소 50000원부터 100000원 까지 투자 가능합니다.\n\n수익은 게임 발매후 6개월 동안, 자신이 배당받은 수익률만큼 1개월 주기로 지급됩니다.");
        gameVO.setCompanyIntroduction("안녕하세요 저희 회사 기푸트는 1인 개발자에오오");
        gameVO.setGoalPrice(5000000L);
        gameVO.setCurrentPrice(4500000L);
        return gameVO;
    }

    public class GameDetailTask extends AsyncTask<String, Void, GameVO> {
        @Override
        protected GameVO doInBackground(String... strings) {
            String uri = strings[0];
            return null;
        }
    }
}