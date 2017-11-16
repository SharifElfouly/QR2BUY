package swp.shafou.qr2buy.ServerCommunication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import swp.shafou.qr2buy.API.ArticleAPI;
import swp.shafou.qr2buy.API.LoginAPI;
import swp.shafou.qr2buy.POJO.Article;
import swp.shafou.qr2buy.POJO.UserLoginRequest;
import swp.shafou.qr2buy.POJO.UserToken;

/**
 *------------------------------------*
 * - 11.02.17 ELFOULY Klasse erstellt.
 *------------------------------------*
 *
 * Beeinhaltet alle ausprogrammierten Methoden, die zum neu regestrieren und anmelden eines Kunden
 * benötigt werden
 */
public class UserServerCommunication {

    /*
     * URL Adresse der LogInAPI
     */
    private static final String LOG_IN_URL = "";

    /*
     * URL Adresse der SignUpAPI
     */
    private static final String SIGN_UP_URL= "";

    public UserServerCommunication() {


    }

    /**
     * Loggt einen User ein
     * --> Falls erfolgreich, wird eine UserToken Objekt zurückgegeben
     * --> Falls nicht erfolgreich, ist das UserToken Objekt = null
     *
     * @param userLoginRequest
     * @return Falls erfolgreich UserToken != null
     */
    public UserToken logInToServer(UserLoginRequest userLoginRequest) {

        final UserToken[] userToken = {new UserToken()};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOG_IN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI articleAPI = retrofit.create(LoginAPI.class);

        Call<UserToken> callServer = articleAPI.loginUser(userLoginRequest);

        callServer.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {

                if(response.body() != null) {

                    userToken[0] = response.body();
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {


            }
        });

        return userToken[0];
    }

    // TODO: Methode signUpToServer()...

}
