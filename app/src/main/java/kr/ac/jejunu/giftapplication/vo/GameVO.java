package kr.ac.jejunu.giftapplication.vo;

import java.io.Serializable;
import java.util.List;

public class GameVO implements Serializable {
    private Long id;
    private String name;
    private DeveloperVO developer;
    private String category;
    private boolean success;
    private Long currentPrice;
    private Long goalPrice;
    private String gameInformation;
    private String investmentInformation;
    private String investmentCondition;
    private String companyIntroduction;
    private String profileImage;
    private List<GameDescribeImagesVO> gameDescribeImages;

    public Long getGameId() {
        return id;
    }

    public void setGameId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeveloperVO getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperVO developer) {
        this.developer = developer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getGoalPrice() {
        return goalPrice;
    }

    public void setGoalPrice(Long goalPrice) {
        this.goalPrice = goalPrice;
    }

    public String getGameInformation() {
        return gameInformation;
    }

    public void setGameInformation(String gameInformation) {
        this.gameInformation = gameInformation;
    }

    public String getInvestmentInformation() {
        return investmentInformation;
    }

    public void setInvestmentInformation(String investmentInformation) {
        this.investmentInformation = investmentInformation;
    }

    public String getInvestmentCondition() {
        return investmentCondition;
    }

    public void setInvestmentCondition(String investmentCondition) {
        this.investmentCondition = investmentCondition;
    }

    public String getCompanyIntroduction() {
        return companyIntroduction;
    }

    public void setCompanyIntroduction(String companyIntroduction) {
        this.companyIntroduction = companyIntroduction;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public List<GameDescribeImagesVO> getGameDescribeImages() {
        return gameDescribeImages;
    }

    public void setGameDescribeImages(List<GameDescribeImagesVO> gameDescribeImages) {
        this.gameDescribeImages = gameDescribeImages;
    }
}
