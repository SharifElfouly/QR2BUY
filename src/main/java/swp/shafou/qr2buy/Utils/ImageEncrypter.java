package swp.shafou.qr2buy.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * ------------------------------------*
 * - 12.02.17 ELFOULY Klasse erstellt.
 * ------------------------------------*
 *
 * Diese Klasse stellt ein byte Array anhand eines Bitmaps zusammen
 *
 *----------------ACHTUNG---------------
 * SOLLTE IN EINEM PRODUKTIVSYSTEM NICHT GEBRAUCHT WERDEN. WURDE ZU TEST ZWECKEN IMPLEMENTIERT.
 *--------------------------------------
 */
public class ImageEncrypter {

    private Bitmap image;

    private Context context;

    private int imageRessource;

    public ImageEncrypter(Context context, int imageRessource) {

        this.context = context;
        this.imageRessource = imageRessource;

        this.image = turnDrawableToBitmap(imageRessource);
    }

    /**
     * Wandelt ein Bild in ein byte Array
     *
     * @return Ein byte Array aus dem das Bild bestand
     */
    public byte[] getBytes() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    /**
     * Wandelt eine Ressource in einen Bitmap
     *
     * @return Bitmap objekt
     */
    private Bitmap turnDrawableToBitmap(int drawableRessource) {

        return BitmapFactory.decodeResource(context.getResources(),
                imageRessource);
    }

}
