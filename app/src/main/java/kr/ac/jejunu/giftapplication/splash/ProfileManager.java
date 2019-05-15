package kr.ac.jejunu.giftapplication.splash;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.ExecutionException;

import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.Room.AppDatabase;
import kr.ac.jejunu.giftapplication.Room.UserDao;
import kr.ac.jejunu.giftapplication.vo.LoginVO;
import kr.ac.jejunu.giftapplication.vo.Profile;
import kr.ac.jejunu.giftapplication.vo.User;

public class ProfileManager {
    public ProfileManager() {
    }
    //해당 User의 시퀀스 넘버에 대한 userName과 userEmail을 반환
    public void getProfile(String loginKey, Application application) {
        String url = "http://117.17.102.139:8080/account/" + loginKey;
        SplashActivity.NetWorkTask netWorkTask = new SplashActivity.NetWorkTask(url);
        LoginVO result = null;
        try {
            result = netWorkTask.execute().get();
            if (result.getCode() == 200) {
                Profile profile = new Profile();
                profile.setName(result.getName());
                profile.setEmail(result.getEmail());

                ((GiftApplication) application).setUserInfo(profile);
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
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return LoginKey;
    }
}