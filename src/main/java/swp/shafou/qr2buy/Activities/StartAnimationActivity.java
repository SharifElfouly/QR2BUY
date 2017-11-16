package swp.shafou.qr2buy.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.TimerTask;

import swp.shafou.qr2buy.R;

/**
 * ------------------------------------*
 * - 14.12.16 ELFOULY Aktivität erstellt.
 * ------------------------------------*
 *
 * - Diese Activity ruft den Start up Screen der Applikation auf
 */
public class StartAnimationActivity extends Activity{

    // Gibt in ms an wie lange das start_animation layout angezeit werden soll
    private static final int ANIMATION_DURATION_IN_MS = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_animation);

        new Handler().postDelayed(new TimerTask() {
            @Override
            public void run() {
                Intent launchHomeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(launchHomeActivity);
            }
        }, ANIMATION_DURATION_IN_MS);
    }
}
