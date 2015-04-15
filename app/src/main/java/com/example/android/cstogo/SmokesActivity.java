package com.example.android.cstogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class SmokesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smokes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        /*nobody cares about you warning
        * but really - I have full control over ArrayList so dynamic check would be waste of resources
        * */
        @SuppressWarnings("unchecked cast")
        final ArrayList<Smoke> tempList = (ArrayList<Smoke>) intent.getSerializableExtra("TEMP");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_smokes);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MySmokesGridAdapter adapter = new MySmokesGridAdapter(this, tempList);
        mRecyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new MySmokesGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Smoke tempSmoke = tempList.get(position);
                Intent myIntent = new Intent(view.getContext(), ViewImageActivity.class);
                myIntent.putExtra("TITLE", tempSmoke.getMapId());
                myIntent.putExtra("PICTURE", tempSmoke.getFullMapId());
                view.getContext().startActivity(myIntent);
            }
        });
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
