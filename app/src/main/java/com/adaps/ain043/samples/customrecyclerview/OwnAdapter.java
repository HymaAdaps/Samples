package com.adaps.ain043.samples.customrecyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.databinding.ItemOwnBinding;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OwnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        private ItemOwnBinding binding;

        public TextViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }

    private final List<Events> messages;


    public OwnAdapter(List<Events> messages) {
        this.messages = messages;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_own, parent, false);
        return new TextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Events m = messages.get(position);
        final Context context = viewHolder.itemView.getContext();

        TextViewHolder holder = (TextViewHolder) viewHolder;

        int previousDay = 0;
        if (position > 0) {
            Events pm = messages.get(position - 1);
            previousDay = pm.dateNo;
        }
//        if (TextUtils.isEmpty(previousDay)) {
//            holder.binding.tvDate.setVisibility(View.VISIBLE);
//            holder.binding.tvDate.setText(m.date);
//        } else {
        boolean sameDay = m.dateNo == previousDay;

        if (sameDay) {
            TextView tv = new TextView(context);
            tv.setText(m.name);
            tv.setBackground(context.getResources().getDrawable(R.drawable.bg_curved_gray));
            tv.setTextColor(context.getResources().getColor(R.color.white));
            tv.setTextSize(20);
            tv.setPadding(10, 10, 10, 10);
            holder.binding.llEvents.addView(tv);
            holder.binding.tvDate.setVisibility(View.INVISIBLE);
            holder.binding.tvEvent.setVisibility(View.GONE);
        } else {
            holder.binding.rlItem.setVisibility(View.VISIBLE);
            holder.binding.tvDate.setText(m.date.substring(m.date.lastIndexOf(",") + 2, m.date.length()));
            holder.binding.tvDay.setText(m.date.substring(0, 3));
//            holder.binding.tvDay.setText(m.day);
            holder.binding.tvEvent.setText(m.name);
//            }
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}