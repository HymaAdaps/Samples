package com.adaps.ain043.samples.customrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adaps.ain043.samples.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatEGRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView timeText;

        public TextViewHolder(View v) {
            super(v);
            timeText = (TextView) v.findViewById(R.id.timeText);
            textView = (TextView) v.findViewById(R.id.textView);
        }
    }

    private final List<Message> messages;


    public ChatEGRecyclerAdapter(List<Message> messages) {
        this.messages = messages;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recy, parent, false);
        return new TextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Message m = messages.get(position);
        final Context context = viewHolder.itemView.getContext();

        TextViewHolder holder = (TextViewHolder) viewHolder;
        holder.textView.setVisibility(View.VISIBLE);
        holder.textView.setText(m.message);

        long previousTs = 0;
        if (position > 0) {
            Message pm = messages.get(position - 1);
            previousTs = pm.date;
        }
        setTimeTextVisibility(m.date, previousTs, holder.timeText);
    }

    private void setTimeTextVisibility(long ts1, long ts2, TextView timeText) {

        if (ts2 == 0) {
            timeText.setVisibility(View.VISIBLE);
            timeText.setText(getDate(ts1));
        } else {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTimeInMillis(ts1);
            cal2.setTimeInMillis(ts2);

//            boolean sameMonth = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
//                    cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
            int pastYear, currentYear, pastDay, currentDay;
            pastYear = cal1.get(Calendar.YEAR);
            currentYear = cal2.get(Calendar.YEAR);
            pastDay = cal1.get(Calendar.DAY_OF_YEAR);
            currentDay = cal2.get(Calendar.DAY_OF_YEAR);
            Log.e("Date", "pastYear " + pastYear + " currentYear " + currentYear + " pastDay " + pastDay + " currentDay " + currentDay);
            boolean sameDay = (pastYear == currentYear && pastDay == currentDay);

            if (sameDay) {
                timeText.setVisibility(View.GONE);
                timeText.setText("");
            } else {
                timeText.setVisibility(View.VISIBLE);
                timeText.setText(getDate(ts2));
            }

        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time /** 1000L*/);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        Log.e("Date", date);
        return date;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}