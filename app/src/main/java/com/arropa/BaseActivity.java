package com.arropa;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by xyz on 22-02-2018.
 */

public class BaseActivity extends AppCompatActivity {
    /*
    * set the default toolbar with back button
    *
    * */

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setToolbar(android.support.v7.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*
    * override method to back button
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
