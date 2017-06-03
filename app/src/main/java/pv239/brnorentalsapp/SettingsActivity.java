package pv239.brnorentalsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        /*PreferenceManager.setDefaultValues(SettingsActivity.this, R.xml.preferences,
                false);*/
        initSummary(getPreferenceScreen());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
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

        if (key.equals("pref_advanced")){
            if (((SwitchPreference) p).isChecked()){
                PreferenceCategory category = (PreferenceCategory)findPreference("advanced_category");

                EditTextPreference addStreet = new EditTextPreference(this);
                addStreet.setKey("pref_add_streets");
                addStreet.setTitle("Add streets");
                addStreet.setDialogMessage("write new streets separated by comma (in csv format:\"street1,street2,...\")");

                EditTextPreference deleteStreet = new EditTextPreference(this);
                deleteStreet.setKey("pref_delete_streets");
                deleteStreet.setTitle("Delete streets");
                deleteStreet.setDialogMessage("write streets to delete separated by comma (in csv format:\"street1,street2,...\")");


                category.addPreference(addStreet);
                category.addPreference(deleteStreet);


            } else if (!((SwitchPreference) p).isChecked()){
                PreferenceCategory category = (PreferenceCategory)findPreference("advanced_category");
                EditTextPreference addStreet = (EditTextPreference) findPreference("pref_add_streets");
                if (addStreet != null)
                    addStreet.setText("");
                EditTextPreference deleteStreet = (EditTextPreference) findPreference("pref_delete_streets");
                if (deleteStreet != null)
                    deleteStreet.setText("");

                if (addStreet != null)
                    category.removePreference(addStreet);
                if (deleteStreet != null)
                    category.removePreference(deleteStreet);
            }

        } else if (key.equals("pref_add_streets")) {
            EditTextPreference addStreet = (EditTextPreference) findPreference("pref_add_streets");
            String data = addStreet.getText();
            if (data.equals(""))
                return;

            //zpracovani dat
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            Set<String> streets = sharedPref.getStringSet("saved_streets", new HashSet<String>());
            SharedPreferences.Editor edit = sharedPref.edit();
            addStreets(streets, data.split(","));
            edit.putStringSet("saved_streets", streets);
            edit.apply();


            if (!data.equals(""))
                Toast.makeText(this, "Added streets: " + streets, Toast.LENGTH_SHORT).show();

            addStreet.setText("");
        } else if (key.equals("pref_delete_streets")) {
            EditTextPreference deleteStreet = (EditTextPreference) findPreference("pref_delete_streets");
            String data = deleteStreet.getText();

            //zpracovani dat
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            Set<String> streets = sharedPref.getStringSet("saved_streets", new HashSet<String>());
            SharedPreferences.Editor edit = sharedPref.edit();
            deleteStreets(streets, data.split(","));
            edit.putStringSet("saved_streets", streets);
            edit.apply();

            if (!data.equals(""))
                Toast.makeText(this, "Added treets: " + streets, Toast.LENGTH_SHORT).show();

            deleteStreet.setText("");
        }


        /*if (p instanceof ListPreference) {
            ListPreference listPref = (ListPreference) p;
            if (key != null && key.contains(""))
            p.setSummary(listPref.getEntry());
        }
        if (p instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) p;
            if (p.getTitle().toString().toLowerCase().contains("password"))
            {
                p.setSummary("******");
            } else {
                p.setSummary(editTextPref.getText());
            }
        }
        if (p instanceof MultiSelectListPreference) {
            EditTextPreference editTextPref = (EditTextPreference) p;
            p.setSummary(editTextPref.getText());
        }*/
    }

    private void addStreets(Set<String> streetSet, String[] streets){

        for (String street : streets){
            streetSet.add(street);
        }

    }

    private void deleteStreets(Set<String> streetSet, String[] streets){

        for (String street : streets){
            streetSet.remove(street);
        }

    }

}

