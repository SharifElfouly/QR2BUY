package swp.shafou.qr2buy.Utils;


import java.text.DecimalFormat;

/**
 * ------------------------------------*
 * - 11.02.17 ELFOULY Klasse erstellt.
 * ------------------------------------*
 *
 * Diese Klasse beeinhaltet statische Methoden, die sich mit Preis formatierung auseinander setzen
 */
public class PriceUtilities {

    /**
     * Runded und formatiert einen übergebenen Preis
     * Diese Methode formattiert einen Preis so, da dieser immer zwei Dezimalstellen hat.
     * Bsp - 16 --> 16.00
     *     - 3.2 --> 3.20
     *
     * @param price Übergebener Preis
     * @return Formatierter Preis
     */
    public static String getRoundedPriceText(double price) {

        double roundedPrice = roundPrice(price);
        DecimalFormat df = new DecimalFormat("#0.00");

        return df.format(roundPrice(roundedPrice));
    }

    /**
     * Rundet einen übergebenen Preis auf 2 Kommastellen auf
     *
     * @param price Der Übergeben Preis eines Artikels oder eines anderen Preises
     * @return Den Preis auf zwei Kommastellen aufgerundet
     */
    public static double roundPrice(double price) {

        int scale = (int) Math.pow(10, 2);
        return (double) Math.round(price * scale) / scale;
    }

}
