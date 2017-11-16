package swp.shafou.qr2buy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;

/**
 *------------------------------------*
 * Test Klasse erstellt. 20.12.16 ELFOULY
 *------------------------------------*
 *
 * Diese Klasse testet die Methoden um Artikel in einer Liste zu suchen
 * Getestete Methoden:
 * - Einen Artikel in einer Liste anhand einer ID suchen
 * - Methode, die testet ob ein Artikel in der Liste bereits besteht
 */
public class ArticleListSearchTest {

    /*
     * Eine Liste mit Aartikel
     */
    ArrayList<ArticleShoppingCartWrapper> articleList;

    @Before
    public void setUp() {

//        articleList = new ArrayList<>();
//
//        ArticleShoppingCartWrapper Snicker = new ArticleShoppingCartWrapper(new Article(1, "Snicker", "", 1.0, 19.0, "", "Eine Tolle Frucht!", 12), 2);
//        ArticleShoppingCartWrapper CocaCola = new ArticleShoppingCartWrapper(new Article(2, "Coca Cola", "", 0.50, 19.0, "",  "Eine Tolle Frucht!", 12), 1);
//        ArticleShoppingCartWrapper Fanta = new ArticleShoppingCartWrapper(new Article(3, "Fanta","", 0.75, 19.0, "", "Eine Tolle Frucht!", 12), 5);
//        ArticleShoppingCartWrapper Chips = new ArticleShoppingCartWrapper(new Article(4, "Chips","", 0.75, 19.0, "", "Eine Tolle Frucht!", 12), 5);
//        ArticleShoppingCartWrapper Sprite = new ArticleShoppingCartWrapper(new Article(5, "Sprite", "", 0.75, 19.0, "", "Eine Tolle Frucht!", 12), 5);
//        ArticleShoppingCartWrapper Mars = new ArticleShoppingCartWrapper(new Article(6, "Mars", "", 0.75, 19.0, "", "Eine Tolle Frucht!", 12), 5);
//
//        articleList.add(Snicker);
//        articleList.add(CocaCola);
//        articleList.add(Fanta);
//        articleList.add(Chips);
//        articleList.add(Sprite);
//        articleList.add(Mars);
    }

    /**
     * Testet das suchen eines Artikels in einer Liste von ArtikelWrapper anhand des Artikel Schlüssels
     */
    @Test
    public void getArticleByIDTest() {

//        assertEquals(getArticleById(1, articleList), new Article(1, "Snicker", "", 1.0, 19.0, "", "Eine Tolle Frucht!", 12));
//        assertEquals(getArticleById(3, articleList), new Article(3, "Fanta","", 0.75, 19.0, "", "Eine Tolle Frucht!", 12));
//        assertEquals(getArticleById(4, articleList), new Article(4, "Chips","", 0.75, 19.0, "", "Eine Tolle Frucht!", 12));
//        assertEquals(getArticleById(6, articleList), new Article(6, "Mars","", 0.75, 19.0, "", "Eine Tolle Frucht!", 12));
    }

    /**
     * Testet ob ein ArtikelWrapper Objekt in einer Liste von ArticleShoppingCartWrapper Objekten existiert
     */
    @Test
    public void isArticleinListTest() {

//        ArticleShoppingCartWrapper Marmelade = new ArticleShoppingCartWrapper(new Article(1, "Marmelade", "", 3.20, 19.00, "", "", 2), 3);
//        ArticleShoppingCartWrapper Snicker = new ArticleShoppingCartWrapper(new Article(1, "Snicker", "", 1.0, 19.0, "", "Eine Tolle Frucht!", 12), 2);
//        ArticleShoppingCartWrapper Mars = new ArticleShoppingCartWrapper(new Article(6, "Mars", "", 0.75, 19.0, "", "Eine Tolle Frucht!", 12), 5);
//        ArticleShoppingCartWrapper Suppe = new ArticleShoppingCartWrapper(new Article(2, "Suppe", "", 1.20, 19.00, "", "", 2), 1);
//
//        assertEquals(false, isArticleWrapperInList(Marmelade, articleList));
//        assertEquals(true, isArticleWrapperInList(Snicker, articleList));
//        assertEquals(true, isArticleWrapperInList(Mars, articleList));
//        assertEquals(false, isArticleWrapperInList(Suppe, articleList));
//    }


        /**
         * Sucht ein Artikel anhand eines Artikel Schlüssels
         * -----------ACHTUNG------------------*
         * - Muss auf not null getestet werden
         * ------------------------------------*
         *
         * @param id          Der Schlüssel (id) eines Artikels
         * @param articleList Eine Liste mit ArticleShoppingCartWrapper Objekte
         * @return Ein Artikel der anhand des Schlüssels in einer Liste gefunden wurde
         */
//    public static Article getArticleById(int id, ArrayList<ArticleShoppingCartWrapper> articleList) {
//
//        Article foundArticle = null;
//
//        for (ArticleShoppingCartWrapper articleWrapper : articleList) {
//
//            if (articleWrapper.getArticleFromTable().getArticleID() == id) {
//                foundArticle = articleWrapper.getArticleFromTable();
//            }
//        }
//
//        return foundArticle;
//    }

        /**
         * Testet ob ein ArticleShoppingCartWrapper Objekt in einer Liste von ArticleShoppingCartWrapper Objekten existiert
         *
         * @param articleWrapper Ein ArticleShoppingCartWrapper Objekt
         * @param articleList    Eine LIste von ArticleShoppingCartWrapper Objekten
         * @return true falls der ArtikelWrapper in der übergebenen Liste existiert
         */
//    public static boolean isArticleWrapperInList(ArticleShoppingCartWrapper articleWrapper, ArrayList<ArticleShoppingCartWrapper> articleList) {
//
//        boolean isArticleInList = false;
//
//        for (ArticleShoppingCartWrapper article : articleList) {
//
//            if (article.getArticleFromTable().equals(articleWrapper.getArticleFromTable())) {
//                isArticleInList = true;
//                break;
//            }
//        }
//
//        return isArticleInList;
//    }

    }
}
