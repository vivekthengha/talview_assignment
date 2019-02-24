package com.myapplication.home;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.myapplication.R;
import com.myapplication.base.BaseActivity;
import com.myapplication.home.albums_fragment.AlbumFragment;
import com.myapplication.home.posts_fragment.PostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements PostFragment.PostFragmentInteractionListener, AlbumFragment.AlbumFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.root_view)
    CoordinatorLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setUpViewPager();
    }

    private void setUpViewPager() {
        HomePagerAdapter pagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(PostFragment.getInstance(),getString(R.string.txt_posts));
        pagerAdapter.addFragment(AlbumFragment.getInstance(),getString(R.string.txt_albums));
        viewPager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected View setSnackView() {
        return rootView;
    }

    @Override
    public void showSnackBar(String message) {
        showSnackbarLong(message);
    }

}
