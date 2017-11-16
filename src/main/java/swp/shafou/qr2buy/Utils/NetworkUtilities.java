package swp.shafou.qr2buy.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import swp.shafou.qr2buy.R;

/**
 * Autor: Sharif Elfouly
 * Erstellt am: 15.12.2016
 *
 * Diese Klasse stellt Methoden zur Verfügung, die überall in der Applikation
 * genutzt werden
 */
public class NetworkUtilities {

    /**
     * Testet ob eine aktive Internet Verbindung bereit steht
     * @param context
     * @return <code>true</code> falls eine Verbindung besteht
     */
    public static boolean isNetworkAvailable(Context context) {

        boolean isConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        isConnected = activeNetwork != null && activeNetwork.isConnected();
        return isConnected;
    }

    /**
     * Ein Dialog, der geöffnet wird, falls eine aktive Internet Verbindung gebraucht wird,
     * diese aber nicht zur Verfügung steht
     * @param context
     */
    public static void showNoNetworkDialog(final Context context) {

        new AlertDialog.Builder(context)
                .setTitle(R.string.no_network_found_title)
                .setMessage(R.string.no_network_function)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setCancelable(false)
                .create().show();
    }
}
