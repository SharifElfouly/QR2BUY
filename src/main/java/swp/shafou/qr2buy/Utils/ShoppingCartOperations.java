package swp.shafou.qr2buy.Utils;

import java.util.ArrayList;
import java.util.List;

import swp.shafou.qr2buy.Activities.ShoppingCartActivity;
import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;

/**
 * ------------------------------------*
 * - 12.02.17 ELFOULY Klasse erstellt.
 * ------------------------------------*
 *
 * Diese Klasse beeinhaltet statische Methoden, die Berechnungen des Warenkorbs behandeln
 */
public class ShoppingCartOperations {

    /**
     * Berechnet den Netto Preis der gesamten Artikel im Warenkorb.
     * Der Inhalt der Liste besteht aus ArticleShoppingCartWrapper Objekte
     * Die ArticleShoppingCartWrapper Objekte beeinhalten ein Article Objekt und eine
     * zugehörige Menge, die sich ändern kann.
     *
     * @param shoppingCartArticles Eine ArrayListe mit ArticleShoppingCartWrapper Objekten
     * @return Der Netto Gesamtpreis aller Artikel im Warenkorb
     */
    public static double calculateSumPriceOfCart(ArrayList<ArticleShoppingCartWrapper> shoppingCartArticles) {

        double sumPrice = 0.00;

        for(ArticleShoppingCartWrapper articleShoppingCartWrapper : shoppingCartArticles) {
            sumPrice = sumPrice + ((articleShoppingCartWrapper.getArticle().getArticlePrice()) * articleShoppingCartWrapper.getMenge());
        }

        return PriceUtilities.roundPrice(sumPrice);
    }

    /**
     * Berechnet den Brutto Preis der gesamten Artikel im Warenkorb.
     * Der Inhalt der Liste besteht aus ArticleShoppingCartWrapper Objekte
     * Die ArticleShoppingCartWrapper Objekte beeinhalten ein Article Objekt und eine
     * zugehörige Menge, die sich ändern kann.
     *
     * @param shoppingCartArticles Eine ArrayListe mit ArticleShoppingCartWrapper Objekten
     * @return Der Brutto Gesamtpreis aller Artikel im Warenkorb
     */
    public static double calculateSumPriceWithVAT(ArrayList<ArticleShoppingCartWrapper> shoppingCartArticles) {

        double sumPriceWithVAT;
        sumPriceWithVAT = calculateSumPriceOfCart(shoppingCartArticles)
                + calculateTotalVATofShoppingCart(shoppingCartArticles);

        return PriceUtilities.roundPrice(sumPriceWithVAT);
    }

    /**
     * Berechnet den Betrag die Mehrwertsteuern des Warenkorbs
     *
     * @param shoppingCartArticles Liste von Warenkorb Artikel Wrapper Objekte
     * @return Der Betrag der zusätslichen Steuern
     */
    public static double calculateTotalVATofShoppingCart(ArrayList<ArticleShoppingCartWrapper> shoppingCartArticles) {

        double sumVAT = 0.00;
        double sumPrice;

        for(ArticleShoppingCartWrapper articleShoppingCartWrapper : shoppingCartArticles) {

            sumPrice = (articleShoppingCartWrapper.getArticle().getArticlePrice() * articleShoppingCartWrapper.getMenge());
            sumVAT = sumVAT + (sumPrice * (articleShoppingCartWrapper.getArticle().getVat() / 100));
        }

        return PriceUtilities.roundPrice(sumVAT);
    }
}
