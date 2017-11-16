package swp.shafou.qr2buy.TestData;

import java.util.ArrayList;

import swp.shafou.qr2buy.Activities.HomeActivity;
import swp.shafou.qr2buy.POJO.Article;


/**
 *------------------------------------*
 * - Klasse erstellt. 31.01.17 ELFOULY
 *------------------------------------*
 *
 * - Diese Klasse beeinhaltet Article Test Objekte.
 */
public class ArticleTestData {

    public static ArrayList<Article> articleTestList;

    static byte[] articleTestDataFirstImage;

    static byte[] articleTestDataSecondImage;

    static byte[] articleTestDataThirdImage;

    static byte[] articleTestDataForthImage;

    static byte[] articleTestDataFifthtImage;

    static byte[] articleTestDataSixthImage;

    static {

        articleTestDataFirstImage = HomeActivity.snickerArray;
        articleTestDataSecondImage = HomeActivity.cocacolaArray;
        articleTestDataThirdImage = HomeActivity.fantaArray;
        articleTestDataForthImage = HomeActivity.chipsArray;
        articleTestDataFifthtImage = HomeActivity.spriteArray;
        articleTestDataSixthImage = HomeActivity.marsArray;

        articleTestList = new ArrayList<>();

        Article Snicker = new Article(2999980, "Snicker","Dieser Schokoriegel schmeckt gut und ist gleichzeitig ungesund.", 1.0, 19.0, "Lecker!", articleTestDataFirstImage, 12);
        Article CocaCola = new Article(2999981, "Coca Cola","Wussten Sie das in einem Liter Coca Cola 25 Zucker Stückchen sind?", 1.0, 19.0, "Lecker!", articleTestDataSecondImage, 12);
        Article Fanta = new Article(2999982, "Fanta","ACHTUNG: Beeihnaltet keine echten Orangen!", 1.0, 19.0, "Lecker!", articleTestDataThirdImage, 12);
        Article Chips = new Article(2999983, "Chips","Warum besteht eine Tüte Chips eigentlich aus 50% Luft?", 1.0, 19.0, "Lecker!", articleTestDataForthImage, 12);
        Article Sprite = new Article(2999984, "Sprite","Zucker Wasser, das einem den erfrischenden Einstieg in den Tag gibt.", 1.0, 19.0, "Lecker!", articleTestDataFifthtImage, 12);
        Article Mars = new Article(2999985, "Mars","Benannt nach dem 4 Planet unseres Sonnensystem.", 1.0, 19.0, "Lecker!", articleTestDataSixthImage, 12);

        articleTestList.add(Snicker);
        articleTestList.add(CocaCola);
        articleTestList.add(Fanta);
        articleTestList.add(Chips);
        articleTestList.add(Sprite);
        articleTestList.add(Mars);
    }

}
