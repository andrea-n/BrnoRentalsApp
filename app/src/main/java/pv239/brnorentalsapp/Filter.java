package pv239.brnorentalsapp;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Filter {

    private SharedPreferences mSharedPref;
    private Context mContext;

    private boolean mAreaFilter = false;
    private boolean mTypeFilter = false;
    private boolean mAdvanceFilter = false;

    private Set<String> mAreaSet;
    private Set<String> mTypeSet;
    private Set<String> mStreetsSet;

    public Filter(SharedPreferences sharedPref, Context context){
        if (sharedPref == null || context == null){
            throw new IllegalArgumentException("sharedPref cannot be null");
        }
        mSharedPref = sharedPref;
        mContext = context;
    }

    public List<Offer> filter(List<Offer> offers){

        updateFilter();

        List<Offer> newOffers = new ArrayList<>();

        for (Offer offer: offers){
            if (canShowOffer(offer)){
                newOffers.add(offer);
            }
        }
        return newOffers;
    }

    private void updateFilter(){

        mAreaFilter = mSharedPref.getBoolean(mContext.getString(R.string.pref_area_filter),false);
        mTypeFilter = mSharedPref.getBoolean(mContext.getString(R.string.pref_type_filter),false);
        mAdvanceFilter = mSharedPref.getBoolean(mContext.getString(R.string.pref_advanced),false);

        if (mAreaFilter){
            mAreaSet = mSharedPref.getStringSet(mContext.getString(R.string.pref_multi_choice_areas),new HashSet<String>());
        } else {
            mAreaSet = null;
        }

        if (mTypeFilter){
            mTypeSet = mSharedPref.getStringSet(mContext.getString(R.string.pref_multi_choice_types),new HashSet<String>());
        } else {
            mTypeSet = null;
        }

        if (mAdvanceFilter){
            mStreetsSet = mSharedPref.getStringSet(mContext.getString(R.string.pref_saved_streets),new HashSet<String>());
        } else {
            mStreetsSet = null;
        }
    }

    private boolean canShowOffer(Offer offer){

        //TODO AREA FILTER
        /*if ( mAreaFilter ){
            if (offer.getStreet() == null){
                return false;
            }
            if (!mAreaSet.contains(offer.getStreet()))
                return false;
        }*/

        if ( mTypeFilter ){
            if (offer.getType() == null){
                if (!mTypeSet.contains("Other"))
                    return false;
            } else if (!mTypeSet.contains(offer.getType()))
                return false;
        }

        if ( mAdvanceFilter ){
            if (offer.getStreet() == null)
                return false;
            if (!mStreetsSet.contains(offer.getStreet()))
                return false;
        }

        return true;
    }


}
