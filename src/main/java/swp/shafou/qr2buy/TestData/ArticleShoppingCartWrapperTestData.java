package swp.shafou.qr2buy.TestData;

import java.util.ArrayList;

import swp.shafou.qr2buy.POJO.Article;
import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;
import swp.shafou.qr2buy.POJO.ArticleTest;

/**
 *------------------------------------*
 * - Klasse erstellt. 21.12.16 ELFOULY
 *------------------------------------*
 *
 * - Diese Klasse beeinhaltet ArtikcleWrapper Test Objekte, die beim testen der Warenkorb Funktionen,
 * genutzt werden können.
 *
 * -----------WICHTIG------------------*
 * - Die Artikel Schlüssel 2.999.980 - 3.000.000 werden zum debuggen benutzt und dürfen deshalb im
 * Produktiv System nicht als Artikel Schlüssel eingesetzt werden.
 * ------------------------------------*
 */
public class ArticleShoppingCartWrapperTestData {

    public static ArrayList<ArticleShoppingCartWrapper> articleShoppingCartWrappersTestList;

    static {

        byte[] eins = null;
        byte[] zwei = null;
        byte[] drei= null;
        byte[] vier= null;
        byte[] funf= null;
        byte[] sechs= null;

        articleShoppingCartWrappersTestList = new ArrayList<>();

        ArticleShoppingCartWrapper Snicker = new ArticleShoppingCartWrapper(ArticleTestData.articleTestList.get(0) , 2);
        ArticleShoppingCartWrapper CocaCola = new ArticleShoppingCartWrapper(ArticleTestData.articleTestList.get(1), 1);
        ArticleShoppingCartWrapper Fanta = new ArticleShoppingCartWrapper(ArticleTestData.articleTestList.get(2), 5);
        ArticleShoppingCartWrapper Chips = new ArticleShoppingCartWrapper(ArticleTestData.articleTestList.get(3), 5);
        ArticleShoppingCartWrapper Sprite = new ArticleShoppingCartWrapper(ArticleTestData.articleTestList.get(4), 5);
        ArticleShoppingCartWrapper Mars = new ArticleShoppingCartWrapper(ArticleTestData.articleTestList.get(5), 5);

        articleShoppingCartWrappersTestList.add(Snicker);
        articleShoppingCartWrappersTestList.add(CocaCola);
        articleShoppingCartWrappersTestList.add(Fanta);
        articleShoppingCartWrappersTestList.add(Chips);
        articleShoppingCartWrappersTestList.add(Sprite);
        articleShoppingCartWrappersTestList.add(Mars);
    }

}
