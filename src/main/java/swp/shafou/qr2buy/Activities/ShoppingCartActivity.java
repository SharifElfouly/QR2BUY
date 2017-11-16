package swp.shafou.qr2buy.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import swp.shafou.qr2buy.Databases.ShoppingCartDatabase.ShoppingCartDataSource;
import swp.shafou.qr2buy.ListAdapters.ShoppingCartListAdapter;
import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;
import swp.shafou.qr2buy.R;
import swp.shafou.qr2buy.TestData.ArticleShoppingCartWrapperTestData;
import swp.shafou.qr2buy.Utils.NetworkUtilities;
import swp.shafou.qr2buy.Utils.PriceUtilities;

/**
 *------------------------------------*
 * - 17.12.16 ELFOULY Aktivität erstellt.
 * - 21.12.16 ELFOULY Anzeigen der Artikel im Warenkorb aus der lokalen Datenbank Tabelle.
 * - 22.12.16 ELFOULY Hinzufügen eines Steuer TextViews
 * - 22.12.16 ELFOULY Dynamisches Aufbauen der TextViews (Nettopreis, Bruttopreis und Mehrwertsteuer)
 * - 10.02.16 ELFOULY Aufbau von einem speziellen Dialog, falls sich keine Artikel im Warenkorb befinden
 * - 10.02.16 ELFOULY Implementierung der Bestellungs Funktion
 * - 12.02.17 ELFOULY Trennung der Warenkob Berechnungs Logik und der Acitity
 *------------------------------------*
 *
 * - Diese Activity beeinhaltet die gesamte Logik des Einkaufswagen
 * - Eine Liste verwaltet die Artikel im Einkaufswagen
 */
public class ShoppingCartActivity extends AppCompatActivity {

    /*
     * Liste der Artikel im Warenkorb
     */
    public static ArrayList<ArticleShoppingCartWrapper> shoppingCartArticles;

    /*
     * Verwalt die Warenkorb Datenbank
     */
    private ShoppingCartDataSource shoppingCartDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout setzen
        setContentView(R.layout.shopping_cart_layout);

        // Liste der Artikel im Layout instanziieren
        ListView shoppingCartListView = (ListView) findViewById(R.id.shopping_cart_listView);

        shoppingCartArticles = new ArrayList<>();

        // Artikel die im Warenkorb sind aus der Datenbank aufrufen und in die Liste schreiben
        shoppingCartDataSource = new ShoppingCartDataSource(this);
//        shoppingCartDataSource.removeAllArticlesInShoppingCart();
//        shoppingCartDataSource.insertArticleToShoppinCartTable(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(0));
//        shoppingCartDataSource.insertArticleToShoppinCartTable(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(1));
//        shoppingCartDataSource.insertArticleToShoppinCartTable(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(2));
//        shoppingCartDataSource.insertArticleToShoppinCartTable(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(3));
//        shoppingCartDataSource.insertArticleToShoppinCartTable(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(4));
//        shoppingCartDataSource.insertArticleToShoppinCartTable(ArticleShoppingCartWrapperTestData.articleShoppingCartWrappersTestList.get(5));

        shoppingCartArticles = shoppingCartDataSource.getAllArticlesInShoppingCartTable();

        if(shoppingCartArticles.size() != 0) {

            // List Adapter instanziieren
            ShoppingCartListAdapter shoppingCartListAdapter = new ShoppingCartListAdapter(this, shoppingCartArticles);
            shoppingCartListView.setAdapter(shoppingCartListAdapter);

            TextView articleListSumPrice = (TextView) findViewById(R.id.sum_total_TV);
            TextView articleListTaxes = (TextView) findViewById(R.id.total_taxes_TV);
            TextView articleListSumPriceVAT = (TextView) findViewById(R.id.sum_total_TV_VAT);

            String sumPriceText = PriceUtilities.getRoundedPriceText(shoppingCartDataSource.getSumPriceOfArticlesInShoppingCart());
            articleListSumPrice.setText("Nettopreis: " + sumPriceText + "€");

            String taxesText = PriceUtilities.getRoundedPriceText(shoppingCartDataSource.getTaxesOfArticlesInShoppingCart());
            articleListTaxes.setText(taxesText);

            String sumPriceWithVATText = PriceUtilities.getRoundedPriceText(shoppingCartDataSource.getGrossPriceOfArticlesInShoppingCart());
            articleListSumPriceVAT.setText("Bruttopreis: " + sumPriceWithVATText + "€");
        } else {

            RelativeLayout shoppingCartLayout = (RelativeLayout) findViewById(R.id.shopping_cart_layout_relative_layout);
            LinearLayout noArticlesInShoppingCartLayout = new LinearLayout(this);

            noArticlesInShoppingCartLayout.setOrientation(LinearLayout.VERTICAL);
            shoppingCartLayout.addView(noArticlesInShoppingCartLayout);

            addNoArticlesInShoppingCartTextView(noArticlesInShoppingCartLayout);
            addNoArticlesInShoppingCartButton(this, noArticlesInShoppingCartLayout);

            hideArticleListInfoLayout();
        }
    }

    /**
     * Berechnet den Brutto Preis einer Einkaufswagen Position
     *
     * @param articlePrice Artikel Preis
     * @param articleAmount Menge eines Artikels im Warenkorb
     * @return Brutto Preis einer Einkaufswagen Position
     */
    public static double calculateSumOfShoppingCartPosition(double articlePrice, int articleAmount) {

        return PriceUtilities.roundPrice(articlePrice * articleAmount);
    }


    /**
     * TextView die angezeigt wird, wenn sich keine Artikel im Warenkorb befinden.
     *
     * @param shoppingCartArticlesLayout Übergebens Layout
     */
    public void addNoArticlesInShoppingCartTextView(LinearLayout shoppingCartArticlesLayout) {

        TextView noArticlesInShoppingCartTextView = new TextView(this);
        noArticlesInShoppingCartTextView.setText(R.string.no_articles_in_shopping_cart_explanation_txt);
        noArticlesInShoppingCartTextView.setPadding(50, 75, 20, 10);
        noArticlesInShoppingCartTextView.setTextSize(18);

        shoppingCartArticlesLayout.addView(noArticlesInShoppingCartTextView);
    }

    public void addNoArticlesInShoppingCartButton(final Context context, LinearLayout shoppingCartArticlesLayout) {

        Button openScannerBtn = new Button(this);
        openScannerBtn.setText(R.string.to_scanner_btn);
        openScannerBtn.setGravity(Gravity.CENTER);

        openScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openScanner(context);
            }
        });

        shoppingCartArticlesLayout.addView(openScannerBtn);
    }

    public void openScanner(Context context) {

        Intent openScanner = new Intent(context, ScannerActivity.class);
        startActivity(openScanner);
    }

    public void hideArticleListInfoLayout() {

        LinearLayout articleListInfoLayout = (LinearLayout) findViewById(R.id.article_list_info);
        articleListInfoLayout.removeAllViews();
    }

    // TODO: Bestellung ausführen und Liste löschen
    public void orderArticlesInShoppingCart(View view) {

        if(NetworkUtilities.isNetworkAvailable(this)) {

            if(showOrderDialog()) {

                if(sendOrdersToServer()) {

                    deleteArticlesInShoppingCart();
                    returnToHomeActivity();
                    Toast.makeText(getApplicationContext(), "Ihre Bestellung wurde erfolgreich ausgeführt!", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Ihre Bestellung wurde leider nicht ausgeführt!", Toast.LENGTH_LONG).show();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Ihre Bestellung wurde abgebrochen!", Toast.LENGTH_LONG).show();
            }
        } else {

            NetworkUtilities.showNoNetworkDialog(this);
        }
    }

    // TODO: POST zum Server
    private boolean sendOrdersToServer() {

        return false;
    }

    private void deleteArticlesInShoppingCart() {

        shoppingCartDataSource.removeAllArticlesInShoppingCart();
    }

    public void returnToHomeActivity() {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * Baut einen Dialog auf, der um die Bestellungs Bestätigung fragt.
     *
     * @return <code>true</code> Falls auf Ja gedrückt wurde
     */
    public boolean showOrderDialog() {

        final boolean[] isUsersOrderAccepted = new boolean[1];

        new AlertDialog.Builder(this)
                .setTitle("Bestellung")
                .setMessage("Möchten Sie diese Bestellung wirklich abschließen?")
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        isUsersOrderAccepted[0] = false;
                    }
                })
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        isUsersOrderAccepted[0] = true;
                    }
                }).setCancelable(false)
                .create().show();

        return isUsersOrderAccepted[0];
    }
}
