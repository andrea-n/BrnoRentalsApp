package pv239.brnorentalsapp;

import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import java.util.Timer;

public class GalleryImageActivity extends AppCompatActivity {

    private ViewPager galleryPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery_image);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String[] imgUrls = getIntent().getStringArrayExtra("imgUrls");

        galleryPager = (ViewPager) findViewById(R.id.galleryFullPager);
        GalleryAdapter adapter = new GalleryAdapter(this, imgUrls, ImageView.ScaleType.FIT_CENTER, false);
        galleryPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

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

}
