package com.example.lixudong.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lixudong.todoapp.R;
import com.example.lixudong.todoapp.model.Item;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item  = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        tvTitle.setText(item.title);

        return convertView;
    }
}
