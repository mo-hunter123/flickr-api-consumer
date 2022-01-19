package com.example.jsonparser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private List<FlickItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public  CustomAdapter(Context context, List<FlickItem> listData){
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.listitemview, null);
            holder = new ViewHolder();
            holder.flickrImage = (ImageView) convertView.findViewById(R.id.imageView);
            holder.itemTitle = (TextView) convertView.findViewById(R.id.textView);
            holder.itemDateTaken = (TextView) convertView.findViewById(R.id.textView2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FlickItem item = this.listData.get(position);
        holder.itemTitle.setText(item.getTitle());
        holder.itemDateTaken.setText(item.getDate_taken());
        Picasso.with(context)
                .load(item.getMedia())
                .into(holder.flickrImage);

        return  convertView;
    }


    static class ViewHolder{
        ImageView flickrImage;
        TextView itemTitle;
        TextView itemDateTaken;
    }
}
