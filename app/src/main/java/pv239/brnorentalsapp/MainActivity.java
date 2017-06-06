package pv239.brnorentalsapp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.Timer;

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
            Timer myTimer = new Timer();
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
