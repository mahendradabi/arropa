package com.arropa;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arropa.adapters.ViewPagerAdapter;
import com.arropa.customviews.CustPagerTransformer;
import com.arropa.servers.Constant;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;

public class MainActivity extends MyAbstractActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;

    NavigationView navigationView;

    TabLayout tabs;


    ViewPager viewpager;

    PreferenceManger preferenceManger;

    TextView tvCreditLimt, tvUserLimit, tvRemainingLimit;

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

        preferenceManger = PreferenceManger.getPreferenceManger();
        navigationView = findViewById(R.id.navigation);
        tabs = findViewById(R.id.tabs);
        viewpager = findViewById(R.id.viewpager);

        View view = navigationView.getHeaderView(0);
        AppCompatTextView userName = view.findViewById(R.id.username);

        navigationView.setNavigationItemSelectedListener(this);

        tvCreditLimt = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.creditLimit));
        tvUserLimit = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.useLimit));
        tvRemainingLimit = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.reamingLimit));

        if (tvCreditLimt != null) {
            initializeCountDrawer();
            tvCreditLimt.setVisibility(View.INVISIBLE);
            tvUserLimit.setVisibility(View.INVISIBLE);
            tvRemainingLimit.setVisibility(View.INVISIBLE);
        }

        if (preferenceManger != null)
            userName.setText(preferenceManger.getString(PrefKeys.USERNAME));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentProductList.intantiateList("product_shirt"), "Shirt");
        adapter.addFragment(FragmentProductList.intantiateList("product_tshirt"), "T-Shirt");
        adapter.addFragment(FragmentProductList.intantiateList("product_bottom"), "Bottom Wear");
        adapter.addFragment(FragmentProductList.intantiateList("product_seasonal"), "Seasonal Wear");

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                PreferenceManger.getPreferenceManger().clearSession();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.myprofile:
                startActivity(new Intent(MainActivity.this, MyProfile.class));
                break;
            case R.id.termsConditions:
                startActivity(new Intent(MainActivity.this, ReadPrivacy.class));
                break;

            case R.id.useLimit:
                hideShowLimit(tvUserLimit);
                break;

            case R.id.creditLimit:
                hideShowLimit(tvCreditLimt);
                break;
            case R.id.reamingLimit:
                hideShowLimit(tvRemainingLimit);
                break;

            case R.id.favorite:
                startActivity(new Intent(MainActivity.this,FavoriteList.class));
                break;
        }
        return true;
    }


    private void initializeCountDrawer() {
        //Gravity property aligns the text
        tvCreditLimt.setGravity(Gravity.CENTER_VERTICAL);
        tvCreditLimt.setTypeface(null, Typeface.BOLD);
        tvCreditLimt.setTextColor(getResources().getColor(R.color.colorAccent));
        tvCreditLimt.setText(Constant.CURRENCY + " " + "10000");

        tvUserLimit.setGravity(Gravity.CENTER_VERTICAL);
        tvUserLimit.setTypeface(null, Typeface.BOLD);
        tvUserLimit.setTextColor(getResources().getColor(R.color.colorAccent));
        tvUserLimit.setText(Constant.CURRENCY + " " + "4000");

        tvRemainingLimit.setGravity(Gravity.CENTER_VERTICAL);
        tvRemainingLimit.setTypeface(null, Typeface.BOLD);
        tvRemainingLimit.setTextColor(getResources().getColor(R.color.colorAccent));
        tvRemainingLimit.setText(Constant.CURRENCY + " " + "6000");


    }

    private void hideShowLimit(TextView tv)
    {
        if (tv.getVisibility()==View.VISIBLE)
            tv.setVisibility(View.INVISIBLE);
        else tv.setVisibility(View.VISIBLE);
    }
}
