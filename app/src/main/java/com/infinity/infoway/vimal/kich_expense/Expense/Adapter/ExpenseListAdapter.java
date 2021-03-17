package com.infinity.infoway.vimal.kich_expense.Expense.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class ExpenseListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    ArrayList<String> ModeOfTransportList_id = new ArrayList<>();

    ArrayList<String> ModeOfTransportList_Name = new ArrayList<>();

    public ExpenseListAdapter(Context context, List<String> listDataHeader,
                              HashMap<String, List<String>> listChildData, ArrayList<String> ModeOfTransportList_id, ArrayList<String> ModeOfTransportList_Name) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.ModeOfTransportList_id = ModeOfTransportList_id;
        this.ModeOfTransportList_Name = ModeOfTransportList_Name;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .get(childPosititon);
        return "";
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        // final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expense_expandable_child, null);
        }

        CustomEditText et_description = (CustomEditText) convertView
                .findViewById(R.id.et_description);
        SearchableSpinner sp_mode_of_transport = (SearchableSpinner) convertView
                .findViewById(R.id.sp_mode_of_transport);

        //   txtListChild.setText(childText);
        System.out.println("TEST!!!!!!!!!!!!! "+ModeOfTransportList_Name.size()+"");
        ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(_context, R.layout.spinner_common_layout, ModeOfTransportList_Name);
        projectAdapter.setDropDownViewResource(R.layout.spinner_common_layout);


        sp_mode_of_transport.setTitle("Select Mode Of Transport");
        sp_mode_of_transport.setAdapter(projectAdapter);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expense_expandable_parent, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.tv_expense_name);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
