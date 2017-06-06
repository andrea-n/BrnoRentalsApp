package pv239.brnorentalsapp;


import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Filter {

    private SharedPreferences mSharedPref;

    private boolean mFilter = false;
    private boolean mAreaFilter = false;
    private boolean mTypeFilter = false;
    private boolean mShowNullArea = false;
    private boolean mMinAreaFilter = false;
    private boolean mMaxAreaFilter = false;

    private Set<String> mAreaSet;
    private Set<String> mTypeSet;
    private Integer mMinArea;
    private Integer mMaxArea;

    public Filter(SharedPreferences sharedPref){
        if (sharedPref == null){
            throw new IllegalArgumentException("sharedPref cannot be null");
        }
        mSharedPref = sharedPref;
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

        mFilter = mSharedPref.getBoolean(Config.PREF_FILTERS,false);
        mAreaFilter = mSharedPref.getBoolean(Config.PREF_AREA_FILTER,false);
        mTypeFilter = mSharedPref.getBoolean(Config.PREF_TYPE_FILTER,false);
        mShowNullArea = mSharedPref.getBoolean(Config.PREF_SHOW_NULL_AREA,true);
        mMinAreaFilter = mSharedPref.getBoolean(Config.PREF_MIN_FLAT_AREA_FILTER,false);
        mMaxAreaFilter = mSharedPref.getBoolean(Config.PREF_MAX_FLAT_AREA_FILTER,false);

        if (mAreaFilter){
            mAreaSet = mSharedPref.getStringSet(Config.PREF_MULTI_CHOICE_AREAS,new HashSet<String>());
        } else {
            mAreaSet = null;
        }

        if (mTypeFilter){
            mTypeSet = mSharedPref.getStringSet(Config.PREF_MULTI_CHOICE_TYPES,new HashSet<String>());
        } else {
            mTypeSet = null;
        }

        if (mMinAreaFilter){
            mMinArea = mSharedPref.getInt(Config.PREF_MIN_AREA,Config.MIN_APARTMENT_AREA);
        } else {
            mMinArea = Config.MIN_APARTMENT_AREA;
        }

        if (mMaxAreaFilter){
            mMaxArea = mSharedPref.getInt(Config.PREF_MAX_AREA,Config.MAX_APARTMENT_AREA);
        } else {
            mMaxArea = Config.MAX_APARTMENT_AREA;
        }

    }

    private boolean canShowOffer(Offer offer){

        if (!mFilter)
            return true;

        if ( mAreaFilter ){
            if (! isInSelectedTownAreas(offer))
                return false;
        }

        if ( mTypeFilter ){
            if (offer.getType() == null){
                if (!mTypeSet.contains("Other"))
                    return false;
            } else if (!mTypeSet.contains(offer.getType()))
                return false;
        }

        if ( mMinAreaFilter ){
            if ( offer.getArea() == null && !mShowNullArea)
                return false;
            else if ( offer.getArea() != null && Integer.valueOf(getAreaString(offer.getArea())) < mMinArea)
                return false;
        }

        if ( mMaxAreaFilter ){
            if ( offer.getArea() == null && !mShowNullArea)
                return false;
            else if ( offer.getArea() != null && Integer.valueOf(getAreaString(offer.getArea())) > mMaxArea)
                return false;
        }

        return true;
    }



    private boolean isInSelectedTownAreas(Offer offer){
        for (String area : mAreaSet){
            for (String keyWord : getKeyWordForArea(area)){
                if (offer.getTitle() != null && offer.getTitle().toLowerCase().contains(keyWord))
                    return true;
                if (offer.getTitle() != null && offer.getDescription().toLowerCase().contains(keyWord))
                    return true;
            }
        }
        return false;
    }

    private List<String> getKeyWordForArea(String area){
        List<String> keyWords = new ArrayList<>();
        switch (area){
            case "Brno-střed":
                keyWords.add("stred");
                keyWords.add("střed");
                break;
            case "Brno-sever":
                keyWords.add("sever");
                break;
            case "Brno-Královo Pole":
                keyWords.add("královo pole");
                keyWords.add("krpole");
                keyWords.add("krpoly");
                keyWords.add("králov");
                keyWords.add("kralov");
                break;
            case "Brno-Líšeň":
                keyWords.add("líšeň");
                keyWords.add("lisen");
                keyWords.add("líšni");
                keyWords.add("lisni");
                break;
            case "Brno-Bystrc":
                keyWords.add("bystrc");
                break;
            case "Brno-Židenice":
                keyWords.add("židenic");
                keyWords.add("zidenic");
                break;
            case "Brno-Žabovřesky":
                keyWords.add("žabovřesk");
                keyWords.add("zabovresk");
                break;
            case "Brno-Řečkovice a Mokrá Hora":
                keyWords.add("mokrá hora");
                keyWords.add("řečkovic");
                keyWords.add("mokra hora");
                keyWords.add("reckovic");
                keyWords.add("mokré hoře");
                keyWords.add("mokre hore");
                break;
            case "Brno-Bohunice":
                keyWords.add("bohunic");
                break;
            case "Brno-Vinohrady":
                keyWords.add("vinohrad");
                break;
            case "Brno-Starý Lískovec":
                keyWords.add("starý lískovec");
                keyWords.add("starém lískovc");
                keyWords.add("stary liskovec");
                keyWords.add("starem liskovc");
                break;
            case "Brno-Kohoutovice":
                keyWords.add("kohoutovic");
                break;
            case "Brno-Nový Lískovec":
                keyWords.add("nový lískovec");
                keyWords.add("novém lískovc");
                keyWords.add("novy liskovec");
                keyWords.add("novem liskovc");
                break;
            case "Brno-jih":
                keyWords.add("jih");
                break;
            case "Brno-Slatina":
                keyWords.add("slatin");
                break;
            case "Brno-Černovice":
                keyWords.add("černovic");
                keyWords.add("cernovic");
                break;
            case "Brno-Komín":
                keyWords.add("komín");
                keyWords.add("komin");
                break;
            case "Brno-Medlánky":
                keyWords.add("medlánk");
                keyWords.add("medlank");
                break;
            case "Brno-Tuřany":
                keyWords.add("tuřan");
                keyWords.add("turan");
                break;
            case "Brno-Maloměřice a Obřany":
                keyWords.add("obřan");
                keyWords.add("maloměřic");
                keyWords.add("obran");
                keyWords.add("malomeric");
                break;
            case "Brno-Jundrov":
                keyWords.add("jundrov");
                break;
            case "Brno-Chrlice":
                keyWords.add("chrlic");
                break;
            case "Brno-Žebětín":
                keyWords.add("žebětín");
                keyWords.add("zebetin");
                break;
            case "Brno-Bosonohy":
                keyWords.add("bosonoh");
                break;
            case "Brno-Ivanovice":
                keyWords.add("ivanovic");
                break;
            case "Brno-Jehnice":
                keyWords.add("jehnic");
                break;
            case "Brno-Kníničky":
                keyWords.add("kníničk");
                keyWords.add("kninick");
                break;
            case "Brno-Útěchov":
                keyWords.add("útěchov");
                keyWords.add("utechov");
                break;
            case "Brno-Ořešín":
                keyWords.add("ořešín");
                keyWords.add("oresin");
                break;
        }
        return keyWords;
    }

    private String getAreaString(String s){
        int i = 0;
        String newS = "";
        while (Character.isDigit(s.charAt(i))){
            Character c = s.charAt(i);
            newS += c;
            i++;
        }
        return newS;
    }

}
