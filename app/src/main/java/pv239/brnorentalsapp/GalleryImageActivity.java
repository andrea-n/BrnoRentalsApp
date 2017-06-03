package pv239.brnorentalsapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        GalleryAdapter adapter = new GalleryAdapter(this, imgUrls, ImageView.ScaleType.FIT_CENTER);
        galleryPager.setAdapter(adapter);
    }

}
