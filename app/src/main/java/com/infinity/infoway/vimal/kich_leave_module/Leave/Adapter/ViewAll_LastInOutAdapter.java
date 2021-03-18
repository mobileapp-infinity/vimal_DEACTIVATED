package com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.PunchDetailPojo;

public class ViewAll_LastInOutAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context ctx;
    PunchDetailPojo punchDetailPojo;

    public ViewAll_LastInOutAdapter(Context ctx, PunchDetailPojo punchDetailPojo) {
        this.ctx = ctx;
        this.punchDetailPojo = punchDetailPojo;
        mInflater = LayoutInflater.from(ctx);

    }

    class ViewHolder {

        LinearLayout ll_pos, ll_line, ll_bg;

        TextView tv_date, tv_time_in, tv_time_out;

        CardView card_root;

    }

    ViewHolder viewHolder;

    @Override
    public int getCount()
    {
        // return college.getTable().size();
        if (punchDetailPojo.getData() != null)
        {
            return punchDetailPojo.getData().size();
        }
        else
            {
            return 0;
        }
    }

    @Override
    public Object getItem(int i)
    {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_view_all_leave, null);

            viewHolder = new ViewHolder();
            initView(view, i);
            view.setTag(viewHolder);
            System.out.println("fffffffffffffffffffffffffffffffff ");
        } else {
            viewHolder = (ViewHolder) view.getTag();
            //   viewHolder.tv_date.setText(i+"");
        }
//        if (i == 0) {
//            viewHolder.ll_pos.setVisibility(View.VISIBLE);
//            viewHolder.ll_line.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.ll_pos.setVisibility(View.GONE);
//            viewHolder.ll_line.setVisibility(View.GONE);
//        }
//        viewHolder.tv_date.setText(i + "");
        viewHolder.tv_date.setText(punchDetailPojo.getData().get(i).getDep_name() + "");
        viewHolder.tv_time_in.setText(punchDetailPojo.getData().get(i).getInTime() + "");
        viewHolder.tv_time_out.setText(punchDetailPojo.getData().get(i).getOutTime() + "");


        if (punchDetailPojo.getData().get(i).getRow_color() != null) {
            // System.out.println("row  color ::::" + punchDetailPojo.getData().get(i).getRow_color()+"");
            if (punchDetailPojo.getData().get(i).getRow_color().compareToIgnoreCase("1") == 0) {
                viewHolder.ll_bg.setBackgroundColor(ctx.getResources().getColor(R.color.row_color_yellow));
            } else if (punchDetailPojo.getData().get(i).getRow_color().compareToIgnoreCase("2") == 0) {
                viewHolder.ll_bg.setBackgroundColor(ctx.getResources().getColor(R.color.row_color_green));
            } else {
                viewHolder.ll_bg.setBackgroundColor(ctx.getResources().getColor(R.color.white));
            }

        } else {

        }


        return view;
    }

    private void initView(@NonNull final View itemView, int pos) {
        viewHolder.ll_pos = (LinearLayout) itemView.findViewById(R.id.ll_pos);
        viewHolder.ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
        viewHolder.card_root = (CardView) itemView.findViewById(R.id.card_root);
        viewHolder.ll_line = (LinearLayout) itemView.findViewById(R.id.ll_line);

        viewHolder.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        viewHolder.tv_time_in = (TextView) itemView.findViewById(R.id.tv_time_in);
        viewHolder.tv_time_out = (TextView) itemView.findViewById(R.id.tv_time_out);
    }
}
