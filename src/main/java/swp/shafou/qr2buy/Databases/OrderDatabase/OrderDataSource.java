package swp.shafou.qr2buy.Databases.OrderDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import swp.shafou.qr2buy.POJO.OrderPosition;

/**
 *------------------------------------*
 * - 06.02.17 ELFOULY Klasse erstellt.
 * - 09.02.17 ELFOULY Methode getAllOrdersInOrdersTable konfiguriert, so das nach dem Bestellungs
 * Kopf sortiert wird
 * - 12.02.17 ELFOULY Erstellen der Methode removeOrderFromOrderTable
 *------------------------------------*
 * OrderPosition Data Source Klasse
 * - Die restliche Applikation kommuniziert mit dieser Klasse um:
 *      - Datenbank zu öffnen
 *      - Datenbank zu schließen
 *      - In die Datenbank schreiben
 *      - Daten aus der Datenbank laden
 */
public class OrderDataSource {

    // Verwaltet die Bestellungs Datenbank Tabelle
    private static SQLiteDatabase orderDataSource;

    // Bestellungs Datenbank Tabellen Helfer
    private SQLiteOpenHelper sqLiteOpenHelper;

    // SQL Statement Ende Zeichen
    private static final String SQL_END_SYMBOL = ";";

    private static final String REMOVE_ALL_ORDERS_IN_ORDER_TABLE_SQL_STMT = "DELETE FROM "
            + OrderDatabaseHelper.TABLE_NAME
            + SQL_END_SYMBOL;

    private static final String REMOVE_ORDER_FROM_ORDER_TABLE_SQL_STMT = "DELETE FROM "
            + OrderDatabaseHelper.TABLE_NAME
            + " WHERE "
            + OrderDatabaseHelper.ORDER_COUNTER_COLUMN
            + " = ";

    /**
     * SQL Statement: Ist die Bestellung in der Datenbank Tabelle
     */
    private static final String IS_ORDER_IN_TABLE_SQL_STMT = "SELECT count(*) FROM "
            + OrderDatabaseHelper.TABLE_NAME
            + " WHERE "
            + OrderDatabaseHelper.ORDER_COUNTER_COLUMN
            + " = ";

    public OrderDataSource(Context context) {

        sqLiteOpenHelper = new OrderDatabaseHelper(context);
        orderDataSource = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * Öffnet die Datenbank
     */
    public void open() {

        orderDataSource = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * Schließt die Datenbank
     */
    private void close() {

        sqLiteOpenHelper.close();
    }

    /**
     * Entfernt alle einträge der bestellungs Datenbank Tabelle
     *
     * @return <code>true</code> Falls alle Bestellungen entfernt werden konnten
     */
    public boolean removeAllOrdersInOrderTable() {

        boolean areArticlesRemoved;

        open();

        try {

            orderDataSource.execSQL(REMOVE_ALL_ORDERS_IN_ORDER_TABLE_SQL_STMT);
            areArticlesRemoved = true;
        } catch(SQLException sqlException) {

            areArticlesRemoved = false;
        }

        close();

        return areArticlesRemoved;
    }

    /**
     * Gibt an ob sich die übergebene Bestellungs Id in der Datenbanktablle befindet
     *
     * @param orderId Die Id eines Bestellung
     * @return <code>true</code> Falls die Bestellung in der Tabelle existiert
     */
    private boolean isOrderInOrderTable(int orderId) {

        boolean orderIsInTable = false;

        open();

        Cursor cursor = orderDataSource.rawQuery(IS_ORDER_IN_TABLE_SQL_STMT
                + orderId
                + SQL_END_SYMBOL, null);

        cursor.moveToFirst();

        int cursorSize = cursor.getInt(0);

        if(cursorSize > 0) {

            orderIsInTable = true;
        }

        cursor.close();
        close();

        return orderIsInTable;
    }

    /**
     * Entfernt eine Bestellung anhand der übergebnen Bestellungs Id
     *
     * @param orderId Id der Bestellung
     * @return <code>true</code> Falls die Bestellung erfolgreich entfernt werden konnte.
     */
    private boolean removeOrderFromOderTable(int orderId) {

        boolean isArticleRemoved;

        open();

        try{

            orderDataSource.execSQL(REMOVE_ORDER_FROM_ORDER_TABLE_SQL_STMT
                    + orderId
                    + SQL_END_SYMBOL);

            isArticleRemoved = true;

        } catch(SQLException sqlException) {

            isArticleRemoved = false;
        }

        return isArticleRemoved;
    }


    /**
     * Schreibt einen Bestellung in die Bestellungen Datenbank Tabelle.
     *
     * @param orderPosition Eine Bestellungs Position
     */
    private boolean insertOrderToOrdersTable(OrderPosition orderPosition) {

        boolean isOrderInserted = false;

        /**
         * Falls die Bestellung bereits existiert, wird diese überschrieben um die Datenkonsitenz zu
         * gewährleisten.
         */
        if(isOrderInOrderTable(orderPosition.getOrderCounter())) {

            removeOrderFromOderTable(orderPosition.getOrderCounter());
        }

        open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderDatabaseHelper.ORDER_COUNTER_COLUMN
                , orderPosition.getOrderCounter());

        contentValues.put(OrderDatabaseHelper.ORDER_FOREIGN_KEY
                , orderPosition.getOrderForiegnKey());

        contentValues.put(OrderDatabaseHelper.ORDER_ARTICLE_ID_COLUMN
                , orderPosition.getOrderArticleID());

        contentValues.put(OrderDatabaseHelper.ORDER_QUANTITY_COLUMN
                , orderPosition.getOrderQuantity());

        contentValues.put(OrderDatabaseHelper.ORDER_PRICE_NET_COLUMN
                , orderPosition.getOderPriceNet());

        contentValues.put(OrderDatabaseHelper.ORDER_PRICE_GROSS_COLUMN
                , orderPosition.getOrderPriceGross());

        contentValues.put(OrderDatabaseHelper.ORDER_DATE
                , orderPosition.getOrderDate());


        contentValues.put(OrderDatabaseHelper.ORDER_STATUS_COLUMN
                , orderPosition.getOrderStatus());


        /**
         * Gibt die Id der neu erstellen Reihe an.
         * Ist -1 falls ein Fehler aufgetreten ist.
         */
        long idOfCreatedRow = orderDataSource.insert(OrderDatabaseHelper.TABLE_NAME, null, contentValues);

        if(idOfCreatedRow != -1) {

            isOrderInserted = true;
        }

        close();

        return isOrderInserted;
    }

    /**
     * Schreibt eine Liste von Bestellungen in die Datenbank Tabelle
     *
     * @param orderPositionList Eine Liste von Bestellungen
     */
    public void insertOrderListToOrderTable(List<OrderPosition> orderPositionList) {

        for(OrderPosition orderPosition : orderPositionList) {

            insertOrderToOrdersTable(orderPosition);
        }
    }

    /**
     * Gibt alle Bestellungen der Datenbank zurück sortiert nach dem Bestellungs Kopf Fremdschlüssel
     *
     * @return Liste von aller Bestellungen der Bestellungs DatenbankTabelle
     */
    public ArrayList<OrderPosition> getAllOrdersInOrdersTable() {

        ArrayList<OrderPosition> orderPositionList = new ArrayList<>();

        open();

        Cursor cursor = orderDataSource.rawQuery("SELECT * FROM "
                + OrderDatabaseHelper.TABLE_NAME
                + " ORDER BY "
                + OrderDatabaseHelper.ORDER_FOREIGN_KEY
                + " ASC;", null);

        while(cursor.moveToNext()) {

            OrderPosition orderPosition = new OrderPosition();

            orderPosition.setOrderCounter(cursor.getInt(
                    cursor.getColumnIndex(OrderDatabaseHelper.ORDER_COUNTER_COLUMN)));

            orderPosition.setOrderForiegnKey(cursor.getInt(cursor.getColumnIndex(OrderDatabaseHelper.ORDER_FOREIGN_KEY)));

            orderPosition.setOrderArticleID(cursor.getInt(
                    cursor.getColumnIndex(OrderDatabaseHelper.ORDER_ARTICLE_ID_COLUMN)));

            orderPosition.setOrderQuantity(cursor.getInt(
                    cursor.getColumnIndex(OrderDatabaseHelper.ORDER_QUANTITY_COLUMN)));

            orderPosition.setOderPriceNet(cursor.getDouble(
                    cursor.getColumnIndex(OrderDatabaseHelper.ORDER_PRICE_NET_COLUMN)));

            orderPosition.setOrderPriceGross(cursor.getDouble(
                    cursor.getColumnIndex(OrderDatabaseHelper.ORDER_PRICE_GROSS_COLUMN)));

            orderPosition.setOrderDate(cursor.getString(
                    cursor.getColumnIndex(OrderDatabaseHelper.ORDER_DATE)));

            orderPosition.setOrderStatus(cursor.getString(
                    cursor.getColumnIndex(OrderDatabaseHelper.ORDER_STATUS_COLUMN)));

            orderPositionList.add(orderPosition);
        }

        close();

        cursor.close();

        return orderPositionList;
    }

}
