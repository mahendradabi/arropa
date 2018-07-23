package com.arropa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arropa.adapters.ViewPagerAdapter;
import com.arropa.customviews.CustPagerTransformer;
import com.arropa.models.CartList;
import com.arropa.models.CartModel;
import com.arropa.models.MyResponse;
import com.arropa.models.PictureModel;
import com.arropa.models.ProfileImgModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends MyAbstractActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener, ServerResponse {
    DrawerLayout mDrawerLayout;
    private static final int PICK_IMAGE = 103;
    NavigationView navigationView;

    TabLayout tabs;


    ViewPager viewpager;

    PreferenceManger preferenceManger;

    TextView tvCreditLimt, tvUserLimit, tvRemainingLimit, tvCartCount;

    CircleImageView img_user_profile;

    //dialog
    ProgressDialog dialog;

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
        img_user_profile = view.findViewById(R.id.img_user_profile);

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

        if (preferenceManger != null) {
            userName.setText(preferenceManger.getString(PrefKeys.USERNAME));
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentProductList.intantiateList("product_shirt"), "Shirt");
        adapter.addFragment(FragmentProductList.intantiateList("product_tshirt"), "T-Shirt");
        adapter.addFragment(FragmentProductList.intantiateList("product_bottom"), "Bottom Wear");
        adapter.addFragment(FragmentProductList.intantiateList("product_seasonal"), "Seasonal Wear");

        tabs.addOnTabSelectedListener(MainActivity.this);
        viewpager.setPageTransformer(false, new CustPagerTransformer(MainActivity.this));
        viewpager.setAdapter(adapter);
        tabs.setupWithViewPager(viewpager);

        dialog = DialogWindow.showProgressDialog(MainActivity.this, "Profile", "Please wait profile is uploding...");
        dialog.setCancelable(false);

        img_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgePicker();
            }
        });

        getCartSize();
        getProfileImage();

    }

    private void getProfileImage() {
        new Requestor(Constant.GETPROFILEPHOTO, this)
                .getProfilePhoto(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
    }

    @Override
    public void initListeners() {

    }

    private void getCartSize() {
        new Requestor(Constant.GET_CART_LIST, this).getCartList(
                PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID)
        );
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
        MenuItem item = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(item, R.layout.cart_count);
        RelativeLayout v = (RelativeLayout) MenuItemCompat.getActionView(item);
        tvCartCount = (TextView) v.findViewById(R.id.cart_item_count);

        if (tvCartCount != null) tvCartCount.setText("0");
        v.findViewById(R.id.cartimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityCart.class));
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.notification:
                startActivity(new Intent(MainActivity.this, ActivityNotification.class));
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
                mDrawerLayout.closeDrawer(Gravity.START);

                break;
            case R.id.termsConditions:
                startActivity(new Intent(MainActivity.this, ReadPrivacy.class).putExtra("url", 1));
                mDrawerLayout.closeDrawer(Gravity.START);

                break;

            case R.id.faq:
                startActivity(new Intent(MainActivity.this, ReadPrivacy.class).putExtra("url", 2));
                mDrawerLayout.closeDrawer(Gravity.START);

                break;

            case R.id.retrunt:
                startActivity(new Intent(MainActivity.this, ReadPrivacy.class).putExtra("url", 3));
                mDrawerLayout.closeDrawer(Gravity.START);

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
                startActivity(new Intent(MainActivity.this, FavoriteList.class));
                mDrawerLayout.closeDrawer(Gravity.START);

                break;

            case R.id.orders:
                startActivity(new Intent(MainActivity.this, MyOrderList.class));
                mDrawerLayout.closeDrawer(Gravity.START);

                break;

            case R.id.mycart:
                startActivity(new Intent(MainActivity.this, ActivityCart.class));
                mDrawerLayout.closeDrawer(Gravity.START);

                break;
            case R.id.contactus:
                startActivity(new Intent(MainActivity.this, ActivityContactUs.class));
                mDrawerLayout.closeDrawer(Gravity.START);

                break;
        }
        return true;
    }


    private void initializeCountDrawer() {
        //Gravity property aligns the text
        tvCreditLimt.setGravity(Gravity.CENTER_VERTICAL);
        tvCreditLimt.setTypeface(null, Typeface.BOLD);
        tvCreditLimt.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvCreditLimt.setText(Constant.CURRENCY + " " + "10000");

        tvUserLimit.setGravity(Gravity.CENTER_VERTICAL);
        tvUserLimit.setTypeface(null, Typeface.BOLD);
        tvUserLimit.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvUserLimit.setText(Constant.CURRENCY + " " + "4000");

        tvRemainingLimit.setGravity(Gravity.CENTER_VERTICAL);
        tvRemainingLimit.setTypeface(null, Typeface.BOLD);
        tvRemainingLimit.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvRemainingLimit.setText(Constant.CURRENCY + " " + "6000");




    }

    private void hideShowLimit(TextView tv) {
        if (tv.getVisibility() == View.VISIBLE)
            tv.setVisibility(View.INVISIBLE);
        else tv.setVisibility(View.VISIBLE);
    }

    private void imgePicker() {
        ImagePicker.with(this)                         //  Initialize ImagePicker with activity or fragment context
                .setMultipleMode(true)              //  Select multiple images or single image
                .setFolderMode(true)                //  Folder mode
                .setMaxSize(1)//  Max images can be selected
                .setShowCamera(false)
                .setDoneTitle("Accept")
                .setSavePath("Arropa")
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images != null && images.size() > 0) {

                File img = new File(images.get(0).getPath());
                if (img.exists()) {
                    img_user_profile.setImageURI(Uri.fromFile(img));
                    uploadPhotos(images.get(0).getPath());
                } else {
                  /*  String path= Environment.getExternalStorageDirectory().getPath();
                    img=new File(path+"/Pictures/Buahh/" + images.get(0).getName());
                    if (img.exists())
                        uploadPhotos(images.get(0).getName());
                    else Toast.makeText(getApplicationContext(),"Internal Error",Toast.LENGTH_SHORT).show();*/


                }

            }

        }

    }

    private void uploadPhotos(String path) {
        if (dialog != null) dialog.show();
        new Requestor(Constant.UPLOAD_PROFILE_PHOTO, MainActivity.this)
                .uloadPhoto(getRequestBody(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID)),
                        prepareFilePart("picture", new File(path)));

    }

    @NonNull
    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        // create RequestBody instance from file
        if (file == null) return null;
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    @Override
    public void success(Object o, int code) {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        switch (code) {
            case Constant.GET_CART_LIST:
                CartList cartList = (CartList) o;
                if (cartList != null && cartList.isStatus()) {
                    List<CartModel> details = cartList.getDetails();
                    if (details != null) {
                        tvCartCount.setText(details.size() + "");

                    }

                } else tvCartCount.setText("0");
                break;

            case Constant.UPLOAD_PROFILE_PHOTO:
                MyResponse response = (MyResponse) o;
                if (response != null && response.getMessage() != null)
                    Utility.showToast(MainActivity.this, response.getMessage());
                if (response.isStatus())
                    getProfileImage();
                break;

            case Constant.GETPROFILEPHOTO:
                ProfileImgModel imgModel = (ProfileImgModel) o;
                if (imgModel.isStatus()) {
                    List<PictureModel> picture = imgModel.getPicture();
                    if (picture != null && picture.size() == 1) {
                        Picasso.get().load(Constant.IMAGEPATH + picture.get(0).getPhoto())
                                .error(R.drawable.dummy_user)
                                .placeholder(R.drawable.dummy_user)
                                .into(img_user_profile);
                    }


                }
                break;
        }

    }

    @Override
    public void error(Object o, int code) {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartSize();
    }
}
