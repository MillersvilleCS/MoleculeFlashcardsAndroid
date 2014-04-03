package com.millersvillecs.moleculeandroid.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.millersvillecs.moleculeandroid.R;

public class SelectionBaseAdapter extends BaseAdapter{
	
	private Context context;
	private SelectionItem[] data;
	
	private class SelectionHolder {
		ImageView image;
		TextView title, description;
	}
	
	public SelectionBaseAdapter(Context context, SelectionItem[] data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return this.data.length;
	}

	@Override
	public Object getItem(int position) {
		return this.data[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = (LayoutInflater)
	            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		SelectionHolder holder;
        if (convertView == null) {
        	holder = new SelectionHolder();
        	convertView = layoutInflater.inflate(R.layout.selection_item, null);
        	holder.image = (ImageView) convertView.findViewById(R.id.selection_item_image);
        	holder.title = (TextView) convertView.findViewById(R.id.selection_item_title);
        	holder.description = (TextView) convertView.findViewById(R.id.selection_item_description);
        	convertView.setTag(holder);
        } else {
        	holder = (SelectionHolder) convertView.getTag();
        }
        
        SelectionItem item = (SelectionItem) getItem(position);
        holder.image.setImageBitmap(item.getImage());
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        
        return convertView;
	}

}
