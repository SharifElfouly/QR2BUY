package swp.shafou.qr2buy.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import swp.shafou.qr2buy.Databases.ArticleDatabase.ArticleDataSource;
import swp.shafou.qr2buy.Databases.ShoppingCartDatabase.ShoppingCartDataSource;
import swp.shafou.qr2buy.POJO.Article;
import swp.shafou.qr2buy.POJO.ArticleShoppingCartWrapper;

/**
 * ------------------------------------*
 * - 05.05.17 ELFOULY Aktivität erstellt.
 * ------------------------------------*
 * <p>
 * - Diese Aktivität beeinhaltet die Kamera Funktionalitäten und die QR Code auswertung
 * <p>
 * -----------WICHTIG------------------*
 * - Das öffnen des Scanners unterbricht den Lebenszyklus der Aktivität, diese ist bei jeglichen
 * Änderungen bezüglich des Lebenszyklusses zu beachten!
 * ------------------------------------*
 */
public class ScannerActivity extends AppCompatActivity{

    /*
     * Der eigentliche Scanner der durch die ZXING Library zur Verfügung steht
     */
    private static ZXingScannerView scanner;

    /*
     * Verwaltet die Artikel Datenbank
     */
    private ArticleDataSource articleDataSource;

    /*
     * Verwalt die Warenkorb Datenbank
     */
    private ShoppingCartDataSource shoppingCartDataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisierunde der Artikel Datenbank
        articleDataSource = new ArticleDataSource(this);

        // Initialisierunde der Warenkorb Datenbank
        shoppingCartDataSource = new ShoppingCartDataSource(this);

        scanQrCode();
    }

    /**
     * TODO: wurde noch nicht getestet, könnte Schwierigkeiten bereiten
     */
    @Override
    public void onBackPressed() {

        scanner.stopCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        scanner.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        restartScanner();
    }

    @Override
    protected void onStop() {
        super.onStop();

        scanner.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        scanner.stopCamera();
    }

    public void scanQrCode() {

        scanner = new ZXingScannerView(this);
        setContentView(scanner);
        scanner.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                handleScanResult(result);
            }
        });
        scanner.startCamera();
    }

    /**
     * Startet den Scanner von neuem
     */
    public void restartScanner() {

        scanner.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                handleScanResult(result);
            }
        });
        scanner.startCamera();
    }

    /**
     * - Wird aufgerufen, falls der Scanner einen QR Code gefunden hat
     * - Falls der QR Code keiner Zahl entspricht wird eine Fehlermeldung ausgegeben
     * - Falls der QR Code aus einer Zahl besteht, wird die Artikel Datenbank nach dieser ID durchsucht
     *      - Die Id existiert in der Tabelle nicht --> Anzeige einer Fehlermeldung
     *      - Die Id existiert in der Datenbank
     *          - Der Artikel besitzt den Status "Freigegeben"
     *              - Anzeige eines Popus mit Auswahl der Menge
     * - Speichern des Artikels in die Warenkorb Datenbank
     *
     * @param result Übergebener Code nach erfolgreichem Scannen
     */
    private void handleScanResult(Result result) {

        int scannedArticleID = Integer.parseInt(result.getText());

        // Falls es den Artikel in der Tabelle gibt
        if(articleDataSource.isArticleInTable(scannedArticleID)) {

            // Artikel aus der Tabelle laden
            Article article = articleDataSource.getArticleFromTable(scannedArticleID);

            // Falls der Artikel freigegeben ist
            if(article.getArticleStatus().equals("Freigegeben")) {

                // Falls der Artikel im Warenkorb vorhanden ist
                if(shoppingCartDataSource.isArticleinShoppingCart(article.getArticleID())) {

                    Toast.makeText(this, "Dieser Artikel befindet sich bereits im Warenkorb", Toast.LENGTH_SHORT).show();

                    // Weiter scannen
                    restartScanner();
                } else {

                    // Artikel in den Warenkorb legen mit der Menge 1
                    shoppingCartDataSource.open();
                    shoppingCartDataSource.insertArticleToShoppinCartTable(new ArticleShoppingCartWrapper(article, 1));
                    shoppingCartDataSource.close();
                    Toast.makeText(this, "Der " +  article.getArticleName() + " wurde in den Warenkrob gelegt.", Toast.LENGTH_SHORT).show();

                    // Weiter scannen
                    restartScanner();
                }
            }
        } else {

            Toast.makeText(this, "Falscher Code, bitte versuchen Sie es erneut!", Toast.LENGTH_SHORT).show();

            // Weiter scannen
            restartScanner();
        }
    }

}
