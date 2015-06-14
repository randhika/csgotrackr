package com.palascak.android.cstogo.activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.palascak.android.cstogo.MyApplication;
import com.palascak.android.cstogo.adapters.MySmokesGridAdapter;
import com.palascak.android.cstogo.R;
import com.palascak.android.cstogo.helpers.Smoke;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;


public class SmokesActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private Toolbar mToolbar;
    private ObservableRecyclerView mRecyclerView;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MyApplication.getThemeId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smokes);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFrameLayout = (FrameLayout) findViewById(R.id.smokes_activity_frame);

        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        /*nobody cares about you warning
        * but really - I have full control over ArrayList so dynamic check would be waste of resources
        * */
        @SuppressWarnings("unchecked cast")
        final ArrayList<Smoke> tempList = (ArrayList<Smoke>) intent.getSerializableExtra("TEMP");

        mRecyclerView = (ObservableRecyclerView) findViewById(R.id.recycler_smokes);
        mRecyclerView.setScrollViewCallbacks(this);

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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        //Log.e("DEBUG", "onUpOrCancelMotionEvent: " + scrollState);
        if (scrollState == ScrollState.UP) {
            if (toolbarIsShown()) {
                hideToolbar();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (toolbarIsHidden()) {
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return mToolbar.getTranslationY() == 0;
    }

    private boolean toolbarIsHidden() {
        return mToolbar.getTranslationY() == -mToolbar.getHeight();
    }

    private void showToolbar() {
        moveToolbar(0);
    }

    private void hideToolbar() {
        moveToolbar(-mToolbar.getHeight());
    }

    private void moveToolbar(float toTranslationY) {
        if (mToolbar.getTranslationY() == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(mToolbar.getTranslationY(), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                mToolbar.setTranslationY(translationY);
                mRecyclerView.setTranslationY(translationY);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mRecyclerView.getLayoutParams();
                lp.height = (int) -translationY + mFrameLayout.getHeight() - lp.topMargin;
                mRecyclerView.requestLayout();
            }
        });
        animator.start();
    }
}
