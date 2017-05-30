package pv239.brnorentalsapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrea Navratilova on 4/17/2017.
 */
public class OfferService {
	private RentalsAPIClient mClient;
	private RecyclerView mRecycler;
	private RentalsAPIClient.RentalsService service;
	private OffersListAdapter adapter;
	private List<Offer> list;

	public OfferService(RecyclerView recycler, RentalsAPIClient client) {
		mRecycler = recycler;
		mClient = client;
	}

	public void loadOffers() {
		service = mClient.getService();
		Call<List<Offer>> call = service.offersList();
		call.enqueue(new Callback<List<Offer>>() {
			@Override
			public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
				list = response.body();
				adapter = new OffersListAdapter(list, mClient);
				mRecycler.setAdapter(adapter);
			}

			@Override
			public void onFailure(Call<List<Offer>> call, Throwable t) {
				Log.e("OfferService", t.getMessage());
			}
		});
	}

	public void updateOffers() {
		if(adapter != null) {
			Call<List<Offer>> call = service.offersList();
			call.enqueue(new Callback<List<Offer>>() {
				@Override
				public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
					list = response.body();
					adapter.changeData(list);
				}

				@Override
				public void onFailure(Call<List<Offer>> call, Throwable t) {
					Log.e("OfferService", t.getMessage());
				}
			});
		}
	}
}
