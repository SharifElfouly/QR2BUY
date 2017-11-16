package swp.shafou.qr2buy.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import swp.shafou.qr2buy.POJO.OrderPosition;

/**
 *------------------------------------*
 * - 10.02.17 ELFOULY Interface erstellt.
 *------------------------------------*
 *
 * Dieses Interaface beeinhaltet alle benötigten Methoden um Bestellungen an den Server zu senden
 */
public interface OrderAPI {

    @POST
    Call<Boolean> sendOrderToServer(@Body List<OrderPosition> orderPositions);
}
