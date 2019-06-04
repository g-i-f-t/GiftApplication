package kr.ac.jejunu.giftapplication.selectbank;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModel;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.vo.BankAccountVO;
import kr.ac.jejunu.giftapplication.vo.BankVO;

public class SelectBankViewModel extends ViewModel {
    public List<BankAccountVO> getBankAccountList(ProfileManager profileManager, Context context) {
        Map<String, String> auth = profileManager.getLoginKey(context);
        List<BankAccountVO> bankVOList = null;
        try {
            bankVOList = new BankListTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO
        return bankVOList;
    }

    public class BankListTask extends AsyncTask<Void, Void, List<BankAccountVO>> {

        @Override
        protected List<BankAccountVO> doInBackground(Void... voids) {

            return null;
        }
    }
}
