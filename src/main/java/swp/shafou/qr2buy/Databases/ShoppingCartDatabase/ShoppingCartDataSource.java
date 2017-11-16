package swp.shafou.qr2buy.Databases.ShoppingCartDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import swp.shafou.qr2buy.POJO.Article;
import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;
import swp.shafou.qr2buy.Utils.ShoppingCartOperations;

/**
 * ------------------------------------*
 * - 21.12.16 ELFOULY Klasse erstellt.
 * - 23.12.16 ELFOULY Berechnung der Artikelpositionen des Warenkorbs.
 * ------------------------------------*
 * <p>
 * Warenkorb Data Source Klasse
 * - Die restliche Applikation kommuniziert mit dieser Klasse um:
 * - Datenbank zu öffnen
 * - Datenbank zu schließen
 * - In die Datenbank schreiben
 * - Daten aus der Datenbank laden
 */
public class ShoppingCartDataSource {

    // Verwaltet die Warenkorb Datenbank TAbelle
    private static SQLiteDatabase shoppingCartDataSource;

    // Warenkorb Tabelle Helfer Klasse
    private SQLiteOpenHelper sqLiteOpenHelper;

    // SQL Statement Ende Zeichen
    private static final String SQL_END_SYMBOL = ";";

    /**
     * SQL STATEMENT: Entferne alle Warenkorb Datenbank Tabellen Einträge.
     */
    private static final String REMOVE_ALL_FROM_SHOPPING_CART_TABLE_SQL_STMT = "DELETE FROM "
            + ShoppingCartDatabaseHelper.TABLE_NAME
            + SQL_END_SYMBOL;

    /**
     * SQL STATEMENT: Gibt an, ob der sich die Artikel Id in der Warenkorb Datenabnak Tabelle
     * befindet.
     */
    private static final String IS_ARTICLE_IN_SHOPPING_CART_TABLE_SQL_STMT = "SELECT count(*) FROM "
            + ShoppingCartDatabaseHelper.TABLE_NAME
            + " WHERE "
            + ShoppingCartDatabaseHelper.ARTICLE_ID_COLUMN
            + " = ";

    /**
     * SQL Statement: Gibt die Anzahl der Warenkorb Reihen an
     */
    private static final String GET_NUMBER_OF_SHOPPING_CART_ITEMS_SQL_STMT = "SELECT * FROM "
            + ShoppingCartDatabaseHelper.TABLE_NAME + SQL_END_SYMBOL;

    private static final String REMOVE_ARTICLE_FROM_SHOPPING_CART_TABLE_SQL_STMT = "DELETE FROM "
            + ShoppingCartDatabaseHelper.TABLE_NAME
            + " WHERE "
            + ShoppingCartDatabaseHelper.ARTICLE_ID_COLUMN
            + " = ";

    public ShoppingCartDataSource(Context context) {

        sqLiteOpenHelper = new ShoppingCartDatabaseHelper(context);
        shoppingCartDataSource = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * Öffnet die Datenbank
     */
    public void open() {

        shoppingCartDataSource = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * Schließt die Datenbank
     */
    public void close() {

        sqLiteOpenHelper.close();
    }

    /**
     * Entfernt alle Artikel aud der Warenkorb Datenbank Tabelle
     *
     * @return <code>true</code> Falls der Lösch Vorgang erfolgreich war
     */
    public boolean removeAllArticlesInShoppingCart() {

        boolean areAllArticlesRemoved;

        open();

        try {

            shoppingCartDataSource.execSQL(REMOVE_ALL_FROM_SHOPPING_CART_TABLE_SQL_STMT);
            areAllArticlesRemoved = true;
        } catch(SQLException sqlException) {

            areAllArticlesRemoved = false;
        }

        close();

        return areAllArticlesRemoved;
    }

    /**
     * Schreibt einen Wrapper Artikel in die Warenkorb Datenbank Tabelle
     *
     * @param articleShoppingCartWrapper Artikel Warenkorb Wrapper Objekt das in Datenbank Tabelle
     * geschrieben werden soll.
     */
    public boolean insertArticleToShoppinCartTable(ArticleShoppingCartWrapper articleShoppingCartWrapper) {

        boolean isArticleInserted = false;

        open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_ID_COLUMN
                , articleShoppingCartWrapper.getArticle().getArticleID());
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_NAME_COLUMN
                , articleShoppingCartWrapper.getArticle().getArticleName());
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_PRICE_COLUMN
                , articleShoppingCartWrapper.getArticle().getArticlePrice());
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_VAT_COLUMN
                , articleShoppingCartWrapper.getArticle().getVat());
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_DESCRIPTION_COULMN
                , articleShoppingCartWrapper.getArticle().getArticleDescription());
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_IMAGE_COLUMN
                , articleShoppingCartWrapper.getArticle().getArticleImagePath());
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_STATUS_COLUMN
                , articleShoppingCartWrapper.getArticle().getArticleStatus());
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_CREATED_BY_COLUMN
                , articleShoppingCartWrapper.getArticle().getCreatedBy());
        contentValues.put(ShoppingCartDatabaseHelper.ARTICLE_AMOUNT_COLUMN
                , articleShoppingCartWrapper.getMenge());

        /**
         * Gibt die Id der neu erstellen Reihe an.
         * Ist -1 falls ein Fehler aufgetreten ist.
         */
        long idOfCreatedRow = shoppingCartDataSource.insert(ShoppingCartDatabaseHelper.TABLE_NAME, null, contentValues);

        if(idOfCreatedRow != -1) {

            isArticleInserted = true;
        }

        close();

        return isArticleInserted;
    }

    /**
     * - Diese Methode gibt alle Artikel in der Warenkorb Tabelle der
     * Datenbank in einer Liste von ArticleShoppingCartWrapper Objekten zurück.
     * - Falls die Tabelle leer ist, wird eine leere Liste zurück gegeben.
     *
     * @return Eine Liste von articleWrapper Objekten
     */
    public ArrayList<ArticleShoppingCartWrapper> getAllArticlesInShoppingCartTable() {

        ArrayList<ArticleShoppingCartWrapper> articleShoppingCartWrappers = new ArrayList<>();

        open();

        Cursor cursor = shoppingCartDataSource.query(ShoppingCartDatabaseHelper.TABLE_NAME,
                ShoppingCartDatabaseHelper.ALL_COLUMNS, null, null, null, null, null);

        while (cursor.moveToNext()) {

            ArticleShoppingCartWrapper articleShoppingCartWrapper = new ArticleShoppingCartWrapper();
            Article article = new Article();

            article.setArticleID(cursor.getInt(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_ID_COLUMN)));
            article.setArticleName(cursor.getString(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_NAME_COLUMN)));
            article.setArticlePrice(cursor.getDouble(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_PRICE_COLUMN)));
            article.setArticleDescription(cursor.getString(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_DESCRIPTION_COULMN)));
            article.setVat(cursor.getDouble(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_VAT_COLUMN)));
            article.setArticleImagePath(cursor.getBlob(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_IMAGE_COLUMN)));
            article.setArticleStatus(cursor.getString(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_STATUS_COLUMN)));
            article.setCreatedBy(cursor.getInt(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_CREATED_BY_COLUMN)));

            articleShoppingCartWrapper.setArticle(article);
            articleShoppingCartWrapper.setMenge(cursor.getInt(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_AMOUNT_COLUMN)));

            articleShoppingCartWrappers.add(articleShoppingCartWrapper);
        }

        cursor.close();
        close();

        return articleShoppingCartWrappers;
    }

    /**
     * Aktualisiert die Menge eines Artikels in der Einkaufswagen Datenbank Tabelle
     *
     * @param articleId     Artikel ID eines Artikels
     * @param updatedAmount Die aktualisierte Menege des Artikels
     */
    public boolean updateAmountOfArticleInShoppingCart(int articleId, int updatedAmount) {

        boolean isAmountOfArticleUpdated;

        open();

        try{

            shoppingCartDataSource.execSQL("UPDATE " + ShoppingCartDatabaseHelper.TABLE_NAME
                    + " SET "
                    + ShoppingCartDatabaseHelper.ARTICLE_AMOUNT_COLUMN
                    + " = "
                    + updatedAmount
                    + " WHERE " + ShoppingCartDatabaseHelper.ARTICLE_ID_COLUMN
                    + " = " + articleId);

            isAmountOfArticleUpdated = true;

        } catch (SQLException sqlException){

            isAmountOfArticleUpdated = false;
        }


        close();

        return isAmountOfArticleUpdated;
    }

    /**
     * Gibt die Menge eines Artikels im Warenkorb zurück
     *
     * ------------WICHTIG------------------*
     * - Es wird davon ausgegangen, das die Datenbank TAbelle nur konsistente Daten enthält. Das
     * heißt in der Datenbank Tabelle existiert nur ein Artikel mit der übergebene Artikel ID
     * -------------------------------------*
     *
     * @param articleId Die übergebene Artikel Id eines Artikel
     * @return Falls der Artikel sich nicht in der Datenbank Atbelle befindet, wird -1
     * zurückgegeben, sonst wird die Menge der Warenkorb Position zurückgegeben.
     */
    public int getAmountOfArticleInShoppingCart(int articleId) {

        int currentAmount;

        open();

        Cursor cursor = shoppingCartDataSource.rawQuery("SELECT "
                + ShoppingCartDatabaseHelper.ARTICLE_AMOUNT_COLUMN
                + " FROM "
                + ShoppingCartDatabaseHelper.TABLE_NAME
                + " WHERE "
                + ShoppingCartDatabaseHelper.ARTICLE_ID_COLUMN
                + " = "
                + articleId, null);

        /**
         * Falls ein Artikel gefunden wurde
         */
        if(cursor.getCount() != 0) {

            cursor.moveToNext();

            currentAmount = cursor.getInt(
                    cursor.getColumnIndex(ShoppingCartDatabaseHelper.ARTICLE_AMOUNT_COLUMN));

        } else {

            currentAmount = -1;
        }

        cursor.close();
        close();

        return currentAmount;
    }

    /**
     * Testet ob ein übergebener Schlüssel eines Artikels in der WArenkorb TAbelle existiert
     *
     * @param articleId Der übergebene Schlüssel eines Artikel
     * @return <code>true</code> Falls es den Artikel in der Warenkorb Datenbank bereits gibt
     */
    public boolean isArticleinShoppingCart(int articleId) {

        boolean isArticleInShoppingCart = false;

        open();

        Cursor cursor = shoppingCartDataSource.rawQuery(IS_ARTICLE_IN_SHOPPING_CART_TABLE_SQL_STMT
                + articleId
                + SQL_END_SYMBOL, null);

        if (cursor.getCount() != 0) {

            isArticleInShoppingCart = true;
        }

        cursor.close();
        close();

        return isArticleInShoppingCart;
    }

    /**
     * Gibt die Anzahl der Artikel in der Einklaufswagen Tabelle zurück
     *
     * @return Anzahl der Artikel im Warenkorb
     */
    public int getNumberOfShoppingCartItems() {

        int numberOfArticles;

        open();

        Cursor cursor = shoppingCartDataSource.rawQuery(GET_NUMBER_OF_SHOPPING_CART_ITEMS_SQL_STMT
                , null);

        // Get count gib an, wie viele Reihen gefunden wurde
        numberOfArticles = cursor.getCount();

        cursor.close();
        close();

        return numberOfArticles;
    }

    /**
     * Löscht einen Artikel anhand dessen ID aus der Warenkorb Tabelle
     *
     * @param articleId Artikel Id
     * @return <code>true</code> falls ein Artikel ergolgreich gelöscht werden konnte
     */
    public boolean removeArticleFromShoppingCart(int articleId) {

        boolean isArticleRemoved;

        open();

        try {
            shoppingCartDataSource.execSQL(REMOVE_ARTICLE_FROM_SHOPPING_CART_TABLE_SQL_STMT
                    + articleId
                    + SQL_END_SYMBOL);

            isArticleRemoved = true;
        } catch (SQLiteException sqlException) {

            isArticleRemoved = false;
        }

        close();

        return isArticleRemoved;
    }

    /**
     * Berechnet den Netto Preis der Artikel aus der Warenkorb Tabelle
     *
     * @return Netto Preis der Artikel im WArenkorb
     */
    public double getSumPriceOfArticlesInShoppingCart() {

        open();

        ArrayList<ArticleShoppingCartWrapper> articlesInShoppingCart =
                getAllArticlesInShoppingCartTable();

        close();

        return ShoppingCartOperations.calculateSumPriceOfCart(articlesInShoppingCart);
    }

    /**
     * Berechnet den Bruttopreis der Artikel aus der Warenkorb Tabelle
     *
     * @return Brutto Preis der Artikel im Warenkorb
     */
    public double getGrossPriceOfArticlesInShoppingCart() {

        open();

        ArrayList<ArticleShoppingCartWrapper> articlesInShoppingCart =
                getAllArticlesInShoppingCartTable();

        close();

        return ShoppingCartOperations.calculateSumPriceWithVAT(articlesInShoppingCart);
    }

    /**
     * Berechnet die Mehrwertsteuern der in der Warenkorb Tabelle befindenen Artikel
     *
     * @return Mehrwertsteuer der Artikel im Warenkorb
     */
    public double getTaxesOfArticlesInShoppingCart() {

        open();

        ArrayList<ArticleShoppingCartWrapper> articlesInShoppingCart =
                getAllArticlesInShoppingCartTable();

        close();

        return ShoppingCartOperations.calculateTotalVATofShoppingCart(articlesInShoppingCart);
    }

}
