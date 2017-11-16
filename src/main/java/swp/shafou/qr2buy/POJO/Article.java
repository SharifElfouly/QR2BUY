package swp.shafou.qr2buy.POJO;

import java.util.Arrays;

/**
 *------------------------------------*
 * - Klasse erstellt. 14.12.16 ELFOULY
 * - Überschreiben der Methoden equals und Hashcode 20.12.16 ELFOULY
 *------------------------------------*
 *
 * POJO Artikel Klasse
 *
 *--------------WARNUNG--------------*
 * - Attribute müssen den gleichen Namen wie in der Datenbank haben
 * --------------WARNUNG--------------*
 */
public class Article {

    private int articleID;
    private String articleName;
    private String articleDescription;
    private double articlePrice;
    private double vat;
    private String articleStatus;
    private byte[] articleImagePath;
    private int createdBy;

    public Article(int articleID, String articleName, String articleDescription, double articlePrice, double vat, String articleStatus, byte[] articleImagePath, int createdBy) {
        this.articleID = articleID;
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.articlePrice = articlePrice;
        this.vat = vat;
        this.articleStatus = articleStatus;
        this.articleImagePath = articleImagePath;
        this.createdBy = createdBy;
    }

    public Article() {


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (articleID != article.articleID) return false;
        if (Double.compare(article.articlePrice, articlePrice) != 0) return false;
        if (Double.compare(article.vat, vat) != 0) return false;
        if (createdBy != article.createdBy) return false;
        if (articleName != null ? !articleName.equals(article.articleName) : article.articleName != null)
            return false;
        if (articleDescription != null ? !articleDescription.equals(article.articleDescription) : article.articleDescription != null)
            return false;
        if (articleStatus != null ? !articleStatus.equals(article.articleStatus) : article.articleStatus != null)
            return false;
        return Arrays.equals(articleImagePath, article.articleImagePath);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = articleID;
        result = 31 * result + (articleName != null ? articleName.hashCode() : 0);
        result = 31 * result + (articleDescription != null ? articleDescription.hashCode() : 0);
        temp = Double.doubleToLongBits(articlePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (articleStatus != null ? articleStatus.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(articleImagePath);
        result = 31 * result + createdBy;
        return result;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
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

    public byte[] getArticleImagePath() {
        return articleImagePath;
    }

    public void setArticleImagePath(byte[] articleImagePath) {
        this.articleImagePath = articleImagePath;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
