package com.example.android.cstogo;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;


/**
 * A simple {@link Fragment} subclass.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String category = getArguments().getString("category");
        if (category != null) {
            if (category.equals(getString(R.string.category_style))) {
                addPreferencesFromResource(R.xml.prefs_style);
            } else if (category.equals(getString(R.string.category_steam))) {
                addPreferencesFromResource(R.xml.prefs_steam);
            }
        }
    }


}