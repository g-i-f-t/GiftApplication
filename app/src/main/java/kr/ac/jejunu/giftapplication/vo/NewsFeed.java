package kr.ac.jejunu.giftapplication.vo;

public class NewsFeed {
    private String title;
    private String img_url;
    private String detail_link;
    private String release;
    private String developer;


    public NewsFeed (String title, String url, String link, String release, String director){
        this.title = title;
        this.img_url = url;
        this.detail_link = link;
        this.release = release;
        this.developer = director;
    }

    public String getTitle() {
        return title;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public String getRelease() {
        return release;
    }

    public String getDirector() {
        return developer;
    }
}