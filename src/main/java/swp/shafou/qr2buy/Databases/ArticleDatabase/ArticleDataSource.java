package swp.shafou.qr2buy.Databases.ArticleDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.List;

import swp.shafou.qr2buy.POJO.Article;

/**
 *------------------------------------*
 * - 31.01.17 ELFOULY Klasse erstellt.
 * - 02.02.17 ELFOULY Methode zum schreiben einer Artikel Liste in die Datenbank Tabelle
 *------------------------------------*
 *
 * Artikel Data Source Klasse
 * - Die restliche Applikation kommuniziert mit dieser Klasse um:
 *      - Datenbank zu öffnen
 *      - Datenbank zu schließen
 *      - In die Datenbank schreiben
 *      - Daten aus der Datenbank laden
 */
public class ArticleDataSource {

    // Übergebener Kontext
    private Context context;

    // Verwaltet die Artikel Datenbank Tabelle
    private static SQLiteDatabase articleDataSource;

    // Artikel Datenbank Tabelle Helfer Klasse
    private SQLiteOpenHelper sqLiteOpenHelper;

    // SQL Statement Ende Zeichen
    private static final String SQL_END_SYMBOL = ";";

    /**
     * SQL Statement: Alle Artikel in der Datenbank Tabelle löschen
     */
    private static final String REMOVE_ALL_ARTICLES_SQL_STMT = "DELETE FROM "
            + ArticleDatabaseHelper.TABLE_NAME;

    /**
     * SQL Statement: Ist der Artikel in der Datenbank Tabelle
     */
    private static final String IS_ARTICLE_IN_TABLE_SQL_STMT = "SELECT count(*) FROM "
            + ArticleDatabaseHelper.TABLE_NAME
            + " WHERE "
            + ArticleDatabaseHelper.ARTICLE_ID_COLUMN
            + " = ";

    /**
     * SQL Statement: Gib den Artikel mit der übergebenen Artikel Id zurück
     */
    private static final String GET_ARTICLE_FROM_TABLE_SQL_STMT = "SELECT * FROM "
            + ArticleDatabaseHelper.TABLE_NAME
            + " WHERE "
            + ArticleDatabaseHelper.ARTICLE_ID_COLUMN
            + " = ";

    private static final String REMOVE_ARTICLE_FROM_TABLE_SQL_STMT = "DELETE FROM "
            + ArticleDatabaseHelper.TABLE_NAME
            + " WHERE "
            + ArticleDatabaseHelper.ARTICLE_ID_COLUMN
            + " = ";

    public ArticleDataSource(Context context) {

        this.context = context;
        sqLiteOpenHelper = new ArticleDatabaseHelper(context);
        articleDataSource = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * Öffnet die Datenbank
     */
    public void open() {

        articleDataSource = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * Schließt die Datenbank
     */
    public void close() {

        sqLiteOpenHelper.close();
    }

    /**
     * Löscht alle Artikel in der Artikel DAtenbak Tabelle
     */
    public void removeAllArticlesInTable() {

        open();

        articleDataSource.execSQL(REMOVE_ALL_ARTICLES_SQL_STMT + SQL_END_SYMBOL);

        close();
    }

    /**
     * Schreibt einen Wrapper Artikel in die Warenkorb Datenbank Tabelle
     * Falls der Artikel bereits in der Tabelle besteht wird dieser überschrieben
     *
     * @param article Übergebner Artikel der in die Datenbank Tabelle geschrieben werden soll.
     */
    private boolean insertArticleToArticleTable(Article article) {

        boolean isArticleInserted = false;

        /**
         * Falls der Artikel bereits existiert, wird dieser überschrieben um die Datenkonsitenz zu
         * gewährleisten.
         */
        if(isArticleInTable(article.getArticleID())) {

            this.removeArticleFromArticleTable(article.getArticleID());
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(ArticleDatabaseHelper.ARTICLE_ID_COLUMN
                , article.getArticleID());
        contentValues.put(ArticleDatabaseHelper.ARTICLE_NAME_COLUMN
                , article.getArticleName());
        contentValues.put(ArticleDatabaseHelper.ARTICLE_PRICE_COLUMN
                , article.getArticlePrice());
        contentValues.put(ArticleDatabaseHelper.ARTICLE_VAT_COLUMN
                , article.getVat());
        contentValues.put(ArticleDatabaseHelper.ARTICLE_DESCRIPTION_COLUMN
                , article.getArticleDescription());
        contentValues.put(ArticleDatabaseHelper.ARTICLE_IMAGE_PATH
                , article.getArticleImagePath());
        contentValues.put(ArticleDatabaseHelper.ARTICLE_STATUS_COLUMN
                , article.getArticleStatus());

        open();

        /**
         * Gibt die Id der neu erstellen Reihe an.
         * Ist -1 falls ein Fehler aufgetreten ist.
         */
        long idOfCreatedRow = articleDataSource.insert(ArticleDatabaseHelper.TABLE_NAME, null, contentValues);

        close();

        if(idOfCreatedRow != -1) {

            isArticleInserted = true;
        }

        return isArticleInserted;
    }

    /**
     * Gibt an ob sich der übergebene Artikel in der Datenbanktablle befindet
     *
     * @param articleId Die Id eines Artikels
     * @return <code>true</code> Falls der Artikel in der Tabelle existiert
     */
    public boolean isArticleInTable(int articleId) {

        boolean articleIsInTable = false;

        open();

        Cursor cursor = articleDataSource.rawQuery(IS_ARTICLE_IN_TABLE_SQL_STMT
                + articleId
                + SQL_END_SYMBOL, null);

        cursor.moveToFirst();

        int cursorSize = cursor.getInt(0);

        if(cursorSize > 0) {

            articleIsInTable = true;
        }

        cursor.close();
        close();

        return articleIsInTable;
    }

    /**
     * Gibt zu einer Artikel Id, das zugehörige Artikel Objekt aus der Datenbank Tabelle zurück
     *
     *-----------WICHTIG------------------*
     * - Falls kein Artikel gefunden wurde, wird ein Artikel mit null Attributen zurückgegeben!
     *------------------------------------*
     *
     * @param articleId Eine Artikel ID
     * @return Ein Artikel der zugehörigen Artikel Id
     */
    public Article getArticleFromTable(int articleId) {

        Article article = new Article();

        open();

        Cursor cursor = articleDataSource.rawQuery(GET_ARTICLE_FROM_TABLE_SQL_STMT
                + articleId
                + SQL_END_SYMBOL, null);

        cursor.moveToFirst();

        article.setArticleImagePath(cursor.getBlob(cursor.getColumnIndex(ArticleDatabaseHelper.ARTICLE_IMAGE_PATH)));
        article.setArticleDescription(cursor.getString(cursor.getColumnIndex(ArticleDatabaseHelper.ARTICLE_DESCRIPTION_COLUMN)));
        article.setArticleID(cursor.getInt(cursor.getColumnIndex(ArticleDatabaseHelper.ARTICLE_ID_COLUMN)));
        article.setArticleName(cursor.getString(cursor.getColumnIndex(ArticleDatabaseHelper.ARTICLE_NAME_COLUMN)));
        article.setArticlePrice(cursor.getDouble(cursor.getColumnIndex(ArticleDatabaseHelper.ARTICLE_PRICE_COLUMN)));
        article.setArticleStatus(cursor.getString(cursor.getColumnIndex(ArticleDatabaseHelper.ARTICLE_STATUS_COLUMN)));
        article.setVat(cursor.getDouble(cursor.getColumnIndex(ArticleDatabaseHelper.ARTICLE_VAT_COLUMN)));

        close();
        cursor.close();

        return article;
    }

    /**
     * Schreibt eine Artikel Liste in die Datenbank Tabelle
     *
     * @return <code>true</code> Falls alle Artikel erfolgreich in die Datenbank Tabelle eingefügt
     * werden konnte oder die Liste == null ist.
     */
    public boolean insertArticleListToArticleTable(List<Article> articleList) {

        boolean isArticleListInserted = true;

        if(articleList == null) {

            return true;
        }

        /**
         * Schreibt die Artikelliste ind die Datenbank Tabelle solange insertArticleToArtilceTable
         * <code>true</code> zurückgibt.
         */
        for(int i = 0; i < articleList.size() && isArticleListInserted; i++) {

            Article article = articleList.get(i);

            isArticleListInserted = insertArticleToArticleTable(article);
        }

        return isArticleListInserted;
    }

    /**
     * Löscht einen Artikel anhand der übergebenen Artikel Id aus der Datenbank Tabelle.
     *
     * @param articleId Artikel Id eines Artikels
     * @return <code>true</code> falls erfolgreich gelöscht werden konnte.
     */
    private boolean removeArticleFromArticleTable(int articleId) {

        boolean isArticleRemoved;

        open();

        try {
            articleDataSource.execSQL(REMOVE_ARTICLE_FROM_TABLE_SQL_STMT
                    + articleId
                    + SQL_END_SYMBOL);

            isArticleRemoved = true;

        } catch(SQLException sqlException) {

            isArticleRemoved = false;
        }

        close();

        return isArticleRemoved;
    }
}
