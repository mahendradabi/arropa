package com.arropa.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Utility {

    public static void openShareIntent(Context mContex, String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        ComponentName componentName = sendIntent.resolveActivity(mContex.getPackageManager());
        if (componentName != null)
            mContex.startActivity(sendIntent);
    }

    public static void showToast(Context mContex,String msg)
    {
        Toast.makeText(mContex,msg,Toast.LENGTH_SHORT).show();
    }
}
