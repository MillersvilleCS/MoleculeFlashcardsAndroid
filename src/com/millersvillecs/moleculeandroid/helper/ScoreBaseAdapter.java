package com.millersvillecs.moleculeandroid.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.millersvillecs.moleculeandroid.R;

/**
 * 
 * @author connor
 * 
 * Custom implementation of a ListViewAdapter for our High Scores screen.
 * 
 */
public class ScoreBaseAdapter extends BaseAdapter{
    
    private Context context;
    private ScoreItem[] data;
    
    private class ScoreHolder {
        TextView rank, username, score;
    }
    
    public ScoreBaseAdapter(Context context, ScoreItem[] data) {
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
        ScoreHolder holder;
        if (convertView == null) {
            holder = new ScoreHolder();
            convertView = layoutInflater.inflate(R.layout.score_item, null);
            holder.rank = (TextView) convertView.findViewById(R.id.rank);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        } else {
            holder = (ScoreHolder) convertView.getTag();
        }
        
        ScoreItem item = (ScoreItem) getItem(position);
        holder.rank.setText(item.getRank());
        holder.username.setText(item.getUsername());
        holder.score.setText(item.getScore());
        
        return convertView;
    }

}
