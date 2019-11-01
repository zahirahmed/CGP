package com.eleganzit.cgp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.models.SlideMenuItem;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<SlideMenuItem> {

    private Activity activity;
    private List<SlideMenuItem> itemList;
    private SlideMenuItem item;
    private int row;

    public MenuAdapter(Activity act, int resource, List<SlideMenuItem> arrayList) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.itemList = arrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.menu_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((itemList == null) || ((position + 1) > itemList.size()))
            return view;

        item = itemList.get(position);

        holder.tvTitle.setText(item.getTitle());

        return view;
    }

    public class ViewHolder {

        public TextView tvTitle;
    }

}