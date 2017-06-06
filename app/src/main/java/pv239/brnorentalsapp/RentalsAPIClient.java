package pv239.brnorentalsapp;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by Andrea Navratilova on 4/17/2017.
 */
public class RentalsAPIClient {
    private Context mContext;
    public static RentalsService rentalService;

    public RentalsAPIClient(Context context) {
        mContext = context;
    }

    public RentalsService getService() {
        String apiUrl = Config.API_URL;
        if(apiUrl!=null) {
            if (rentalService == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(apiUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                rentalService = retrofit.create(RentalsService.class);
            }

            return rentalService;
        }

        return null;
    }

    public interface RentalsService {
        @GET("/")
        Call<List<Offer>> offersList();

        // send Base 64 encoded Url to like offer
        @POST("/like/")
        Call<Offer> likeOffer(@Query("id") String encodedUrl);
    }
}
