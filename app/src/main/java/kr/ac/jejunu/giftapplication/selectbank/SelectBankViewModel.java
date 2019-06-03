package kr.ac.jejunu.giftapplication.selectbank;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.ViewModel;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.vo.BankAccountVO;

public class SelectBankViewModel extends ViewModel {
    public List<BankAccountVO> getBankAccountList(ProfileManager profileManager, Context context) {
        String userReqNo = profileManager.getLoginKey(context);
        // TODO
        return null;
    }
}
