package pv239.brnorentalsapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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
    }
}
