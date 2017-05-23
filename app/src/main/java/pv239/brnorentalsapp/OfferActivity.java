package pv239.brnorentalsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class OfferActivity extends AppCompatActivity {
    private TextView priceText;
    private TextView streetText;
    private TextView descText;
    private TextView contactText;
    private TextView infoText;
    private GridView galleryGridView;
    private GalleryGridAdapter galleryGridAdapter;
    private TextView likesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.offerToolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent myIntent = getIntent();
        Offer offer = (Offer) myIntent.getSerializableExtra("offer");

        getSupportActionBar().setTitle(offer.title);

        if (offer.preview_image != null && offer.preview_image != "") {
            ImageView bgImg = (ImageView) findViewById(R.id.offerBgImage);
            Picasso.with(this).load(offer.preview_image).into(bgImg);
        }

        if (offer.images != null && offer.images.length != 0) {
            galleryGridView = (GridView) findViewById(R.id.offerGalleryGrid);
            galleryGridAdapter = new GalleryGridAdapter(this, R.layout.gallery_item, offer.images);
            galleryGridView.setAdapter(galleryGridAdapter);
        }

        priceText = (TextView) findViewById(R.id.offerPrice);
        streetText = (TextView) findViewById(R.id.offerStreet);
        descText = (TextView) findViewById(R.id.offerDescription);
        contactText = (TextView) findViewById(R.id.offerContacts);
        infoText = (TextView) findViewById(R.id.offerInfo);
        likesText = (TextView) findViewById(R.id.offerLikes);

        priceText.setText(offer.price + " Kč");
        streetText.setText(offer.street);
        descText.setText(offer.description);
        likesText.setText(offer.likes.toString());

        String contacts = "";
        if(offer.email != null) contacts += offer.email + "\n";
        if(offer.phone != null) contacts += offer.phone + "\n";
        if(offer.fb_user != null) contacts += offer.fb_user + "\n";
        contactText.setText(contacts);

        String info = "";
        if(offer.type != null) info += offer.type + "\n";
        if(offer.area != null) info += offer.area + "\n";
        infoText.setText(info);
    }
}
