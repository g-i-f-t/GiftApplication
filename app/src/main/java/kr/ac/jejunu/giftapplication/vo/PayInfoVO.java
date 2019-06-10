package kr.ac.jejunu.giftapplication.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PayInfoVO implements Serializable {
    private Long id;
    private String payDate;
    private Long price;
    private GameVO game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public GameVO getGame() {
        return game;
    }

    public void setGame(GameVO game) {
        this.game = game;
    }
}
