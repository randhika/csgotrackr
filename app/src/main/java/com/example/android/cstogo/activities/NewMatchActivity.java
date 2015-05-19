package com.example.android.cstogo.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.android.cstogo.MyApplication;
import com.example.android.cstogo.R;
import com.example.android.cstogo.helpers.Match;
import com.example.android.cstogo.helpers.MatchList;
import com.melnykov.fab.FloatingActionButton;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;


public class NewMatchActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MyApplication.getThemeId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);

        Toolbar toolbar = (Toolbar) findViewById(R.id.new_match_toolbar);

        setSupportActionBar(toolbar);

        //getSupportActionBar().setTitle("New Match");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        NumberPicker np1 = (NumberPicker) findViewById(R.id.new_match_picker1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.new_match_picker2);

        np1.setMinValue(0);
        np1.setMaxValue(16);

        np2.setMinValue(0);
        np2.setMaxValue(16);

        setNumberPickerTextColor(np1, getResources().getColor(R.color.accent));
        setNumberPickerTextColor(np2, getResources().getColor(R.color.accent));

        Spinner spn_label = (Spinner) findViewById(R.id.spinner_label);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.maps_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(R.layout.row_spn);
        // Apply the adapter to the spinner
        spn_label.setAdapter(adapter2);

        android.widget.TextView toolbarAdd = (android.widget.TextView) findViewById(R.id.new_match_toolbar_add);
        android.widget.TextView toolbarDiscard = (android.widget.TextView) findViewById(R.id.new_match_toolbar_discard);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_match_fab);

        Animation fabGrow = AnimationUtils.loadAnimation(this, R.anim.anim_fab);
        fab.startAnimation(fabGrow);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputs()) {
                    changeColor();
                } else {
                    saveMatch();
                }
            }
        });

        toolbarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputs()) {
                    changeColor();
                } else {
                    saveMatch();
                }
            }
        });

        toolbarDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //it's used, i promise
    @SuppressWarnings("unused")
    private boolean checkInputs() {
        //Do not trust user input. Check for empty or non-int input
        //RETURN: true for bad input

        EditText kills = (EditText) findViewById(R.id.new_match_kills);
        EditText assists = (EditText) findViewById(R.id.new_match_assists);
        EditText deaths = (EditText) findViewById(R.id.new_match_deaths);
        EditText mvps = (EditText) findViewById(R.id.new_match_mvps);
        EditText score = (EditText) findViewById(R.id.new_match_score);

        if (isEmpty(kills) || isEmpty(assists) || isEmpty(deaths) || isEmpty(mvps) || isEmpty(score)){
            Toast.makeText(this, "Match stats are not filled", Toast.LENGTH_SHORT).show();
            return true;
        }

        try {
            String killsString = kills.getText().toString();
            String assistsString = assists.getText().toString();
            String deathsString = deaths.getText().toString();
            String mvpsString = mvps.getText().toString();
            String scoreString = score.getText().toString();
            int killsNum = Integer.parseInt(killsString);
            int assistsNum = Integer.parseInt(assistsString);
            int deathsNum = Integer.parseInt(deathsString);
            int mvpsNum = Integer.parseInt(mvpsString);
            int scoreNum = Integer.parseInt(scoreString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Match stats are filled incorrectly", Toast.LENGTH_SHORT).show();
            return true;
        }

        NumberPicker np1 = (NumberPicker) findViewById(R.id.new_match_picker1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.new_match_picker2);

        int np1Val = np1.getValue();
        int np2Val = np2.getValue();

        if (np1Val == 0 && np2Val == 0){
            Toast.makeText(this, "Match result not filled (Score is 0:0)", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    private void changeColor(){
        int newColor = getResources().getColor(android.R.color.holo_red_dark);

        TypedValue typedValue = new TypedValue();

        TypedArray a = obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);

        a.recycle();

        Drawable oldBackground = new ColorDrawable(color);
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
        getSupportActionBar().setBackgroundDrawable(td);
        td.startTransition(850);
        td.reverseTransition(650);
    }

    private void saveMatch() {

        NumberPicker np1 = (NumberPicker) findViewById(R.id.new_match_picker1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.new_match_picker2);
        Spinner mapSpinner = (Spinner) findViewById(R.id.spinner_label);
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

        File file = new File(MyApplication.getAppContext().getFilesDir(), "match_list.dat");
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream objectOutStream;
        try {
            objectOutStream = new ObjectOutputStream(outStream);
            objectOutStream.writeInt(MatchList.getInstance().matchList.size()); // Save size first
            for(Match m:MatchList.getInstance().matchList)
                objectOutStream.writeObject(m);
            objectOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finish();

    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof android.widget.EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((android.widget.EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException | IllegalAccessException | IllegalArgumentException e){
                    Log.w("setNumberPickerTextCol", e);
                }
            }
        }
        return false;
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
