package swp.shafou.qr2buy.Activities;

import android.os.Bundle;

import swp.shafou.qr2buy.R;
import swp.shafou.qr2buy.Utils.AppCompatPreferenceActivity;

/**
 * ------------------------------------*
 * - 15.12.16 ELFOULY Aktivität erstellt.
 * ------------------------------------*
 *
 * - Diese Activity beeinhaltet die gesamte Einstellungs möglichkeiten der App.
 * TODO: Still crashes
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_layout);
    }
}
