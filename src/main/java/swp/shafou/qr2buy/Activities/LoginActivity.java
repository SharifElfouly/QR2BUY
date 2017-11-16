package swp.shafou.qr2buy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import swp.shafou.qr2buy.API.LoginAPI;
import swp.shafou.qr2buy.POJO.UserLoginRequest;
import swp.shafou.qr2buy.POJO.UserToken;
import swp.shafou.qr2buy.R;
import swp.shafou.qr2buy.Utils.NetworkUtilities;

/**
 * ------------------------------------*
 * - 04.01.17 ELFOULY Aktivität erstellt.
 * ------------------------------------*
 *
 * Diese Activity steuert das login Layout
 */
public class LoginActivity extends AppCompatActivity{

    // Zur aufrufender URL um den Login abzufragen
    private static final String loginURL = "http://192.168.2.106:8080/";

    private EditText emailInputField;

    private EditText passwordInputField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        emailInputField = (EditText) findViewById(R.id.email_inputfield);
        passwordInputField = (EditText) findViewById(R.id.password_inputfield);
    }

    /**
     * Versucht einen User einzuloggen
     *
     * @param view Übergebene View des Layouts
     */
    public void loginUser(View view) {

        boolean isUser = isValidUser(emailInputField.getText().toString(), passwordInputField.getText().toString());

        if(NetworkUtilities.isNetworkAvailable(this)) {

            if(isUser) {

                Toast.makeText(this, "Sie haben sich erfolgreich eingeloggt!", Toast.LENGTH_LONG).show();

                openHomeActivity();
            } else {

                Toast.makeText(this, "Falsche Daten. Versuchen Sie es erneut!", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(this, "Bitte überprüfen Sie ihre Internetverbindung!", Toast.LENGTH_LONG).show();
        }

    }

    private void openHomeActivity() {

        Intent openHomeActivityIntent = new Intent(this, HomeActivity.class);
        startActivity(openHomeActivityIntent);
    }

    /**
     * Ruft die Datenbank auf um zu testen ob es den User mit dieser Emial Adresse und Passwort
     * gibt
     *
     * @param email Die angegebene Email des Users
     * @param password Das angegebene Passswort des Users
     * @return <code>true</code> Falls der User bekannt ist und dieser sich anmelden konnte
     */
    private boolean isValidUser(String email, String password) {

        final boolean[] isValidUser = {false};

        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(loginURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI apiService =
                retrofit.create(LoginAPI.class);

        Call<UserToken> call = apiService.loginUser(userLoginRequest);

        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {

                if(response.body().getTOKEN_HASH() != null) {

                    isValidUser[0] = true;
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {

            }
        });

        return isValidUser[0];
    }
}
