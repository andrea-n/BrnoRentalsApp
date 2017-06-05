package pv239.brnorentalsapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    OfferService offerService;
    private ProgressBar loader;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(myToolbar);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.offersRecycler);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        RentalsAPIClient client = new RentalsAPIClient(this);
        loader = (ProgressBar) findViewById(R.id.offersLoader);
        offerService = new OfferService(recycler, client, loader, this);

        offerService.loadOffers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(offerService != null) {
            offerService.updateOffers();
        }

        // cancel notification "listener"
        if (mTimer != null)
            mTimer.cancel();
    }

    protected void onPause(){
        super.onPause();

        // start notification "listener"
        mTimer = new Timer();
        NotificationTask myTask = new NotificationTask(this);
        mTimer.schedule(myTask, 5000, 5000);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsAction:
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void generateNotification(Context context, String message) {

        int icon = R.mipmap.ic_notification_white;
        long when = System.currentTimeMillis();
        String appname = context.getResources().getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Notification notification;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);

        long[] pattern = {500,500,500,500};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification = builder.setContentIntent(contentIntent)
                .setSmallIcon(icon)
                .setTicker(appname)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(appname)
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(pattern)
                .setContentText(message)
                .setSound(alarmSound)
                .build();

        notificationManager.notify((int) when, notification);


    }

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
                        myFilter.filter(list);
                        if (!list.isEmpty()) {
                            if (list.size() > 0 && !list.get(0).getSource_url().equals(sharedPreference.getString(Config.PREF_LAST_URL, ""))){
                                generateNotification(mContext, "We have new offer for you:" + list.get(0).getTitle());
                                SharedPreferences.Editor editor = sharedPreference.edit().putString(Config.PREF_LAST_URL, list.get(0).getSource_url());
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

}