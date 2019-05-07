package kr.ac.jejunu.giftapplication.Room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import kr.ac.jejunu.giftapplication.vo.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM room_info")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(User u1);

    @Query("SELECT * FROM room_info WHERE userNum=:id")
    User get(long id);

    @Delete
    void delete(User roomUser);

    @Update
    void update(User roomUser);
}