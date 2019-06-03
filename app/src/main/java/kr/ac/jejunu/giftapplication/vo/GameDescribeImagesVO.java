package kr.ac.jejunu.giftapplication.vo;

import java.io.Serializable;

public class GameDescribeImagesVO implements Serializable {
    private Long id;
    private String describeImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescribeImage() {
        return describeImage;
    }

    public void setDescribeImage(String describeImage) {
        this.describeImage = describeImage;
    }
}
