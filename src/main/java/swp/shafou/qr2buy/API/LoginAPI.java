package swp.shafou.qr2buy.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import swp.shafou.qr2buy.POJO.UserLoginRequest;
import swp.shafou.qr2buy.POJO.UserToken;

/**
 *------------------------------------*
 * - 03.01.17 ELFOULY Interface erstellt.
 *------------------------------------*
 *
 * Dieses Interaface beeinhaltet alle benötigten Methoden zum Login eines Users
 */
public interface LoginAPI {

    /**
     * Sendet dem Server ein UserLoginRequest
     * Falls dieser exisitiert wird ein UserToken zurück geschickt
     *
     * @param user Übergebenes UserLoginRequest Objekt
     * @return UserToken
     */
    @POST("/com.secure.authentication")
    Call<UserToken> loginUser(@Body UserLoginRequest user);

}
