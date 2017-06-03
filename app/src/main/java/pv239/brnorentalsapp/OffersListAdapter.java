package pv239.brnorentalsapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.ViewHolder> {
	private List<Offer> mDataset;
	private RentalsAPIClient mClient;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView titleTextView;
		public TextView streetTextView;
		public TextView priceTextView;
		public TextView descTextView;
		public TextView likesTextView;
		public FloatingActionButton likesBtn;
		public ImageView imgImageView;
		public ImageView starImageView;
		private final Context context;

		public ViewHolder(LinearLayout layout) {
			super(layout);
			context = layout.getContext();
			titleTextView = (TextView) layout.findViewById(R.id.offerCardTitle);
			streetTextView = (TextView) layout.findViewById(R.id.offerCardStreet);
			priceTextView = (TextView) layout.findViewById(R.id.offerCardPrice);
			descTextView = (TextView) layout.findViewById(R.id.offerCardDesc);
			likesTextView = (TextView) layout.findViewById(R.id.offerCardLikes);
			likesBtn = (FloatingActionButton) layout.findViewById(R.id.offerCardLikeBtn);
			imgImageView = (ImageView) layout.findViewById(R.id.offerCardImage);
			starImageView = (ImageView) layout.findViewById(R.id.offerCardStar);
		}
	}

	public OffersListAdapter(List<Offer> myDataset, RentalsAPIClient client, Context context) {

    	Filter myFilter = new Filter(PreferenceManager.getDefaultSharedPreferences(context), context);

		mDataset = myFilter.filter(myDataset);
		mClient = client;
	}

	@Override
	public OffersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);

		final ViewHolder vh = new ViewHolder(layout);

		vh.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Offer offer = (Offer) getItem(vh.getAdapterPosition());
				Intent myIntent = new Intent(layout.getContext(), OfferActivity.class);
				myIntent.putExtra("offer", offer);
				layout.getContext().startActivity(myIntent);
			}
		});
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final Offer offer = (Offer) getItem(position);
		holder.titleTextView.setText(offer.getTitle());
		holder.likesTextView.setText(offer.getLikes().toString());

		holder.likesBtn.setOnClickListener(new LikesOnclickListener(holder.likesTextView, offer, holder.context, mClient));
		ArrayList<String> likedOffers = OfferService.getLikedOffers();
		for (String sourceUrl : likedOffers){
			if (sourceUrl.contains(offer.getSource_url())){
				holder.likesBtn.setEnabled(false);
				holder.likesBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.context, R.color.colorAccentDisabled)));
			}
		}


		holder.streetTextView.setText(offer.getStreet());
		if(offer.getStreet() == null) holder.streetTextView.getLayoutParams().height = 0;

		holder.priceTextView.setText(offer.getPrice() + " Kč");
		if(offer.getPrice() == null) holder.priceTextView.getLayoutParams().height = 0;

		holder.descTextView.setText(offer.getDescription());
		if(offer.getDescription() == null) holder.descTextView.getLayoutParams().height = 0;

		if (offer.getPreview_image() != null && offer.getPreview_image() != "") {
			Picasso.with(holder.context).load(offer.getPreview_image()).into(holder.imgImageView);
		}
		else {
			holder.imgImageView.getLayoutParams().height = 0;
			holder.titleTextView.setBackgroundColor(ContextCompat.getColor(holder.context, R.color.colorPrimary));
		}

	}

	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public Offer getItem(int i) {
		return mDataset.get(i);
	}

	public void changeData(List<Offer> newDataset) {
		mDataset.clear();
		mDataset.addAll(newDataset);
		notifyDataSetChanged();
	}
}



