package swp.shafou.qr2buy.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * ------------------------------------*
 * - 12.02.17 ELFOULY Klasse erstellt.
 * ------------------------------------*
 *
 * Diese Klasse stellt ein Bild (Bitmap) anhand eines Byte Arrays zusammen
 */
public class ImageDecrypter {

    /**
     * Byte Array aus dem das Bild besteht
     */
    private byte[] imageBytes;


    public ImageDecrypter(byte[] imageBytes) {

        this.imageBytes = imageBytes;
    }

    /**
     * Wandelt ein Byte Array in ein Bitmap Objekt
     *
     * @return Bitmap
     */
    public Bitmap getBitmap() {

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public void clean() {

        this.imageBytes = null;
    }

}
