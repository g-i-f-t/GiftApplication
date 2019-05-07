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

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getUsereamil() {
        return usereamil;
    }

    public void setUsereamil(String usereamil) {
        this.usereamil = usereamil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}