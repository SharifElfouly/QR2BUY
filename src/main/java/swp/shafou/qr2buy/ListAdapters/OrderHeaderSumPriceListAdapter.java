package swp.shafou.qr2buy.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import swp.shafou.qr2buy.POJO.OrderHeader;
import swp.shafou.qr2buy.R;
import swp.shafou.qr2buy.Utils.PriceUtilities;

/**
 * Autor: Sharif Elfouly
 *-----------------------------------
 * - Erstellt am: 09.02.2017
 *-----------------------------------
 * Dieser Adapter baut eine View von dem gesamt Preis der übergebenen Bestellungspoistionen auf
 */
public class OrderHeaderSumPriceListAdapter extends ArrayAdapter<OrderHeader>{

    /*
     * Eine Liste mit den aufzubauenden Artikel
     */
    private static List<OrderHeader> orderHeaderList;

    /*
     * LayoutInfalter Objekt um aus dem Layout eine View zu erstellen
     */
    private LayoutInflater inflater;

    /*
     * Der Context der aufrufenden Aktivität
     */
    private Context context;

    public OrderHeaderSumPriceListAdapter(Context context, List<OrderHeader> orderHeaderList) {
        super(context, R.layout.order_header_list_item, orderHeaderList);

        // Übergebene Liste als statische Liste speichern
        OrderHeaderSumPriceListAdapter.orderHeaderList = orderHeaderList;

        // Initialisiert den aufrufenden Context
        inflater = LayoutInflater.from(context);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // Falls die View noch nicht existiert
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.order_header_list_item, parent, false);
        }

        TextView sumPriceOfOrderPositions = (TextView) convertView.findViewById(R.id.sum_price_order_positions_tv);

        // TODO: change getOrderPriceNet() to getorderPriceGross()
        String sumPriceText = PriceUtilities.getRoundedPriceText(orderHeaderList.get(position).getOrderPriceNet());
        String sumPrice = "= SUMME " + sumPriceText + "€";

        sumPriceOfOrderPositions.setText(sumPrice);
        sumPriceOfOrderPositions.setTextColor(Color.rgb(255, 255, 255));

        return convertView;
    }
}
