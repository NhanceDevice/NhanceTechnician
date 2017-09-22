package com.nhance.technician.ui.custom.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhance.technician.R;

public class NavigationDrawerListviewAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;
  private String[] titleArray;
  private TypedArray/*int[]*/ iconsArray;

  public NavigationDrawerListviewAdapter(Context context, String[] titleArray, TypedArray iconsArray/*int[] iconsArray*/) {
    layoutInflater = LayoutInflater.from(context);
    this.titleArray = titleArray;
    this.iconsArray = iconsArray;
  }

  @Override
  public int getCount() {
    return titleArray != null ? titleArray.length : 0;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    View view = convertView;

    if (view == null) {

      view = layoutInflater.inflate(R.layout.navigation_drawer_listview_item, parent, false);

      viewHolder = new ViewHolder();
      viewHolder.textView = (TextView) view.findViewById(R.id.text1);
      viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }

    viewHolder.textView.setText(titleArray[position]);


    if(iconsArray != null)
      viewHolder.imageView.setImageResource(iconsArray.getResourceId(position, 0));

    return view;
  }

  static class ViewHolder {
    TextView textView;
    ImageView imageView;
  }
}
