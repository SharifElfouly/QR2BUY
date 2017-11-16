package swp.shafou.qr2buy.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import swp.shafou.qr2buy.Databases.ArticleDatabase.ArticleDataSource;
import swp.shafou.qr2buy.POJO.Article;
import swp.shafou.qr2buy.POJO.OrderPosition;
import swp.shafou.qr2buy.R;
import swp.shafou.qr2buy.Utils.ImageDecrypter;
import swp.shafou.qr2buy.Utils.PriceUtilities;

/**
 *-----------------------------------
 * - 06.02.17 ELFOULY erstellt
 * - 12.02.17 ELFOULY Dynamisches aufbauen der Artikel Bilder
 *-----------------------------------
 *
 * Dieser Adapter baut eine Liste von Bestellungs Positionen auf
 */
public class OrderPositionListAdapter extends ArrayAdapter<OrderPosition> {

    /*
     * Eine Liste mit den aufzubauenden Artikel
     */
    private static List<OrderPosition> orderPositionList;

    /*
     * LayoutInfalter Objekt um aus dem Layout eine View zu erstellen
     */
    private LayoutInflater inflater;

    /*
     * Der Context der aufrufenden Aktivität
     */
    private Context context;

    /*
     * Verwaltet die Artikel Datenbank
     */
    private ArticleDataSource articleDataSource;

    public OrderPositionListAdapter(Context context, List<OrderPosition> orderPositionList) {
        super(context, R.layout.order_list_item, orderPositionList);

        // Übergebene Liste als statische Liste speichern
        OrderPositionListAdapter.orderPositionList = orderPositionList;

        // Initialisiert den aufrufenden Context
        inflater = LayoutInflater.from(context);

        articleDataSource = new ArticleDataSource(getContext());

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // Falls die View noch nicht existiert
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
        }

        // Layout Elemente der order_list_item layouts initialisieren
        ImageView layoutArticleImage = (ImageView) convertView.findViewById(R.id.order_custom_list_article_image);
        TextView layoutArticleTitle = (TextView) convertView.findViewById(R.id.order_custom_list_article_name);

        TextView layoutArticleGrossPrice = (TextView) convertView.findViewById(R.id.order_custom_list_article_price);
        TextView layoutArticleAmount = (TextView) convertView.findViewById(R.id.order_custom_list_article_amount);
        TextView layoutArticleSumPrice = (TextView) convertView.findViewById(R.id.order_custom_list_article_sum_price);

        OrderPosition orderPosition = orderPositionList.get(position);
        Article article = new Article();

        // Artikel Id aus der Bestellung
        int articleId = orderPosition.getOrderArticleID();

        if(articleDataSource.isArticleInTable(articleId)) {

            // Arttikel aus der Bestellung initialisieren
            article = articleDataSource.getArticleFromTable(articleId);
        } else {

//            orderPositionList.remove(position);
//            notifyDataSetChanged();
        }


        String articleName = article.getArticleName();
//        String orderdate = orderPosition.getOrderDate();
        double articlePrice = article.getArticlePrice();
        int orderAmount = orderPosition.getOrderQuantity();
        double orderSumPrice = orderPosition.getOrderPriceGross();

        // TODO: articleImage
//        layoutArticleImage = ...

        // Textfelder setzen
        layoutArticleTitle.setText(articleName);
        layoutArticleTitle.setTextColor(Color.rgb(255, 255, 255));
//        layoutOrderDate.setText(orderdate);
        layoutArticleGrossPrice.setText(PriceUtilities.getRoundedPriceText(articlePrice) + "€");
        layoutArticleGrossPrice.setTextColor(Color.rgb(255, 255, 255));
        layoutArticleAmount.setText(String.valueOf(orderAmount));
        layoutArticleAmount.setTextColor(Color.rgb(255, 255, 255));
        layoutArticleSumPrice.setText(PriceUtilities.getRoundedPriceText(orderSumPrice) + "€");
        layoutArticleSumPrice.setTextColor(Color.rgb(255, 255, 255));

        // TODO: individuelles Artikel Bild
        ImageDecrypter imageDecrypter = new ImageDecrypter(article.getArticleImagePath());
        layoutArticleImage.setImageBitmap(imageDecrypter.getBitmap());

//        imageDecrypter.clean();

        final Article finalArticle = article;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showArticleDescriptionAndPriceDialog(finalArticle, context);
            }
        });

        return convertView;
    }

    /**
     * Erzeugt eine Informations Dialog mit der jeweiligen Beschreibung und dem Artikelpreis des
     * Artikels
     *
     * @param article Artikel zu dem die Beschreibung und der Preis ausgegeben werden soll
     * @param context Übergebener Kontext
     */
    private void showArticleDescriptionAndPriceDialog(Article article, Context context) {

        ShoppingCartListAdapter.showArticleDescriptionAndPriceDialog(article, context);
    }
}
