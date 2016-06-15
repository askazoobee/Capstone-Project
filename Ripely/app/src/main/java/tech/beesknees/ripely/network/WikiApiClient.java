package tech.beesknees.ripely.network;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tech.beesknees.ripely.model.WikiInfo;

/**
 * Created by littleBIG on 5/12/2016.
 */
public class WikiApiClient {

    private static WikiApiInterface wikiApiInterface;
    private static String baseUrl = "https://en.wikipedia.org/api/rest_v1/";

    public static WikiApiInterface getClient() {
        if (wikiApiInterface == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            wikiApiInterface = client.create(WikiApiInterface.class);
        }
        return wikiApiInterface;
    }

    public interface WikiApiInterface {
        @GET("page/summary/{title}?redirect=true")
        Call<WikiInfo> getWikiDesc(@Path("title") String name);
    }
}