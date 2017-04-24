package pv239.brnorentalsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.ViewHolder> {
	private List<Offer> mDataset;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView titleTextView;
		public TextView streetTextView;
		public TextView priceTextView;
		public TextView descTextView;
		private final Context mContext;

		public ViewHolder(LinearLayout layout) {
			super(layout);
			mContext = layout.getContext();
			titleTextView = (TextView) layout.findViewById(R.id.offerCardTitle);
			streetTextView = (TextView) layout.findViewById(R.id.offerCardStreet);
			priceTextView = (TextView) layout.findViewById(R.id.offerCardPrice);
			descTextView = (TextView) layout.findViewById(R.id.offerCardDesc);
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
		holder.streetTextView.setText(offer.street);
		holder.priceTextView.setText(offer.price);
		holder.descTextView.setText(offer.description);
	}

	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public Offer getItem(int i) {
		return mDataset.get(i);
	}
}


