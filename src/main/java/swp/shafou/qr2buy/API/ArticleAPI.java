package swp.shafou.qr2buy.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import swp.shafou.qr2buy.POJO.Article;

/**
 *------------------------------------*
 * - 02.02.17 ELFOULY Interface erstellt.
 *------------------------------------*
 *
 * Dieses Interaface beeinhaltet alle benötigten Methoden um Artikel aus der Datenbank zu laden
 */
public interface ArticleAPI {

    // GET alle Artikel aus der Datenbank
    @GET("http://192.168.2.106:8080/QR2BUY-ServrNEU/api/com.secure.entity.article")
    Call<List<Article>> getAllArticles();

}
