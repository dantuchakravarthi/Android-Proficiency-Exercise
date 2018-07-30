package com.chakravarthi.listviewtask.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chakravarthi.listviewtask.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class RowListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<JSONObject> rowItems;
    private LayoutInflater inflater;

   public RowListAdapter(Context context, ArrayList<JSONObject> rowItems) {
       this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       this.context = context;
       this.rowItems = rowItems;
   }

    public void refreshEvents(ArrayList<JSONObject> rowItems) {
        this.rowItems.clear();
        this.rowItems.addAll(rowItems);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;

        try {
            if (v == null) {
                viewHolder = new ViewHolder();
                v = inflater.inflate(R.layout.row_item, null);
                v.setTag(viewHolder);

                viewHolder.rowTitle = v.findViewById(R.id.row_title);
                viewHolder.rowDescription = v.findViewById(R.id.row_description);
                viewHolder.rowImage = v.findViewById(R.id.row_image);
            } else {
                viewHolder = (ViewHolder) v.getTag();
            }

            JSONObject rowItemObject = rowItems.get(position);

            if(rowItemObject.has("title")) {
                if(!rowItemObject.getString("title").equals("null")) {
                    viewHolder.rowTitle.setText(rowItemObject.getString("title"));
                } else {
                    viewHolder.rowTitle.setText(R.string.not_available);
                }
            } else {
                viewHolder.rowTitle.setText(R.string.not_available);
            }

            if(rowItemObject.has("description")) {
                if(!rowItemObject.getString("description").equals("null")) {
                    viewHolder.rowDescription.setText(rowItemObject.getString("description"));
                } else {
                    viewHolder.rowDescription.setText(R.string.not_available);
                }
            } else {
                viewHolder.rowDescription.setText(R.string.not_available);
            }

            if (rowItemObject.has("imageHref")) {
                if (rowItemObject.getString("imageHref") != null) {
                    Picasso.with(context).load(rowItemObject.getString("imageHref")).placeholder(R.drawable.placeholder_image).
                            into(viewHolder.rowImage);
                } else {
                    Picasso.with(context).load(R.drawable.placeholder_image).into(viewHolder.rowImage);
                }
            } else {
                Picasso.with(context).load(R.drawable.placeholder_image).into(viewHolder.rowImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    class ViewHolder{
        TextView rowTitle, rowDescription;
        ImageView rowImage;
    }
}
