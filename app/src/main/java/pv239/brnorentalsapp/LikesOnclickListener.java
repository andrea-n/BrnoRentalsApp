package pv239.brnorentalsapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikesOnclickListener implements View.OnClickListener
{
	TextView likesTextView;
	Offer offer;
	Boolean liked = false;
	Context context;
	RentalsAPIClient client;

	public LikesOnclickListener(TextView likesTextView, Offer offer, Context context, RentalsAPIClient client) {
		this.likesTextView = likesTextView;
		this.offer = offer;
		this.context = context;
		this.client = client;
	}

	@Override
	public void onClick(View v)
	{
		if(!liked) {
			RentalsAPIClient.RentalsService service = client.getService();
			String encodedUrl = Base64.encodeToString(offer.getSource_url().getBytes(), Base64.CRLF);
			final View btn = v;

			Call<Offer> call= service.likeOffer(encodedUrl);
			Log.e("CALL",call.request().toString());

			call.enqueue(new Callback<Offer>() {
				@Override
				public void onResponse(Call<Offer> call, Response<Offer> response) {
					Offer nOffer = response.body();
					likesTextView.setText(nOffer.getLikes().toString());
					offer.setLikes(nOffer.getLikes());
					liked = true;
					OfferService.addLikedOffer(offer.getSource_url());
					btn.setEnabled(false);
					btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccentDisabled)));
				}

				@Override
				public void onFailure(Call<Offer> call, Throwable t) {
					Log.e("Like offer", t.getMessage());
				}
			});
		}
	}
}
