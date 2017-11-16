package swp.shafou.qr2buy.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import swp.shafou.qr2buy.R;

/**
 * ------------------------------------*
 * - 14.12.16 ELFOULY Aktivität erstellt.
 * ------------------------------------*
 *
 * - Diese Aktivität beeinhaltet die gesamte LogIn und Regestrierungs Logik
 */
public class SignInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

    }

    /**
     * Öffnet die Login Aktivität
     *
     * @param view Aufrufende View
     */
    public void openLoginActivity(View view) {

        Intent openLoginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(openLoginActivityIntent);
    }
}
