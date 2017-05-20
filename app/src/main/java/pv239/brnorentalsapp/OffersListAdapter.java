package pv239.brnorentalsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.ViewHolder> {
	private List<Offer> mDataset;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView titleTextView;
		public TextView streetTextView;
		public TextView priceTextView;
		public TextView descTextView;
		public TextView likesTextView;
		public ImageView imgImageView;
		private final Context context;

		public ViewHolder(LinearLayout layout) {
			super(layout);
			context = layout.getContext();
			titleTextView = (TextView) layout.findViewById(R.id.offerCardTitle);
			streetTextView = (TextView) layout.findViewById(R.id.offerCardStreet);
			priceTextView = (TextView) layout.findViewById(R.id.offerCardPrice);
			descTextView = (TextView) layout.findViewById(R.id.offerCardDesc);
			likesTextView = (TextView) layout.findViewById(R.id.offerCardLikes);
			imgImageView = (ImageView) layout.findViewById(R.id.offerCardImage);
		}
	}

	public OffersListAdapter(List<Offer> myDataset) {
		mDataset = myDataset;
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
		Offer offer = (Offer) getItem(position);
		holder.titleTextView.setText(offer.title);
		holder.likesTextView.setText(offer.likes.toString());

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
}


