package com.millersvillecs.moleculeandroid.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.millersvillecs.moleculeandroid.R;

public class CategoryBaseAdapter extends BaseAdapter {
	
	private Context context;
	private CategoryItem[] data;
	
	private class CategoryHolder {
		TextView title, description;
	}
	
	public CategoryBaseAdapter(Context context, CategoryItem[] data) {
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
		CategoryHolder holder;
        if (convertView == null) {
        	holder = new CategoryHolder();
        	convertView = layoutInflater.inflate(R.layout.category_item, null);
        	holder.title = (TextView) convertView.findViewById(R.id.category_item_title);
        	holder.description = (TextView) convertView.findViewById(R.id.category_item_description);
        	convertView.setTag(holder);
        } else {
        	holder = (CategoryHolder) convertView.getTag();
        }
        
        CategoryItem item = (CategoryItem) getItem(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        
        return convertView;
	}

}
