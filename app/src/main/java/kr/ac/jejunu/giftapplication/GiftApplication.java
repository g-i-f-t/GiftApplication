/**
 * Author. Aerain
 * SSLAB
 * Jeju National University
 */

package kr.ac.jejunu.giftapplication;

import android.app.Application;

import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.vo.Profile;

public class GiftApplication extends Application {
    private ProfileManager profileManager;
    @Override
    public void onCreate() {
        super.onCreate();
        profileManager = new ProfileManager();
    }

    private Profile profile = null;
    public void setUserInfo(Profile profile) {
        this.profile = profile;
    }
    public Profile userInfo() {
        return profile;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public void setProfileManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }
}
