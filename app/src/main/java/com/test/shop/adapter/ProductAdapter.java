package com.test.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.test.shop.app.R;

/**
 * Modified by  nileshpatel on 15/2/16.
 */
public class ProductAdapter extends BaseAdapter {

    private Context mContext;

    public ProductAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        ViewHolder view;
        if (convertView == null)


        {
            view = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.product_items, null);
            view.productImageView = (ImageView) convertView.findViewById(R.id.productItem);
            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0)
            view.productImageView.setImageResource(R.drawable.product1);
        else
            view.productImageView.setImageResource(R.drawable.product2);


        return convertView;
    }

    public static class ViewHolder {
        public ImageView productImageView;

    }
}
