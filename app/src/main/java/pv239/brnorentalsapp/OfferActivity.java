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
    private FloatingActionButton likeBtn;

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

        getSupportActionBar().setTitle(offer.getTitle());

        if (offer.getPreview_image() != null && offer.getPreview_image() != "") {
            ImageView bgImg = (ImageView) findViewById(R.id.offerBgImage);
            Picasso.with(this).load(offer.getPreview_image()).into(bgImg);
        }

        if (offer.getImages() != null && offer.getImages().length != 0) {
            galleryGridView = (GridView) findViewById(R.id.offerGalleryGrid);
            galleryGridAdapter = new GalleryGridAdapter(this, R.layout.gallery_item, offer.getImages());
            galleryGridView.setAdapter(galleryGridAdapter);
        }

        priceText = (TextView) findViewById(R.id.offerPrice);
        streetText = (TextView) findViewById(R.id.offerStreet);
        descText = (TextView) findViewById(R.id.offerDescription);
        contactText = (TextView) findViewById(R.id.offerContacts);
        infoText = (TextView) findViewById(R.id.offerInfo);
        likesText = (TextView) findViewById(R.id.offerLikes);
        likeBtn = (FloatingActionButton) findViewById(R.id.fabLike);

        priceText.setText(offer.getPrice() + " Kč");
        streetText.setText(offer.getStreet());
        descText.setText(offer.getDescription());
        likesText.setText(offer.getLikes().toString());
        likeBtn.setOnClickListener(new LikesOnclickListener(likesText, offer, this, new RentalsAPIClient(this)));

        String contacts = "";
        if(offer.getEmail() != null) contacts += offer.getEmail() + "\n";
        if(offer.getPhone() != null) contacts += offer.getPhone() + "\n";
        if(offer.getFb_user() != null) contacts += offer.getFb_user() + "\n";
        contactText.setText(contacts);

        String info = "";
        if(offer.getType() != null) info += offer.getType() + "\n";
        if(offer.getArea() != null) info += offer.getArea() + "\n";
        infoText.setText(info);
    }
}
