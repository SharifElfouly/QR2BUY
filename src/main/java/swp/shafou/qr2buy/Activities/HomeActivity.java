package swp.shafou.qr2buy.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import swp.shafou.qr2buy.Databases.ArticleDatabase.ArticleDataSource;
import swp.shafou.qr2buy.Databases.ShoppingCartDatabase.ShoppingCartDataSource;
import swp.shafou.qr2buy.R;
import swp.shafou.qr2buy.TestData.ArticleTestData;
import swp.shafou.qr2buy.Utils.ImageEncrypter;
import swp.shafou.qr2buy.Utils.LocalCamera;
import swp.shafou.qr2buy.Utils.NetworkUtilities;

/**
 * ------------------------------------*
 * - 14.12.16 ELFOULY Aktivität erstellt.
 * - 16.12.16 ELFOULY Scanner Funktion hinzugefügt.
 * - 21.12.16 ELFOULY Speichern der gescannten Artikel in die lokale Warenkorb Datenbank.
 * - 23.12.16 ELFOULY Dynamische Anzeige der Positions Anzahl des Warenkorbs.
 * - 10.01.17 ELFOULY Anzeige einer vertikalen Liste von Artikeln
 * - 10.01.17 ELFOULY Überarbeitung des Scannen Buttons
 * ------------------------------------*
 *
 * - Diese Activity ist als Anker der Navigation anzusehen --> siehe Manifest
 * - Diese Activity beeinhaltet die Logik zu Scannen eines Barcodes
 * - Als Library zum Scannen wird ZXING benutzt
 * - Die Methode handleResult wird aufgerufen, falls der Scanner einen QR Code scannen konnte
 *
 * -----------WICHTIG------------------*
 * - Das öffnen des Scanners unterbricht den Lebenszyklus der Aktivität, diese ist bei jeglichen
 * Änderungen bezüglich des Lebenszyklusses zu beachten!
 * ------------------------------------*
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*
     * Der eigentliche Scanner der durch die ZXING Library zur Verfügung steht
     */
    private static ZXingScannerView scanner;

    // TODO:Test Daten MÜSSEN GELÖSCHT WERDEN
    public static byte[] snickerArray;
    public static byte[] cocacolaArray;
    public static byte[] fantaArray;
    public static byte[] chipsArray;
    public static byte[] spriteArray;
    public static byte[] marsArray;

    /**
     * Dient zur Initialisierung der Activity
     * Setzen des activity_home Layout
     * Setzen der Toolbar und der NavigationView
     *
     * @param savedInstanceState Ein bisheriger "State" kann übergeben werden
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setze Layout
        setContentView(R.layout.activity_home);

        // Initialisierunde der Warenkorb Datenbank
        ShoppingCartDataSource shoppingCartDataSource = new ShoppingCartDataSource(this);

        // Initialisierunde der Artikel Datenbank
        ArticleDataSource articleDataSource = new ArticleDataSource(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Anzeige der im Warenkorb befindenen Artikel Position
        TextView shoppingCartCounterTextView = (TextView) findViewById(R.id.shopping_cart_position_counter);

        // Gibt die Anzahl der Artikelpositionen im Warenkorb an
        String numberOfArticlesInShoppinCart = String.valueOf(shoppingCartDataSource.getNumberOfShoppingCartItems());
        shoppingCartCounterTextView.setText(numberOfArticlesInShoppinCart);

        // Setze Toolbar
        setSupportActionBar(toolbar);

        // Setzt die ActionBar mithilfe des Toolbars und dem DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Setzt den listener für fie Items in der NavigationBar
        navigationView.setNavigationItemSelectedListener(this);

        /*
         * Direkt beim Anrufen der Activity wird der Benutzer nach der Erlaubnis gefragt, die
         * Kamera zu nutzen.
         * Falls die Kamera Erlaubnis bereits erteilt wurde, wird nicht mehr nachfgefragt
         */
        if (!LocalCamera.isCameraPermissionEnabled(this)) {

            LocalCamera.askForCameraPermission(this);
        }

        // Initialisierung des Scanners
        scanner = new ZXingScannerView(this);

//        ArticleServerCommunication articleServerCommunication = new ArticleServerCommunication(this);

        /*
         * Falls eine aktive Internetverbindung besteht werden die Artikel aus der Datenbank geladen
         */
        if(NetworkUtilities.isNetworkAvailable(this)) {

            // TODO: Server Kommunikation
//            articleServerCommunication.getAllArticlesFromServer();
        }

        /**
         * Image byte array Test daten TODO: SOllten gelöscht werden!!!
         */
        ImageEncrypter imageEncrypter = new ImageEncrypter(this, R.mipmap.snickers);
        snickerArray = imageEncrypter.getBytes();

        imageEncrypter = new ImageEncrypter(this, R.mipmap.cocacola);
        cocacolaArray = imageEncrypter.getBytes();

        imageEncrypter = new ImageEncrypter(this, R.mipmap.fanta);
        fantaArray = imageEncrypter.getBytes();

        imageEncrypter = new ImageEncrypter(this, R.mipmap.chips);
        chipsArray = imageEncrypter.getBytes();

        imageEncrypter = new ImageEncrypter(this, R.mipmap.sprite);
        spriteArray = imageEncrypter.getBytes();

        imageEncrypter = new ImageEncrypter(this, R.mipmap.mars);
        marsArray = imageEncrypter.getBytes();

        /**
         * DATABASE REFRESH TEST
         */
//        boolean dbSucces;
//        articleDataSource.open();
//        articleDataSource.removeAllArticlesInTable();
//        dbSucces = articleDataSource.insertArticleListToArticleTable(ArticleTestData.articleTestList);
//        articleDataSource.close();



//        byte[] test;
//        Article testArticle = articleDataSource.getArticleFromTable(2999980);
//        test = testArticle.getArticleImagePath();

        ImageView image1 = (ImageView) findViewById(R.id.image1);
        ImageView image2 = (ImageView) findViewById(R.id.image2);
        ImageView image3 = (ImageView) findViewById(R.id.image3);
        ImageView image4 = (ImageView) findViewById(R.id.image4);
        ImageView image5 = (ImageView) findViewById(R.id.image5);


        image1.setImageResource(R.mipmap.chips);
        image2.setImageResource(R.mipmap.sprite);
        image3.setImageResource(R.mipmap.cocacola);
        image4.setImageResource(R.mipmap.mars);
        image5.setImageResource(R.mipmap.snickers);


    }

    /**
     * Wird aufgerufen wenn die Aktivität pausiert wird
     */
    @Override
    protected void onPause() {
        super.onPause();

        scanner.stopCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        scanner.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scanner.startCamera();
    }

    /**
     * Wird aufgerufen wenn die Aktivität beendet wird
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        scanner.stopCamera();
    }

    /**
     * Wird ausgeführt wenn der Zurück Button auf dem Gerät betätigt wird
     */
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Falls der Drawer in der derzeitigen View existiert
        if (drawer != null) {

            // Falls das Navigationsfeld geöffnet ist
            if (drawer.isDrawerOpen(GravityCompat.START)) {

                // Navigations Feld schließen
                drawer.closeDrawer(GravityCompat.START);
            } else {

                // Applikation beenden
                new AlertDialog.Builder(this)
                        .setTitle("Applikation Schließen")
                        .setMessage("Möchten Sie die Applikation wirklich verlassen?")
                        .setNegativeButton("Nein", null)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HomeActivity.super.onBackPressed();
                            }
                        }).setCancelable(false)
                        .create().show();
            }
        } else {

            onCreate(null);
        }

        scanner.stopCamera();
    }

    /**
     * Setzt den Listener für alle Menu Items der NaviagtionBar
     *
     * @param item Ein Item in der Navigations Bar
     * @return <code>true</code>
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_about_us) {

            Intent openAboutUs = new Intent(this, AboutUsActivity.class);
            startActivity(openAboutUs);
        } else if (id == R.id.nav_orders) {

            Intent openOrders = new Intent(this, OrdersActivity.class);
            startActivity(openOrders);
        } else if (id == R.id.nav_settings) {

            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
        }

        // Geöffnetes Navigations Feld schließen
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * - Wird aufgerufen wenn der Login Text des Navigations Feldes geklickt wird
     * - Öffnet die Login Activity
     *
     * @param view Übergebene View beim betätigen des Einloggen/Regestrieren Textes
     */
    public void openSignInActivity(View view) {

        Intent openLogIn = new Intent(this, SignInActivity.class);
        startActivity(openLogIn);
    }

    /**
     * Wird aufgerufen wenn der Warenkorb Button gedrückt wird
     * Öffnet die Warenkorb Activity
     *
     * @param view Übergebene View beim betätigen des Warenkorb Buttons
     */
    public void openShoppingCartActivity(View view) {

        Intent openShoppingCart = new Intent(this, ShoppingCartActivity.class);
        startActivity(openShoppingCart);
    }

    public void openCameraActivity(View view) {

        Intent intent = new Intent(this, ScannerActivity.class);
        startActivity(intent);
    }


    // TODO: Klasse die, Bilder übersetzt
    public static byte[] turnBitmapToBytes(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public Bitmap turnDrawableToBitmap(int id) {

        return BitmapFactory.decodeResource(this.getResources(),
                id);
    }

//    public static Bitmap turnBytesToBitmap(byte[] bitmapdata) {
//
//        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
//    }
}
