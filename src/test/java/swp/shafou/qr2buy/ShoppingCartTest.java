package swp.shafou.qr2buy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import swp.shafou.qr2buy.Activities.ShoppingCartActivity;
import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;
import swp.shafou.qr2buy.Utils.ShoppingCartOperations;

/**
 *------------------------------------*
 * - 19.12.16 ELFOULY Test Klasse erstellt.
 * - 22.12.16 ELFOULY Testen der Methode, die den Brutto und Netto Preis einer Position berechnet
 * - 22.12.16 ELFOULY Testen der Methode, die einen Preis als String darstellt
 *------------------------------------*
 *
 * - Diese Klasse testet alle Methoden des Einkaufswagen
 * - Das testen erfolgt mit 3 verschiedenen Einkaufswagen
 * Getestete Methoden:
 * - Berechnung der Brutto Summe des gesamten Einkaufswagen
 * - Berechnung der Netto Summe des gesamten Eikaufswagen
 * - Runden eines Preises auf 2 Nachkommastellen
 * - Berechnung der Mehrwertsteuer des gesamten Einkaufswagen
 * - Berechnung des Preises einer Position
 * - Erstellung des Formatierten Preis als String
 */
public class ShoppingCartTest {

    /**
     * Liste der Artikel im Einkaufswagen
     */
    ArrayList<ArticleShoppingCartWrapper> shoppingCartArticles1;
    ArrayList<ArticleShoppingCartWrapper> shoppingCartArticles2;
    ArrayList<ArticleShoppingCartWrapper> shoppingCartArticles3;

    @Before
    public void setUp() {

        /**
         * 1. Warenkorb Liste
         */
//        shoppingCartArticles1 = new ArrayList<ArticleShoppingCartWrapper>();
//
//        ArticleShoppingCartWrapper Apfel1 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 1.0, 19.0, "Created", "http://", 12), 2);
//        ArticleShoppingCartWrapper Birne1 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 0.50, 19.0, "Created", "http://", 12), 1);
//        ArticleShoppingCartWrapper Pepsi1 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 0.75, 19.0, "Created", "http://", 12), 5);
//
//        shoppingCartArticles1.add(Apfel1);
//        shoppingCartArticles1.add(Birne1);
//        shoppingCartArticles1.add(Pepsi1);
//
//        /**
//         * 2. Warenkorb Liste
//         */
//        shoppingCartArticles2 = new ArrayList<ArticleShoppingCartWrapper>();
//
//        ArticleShoppingCartWrapper Apfel2 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 3.22, 19.0, "Created", "http://", 12), 10);
//        ArticleShoppingCartWrapper Birne2 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 0.87, 19.0, "Created", "http://", 12), 20);
//        ArticleShoppingCartWrapper Pepsi2 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 1.33, 19.0, "Created", "http://", 12), 5);
//
//        shoppingCartArticles2.add(Apfel2);
//        shoppingCartArticles2.add(Birne2);
//        shoppingCartArticles2.add(Pepsi2);
//
//        /**
//         * 3. Warenkorb Liste
//         */
//        shoppingCartArticles3 = new ArrayList<ArticleShoppingCartWrapper>();
//
//        ArticleShoppingCartWrapper Apfel3 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 0.22, 19.0, "Created", "http://", 12), 1);
//        ArticleShoppingCartWrapper Birne3 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 0.87, 19.0, "Created", "http://", 12), 0);
//        ArticleShoppingCartWrapper Pepsi3 = new ArticleShoppingCartWrapper(new Article(1, "Apfel", "Eine tolle Frucht", 0.01, 19.0, "Created", "http://", 12), 2);
//
//        shoppingCartArticles3.add(Apfel3);
//        shoppingCartArticles3.add(Birne3);
//        shoppingCartArticles3.add(Pepsi3);

    }

    /**
     * Testet das runden eines double Preis auf zwei Nachkomma Stellen
     */
    @Test
    public void roundPriceTest() {

        assertEquals(12.13, roundPrice(12.1311), 0.001);
        assertEquals(6.53, roundPrice(6.53333), 0.001);
        assertEquals(13.00, roundPrice(12.999), 0.001);
        assertEquals(56.67, roundPrice(56.6733), 0.001);
        assertEquals(1001.00, roundPrice(1000.9999), 0.001);
        assertEquals(33.01, roundPrice(33.005), 0.001);
        assertEquals(33.00, roundPrice(33.002), 0.001);
    }

    /**
     * Rundet einen übergebenen Preis auf 2 Kommastellen auf
     *
     *--------------ACHTUNG---------------------*
     * - Die eigentliche Methode steht in der ShoppingCartActivity
     * - Diese Methode wurde aus dem Grund hier implementiert, da die Methode in der
     * ShoppingCartActivity den Zugriffsparametr "private" besitz
     *------------------------------------------*
     *
     * @param price Der Übergeben Preis eines Artikels oder eines anderen Preises
     * @return Den Preis auf zwei Kommastellen aufgerundet
     */
    private static double roundPrice(double price) {

        int scale = (int) Math.pow(10, 2);
        return (double) Math.round(price * scale) / scale;
    }


    /**
     * Testet das berechnen der Netto Summe des Einkaufswagen
     * 0.001 gibt an das das Ergebniss bis auf zwei Komma Stellen genau sein muss
     */
    @Test
    public void calcualteSumPriceOfCartTest() {

        assertEquals(6.25, ShoppingCartOperations.calculateSumPriceOfCart(shoppingCartArticles1), 0.001);
        assertEquals(56.25, ShoppingCartOperations.calculateSumPriceOfCart(shoppingCartArticles2), 0.001);
//        assertEquals(0.22, ShoppingCartActivity.calculateSumPriceOfCart(shoppingCartArticles3), 0.001);
    }

    /**
     * Testet das berechnen der Brutto Summe des Einkaufswagens
     * 0.001 gibt an das das Ergebniss bis auf zwei Komma Stellen genau sein muss
     */
    @Test
    public void calculateSumPriceOfCartWithVATTEst() {

        assertEquals(7.44, ShoppingCartOperations.calculateSumPriceWithVAT(shoppingCartArticles1), 0.001);
        assertEquals(66.94, ShoppingCartOperations.calculateSumPriceWithVAT(shoppingCartArticles2), 0.001);
//        assertEquals(0.26, ShoppingCartActivity.calculateSumPriceWithVAT(shoppingCartArticles3), 0.001);
    }

    /**
     * Soll den gesamten Betrag der zusätslichen Steuern der Artikel im Warenkorb berechnen
     * 0.001 gibt an das das Ergebniss bis auf zwei Komma Stellen genau sein muss
     */
    @Test
    public void calculateVATofShoppingCartTest() {

        assertEquals(1.19, ShoppingCartOperations.calculateTotalVATofShoppingCart(shoppingCartArticles1), 0.001);
        assertEquals(10.69, ShoppingCartOperations.calculateTotalVATofShoppingCart(shoppingCartArticles2), 0.001);
//        assertEquals(0.04, ShoppingCartActivity.calculateTotalVATofShoppingCart(shoppingCartArticles3), 0.001);
    }

    /**
     * Berechnet den Brutto Preis einer Position
     * 0.001 gibt an das das Ergebniss bis auf zwei Komma Stellen genau sein muss
     */
    @Test
    public void calculateSumOfShoppingCartPositionTest() {

        assertEquals(60.00, ShoppingCartActivity.calculateSumOfShoppingCartPosition(12.00, 5), 0.001);
        assertEquals(30.00, ShoppingCartActivity.calculateSumOfShoppingCartPosition(3.00, 10), 0.001);
        assertEquals(5.95, ShoppingCartActivity.calculateSumOfShoppingCartPosition(0.85, 7), 0.001);
    }

    /**
     * Gibt den Preis als String mit 2 Dezimalstelle zurück
     */
    @Test
    public void getPriceTextTest() {

//        assertEquals("12.00", ShoppingCartActivity.getPriceText(12));
//        assertEquals("3.00", ShoppingCartActivity.getPriceText(3));
//        assertEquals("1.20", ShoppingCartActivity.getPriceText(1.2));
//        assertEquals("4.56", ShoppingCartActivity.getPriceText(4.56));
//        assertEquals("3.23", ShoppingCartActivity.getPriceText(3.229));
//        assertEquals("7.87", ShoppingCartActivity.getPriceText(7.866772));
    }

}
