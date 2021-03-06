package pv239.brnorentalsapp;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
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
	private static ArrayList<String> likedOffersUrls;
	private ProgressBar mLoader;
	private Context mContext;

	public OfferService(RecyclerView recycler, RentalsAPIClient client, ProgressBar loader, Context context) {
		mRecycler = recycler;
		mClient = client;
		mLoader = loader;
		mContext = context;
	}

	public void loadOffers() {
		service = mClient.getService();
		Call<List<Offer>> call = service.offersList();
		call.enqueue(new Callback<List<Offer>>() {
			@Override
			public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
				list = response.body();
				if(list != null) {
					adapter = new OffersListAdapter(list, mClient, mContext);
					mRecycler.setAdapter(adapter);
					mLoader.setVisibility(View.GONE);
					mRecycler.setVisibility(View.VISIBLE);
				}
				else loadOffers();
			}

			@Override
			public void onFailure(Call<List<Offer>> call, Throwable t) {
				Log.e("OfferService", t.getMessage());
			}
		});
	}

	public void updateOffers() {
		mLoader.setVisibility(View.VISIBLE);
		mRecycler.setVisibility(View.GONE);
		if(adapter != null) {
			Call<List<Offer>> call = service.offersList();
			call.enqueue(new Callback<List<Offer>>() {
				@Override
				public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
					Filter myFilter = new Filter(PreferenceManager.getDefaultSharedPreferences(mContext));
					list = myFilter.filter(response.body());

					if(list != null) {
						adapter.changeData(list);
						mLoader.setVisibility(View.GONE);
						mRecycler.setVisibility(View.VISIBLE);
					}
					else loadOffers();
				}

				@Override
				public void onFailure(Call<List<Offer>> call, Throwable t) {
					Log.e("OfferService", t.getMessage());
				}
			});
		}
	}

	public static ArrayList<String> getLikedOffers() {
		if (likedOffersUrls == null) {
			likedOffersUrls = new ArrayList<>();
		}
		return likedOffersUrls;
	}

	public static void addLikedOffer(String sourceUrl) {
		if (likedOffersUrls == null) {
			likedOffersUrls = new ArrayList<>();
		}
		likedOffersUrls.add(sourceUrl);
	}
}
