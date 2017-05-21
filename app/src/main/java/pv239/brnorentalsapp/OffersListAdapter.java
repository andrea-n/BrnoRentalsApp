package pv239.brnorentalsapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;

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

	public OffersListAdapter(List<Offer> myDataset, RentalsAPIClient client) {
		mDataset = myDataset;
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
		holder.titleTextView.setText(offer.title);
		holder.likesTextView.setText(offer.likes.toString());

		holder.likesBtn.setOnClickListener(new LikesOnclickListener(holder.likesTextView, offer.likes, offer.source_url));

		holder.streetTextView.setText(offer.street);
		if(offer.street == null) holder.streetTextView.getLayoutParams().height = 0;

		holder.priceTextView.setText(offer.price + " Kč");
		if(offer.price == null) holder.priceTextView.getLayoutParams().height = 0;

		holder.descTextView.setText(offer.description);
		if(offer.description == null) holder.descTextView.getLayoutParams().height = 0;

		if (offer.preview_image != null && offer.preview_image != "") {
			Picasso.with(holder.context).load(offer.preview_image).into(holder.imgImageView);
		}
		else {
			holder.imgImageView.getLayoutParams().height = 0;
		}

	}

	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public Offer getItem(int i) {
		return mDataset.get(i);
	}

	public class LikesOnclickListener implements View.OnClickListener
	{
		TextView likesTextView;
		Integer likesCount;
		String offerUrl;
		public LikesOnclickListener(TextView likesTextView, int likesCount, String offerUrl) {
			this.likesTextView = likesTextView;
			this.likesCount = likesCount;
			this.offerUrl = offerUrl;
		}

		@Override
		public void onClick(View v)
		{
			String encodedUrl = Base64.encodeToString(offerUrl.getBytes(), Base64.DEFAULT);
			Log.e("BASE 64 URL", encodedUrl);
			likesCount++;
			likesTextView.setText(likesCount.toString());
			RentalsAPIClient.RentalsService service = mClient.getService();

			service.likeOffer(encodedUrl);
		}
	};
}


