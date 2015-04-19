package com.example.android.cstogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.rey.material.widget.Spinner;
import com.melnykov.fab.FloatingActionButton;


public class NewMatchActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        //TODO: change layout

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("New Match");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker np1 = (NumberPicker) findViewById(R.id.new_match_picker1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.new_match_picker2);

        np1.setMinValue(0);
        np1.setMaxValue(16);

        np2.setMinValue(0);
        np2.setMaxValue(16);

        Spinner spn_label = (Spinner) findViewById(R.id.spinner_label);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.maps_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(R.layout.row_spn);
        // Apply the adapter to the spinner
        spn_label.setAdapter(adapter2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_match_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMatch();
            }
        });
    }

    private void saveMatch() {

        NumberPicker np1 = (NumberPicker) findViewById(R.id.new_match_picker1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.new_match_picker2);
        Spinner mapSpinner = (Spinner) findViewById(R.id.spinner_label);
        //String map = mapSpinner.getSelectedItem().toString();
        String map = getResources().getStringArray(R.array.maps_array)[mapSpinner.getSelectedItemPosition()];

        EditText kills = (EditText) findViewById(R.id.new_match_kills);
        EditText assists = (EditText) findViewById(R.id.new_match_assists);
        EditText deaths = (EditText) findViewById(R.id.new_match_deaths);
        EditText mvps = (EditText) findViewById(R.id.new_match_mvps);
        EditText score = (EditText) findViewById(R.id.new_match_score);

        Match matchObject = new Match(np1.getValue(),
                np2.getValue(),
                Integer.parseInt(kills.getText().toString()),
                Integer.parseInt(assists.getText().toString()),
                Integer.parseInt(deaths.getText().toString()),
                Integer.parseInt(mvps.getText().toString()),
                Integer.parseInt(score.getText().toString()),
                map);

        MatchList.getInstance().matchList.add(0, matchObject);

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);

        //TODO: Check for valid input
        finish();

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_new_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
