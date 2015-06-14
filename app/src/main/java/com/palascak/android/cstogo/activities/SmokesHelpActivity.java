/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.palascak.android.cstogo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.palascak.android.cstogo.MyApplication;
import com.palascak.android.cstogo.R;
import com.squareup.picasso.Picasso;

public class SmokesHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MyApplication.getThemeId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smokes_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.smokes_help_toolbar);

        setSupportActionBar(toolbar);

        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView smokesCrouching = (ImageView) findViewById(R.id.smokes_help_crouching);
        ImageView smokesStanding = (ImageView) findViewById(R.id.smokes_help_standing);
        ImageView smokesJumpThrow = (ImageView) findViewById(R.id.smokes_help_jump_throw);

        Picasso.with(this).load(R.drawable.crouch).fit().centerCrop().into(smokesCrouching);
        Picasso.with(this).load(R.drawable.standing).fit().centerCrop().into(smokesStanding);
        Picasso.with(this).load(R.drawable.jumping_throw).fit().centerCrop().into(smokesJumpThrow);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_smokes_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
