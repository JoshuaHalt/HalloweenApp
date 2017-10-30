package com.zall.halloweenapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zall.halloweenapp.HalloweenApplication;
import com.zall.halloweenapp.R;

import java.util.ArrayList;

public class SimpleTextItemAdapter<T> extends BaseAdapter {

    private HalloweenApplication theApp = null;
    private LayoutInflater _inflater;

    private ArrayList<T> _data;

    public SimpleTextItemAdapter(Context context, ArrayList<T> curData) {
        theApp = (HalloweenApplication) context.getApplicationContext();
        _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        _data = curData;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public T getItem(int position) {
        return _data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleTextItemViewHolder viewHolder;

        if (convertView == null) {
            convertView = _inflater.inflate(R.layout.row_simple_text_item, parent, false);

            viewHolder = new SimpleTextItemViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SimpleTextItemViewHolder) convertView.getTag();
        }

        T current = _data.get(position);

        viewHolder.tvText.setText(current.toString());

        viewHolder.rlRootContainer.setBackgroundResource(position % 2 == 0 ?
                R.drawable.row_first_background : R.drawable.row_second_background);

        return convertView;
    }

    private class SimpleTextItemViewHolder {
        RelativeLayout rlRootContainer = null;

        TextView tvText = null;

        public SimpleTextItemViewHolder(View convertView) {
            rlRootContainer = (RelativeLayout) convertView.findViewById(R.id.rlRowSimpleTextItemRootContainer);

            tvText = (TextView) convertView.findViewById(R.id.tvRowSimpleTextItemText);
        }
    }
}
