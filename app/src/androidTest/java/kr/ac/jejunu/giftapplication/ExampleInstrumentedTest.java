package kr.ac.jejunu.giftapplication;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import kr.ac.jejunu.giftapplication.Room.AppDatabase;
import kr.ac.jejunu.giftapplication.vo.User;
import kr.ac.jejunu.giftapplication.Room.UserDao;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Context context = InstrumentationRegistry.getInstrumentation().getContext();
    @Test
    public void addRoom() {
        String userId = "ohg429";
        String passWord = "1234";
        User u1, u2;
        UserDao roomUserDao = AppDatabase.getInstance(context).roomUserDao();
        u1 = new User();
//        u1.setUsereamil(userId);
//        u1.setPassword(passWord);
//        long id = roomUserDao.add(u1);
//
//        u2 = roomUserDao.get(id);
//        assertThat(u2.getId(), is(id));
//        assertThat(u2.getUsereamil(), is(userId));
//        assertThat(u2.getPassword(), is(passWord));
    }
}
