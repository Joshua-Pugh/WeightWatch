package com.weightwatch.view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.weightwatch.R;
import com.weightwatch.model.DailyWeight;

import java.util.List;

public class WeightAdapter extends BaseAdapter {

    private final Context context;
    private final List<DailyWeight> dailyWeightList;
    private final OnDeleteClickListener onDeleteClickListener;

    public WeightAdapter(Context context, List<DailyWeight> dailyWeightList,
                         OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.dailyWeightList = dailyWeightList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getCount() {
        return dailyWeightList.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyWeightList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_weight, parent, false);
        }

        // Get the current daily weight
        DailyWeight entry = dailyWeightList.get(position);

        // Bind data to views
        TextView tvWeight = convertView.findViewById(R.id.tvWeight);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        Button btnDelete = convertView.findViewById(R.id.deleteButton);

        // Set the weight
        tvWeight.setText(String.valueOf(entry.getWeight()));

        // Set date
        tvDate.setText(entry.getDate());

        btnDelete.setOnClickListener(view -> onDeleteClickListener.onDeleteClick(entry));

        return convertView;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(DailyWeight entry);
    }
}
