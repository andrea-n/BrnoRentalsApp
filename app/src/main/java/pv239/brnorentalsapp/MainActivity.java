package pv239.brnorentalsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    OfferService offerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(myToolbar);


    }

    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.offersRecycler);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        RentalsAPIClient client = new RentalsAPIClient(this);
        ProgressBar loader = (ProgressBar) findViewById(R.id.offersLoader);
        offerService = new OfferService(recycler, client, loader, this);
        offerService.loadOffers();

        if(offerService != null) {
            offerService.updateOffers();
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
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}