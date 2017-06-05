package pv239.brnorentalsapp;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.Timer;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        initSummary(getPreferenceScreen());


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

        // cancel notification "listener"
        Timer myTimer = Notifications.getTimer();
        if (myTimer != null)
            myTimer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Config.PREF_NOTIFICATIONS, false)){
            // start notification "listener"
            Timer myTimer = Notifications.getTimer();
            myTimer = new Timer();
            NotificationTask myTask = new NotificationTask(this);
            myTimer.schedule(myTask, 5000, 5000);
            Notifications.setTimer(myTimer);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        updatePrefSummary(findPreference(key), key);
    }

    private void initSummary(Preference p) {
        if (p instanceof PreferenceGroup) {
            PreferenceGroup pGrp = (PreferenceGroup) p;
            for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                initSummary(pGrp.getPreference(i));
            }

        } else {
            updatePrefSummary(p,p.getKey());
        }
    }

    private void updatePrefSummary(Preference p, String key) {

        if (key == null)
            return;

        if (key.equals(Config.PREF_SHOW_NULL_AREA)){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPreferences.getBoolean(Config.PREF_MIN_FLAT_AREA_FILTER,false)
                    || sharedPreferences.getBoolean(Config.PREF_MAX_FLAT_AREA_FILTER,false)){
                findPreference(Config.PREF_SHOW_NULL_AREA).setEnabled(true);
            } else {
                findPreference(Config.PREF_SHOW_NULL_AREA).setEnabled(false);
            }
        } else if (key.equals(Config.PREF_MIN_FLAT_AREA_FILTER)
                || key.equals(Config.PREF_MAX_FLAT_AREA_FILTER)){

            updatePrefSummary(findPreference(Config.PREF_SHOW_NULL_AREA),Config.PREF_SHOW_NULL_AREA);

        } else if (key.equals(Config.PREF_MIN_AREA)){
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                NumberPickerPreference pref = (NumberPickerPreference)findPreference(Config.PREF_MAX_AREA);
                int max = sharedPreferences.getInt(Config.PREF_MAX_AREA,Config.MAX_APARTMENT_AREA);
                int min = sharedPreferences.getInt(Config.PREF_MIN_AREA,Config.MIN_APARTMENT_AREA);
                if ( max < min){
                    pref.setValue(min);
                }
                if (pref.getMinValue() != sharedPreferences.getInt(Config.PREF_MIN_AREA,Config.MIN_APARTMENT_AREA))
                    pref.setMinValue(sharedPreferences.getInt(Config.PREF_MIN_AREA,Config.MIN_APARTMENT_AREA));
        } else if (key.equals(Config.PREF_MAX_AREA)){
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                NumberPickerPreference pref = (NumberPickerPreference)findPreference(Config.PREF_MIN_AREA);
                int max = sharedPreferences.getInt(Config.PREF_MAX_AREA,Config.MAX_APARTMENT_AREA);
                int min = sharedPreferences.getInt(Config.PREF_MIN_AREA,Config.MIN_APARTMENT_AREA);
                if ( max < min){
                    pref.setValue(max);
                }
                if (pref.getMaxValue() != sharedPreferences.getInt(Config.PREF_MAX_AREA,Config.MAX_APARTMENT_AREA))
                    pref.setMaxValue(sharedPreferences.getInt(Config.PREF_MAX_AREA,Config.MAX_APARTMENT_AREA));

        }

    }

}

