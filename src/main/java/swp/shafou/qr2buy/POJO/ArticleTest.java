package swp.shafou.qr2buy.POJO;

import java.io.Serializable;

/**
 * Created by Shafou on 03/01/2017.
 */

public class ArticleTest {

    private static final long serialVersionUID = 1L;
    private Integer articleID;
    private String articleName;
    private String articleDescription;
    private double articlePrice;
    private double vat;
    private String articleStatus;
    private String articleImagePath;
    private Boolean isMarked;

    public ArticleTest() {
    }

    public ArticleTest(Integer articleID) {
        this.articleID = articleID;
    }

    public ArticleTest(String articleDescription, Integer articleID, String articleName, double articlePrice, String articleStatus, double vat) {
        this.articleDescription = articleDescription;
        this.articleID = articleID;
        this.articleName = articleName;
        this.articlePrice = articlePrice;
        this.vat = vat;
        this.articleStatus = articleStatus;
    }

    public Integer getArticleID() {
        return articleID;
    }

    public void setArticleID(Integer articleID) {
        this.articleID = articleID;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public double getArticlePrice() {
        return articlePrice;
    }

    public void setArticlePrice(double articlePrice) {
        this.articlePrice = articlePrice;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public String getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(String articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getArticleImagePath() {
        return articleImagePath;
    }

    public void setArticleImagePath(String articleImagePath) {
        this.articleImagePath = articleImagePath;
    }

    @Override
    public String toString() {
        return "com.secure.entity.Article[ articleID=" + articleID + " ]";
    }

    public Boolean getIsMarked() {
        return isMarked;
    }

    public void setIsMarked(Boolean isMarked) {
        this.isMarked = isMarked;
    }

}
