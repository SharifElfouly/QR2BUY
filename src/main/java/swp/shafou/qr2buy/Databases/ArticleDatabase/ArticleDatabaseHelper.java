package swp.shafou.qr2buy.Databases.ArticleDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *------------------------------------*
 * - Klasse erstellt. 31.01.17 ELFOULY
 *------------------------------------*
 *
 * Datenbak Tabelle Helfer Klasse.
 * Beeinhaltet den Aufbau der Artikel Datenbank Tabelle
 */
class ArticleDatabaseHelper extends SQLiteOpenHelper {

    /*
     * Datenbank Name
     */
    private static final String DATABASE_NAME = "Articles.db";

    /*
     * Datenbank Version
     * Falls Veränderungen an der Datenbank gamacht werden, muss die Version um 1 erhöht werden
     */
    private static final int DATABASE_VERSION = 3;

    /*
     * Tabellen Name
     */
    static final String TABLE_NAME = "articles";

    /*
     * Tabellen Spalten
     */
    static final String ARTICLE_ID_COLUMN = "ID";
    static final String ARTICLE_NAME_COLUMN = "Name";
    static final String ARTICLE_DESCRIPTION_COLUMN = "Description";
    static final String ARTICLE_PRICE_COLUMN = "Price";
    static final String ARTICLE_VAT_COLUMN = "VAT";
    static final String ARTICLE_STATUS_COLUMN = "Status";
    static final String ARTICLE_IMAGE_PATH = "Image_Path";

    /*
    * Create Statement
    */
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ARTICLE_ID_COLUMN + " INTEGER PRIMARY KEY, " +
                    ARTICLE_NAME_COLUMN + " TEXT, " +
                    ARTICLE_PRICE_COLUMN + " REAL, " +
                    ARTICLE_VAT_COLUMN + " REAL, " +
                    ARTICLE_DESCRIPTION_COLUMN + " TEXT, " +
                    ARTICLE_IMAGE_PATH + " BLOB, " +
                    ARTICLE_STATUS_COLUMN + " TEXT);";

    /**
     * SQL Statemnt um Tabelle zu löschen
     */
    private static final String DELETE_TABLE = "DROP TABLE " + TABLE_NAME;

    ArticleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
