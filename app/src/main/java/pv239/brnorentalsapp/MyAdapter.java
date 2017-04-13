package pv239.brnorentalsapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
	private List<Offer> mDataset;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView mTextView;

		public ViewHolder(LinearLayout layout) {
			super(layout);
			mTextView = (TextView) layout.findViewById(R.id.offerCardTitle);
		}
	}

	public MyAdapter(List<Offer> myDataset) {
		mDataset = myDataset;
	}

	@Override
	public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);

		ViewHolder vh = new ViewHolder(layout);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Offer offer = (Offer) getItem(position);
		holder.mTextView.setText(offer.title);
	}

	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public Offer getItem(int i) {
		return mDataset.get(i);
	}
}


