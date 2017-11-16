package swp.shafou.qr2buy.ServerCommunication;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import swp.shafou.qr2buy.API.ArticleAPI;
import swp.shafou.qr2buy.Databases.ArticleDatabase.ArticleDataSource;
import swp.shafou.qr2buy.POJO.Article;

/**
 *------------------------------------*
 * - 10.02.17 ELFOULY Klasse erstellt.
 *------------------------------------*
 *
 * Beeinhaltet alle ausprogrammierten Methoden, die zum empfangen von Artikel vom Server
 * bennötigt werden.
 */
public class ArticleServerCommunication {

    /*
     * URL Adresse der ArtikelAPI
     */
    private static final String ARTICLE_URL = "";

    /*
     * Verwaltet die Artikel Datenbank
     */
    private ArticleDataSource articleDataSource;

    /*
     * Kontext der aufrufenden Aktivität
     */
    private Context context;

    public ArticleServerCommunication(Context context) {

        this.context = context;
        articleDataSource = new ArticleDataSource(context);
    }

    /**
     * Gibt alle Artikel der Server Datenbank zurück
     */
    public boolean getAllArticlesFromServer() {

        final Boolean[] successfullCommunication = {false};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ARTICLE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticleAPI articleAPI = retrofit.create(ArticleAPI.class);

        Call<List<Article>> callServer = articleAPI.getAllArticles();

        callServer.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {

                articleDataSource.open();
                articleDataSource.insertArticleListToArticleTable(response.body());
                articleDataSource.close();

                successfullCommunication[0] = true;
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {

                successfullCommunication[0] = false;
            }
        });

        return successfullCommunication[0];
    }
}
