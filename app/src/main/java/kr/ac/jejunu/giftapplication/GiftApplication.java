/**
 * Author. Aerain
 * SSLAB
 * Jeju National University
 */

package kr.ac.jejunu.giftapplication;

import android.app.Application;

import kr.ac.jejunu.giftapplication.vo.Profile;

public class GiftApplication extends Application {

    private Profile profile = null;
    public void setUserInfo(Profile profile) {
        this.profile = profile;
    }
    public String userInfo() {
        // Todo

        return profile != null ? profile.getName() : "로그인";
    }
}
