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

                Log.e("pos", "" + position);

                JSONObject rowItemObject = rowItems.get(position);
                viewHolder.rowTitle.setText(rowItemObject.has("title") ? rowItemObject.getString("title") : "NA");
                viewHolder.rowDescription.setText(rowItemObject.has("description") ? rowItemObject.getString("description") : "NA");

                if (rowItemObject.has("imageHref")) {
                    if (rowItemObject.getString("imageHref") != null) {
                        Picasso.with(context).load(rowItemObject.getString("imageHref")).placeholder(R.drawable.placeholder_image).
                                into(viewHolder.rowImage);
                    } else {
                        Picasso.with(context).load(R.drawable.placeholder_image).into(viewHolder.rowImage);
                    }
                }

            } else {
                viewHolder = (ViewHolder) v.getTag();

                Log.e("pos", "" + position);

                JSONObject rowItemObject = rowItems.get(position);
                viewHolder.rowTitle.setText(rowItemObject.has("title") ? rowItemObject.getString("title") : "NA");
                viewHolder.rowDescription.setText(rowItemObject.has("description") ? rowItemObject.getString("description") : "NA");

                if (rowItemObject.has("imageHref")) {
                    if (rowItemObject.getString("imageHref") != null) {
                        Picasso.with(context).load(rowItemObject.getString("imageHref")).placeholder(R.drawable.placeholder_image).
                                into(viewHolder.rowImage);
                    } else {
                        Picasso.with(context).load(R.drawable.placeholder_image).into(viewHolder.rowImage);
                    }
                }

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
