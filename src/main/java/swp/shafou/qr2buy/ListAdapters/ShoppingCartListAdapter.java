package swp.shafou.qr2buy.ListAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

import swp.shafou.qr2buy.Activities.ShoppingCartActivity;
import swp.shafou.qr2buy.Databases.ShoppingCartDatabase.ShoppingCartDataSource;
import swp.shafou.qr2buy.POJO.Article;
import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;
import swp.shafou.qr2buy.R;
import swp.shafou.qr2buy.Utils.ImageDecrypter;
import swp.shafou.qr2buy.Utils.PriceUtilities;

import static swp.shafou.qr2buy.Activities.ShoppingCartActivity.shoppingCartArticles;

/**
 * ------------------------------------*
 * - 15.12.16 ELFOULY Aktivität erstellt.
 * - 21.12.16 ELFOULY Ändern der Menge der Artikel im Warenkorb in der Datenbank Tabelle.
 * - 22.12.16 ELFOULY Dynamisches Anzeigen der Artikel Preise einer Warenkorb Position.
 * - 11.12.17 ELFOULY Ersetzen von Buttons zur Mengen Angabe mit einem Number Picker zur
 * intuitiveren Benutzung.
 * - 11.12.17 ELFOULY Aufteilen des Layouts Aufbau in Methoden die in getView aufgerufen werden.
 * - 11.12.17 ELFOULY Beim klick auf einer Warenkorb Poition wird die Beschreibung und der
 * Artikelpreis angezeigt.
 * - 12.02.17 ELFOULY Dynamisches aufbauen der Artikel Bilder
 * ------------------------------------*
 *
 * Dieser Adapter baut eine Liste von Artikel Warenkorb Objekte auf auf
 */
public class ShoppingCartListAdapter extends ArrayAdapter<ArticleShoppingCartWrapper> {

    /*
     * Eine Liste mit den aufzubauenden Artikel
     */
    private static List<ArticleShoppingCartWrapper> articleShoppingCartList;

    /*
     * LayoutInfalter Objekt um aus dem Layout eine View zu erstellen
     */
    private LayoutInflater inflater;

    /*
     * Der Context der aufrufenden Aktivität
     */
    private Context context;

    /*
     * Verwalt die Warenkorb Datenbank
     */
    private ShoppingCartDataSource shoppingCartDatabase;

    /**
     * Minimale Eingabe des Number Picker bei der Mengen Eingabe des Artikels
     */
    private static final int EDIT_AMOUNT_NUMBER_PICKER_MIN_VALUE = 0;

    /**
     * Maximale Eingabe des Number Picker bei der Mengen Eingabe des Artikels
     */
    private static final int EDIT_AMOUNT_NUMBER_PICKER_MAX_VALUE = 99;

    public ShoppingCartListAdapter(Context context, List<ArticleShoppingCartWrapper> articleShoppingCartList) {
        super(context, R.layout.shopping_cart_article_list_item, articleShoppingCartList);

        // Übergebene Liste als statische Liste speichern
        ShoppingCartListAdapter.articleShoppingCartList = articleShoppingCartList;

        // Initialisiert den aufrufenden Context
        inflater = LayoutInflater.from(context);

        // Iitialisierunde der Warenkorb Datenbank
        shoppingCartDatabase = new ShoppingCartDataSource(getContext());

        this.context = context;
    }

    /**
     * Baut die einzelnen List Elemente auf
     *
     * @param position    gibt die momentane Position in der Liste an
     * @param convertView Gibt jeweils eine Reihe des Warenkorbs zurück
     * @param parent Vater Layout, dem die convertView zugeordnet werden soll
     * @return Eine View mit den Warenkorb Positions Inforamtionen
     */
    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        // Falls die View noch nicht existiert
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.shopping_cart_article_list_item, parent, false);
        }

        // Momentane Warenkorb Objekt aus der Warenkorb Artikel Liste
        ArticleShoppingCartWrapper currentArticleWrapper = articleShoppingCartList.get(position);
        final Article  currentArticle = currentArticleWrapper.getArticle();
        // Momnetane Artikel Id
        final int currentArticleId = currentArticle.getArticleID();

        // Daten aus der Datenbank lesen
        final int articleAmount = shoppingCartDatabase.getAmountOfArticleInShoppingCart(currentArticleId);

        // Fügt der convertView alle Textfelder hinzu
        addArticleInfoTextViewsToView(position, convertView);

        // Fügt das Bild des Artikels zur View hinzu
        addArticleImageToView(convertView, currentArticle);

        // Fügt der currentView den Number Picker zur Artikel Mengen auswahl hinzu
        addAmountNumberPickerToView(convertView, articleAmount, currentArticle);

        // Wenn man lange auf ein Item drückt, kann man dieses aus dem Warenkorb entfernen
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // Artikel aus dem Artikel Warenkorb lösch Dialog
                new AlertDialog.Builder(getContext())
                        .setMessage("Möchten Sie diesen Artikel wirklich löschen?")
                        .setTitle("Artikel Löschen")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Artikleposition aus der Warenkorb Liste entfernen
                                shoppingCartArticles.remove(position);

                                // Artikelposition aus der Datenbank Tabelle löschen
                                shoppingCartDatabase.removeArticleFromShoppingCart(currentArticleId);

                                /*
                                * Informiert das Framework das sich etwas geändert hat und baut
                                * das Layout neu auf.
                                */
                                notifyDataSetChanged();

                                // Aktualisert die totale Anischt des gesamten Preises der ArtikelPositionen
                                updateOverallPriceTextView(context);
                            }
                        })
                        .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Falls der Nutzer Nein drückt, passiert nichts
                            }
                        }).create().show();


                return true;
            }
        });

        // Bei einem Klick auf eine Position, werden Artikelbeschreibung und Artikelpreis angezeigt
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Öffnet ein Dialog Fenster mit der Artikelbeschreibung und dem Artikelperis
                showArticleDescriptionAndPriceDialog(currentArticle, context);
            }
        });

        return convertView;
    }

    /**
     * Erzeugt eine Informations Dialog mit der jeweiligen Beschreibung und dem Artikelpreis des
     * Artikels
     *
     * @param article Artikel zu dem die Beschreibung und der Preis ausgegeben werden soll
     */
     static void showArticleDescriptionAndPriceDialog(Article article, Context context) {

        new AlertDialog.Builder(context)
                .setTitle(article.getArticleName())
                .setMessage(article.getArticleDescription()
                        + "\n"
                        + "\n"
                        + "Artikelpreis: "
                        + article.getArticlePrice()
                        + " €")
                .setNeutralButton("OK", null)
                .create().show();
    }

    /**
     * Fügt der übergebenen View die relevanten Textviews eines Artikels hinzu
     *
     * @param position Position in der ArtikelWrapper Warenkorb Liste
     * @param convertView View, denen die Textviews hinzugefügt werden
     */
    private void addArticleInfoTextViewsToView(int position, View convertView) {

        // Artikel Informationen, wie Beschreibung und Preis
        final TextView articleTitleTV = (TextView) convertView.findViewById(R.id.article_custom_list_title);
        final TextView articleSumPrice = (TextView) convertView.findViewById(R.id.article_position_custom_list_sum_price);


        // Momentane Warenkorb Objekt aus der Warenkorb Artikel Liste
        final ArticleShoppingCartWrapper currentArticle = articleShoppingCartList.get(position);

        // Daten des Artikel auslesen
        final int articleId = currentArticle.getArticle().getArticleID();
        String articleTitle = currentArticle.getArticle().getArticleName();

        final double articlePrice = currentArticle.getArticle().getArticlePrice();

        // Daten aus der Datenbank lesen
        final int articleAmount = shoppingCartDatabase.getAmountOfArticleInShoppingCart(articleId);

        // Setzen des Titels und der Beschreibung
        articleTitleTV.setText(articleTitle);


        /**
         * Summe des Preises der Artikelposition
         * Errechnet sich wie folgt: Artikel Brutto Preis * Mege im Warenkorb
         */
        double sumPriceOfArticle = ShoppingCartActivity.calculateSumOfShoppingCartPosition(articlePrice, articleAmount);

        // Rundet den Preis und wandelt diesen in String
        String roundedSumPriceOfArticleText = PriceUtilities.getRoundedPriceText(sumPriceOfArticle);

        // Setzen der Summe des Warenkorb Artikel Position
        articleSumPrice.setText(roundedSumPriceOfArticleText);
    }

    /**
     * Fügt der übergebenen View den ArtikelBild hinzu
     *
     * @param convertView View, denen die ImageView hinzugefügt werden
     * @param currentarticle Artikel von dem das Bild angezeigt werden soll
     */
    private void addArticleImageToView(View convertView, Article currentarticle) {

        ImageView articleImageView = (ImageView) convertView.findViewById(R.id.article_custom_list_image);

        // TODO: Artikel Image muss noch aus der DB geladen werden
        // Setzen des Artikel Bildes
        ImageDecrypter imageDecrypter = new ImageDecrypter(currentarticle.getArticleImagePath());
        articleImageView.setImageBitmap(imageDecrypter.getBitmap());
        imageDecrypter.clean();
    }

    /**
     * - Fügt einen Number Picker der übergebenen View zu.
     * - Setzt den Initialwert auf dem vorgefundenen Wert des Artikels in der Warenkorb Tabelle.
     * - Setzt einen MAX/MIN Wert des Number Pickers
     *
     * @param convertView View, denen der NumberPicker hinzugefügt werden
     * @param articleAmount Artikel Menge
     * @param currentArticle Momentaner Artikel
     */
    private void addAmountNumberPickerToView(View convertView, int articleAmount, final Article currentArticle) {

        // Number Picker zur Änderung der Mengenangabe
        final NumberPicker editAmountOfArticleNumberPicker = (NumberPicker) convertView.findViewById(R.id.edit_amount_of_article_in_shopping_cart_number_picker);
        // Number Picker ist endet bei der kleinsten und höchsten Angabe
        editAmountOfArticleNumberPicker.setWrapSelectorWheel(false);
        // Min/Max des Num Pickers
        editAmountOfArticleNumberPicker.setMinValue(EDIT_AMOUNT_NUMBER_PICKER_MIN_VALUE);
        editAmountOfArticleNumberPicker.setMaxValue(EDIT_AMOUNT_NUMBER_PICKER_MAX_VALUE);
        // Number Picker ist nicht per Keyboard editierbar
        editAmountOfArticleNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        // Setzen des Number Pickers auf die anzahl des jeweiligen Artikels in der Datenbank Tabelle
        editAmountOfArticleNumberPicker.setValue(articleAmount);

        // Listener, falls sich die Angabe des Number Pickers der Mengen Eingabe ändert
        editAmountOfArticleNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                // Artikel Id des momentanen Artikels
                int articleId = currentArticle.getArticleID();

                // Aktualisieren der eingegebenen Menge des jeweiligen Artikels in der Warenkorb Datenbank Tabelle
                shoppingCartDatabase.updateAmountOfArticleInShoppingCart(articleId, editAmountOfArticleNumberPicker.getValue());

                /*
                * Informiert das Framework das sich etwas geändert hat und baut
                * das Layout neu auf.
                */
                notifyDataSetChanged();


                updateOverallPriceTextView(context);
            }
        });
    }

    /**
     * Aktualisiert die Preis Ansicht des ShoppingCartActivity Layouts der gesamten Artikelpositionen
     * im Warenkob:
     * - Netto Preis
     * - Brutto Preis
     * - Steuern
     *
     * @param context Context der Aufrufenden Aktiviät
     */
    private void updateOverallPriceTextView(Context context) {

        TextView totalNetTextView = (TextView) ((Activity) context).findViewById(R.id.sum_total_TV);
        TextView totalGrossTextView = (TextView) ((Activity) context).findViewById(R.id.sum_total_TV_VAT);
        TextView totalTaxesTextView = (TextView) ((Activity) context).findViewById(R.id.total_taxes_TV);
        String priceNetText = PriceUtilities.getRoundedPriceText(
                shoppingCartDatabase.getSumPriceOfArticlesInShoppingCart());

        String taxesText = PriceUtilities.getRoundedPriceText(
                shoppingCartDatabase.getTaxesOfArticlesInShoppingCart());

        String priceGrossText = PriceUtilities.getRoundedPriceText(
                shoppingCartDatabase.getGrossPriceOfArticlesInShoppingCart());

        totalNetTextView.setText("Nettopreis: " + priceNetText + "€");
        totalTaxesTextView.setText("Mehrwertsteuer:           +" + taxesText + "€");
        totalGrossTextView.setText("Bruttopreis: " + priceGrossText + "€");
    }

}
