package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.infinity.infoway.vimal.R;
import com.infinity.kich.Leave.Pojo.LastInOutPojo;
public class HiBondListingAdapter extends BaseAdapter
{


    Context ctx;
    private long lastClickTime = 0;
    LastInOutPojo lastInOutPojo;

    public HiBondListingAdapter(Context ctx)
    {
        this.ctx = ctx;



    }

    class ViewHolder {


    }

    ViewHolder viewHolder;

    @Override
    public int getCount() {
        // return college.getTable().size();

        return 10;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_hi_bond, viewGroup, false);

            viewHolder = new ViewHolder();

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }


}
