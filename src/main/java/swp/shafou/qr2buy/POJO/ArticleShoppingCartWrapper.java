package swp.shafou.qr2buy.POJO;

/**
 *------------------------------------*
 * - Klasse erstellt. 20.12.16 ELFOULY
 *------------------------------------*
 *
 * Diese Klasse Wrappt einen Artikel und hat zusätzlich die Menge als Attribut
 */
public class ArticleShoppingCartWrapper {

    /*
     * Ein Artikel der Wrapper Klasse
     */
    private Article article;

    /*
     * Die Menge zum zugehörigen Artikel
     */
    private int menge;

    public ArticleShoppingCartWrapper(Article article, int menge) {

        this.article = article;
        this.menge = menge;
    }

    public ArticleShoppingCartWrapper() {


    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleShoppingCartWrapper that = (ArticleShoppingCartWrapper) o;

        if (menge != that.menge) return false;
        return article != null ? article.equals(that.article) : that.article == null;

    }

    @Override
    public int hashCode() {
        int result = article != null ? article.hashCode() : 0;
        result = 31 * result + menge;
        return result;
    }
}
