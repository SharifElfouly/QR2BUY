package swp.shafou.qr2buy.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Autor: Sharif Elfouly
 * Erstellt am: 04.02.2017
 *
 * Dieses Klasse enthält Kamera Funktionen
 */
public class LocalCamera {

    /*
     * Erlaubniss Code der Kamera
     */
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1001;


    /**
     * Fragt den User um Erlaubniss die Kamera benutzen zu dürfen
     */
    public static void askForCameraPermission(Activity activity) {

        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);
    }


    /**
     * Testet ob die Camera Erlaubniss erteilt worden ist
     *
     * @return true falls Erlaubnis erteilt worden ist
     */
    public static boolean isCameraPermissionEnabled(Activity activity) {

        boolean cameraPermissionIsEnabled = false;

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            cameraPermissionIsEnabled = true;
        }

        return cameraPermissionIsEnabled;
    }



}
