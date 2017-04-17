package pv239.brnorentalsapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

		public ViewHolder(LinearLayout layout) {
			super(layout);
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
		LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);

		ViewHolder vh = new ViewHolder(layout);
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


