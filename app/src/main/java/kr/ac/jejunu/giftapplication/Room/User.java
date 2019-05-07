package kr.ac.jejunu.giftapplication.Room;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_info")
public class User implements Serializable {
    @PrimaryKey
    private long id;
    @ColumnInfo(name = "usereamil")
    private String usereamil;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "usereamil")

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord(){
        return passWord;
    }
    public void setPassWord(String passWord){
        this.passWord = passWord;
    }

}