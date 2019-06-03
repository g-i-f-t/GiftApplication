package kr.ac.jejunu.giftapplication.splash;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.concurrent.ExecutionException;

import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.NetworkTask;
import kr.ac.jejunu.giftapplication.Room.AppDatabase;
import kr.ac.jejunu.giftapplication.Room.UserDao;
import kr.ac.jejunu.giftapplication.vo.LoginVO;
import kr.ac.jejunu.giftapplication.vo.Profile;
import kr.ac.jejunu.giftapplication.vo.User;

public class ProfileManager {
    private NetworkTask netWorkTask;
    public ProfileManager() {
    }

    public interface Callback {
        public void callback();
    }

    public void stopTask() {
        if(!netWorkTask.isCancelled()) netWorkTask.cancel(true);
    }
    //해당 User의 시퀀스 넘버에 대한 userName과 userEmail을 반환
    public void getProfile(String loginKey, Activity activity, ProfileManager.Callback callback) {
        String url = "http://117.17.102.139:8080/account/" + loginKey;
        netWorkTask = new NetworkTask(url, activity);
        LoginVO result;
        try {
            result = netWorkTask.execute().get();
            if (result.getCode() == 200) {
                Profile profile = new Profile();
                profile.setName(result.getName());
                profile.setEmail(result.getEmail());

                ((GiftApplication) activity.getApplication()).setUserInfo(profile);
                callback.callback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //RoomDB에 저장된 시퀀스넘버 반환
    public String getLoginKey(Context context) {
        User user = new User();
        UserDao roomUserDao = AppDatabase.getInstance(context).roomUserDao();
        // Room에서 get, user정보가 있을경우 서버로 로그인 요청
        String LoginKey = null;
        try {
            LoginKey = new RoomLog.getDBTask(roomUserDao).execute(user).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return LoginKey;
    }
}