package pv239.brnorentalsapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Andrea Navratilova on 4/29/2017.
 */

public class GalleryGridAdapter extends ArrayAdapter {
    private Context mContext;
    private int layoutResourceId;
    private String[] data;

    public GalleryGridAdapter(Context context, int layoutResourceId, String[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.galleryItemImg);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        String imgUrl = data[position];
        Picasso.with(mContext).load(imgUrl).into(holder.image);
        return row;
    }

    static class ViewHolder {
        ImageView image;
    }
}
