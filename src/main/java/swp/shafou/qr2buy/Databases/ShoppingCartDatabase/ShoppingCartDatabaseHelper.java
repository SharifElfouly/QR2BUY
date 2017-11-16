package swp.shafou.qr2buy.Databases.ShoppingCartDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *------------------------------------*
 * - 21.12.16 ELFOULY Klasse erstellt.
 *------------------------------------*
 *
 * Warenkorb Datenbank Helper
 * - Diese Klasse dient dazu die Datenabank, um Artikel im Warenkorb zu verwalten, zu verwalten
 * - Die Datenbank enthält eine Tabelle mit folgenden Attributen
 *      - Artikel Attribute (siehe Klasse Article)
 *      - Die Menge eines Artikels
 */
class ShoppingCartDatabaseHelper extends SQLiteOpenHelper{

    /*
     * Datenbank Name
     */
    private static final String DATABASE_NAME = "ShoppinCartArticles.db";

    /*
     * Datenbank Version
     * Falls Veränderungen an der Datenbank gamacht werden, muss die Version um 1 erhöht werden
     */
    private static final int DATABASE_VERSION = 5;

    /*
     * Tabellen Name
     */
    static final String TABLE_NAME = "shoppingCartArticles";

    /*
     * Tabellen Spalten
     */
    static final String ARTICLE_ID_COLUMN = "ID";
    static final String ARTICLE_NAME_COLUMN = "Name";
    static final String ARTICLE_DESCRIPTION_COULMN = "Description";
    static final String ARTICLE_PRICE_COLUMN = "Price";
    static final String ARTICLE_VAT_COLUMN = "VAT";
    static final String ARTICLE_IMAGE_COLUMN = "Image";
    static final String ARTICLE_STATUS_COLUMN = "Status";
    static final String ARTICLE_CREATED_BY_COLUMN = "CreatedBy";
    static final String ARTICLE_AMOUNT_COLUMN = "Amount";

    /*
     * SQL Create Statement
     */
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ARTICLE_ID_COLUMN + " INTEGER PRIMARY KEY, " +
                    ARTICLE_NAME_COLUMN + " TEXT, " +
                    ARTICLE_DESCRIPTION_COULMN + " TEXT, " +
                    ARTICLE_PRICE_COLUMN + " REAL, " +
                    ARTICLE_VAT_COLUMN + " REAL, " +
                    ARTICLE_IMAGE_COLUMN + " BLOB, " +
                    ARTICLE_STATUS_COLUMN + " TEXT, " +
                    ARTICLE_CREATED_BY_COLUMN + " INTEGER, " +
                    ARTICLE_AMOUNT_COLUMN + " INTEGER);";

    static final String[] ALL_COLUMNS =
            {ARTICLE_ID_COLUMN, ARTICLE_NAME_COLUMN, ARTICLE_DESCRIPTION_COULMN,  ARTICLE_PRICE_COLUMN,
                    ARTICLE_VAT_COLUMN, ARTICLE_IMAGE_COLUMN, ARTICLE_STATUS_COLUMN, ARTICLE_CREATED_BY_COLUMN,
                    ARTICLE_AMOUNT_COLUMN};

    /**
     * SQL Statemnt um Tabelle zu löschen
     */
    private static final String DELETE_TABLE = "DROP TABLE " + TABLE_NAME;

    ShoppingCartDatabaseHelper(Context context) {
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
