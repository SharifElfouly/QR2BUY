package swp.shafou.qr2buy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

import swp.shafou.qr2buy.Databases.ShoppingCartDatabase.ShoppingCartDataSource;
import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;
import swp.shafou.qr2buy.TestData.ArticleShoppingCartWrapperTestData;

/**
 * ------------------------------------*
 * - 14.12.16 ELFOULY Testklasse erstellt.
 * ------------------------------------*
 *
 * <p>
 * - Diese Tesklasse testet alle Methoden die auf die Warenkorb Datenbanktabelle zugreifen
 * <p>
 *
 * -----------WICHTIG------------------*
 * - Die Artikel Schlüssel 2.999.980 - 3.000.000 werden zum debuggen und testen benutzt und
 * dürfen deshalb im Produktiv System auf keinem Fall als Artikel Schlüssel eingesetzt werden.
 * ------------------------------------*
 */
@RunWith(AndroidJUnit4.class)
public class ShoppingCartDatabaseTest {

    /**
     * Einkaufswagen Datenbank
     */
    ShoppingCartDataSource shoppingCartDatabase;

    /**
     * Liste mit allen Artikel in der Datenbank
     */
    ArrayList<ArticleShoppingCartWrapper> shoppingCartArticles;

    /**
     * Artikel ids mit denen getestet wird
     */
    private static final int articleId1 = 2999980;
    private static final int articleId2 = 2999981;
    private static final int articleId3 = 2999982;
    private static final int articleId4 = 2999983;

    /**
     * Datenbank mit Daten füllen
     */
    @Before
    public void setUp() {

        Context appContext = InstrumentationRegistry.getTargetContext();
        shoppingCartDatabase = new ShoppingCartDataSource(appContext);

        shoppingCartArticles = new ArrayList<>();
        shoppingCartArticles.add(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(0));
        shoppingCartArticles.add(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(1));
        shoppingCartArticles.add(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(2));
        shoppingCartArticles.add(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(3));

        shoppingCartDatabase.open();
        shoppingCartDatabase.insertArticleToShoppinCartTable(shoppingCartArticles.get(0));
        shoppingCartDatabase.insertArticleToShoppinCartTable(shoppingCartArticles.get(1));
        shoppingCartDatabase.insertArticleToShoppinCartTable(shoppingCartArticles.get(2));
        shoppingCartDatabase.insertArticleToShoppinCartTable(shoppingCartArticles.get(3));
        shoppingCartDatabase.close();
    }

    /**
     * Testet die Methode, die Anzahl der Artikel Position im Warenkorb auszugeben
     */
    @Test
    public void getNumberOfArticlesOfShoppingCartTest() {

        assertEquals(4, shoppingCartDatabase.getNumberOfShoppingCartItems());

        // Entfernen eines Artikels
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId1);

        assertEquals(3, shoppingCartDatabase.getNumberOfShoppingCartItems());

        // Entfernen bereits entfernter Artikel
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId1);
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId1);

        assertEquals(3, shoppingCartDatabase.getNumberOfShoppingCartItems());

        // Entfernen von zwei Artikeln
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId2);
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId3);

        assertEquals(1, shoppingCartDatabase.getNumberOfShoppingCartItems());

        // Entfernen eines Artikels
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId4);

        assertEquals(0, shoppingCartDatabase.getNumberOfShoppingCartItems());

        // Ausgangssituation herstellen
        setUp();
    }

    /**
     * Soll alle Artikel in der Datenbank als ArrayListe zurückgeben
     */
    @Test
    public void getAllArticlesInShoppingCartTableTest() {

        assertEquals(shoppingCartArticles, shoppingCartDatabase.getAllArticlesInShoppingCartTable());
    }

    /**
     * Testet das ändern von Mengen einzelner Artikel
     */
//    @Test
//    public void changeAmountOfArticleInShoppingCartTest() {
//
//        // Testen der Ausgangsmnenge des Artikels
//        assertEquals(2, shoppingCartDatabase.getAmountOfArticleInShoppingCart(articleId1));
//
//        // Erhöhen der Menge des Artikels um 1
//        shoppingCartDatabase.increaseAmountOfArticleInShoppingCart(articleId1);
//
//        assertEquals(3, shoppingCartDatabase.getAmountOfArticleInShoppingCart(articleId1));
//
//        // Erhöhen der Menge des Artikels um 1
//        shoppingCartDatabase.increaseAmountOfArticleInShoppingCart(articleId1);
//
//        assertEquals(4, shoppingCartDatabase.getAmountOfArticleInShoppingCart(articleId1));
//
//        // Mindern der Menge des Artikles um 1
//        shoppingCartDatabase.decreseAmountOfArticleInShoppingCart(articleId1);
//
//        assertEquals(3, shoppingCartDatabase.getAmountOfArticleInShoppingCart(articleId1));
//
//        // Mindern der Menge des Artikles um 1
//        shoppingCartDatabase.decreseAmountOfArticleInShoppingCart(articleId1);
//
//        assertEquals(2, shoppingCartDatabase.getAmountOfArticleInShoppingCart(articleId1));
//
//        setUp();
//    }

    /**
     * Alle Artikel aus der Datenbank löschen
     */
    @After
    public void teardown() {

        // Alle Artikel der ArrayListe löschen
        for(int i = 0; i < shoppingCartArticles.size(); i++) {

            shoppingCartArticles.remove(i);
        }

        // Alle Artikel in der Warenkorb TAbelle löschen
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId1);
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId2);
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId3);
        shoppingCartDatabase.removeArticleFromShoppingCart(articleId4);
    }

}
