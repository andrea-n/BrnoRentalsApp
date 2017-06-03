package pv239.brnorentalsapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Andrea Navratilova on 6/3/2017.
 */

public class GalleryAdapter extends PagerAdapter {
    private Context context;
    private String[] imagesUrls;

    GalleryAdapter(Context context, String[] imagesUrls){
        this.context = context;
        this.imagesUrls = imagesUrls;
    }

    @Override
    public int getCount() {
        return imagesUrls.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        int padding = 0;
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        String imgUrl = imagesUrls[position];
        Picasso.with(context).load(imgUrl).into(imageView);

        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
