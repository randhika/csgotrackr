package com.example.android.cstogo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SmokesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smokes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_smokes);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        final List<Smoke> tempList = createItems();
        MyRecyclerSmokesActivityAdapter adapter = new MyRecyclerSmokesActivityAdapter(this, tempList);
        mRecyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new MyRecyclerSmokesActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Smoke tempSmoke = tempList.get(position);
                Toast.makeText(SmokesActivity.this, "clicked: " + tempSmoke.getMapId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Smoke> createItems() {
        List<Smoke> tempList = new ArrayList<>();
        tempList.clear();
        for (int i = 0; i < 15; i++) {
            tempList.add(new Smoke("de_dust" + i));
        }
        return tempList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dust2_smokes, menu);
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
