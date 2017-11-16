package swp.shafou.qr2buy.ServerCommunication;

import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import swp.shafou.qr2buy.API.ArticleAPI;
import swp.shafou.qr2buy.API.OrderAPI;
import swp.shafou.qr2buy.POJO.OrderPosition;

/**
 *------------------------------------*
 * - 10.02.17 ELFOULY Klasse erstellt.
 *------------------------------------*
 *
 * Beeinhaltet alle ausprogrammierten Methoden, die zum senden von Bestellungspositionen zum Server
 * benötigt werden.
 */
public class OrderServerCommunication {

    /*
     * URL Adresse der API
     */
    private static final String ORDER_URL = "";

    private List<OrderPosition> orderPositions;

    public OrderServerCommunication(List<OrderPosition> orderPositions) {

        this.orderPositions = orderPositions;
    }

    /**
     * Versucht eine Liste von Bestellungspositionen an den Server zu senden.
     * Falls dieses erfolgreich durchgeführt werden konnte wird <code>true</code> ausgegeben
     *
     * @param orderPositions Eine Liste von Bestellungspositionen
     * @return <code>true</code> Falls die kommunikation erfolgreich war
     */
    public boolean sendOrderToServer(List<OrderPosition> orderPositions) {

        final Boolean[] successfullCommunication = {false};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ORDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderAPI orderAPI = retrofit.create(OrderAPI.class);

        Call<Boolean> sendOrderToServerCall = orderAPI.sendOrderToServer(orderPositions);

        sendOrderToServerCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                successfullCommunication[0] = response.body();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

                successfullCommunication[0] = false;
            }
        });

        return successfullCommunication[0];
    }
}
