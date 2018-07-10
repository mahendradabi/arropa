package com.arropa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arropa.adapters.ViewPagerAdapter;
import com.arropa.customviews.CustPagerTransformer;

public class MainActivity extends MyAbstractActivity implements TabLayout.OnTabSelectedListener {
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    TabLayout tabs;
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);

        navigationView = findViewById(R.id.navigation);

        View view = navigationView.getHeaderView(0);


        tabs = findViewById(R.id.tabs);
        viewpager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentProductList(), "Mens");
        adapter.addFragment(new FragmentProductList(), "Womens");
        adapter.addFragment(new FragmentProductList(), "Kids");

        tabs.addOnTabSelectedListener(MainActivity.this);
        viewpager.setPageTransformer(false, new CustPagerTransformer(MainActivity.this));
        viewpager.setAdapter(adapter);
        tabs.setupWithViewPager(viewpager);

    }

    @Override
    public void initListeners() {

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose category")
                        .setItems(R.array.options, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                            }
                        });
                builder.create().show();
                break;

            case R.id.cart:
                startActivity(new Intent(MainActivity.this, ActivityCart.class));
                break;
        }
        return true;
    }
}
