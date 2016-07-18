package tech.beesknees.ripely.network;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tech.beesknees.ripely.model.UsdaInfo;

/**
 * Created by littleBIG on 5/13/2016.
 */
public class UsdaApiClient {


    private static UsdaApiInterface usdaApiInterface;
    private static String baseUrl = "http://api.nal.usda.gov/";

    public static UsdaApiInterface getClient() {
        if (usdaApiInterface == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            usdaApiInterface = client.create(UsdaApiInterface.class);
        }
        return usdaApiInterface;
    }

    public interface UsdaApiInterface {
        @GET("ndb/reports")
        Call<UsdaInfo> getUsdaNutrients(@Query("ndbno") String number,
                                        @Query("type") String type,
                                        @Query("format") String format,
                                        @Query("api_key") String key);
    }
}
