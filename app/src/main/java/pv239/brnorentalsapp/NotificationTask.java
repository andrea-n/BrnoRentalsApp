package pv239.brnorentalsapp;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class NotificationTask extends TimerTask {
    RentalsAPIClient.RentalsService mService;
    RentalsAPIClient mClient;
    Context mContext;


    public NotificationTask(Context context) {
        mContext = context;
        mClient = new RentalsAPIClient(mContext);
    }

    public void run() {

        mService = mClient.getService();
        Call<List<Offer>> call = mService.offersList();
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
                if (!sharedPreference.getString(Config.PREF_LAST_URL,"").equals("")) {
                    List<Offer> list = response.body();
                    if (list == null){
                        return;
                    }
                    Filter myFilter = new Filter(sharedPreference);
                    List<Offer> filteredList = myFilter.filter(list);
                    if (!filteredList.isEmpty()) {
                        if (filteredList.size() > 0 && !filteredList.get(0).getSource_url().equals(sharedPreference.getString(Config.PREF_LAST_URL, ""))){
                            Notifications.generateNotification(mContext, "We have new offer for you: " + filteredList.get(0).getTitle());
                            SharedPreferences.Editor editor = sharedPreference.edit().putString(Config.PREF_LAST_URL, filteredList.get(0).getSource_url());
                            editor.apply();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Log.e("OfferService", t.getMessage());
            }
        });
    }
}
