package com.stephaniecure.stephapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter {


    private final List<String> data;
    private final Context context;

    public MyAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListRow(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListRow row = (ListRow) holder;
        row.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ListRow extends RecyclerView.ViewHolder {
        private TextView textView;

        public ListRow(View view) {
            super(view);
            textView = view.findViewById(R.id.my_list_item_text);
        }

        public void bindData(String text) {
            textView.setText(text);
        }
    }
}
