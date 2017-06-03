package pv239.brnorentalsapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class OfferActivity extends AppCompatActivity {
    private TextView priceText;
    private TextView streetText;
    private TextView descText;
    private TextView contactText;
    private TextView infoText;
    private ViewPager galleryPager;
    private TextView likesText;
    private FloatingActionButton likeBtn;
    private FloatingActionButton contactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.offerToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent myIntent = getIntent();
        final Offer offer = (Offer) myIntent.getSerializableExtra("offer");

        getSupportActionBar().setTitle(offer.getTitle());

        ImageView bgImg = (ImageView) findViewById(R.id.offerBgImage);
        if (offer.getPreview_image() != null && offer.getPreview_image() != "") {
            Picasso.with(this).load(offer.getPreview_image()).into(bgImg);
        }
        else if(offer.getImages() != null && offer.getImages().length != 0) {
            String[] images = offer.getImages();
            Picasso.with(this).load(images[0]).into(bgImg);
        }

        if (offer.getImages() != null && offer.getImages().length != 0) {
            galleryPager = (ViewPager) findViewById(R.id.galleryPager);
            GalleryAdapter adapter = new GalleryAdapter(this, offer.getImages(), ImageView.ScaleType.CENTER_CROP, true);
            galleryPager.setAdapter(adapter);
        }

        priceText = (TextView) findViewById(R.id.offerPrice);
        streetText = (TextView) findViewById(R.id.offerStreet);
        descText = (TextView) findViewById(R.id.offerDescription);
        contactText = (TextView) findViewById(R.id.offerContacts);
        infoText = (TextView) findViewById(R.id.offerInfo);
        likesText = (TextView) findViewById(R.id.offerLikes);
        likeBtn = (FloatingActionButton) findViewById(R.id.fabLike);
        contactBtn = (FloatingActionButton) findViewById(R.id.fabContact);

        priceText.setText(offer.getPrice());
        streetText.setText(offer.getStreet());
        descText.setText(offer.getDescription());
        likesText.setText(offer.getLikes().toString());
        likeBtn.setOnClickListener(new LikesOnclickListener(likesText, offer, this, new RentalsAPIClient(this)));

        ArrayList<String> likedOffers = OfferService.getLikedOffers();
        for (String sourceUrl : likedOffers){
            if (sourceUrl.contains(offer.getSource_url())){
                likeBtn.setEnabled(false);
                likeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorAccentDisabled)));
            }
        }

        if(offer.getEmail() != null) {
            contactBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { offer.getEmail() });
                    startActivity(Intent.createChooser(intent, ""));
                }
            });
        }
        else {
            contactBtn.hide();
        }

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
