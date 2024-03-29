package com.palascak.android.cstogo.activities;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.palascak.android.cstogo.MyApplication;
import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.fragments.PrefsFragment;

import java.util.List;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MyApplication.getThemeId());
        super.onCreate(savedInstanceState);

        prepareLayout();

    }

    private void prepareLayout() {
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        View content = root.getChildAt(0);
        LinearLayout toolbarContainer = (LinearLayout) View.inflate(this, R.layout.settings_page, null);

        root.removeAllViews();
        toolbarContainer.addView(content);
        root.addView(toolbarContainer);

        Toolbar toolbar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);

        toolbar.setTitle(getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.headers, target);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return fragmentName.equals(PrefsFragment.class.getName());
    }

}
