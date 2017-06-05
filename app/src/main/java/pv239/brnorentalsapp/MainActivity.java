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
        Timer myTimer = Notifications.getTimer();
        if (myTimer != null)
            myTimer.cancel();
    }

    @Override
    protected void onPause(){
        super.onPause();

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Config.PREF_NOTIFICATIONS, false)){
            // start notification "listener"
            Timer myTimer = Notifications.getTimer();
            myTimer = new Timer();
            NotificationTask myTask = new NotificationTask(this);
            myTimer.schedule(myTask, 5000, 5000);
            Notifications.setTimer(myTimer);
        }
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
            case R.id.reloadAction:
                if(offerService != null) {
                    offerService.updateOffers();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
