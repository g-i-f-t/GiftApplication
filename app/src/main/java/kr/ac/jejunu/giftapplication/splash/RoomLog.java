package kr.ac.jejunu.giftapplication.splash;

import android.os.AsyncTask;

import kr.ac.jejunu.giftapplication.Room.UserDao;
import kr.ac.jejunu.giftapplication.vo.AuthVO;
import kr.ac.jejunu.giftapplication.vo.User;

public class RoomLog {
    public static class addDBTask extends AsyncTask<User, Void, Void> {
        private final UserDao roomUserDao;

        public addDBTask(UserDao roomUserDao) {
            this.roomUserDao = roomUserDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            for(User user: users)
                roomUserDao.add(user);
            final String result = roomUserDao.getAll().get(0).getUserSeqNo();

            return null;
        }
    }

    public static class getDBTask extends AsyncTask<User, Void, String> {
        private final UserDao roomUserDao;

        public getDBTask(UserDao roomUserDao) {
            this.roomUserDao = roomUserDao;
        }

        @Override
        protected String doInBackground(User... users) {
            String result = null;
            try {
                result = roomUserDao.getAll().get(0).getUserSeqNo();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}