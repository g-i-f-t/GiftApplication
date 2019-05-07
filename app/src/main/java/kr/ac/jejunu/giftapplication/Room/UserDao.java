package kr.ac.jejunu.giftapplication.Room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Query("SELECT * FROM room_info")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(User u1);

    @Query("SELECT * FROM room_info WHERE id=:id")
    User get(long id);

    @Delete
    void delete(User roomUser);

    @Update
    void update(User roomUser);
}