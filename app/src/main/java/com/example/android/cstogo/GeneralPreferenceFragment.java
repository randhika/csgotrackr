package com.example.android.cstogo;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralPreferenceFragment extends PreferenceFragment {


    public GeneralPreferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);
    }
}
