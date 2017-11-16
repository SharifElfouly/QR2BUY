package swp.shafou.qr2buy.Databases.OrderDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *------------------------------------*
 * - Klasse erstellt. 06.02.17 ELFOULY
 *------------------------------------*
 *
 * Datenbak Tabelle Helfer Klasse.
 * Beeinhaltet den Aufbau der Bestellungs Datenbank Tabelle
 */
class OrderDatabaseHelper extends SQLiteOpenHelper {

    /*
    * Datenbank Name
    */
    private static final String DATABASE_NAME = "Orders.db";

    /*
     * Datenbank Version
     * Falls Veränderungen an der Datenbank gamacht werden, muss die Version um 1 erhöht werden
     */
    private static final int DATABASE_VERSION = 3;

    /*
     * Tabellen Name
     */
    static final String TABLE_NAME = "orders";

    /*
     * Tabellen Spalten
     */
    static final String ORDER_COUNTER_COLUMN = "Counter";
    static final String ORDER_FOREIGN_KEY = "OrderFK";
    static final String ORDER_ARTICLE_ID_COLUMN = "ArticleID";
    static final String ORDER_QUANTITY_COLUMN = "Quantity";
    static final String ORDER_PRICE_NET_COLUMN = "Net_Price";
    static final String ORDER_PRICE_GROSS_COLUMN = "Gross_Price";
    static final String ORDER_STATUS_COLUMN = "Status";
    static final String ORDER_DATE = "Order_Date";

    /*
    * Create Statement
    */
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ORDER_COUNTER_COLUMN + " INTEGER PRIMARY KEY, " +
                    ORDER_FOREIGN_KEY + " INTEGER, " +
                    ORDER_ARTICLE_ID_COLUMN + " INTEGER, " +
                    ORDER_QUANTITY_COLUMN + " INTEGER, " +
                    ORDER_PRICE_NET_COLUMN + " REAL, " +
                    ORDER_PRICE_GROSS_COLUMN + " REAL, " +
                    ORDER_DATE + " TEXT, " +
                    ORDER_STATUS_COLUMN + " TEXT);";

    /**
     * Statemnt um Tabelle zu löschen
     */
    private static final String DELETE_TABLE = "DROP TABLE " + TABLE_NAME;

    OrderDatabaseHelper(Context context) {
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
