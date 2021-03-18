//package com.infinity.kich.Leave.Adapter;
package com.infinity.kich.Leave.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.infinity.kich.CommonCls.CustomTextView;
import com.infinity.kich.Leave.Pojo.AttReportDetailPojo;
import com.infinity.kich.R;

public class AttReportAdapter extends BaseAdapter {
    Context ctx;

    AttReportDetailPojo attReportDetailPojo;


    View view_;


    public AttReportAdapter(Context ctx, AttReportDetailPojo attReportDetailPojo) {
        this.ctx = ctx;
        this.attReportDetailPojo = attReportDetailPojo;

        System.out.println("call ################ ");

    }

    class ViewHolder {


        private CustomTextView tv_name;
        private CustomTextView tv_staus;
        LinearLayout ll_main;

    }

    ViewHolder viewHolder;

    @Override
    public int getCount() {
        // return college.getTable().size();

        if (attReportDetailPojo.getData().size()>0)
        {
            return attReportDetailPojo.getData().size();
        }
        else
        {
            return 0;
        }
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

        view_ = view;
        if (view_ == null) {
            view_ = mInflater.inflate(R.layout.leave_adapter_att_report, viewGroup, false);

            viewHolder = new ViewHolder();
            initView(view_);
            view_.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view_.getTag();
        }


        viewHolder.tv_staus.setText(attReportDetailPojo.getData().get(i).getPresent_char() + "");
        viewHolder.tv_name.setText(attReportDetailPojo.getData().get(i).getMonthDate() + "");


//        if (i % 2 == 0) {
//            viewHolder.ll_main.setBackgroundColor(ctx.getResources().getColor(R.color.test));
//        }


        return view_;
    }

    private void initView(@NonNull final View itemView) {


        viewHolder.ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);
        viewHolder.tv_name = (CustomTextView) itemView.findViewById(R.id.tv_name);
        viewHolder.tv_staus = (CustomTextView) itemView.findViewById(R.id.tv_staus);
    }


}
