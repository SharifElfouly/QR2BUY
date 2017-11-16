package swp.shafou.qr2buy.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import swp.shafou.qr2buy.Databases.OrderDatabase.OrderDataSource;
import swp.shafou.qr2buy.ListAdapters.OrderHeaderSumPriceListAdapter;
import swp.shafou.qr2buy.ListAdapters.OrderPositionListAdapter;
import swp.shafou.qr2buy.POJO.OrderHeader;
import swp.shafou.qr2buy.POJO.OrderPosition;
import swp.shafou.qr2buy.POJO.OrderCustomListView;
import swp.shafou.qr2buy.R;
import swp.shafou.qr2buy.TestData.OrderHeaderTestData;

/**
 * Autor: Sharif Elfouly
 *-----------------------------------
 * - 14.12.16 ELFOULY Klasse Erstellt
 * - 06.02.17 ELFOULY Datenbanksynchronisation
 * - 09.02.17 ELFOULY Dynamisches Aufbauen der Bestellungsköpfe und Position
 * - 10.02.17 ELFOULY Überarbeitung des Layouts und anzeige der Gesamtsumme der Bestellung
 * - 10.02.17 ELFOULY Erstellung einer speziellen Anzeige, falls keine Bestellungen vorhanden sind
 *-----------------------------------
 * Diese Activity steuert das orders_layout
 */
public class OrdersActivity extends AppCompatActivity {

    /**
     * List der Bestellungspositionen
     */
    public static ArrayList<OrderPosition> bestellungen;

    /**
     * Liste der Bestellungsköpfe
     */
    private static ArrayList<OrderHeader> orderHeaders;

    /**
     * Zähler der Bestellungsköpfe
     */
    private int orderHeaderCounter = 0;

    /**
     * Höhe der Vertikalen Linie, die nach jedem View erzeugt wird
     */
    private static final int VERTICAL_LINE_HEIGHT = 2;

    /**
     * Größe des Textes des Datums, des Bestellungskopfes
     */
    private static final int ORDER_HEADER_DATE_ORDER_ID_TEXT_SIZE = 19;

    /**
     * Größe des textes, der angezeigt wird wenn keine Bestellungen vorzufinden sind.
     */
    private static final int NO_ORDERS_EXPLANATION_TEXT_SIZE = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_layout);

        /**
         * Verwaltet die Bestllungen Datenbank Tabelle
         */
        OrderDataSource orderDataSource = new OrderDataSource(this);
        bestellungen = new ArrayList<>();

        orderHeaders = OrderHeaderTestData.orderHeaderTestList;

        /**
         * DATABASE REFRESH TEST
         */
//        orderDataSource.open();
//        orderDataSource.removeAllOrdersInOrderTable();
//        orderDataSource.insertOrderListToOrderTable(OrderPositionTestData.orderPositionTestList);
//        orderDataSource.close();

        // Gibt alle Bestellungspositionen der Datenbank Tabelle zurück
        bestellungen = orderDataSource.getAllOrdersInOrdersTable();

        // Erstellt die gesamte Bestellungsansicht
        createOrderView(bestellungen);
    }

    /**
     * - Baut das gesamte Layout der Bestellungs Aktivität auf.
     * - In Klammern wird beschrieben, welche Informationen jewils ausgegeben werden.
     * - Der Pfeil --> zeigt an welche Methode aufgerufen wird.
     * - Falls Bestellungspositionen vorhanden sind sieht der Aufbau wie folgt aus:
     *
     *      Bestellunskopf (Datum, Bestellungsnummer) --> addOrderPriceAndDateTextViewToLayout()
     *          - Bestellungsposition (Bild, Titel, Preis, Menege, Summe) --> addOrderPositionsToLayout()
     *          - Bestellungsposition
     *          .
     *          .
     *          .
     *          Bestellungskopf (Gesamtpreis der Bestellungspositionen) --> addOrderHeaderSumPriceTextViewToLayout()
     *      Bestellunskopf
     *          - Bestellungsposition
     *          .
     *          .
     *          Bestellungskopf
     *      Bestellunskopf
     *          .
     *          .
     *
     * - Falls die übergeben Bestellungspositoins Liste leer ist, wird eine spezielle Ansicht
     * genriert.
     * - In dieser Ansicht hat der Nutzer die möglichkeit zur Warenkorb Ansicht zu wechseln, um dort
     * eine Bestellung durchzuführen.
     * - Bei einem Klick auf den Bestellungskopf, werden weitere Inforamtionen zur jeweiligen
     * Bestellung angezeigt.
     * - Bei einem Klick auf eine Bestllungsposition, werden Informationen über den jeweilige
     * Artikel angezeigt.
     *
     *------------WICHTIG------------------*
     * - Die übergeben List muss eine sortierte Reihenfolge des Attributes foreignKey beeinhalten.
     * - im Falle einer nicht sortierten Liste, funktioniert der Alogorithmus nicht.
     *-------------------------------------*
     *
     * @param orderPositionsList Übergabe einer Liste von Bestellungspositionen
     */
    public void createOrderView(List<OrderPosition> orderPositionsList) {

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.orders_ll);

        // Ist die Bestellungsposionsliste nicht leer
        if(orderPositionsList.size() != 0) {

            // Beeinhaltet, die Bestellungspositionen zu einem Bestellungskopf
            ArrayList<OrderPosition> orderPositions = new ArrayList<>();

            // Foreign Key, gibt an zu welchem Bestellungskopf, die einzelne Position gehört
            int orderForeignKey = orderPositionsList.get(0).getOrderForiegnKey();

            // Aufbau der einzelnen Bestellunspositionen der Bestellungsköpfe
            for(OrderPosition order: orderPositionsList) {

                // Solange die Position die gleiche wie die vorherige ist
                if(orderForeignKey == order.getOrderForiegnKey()) {

                    orderPositions.add(order);
                } else {

                    addOrderPriceAndDateTextViewToLayout(this, orderHeaders, orderHeaderCounter, linearLayout);
                    addOrderPositionsToLayout(orderPositions, linearLayout);

                    // Springe zum nächsten Bestellungs Kopf
                    orderHeaderCounter++;

                    // Lösche alle Elemente der Bestellungspositonen um die neuene Positionen des neuen
                    // Kopfes zu speichern
                    orderPositions.clear();
                    orderPositions.add(order);
                    orderForeignKey = order.getOrderForiegnKey();
                }
            }

            addOrderPriceAndDateTextViewToLayout(this, orderHeaders, orderHeaderCounter, linearLayout);
            addOrderPositionsToLayout(orderPositions, linearLayout);
        } else {

            addNoOrdersTextView(this, linearLayout);
        }
    }

    /**
     * Fügt die einzelnen Bestellungs Positionen der übergebenen Liste dem übergebenen Layout zu.
     * Nach jeder BestellungsPosition wird eine vertikale Line hinzugefügt.
     * Nach allen BestellungsPositionen wird der Preis der gesamten Bestellung angezeigt.
     *
     * @param orderPositionList Liste von Bestellungspositionen
     * @param linearLayout Lineares Layout
     */
    public void addOrderPositionsToLayout(List<OrderPosition> orderPositionList,  LinearLayout linearLayout) {

        // Iteriere über alle Bestellungspositionen des jewiligen Bestellungskopfes
        for(OrderPosition orderPosition: orderPositionList) {

            // Anzeige der einzelnen Bestellungsposition
            addOrderViewToLayout(this, linearLayout, orderPositionList, orderPosition);
            // Nach jder Bestellungsposition, wird eine vertikale Linie hinzugefügt
            addVerticalLineToLayout(this, linearLayout);
        }

        // Anzeige des Gesamtpreis der Bestellung
        addOrderHeaderSumPriceTextViewToLayout(this, orderHeaders, orderHeaderCounter, linearLayout);
        addVerticalLineToLayout(this, linearLayout);
    }

    /**
     * - Fügt einem Linearen LAyout die Bestellungskopf Inforamtionen: Bestellungskopf Nummer und Datum
     * der Bestellung hinzu.
     *
     * @param context Kontext der aufrufenden Aktivität
     * @param orderHeadersList Liste der Bestellungs Köpfe
     * @param position Position in der Liste der Bestellungs Köpfe
     * @param linearLayout Lineares Layout dem die TextViews hinzugefügt werden
     */
    public void addOrderPriceAndDateTextViewToLayout(final Context context, List<OrderHeader> orderHeadersList, int position, LinearLayout linearLayout) {

        final OrderHeader orderHeader = orderHeadersList.get(position);

        TextView orderHeaderDateAndIdTV = new TextView(context);
        orderHeaderDateAndIdTV.setText(orderHeader.getOrderCreationDate()
                + "\n"
                + "Bestellungsnummer: "
                + orderHeader.getOrderID());
        orderHeaderDateAndIdTV.setTextSize(ORDER_HEADER_DATE_ORDER_ID_TEXT_SIZE);
        orderHeaderDateAndIdTV.setPadding(2, 5, 2, 0);
        orderHeaderDateAndIdTV.setGravity(Gravity.CENTER);

        orderHeaderDateAndIdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrderHeaderInformationDialog(context, orderHeader);
            }
        });

        linearLayout.addView(orderHeaderDateAndIdTV);
    }

    // TODO: Es müssen alle kopf Positions daten ausgegeben werden! POJO Klasse muss angepasst werden
    /**
     *
     * @param context Kontext der aufrufenden Aktivität
     * @param orderHeader Kopfposition
     */
    public void showOrderHeaderInformationDialog(Context context, OrderHeader orderHeader) {

        new AlertDialog.Builder(context)
                .setTitle("Informationen zur Bestellung: " + orderHeader.getOrderID())
                .setMessage(orderHeader.getOrderCreationDate()
                        + "\n"
                        + "\n"
                        + "Artikelpreis: "
                        + " €")
                .setNeutralButton("OK", null)
                .create().show();
    }

    /**
     * Fügt eine Liste von Bestellungspositionen einem Layout zu
     *
     * @param context Kontext der aufrufenden Aktivität
     * @param linearLayout Lineares Layout
     * @param orderPosistionList Liste von Bestellungspositionen
     * @param orderPosition Bestellungs Position
     */
    public void addOrderViewToLayout(Context context, LinearLayout linearLayout, List<OrderPosition> orderPosistionList, OrderPosition orderPosition) {

        OrderPositionListAdapter orderPositionListAdapter;
        OrderCustomListView orderList;

        orderList = new OrderCustomListView(context, null);
        orderPositionListAdapter = new OrderPositionListAdapter(context, orderPosistionList);
        orderList.setAdapter(orderPositionListAdapter);

        View view = orderPositionListAdapter.getView(orderPosistionList.indexOf(orderPosition), null, linearLayout);
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        linearLayout.addView(view);
    }

    /**
     * Fügt eine schwarze vertikale Linie einem Layout zu
     *
     * @param context Kontext der aufrufenden Aktivität
     * @param linearLayout Lineares Layout
     */
    public void addVerticalLineToLayout(Context context, LinearLayout linearLayout) {

        View verticalLine = new View(context);
        verticalLine.setLayoutParams(new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                VERTICAL_LINE_HEIGHT));

        verticalLine.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        linearLayout.addView(verticalLine);
    }

    /**
     * Fügt die Bestellungs Kopf Informationen einem Layout hinzu
     *
     * @param context Kontext der aufrufenden Aktivität
     * @param orderHeaders Bestellungskopf
     * @param position Position des bestellungskopfes
     * @param linearLayout Lineares Layout
     */
    public void addOrderHeaderSumPriceTextViewToLayout(Context context, List<OrderHeader> orderHeaders, int position, LinearLayout linearLayout) {

        OrderHeaderSumPriceListAdapter orderHeaderSumPriceListAdapter = new OrderHeaderSumPriceListAdapter(context, orderHeaders);

        View orderHeaderTextView = orderHeaderSumPriceListAdapter.getView(position, null, linearLayout);
        orderHeaderTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        orderHeaderTextView.setPadding(1, 4, 1, 4);

        linearLayout.addView(orderHeaderTextView);
    }

    /**
     * - Fügt ein Textfeld einem Layout hinzu
     * - Textfeld besagt, das noch keine Bestellungen aufgegeben wurden.
     * - Wird nur angeziegt, falls keine Bestellungen existieren.
     * - Soll den Nutzer aufklären, das dieser als erstes eine Betsllung abschicken muss, damit diese
     * in der bestllungsanischt angezeigt werden kann.
     *
     * @param context Kontext der aufrufenden Aktivität
     * @param linearLayout Lineares Layout
     */
    public void addNoOrdersTextView(Context context, LinearLayout linearLayout) {

        TextView noOrdersTV = new TextView(context);
        noOrdersTV.setPadding(50, 75, 20, 10);
        noOrdersTV.setText(R.string.no_orders_explanation_txt);
        noOrdersTV.setTextSize(NO_ORDERS_EXPLANATION_TEXT_SIZE);

        linearLayout.addView(noOrdersTV);

        addNoOrdersShoppingCartButton(this, linearLayout);
    }

    /**
     * Fügt einen Button einem Layout hinzu
     * Button führt zur Warenkorb Ansicht um eine Bestellung abgeben zu können
     * Wird nur angeziegt, falls keine Bestellungen existieren.
     *
     * @param context Kontext der aufrufenden Aktivität
     * @param linearLayout Lineares Layout
     */
    public void addNoOrdersShoppingCartButton(final Context context, LinearLayout linearLayout) {

        Button openShoppingCartBtn = new Button(this);
        openShoppingCartBtn.setText(R.string.to_shopping_cart_btn_txt);
        openShoppingCartBtn.setGravity(Gravity.CENTER);

        openShoppingCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openShoppingCartActivity(context);
            }
        });

        linearLayout.addView(openShoppingCartBtn);
    }

    /**
     * Öffnet die Einkaufswagen Aktivität
     *
     * @param context Kontext der aufrufenden Aktivität
     */
    public void openShoppingCartActivity(Context context) {

        Intent openShoppingCart = new Intent(context, ShoppingCartActivity.class);
        startActivity(openShoppingCart);
    }
}
