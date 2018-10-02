package com.arropa.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arropa.R;
import com.arropa.servers.Constant;
import com.squareup.picasso.Picasso;



/**
 * Created by xyz on 06-02-2018.
 */

public class SliderPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private  String images[];

    public SliderPagerAdapter(Context context, String[] images) {
        this.context = context;
        this.images=images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.itme_slider_img, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.get()
                .load(Constant.IMAGEPATH+images[position])
                .placeholder(R.drawable.shirt)
                .error(R.drawable.shirt)
                .into(imageView);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
