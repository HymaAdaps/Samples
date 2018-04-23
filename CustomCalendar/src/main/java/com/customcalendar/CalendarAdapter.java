package com.customcalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


/**
 * Created by ALPHABITTECH on 3/15/2018.
 */

public class CalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SchedulerEvent> eventsList;
    private Context context;

    public CalendarAdapter(Context context) {
        this.context = context;
        this.eventsList = new ArrayList<>();
    }

    public void addItems(List<SchedulerEvent> eventsList) {
        this.eventsList = eventsList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new CalendarVH1(LayoutInflater.from(context).inflate(R.layout.item_calendar_1, parent, false));
        } else if (viewType == 2) {
            return new CalendarVH2(LayoutInflater.from(context).inflate(R.layout.item_calendar_2, parent, false));
        } else {
            return new CalendarVH3(LayoutInflater.from(context).inflate(R.layout.item_calendar_3, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return eventsList.get(position).type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SchedulerEvent events = eventsList.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(events.timeInMillis);
        int date = calendar.get(Calendar.DATE);
        String day = CommonUtils.getDayName(calendar.get(Calendar.DAY_OF_WEEK));

        switch (events.type) {
            case 1:
                CalendarVH1 calendarVH1 = (CalendarVH1) holder;
                calendarVH1.tvDate.setText(String.format("%02d", date));
                calendarVH1.tvDay.setText(day);
                calendarVH1.tvTitle.setText(events.name);
//                calendarVH1.clEvent.setBackgroundResource(R.drawable.bg_curved_event);
//                GradientDrawable drawable = (GradientDrawable) calendarVH1.clEvent.getBackground();
//                Random rnd = new Random();
//                drawable.setColor(Color.argb(255, 126, 126, 126));
                calendarVH1.tvStartTime.setText(events.starttime);
                if (!TextUtils.isEmpty(events.endtime))
                    calendarVH1.tvEndTime.setText(events.endtime.contains("Untill") ? events.endtime : " - " + events.endtime);
                calendarVH1.tvStartTime.setVisibility(!TextUtils.isEmpty(events.starttime) ? View.VISIBLE : View.GONE);
                calendarVH1.tvEndTime.setVisibility(!TextUtils.isEmpty(events.endtime) ? View.VISIBLE : View.GONE);
                Glide.with(context).load(events.imgurl).into(calendarVH1.ivEvent);
                break;
            case 2:
                CalendarVH2 calendarVH2 = (CalendarVH2) holder;
                calendarVH2.tvTitle.setText(events.name);
                calendarVH2.tvStartTime.setText(events.starttime);
                calendarVH2.tvEndTime.setText(" - " + events.endtime);
//                calendarVH2.clEvent.setBackgroundResource(R.drawable.bg_curved_event);
//                drawable = (GradientDrawable) calendarVH2.clEvent.getBackground();
//                rnd = new Random();
//                drawable.setColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                Glide.with(context).load(events.imgurl).into(calendarVH2.ivEvent);
                break;
            case 3:
                CalendarVH3 calendarVH3 = (CalendarVH3) holder;
                calendarVH3.tvTitle.setText(events.name);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class CalendarVH1 extends RecyclerView.ViewHolder {

        private TextView tvDate, tvDay, tvTitle, tvStartTime, tvEndTime;
        public ConstraintLayout clEvent;
        private ImageView ivEvent;

        public CalendarVH1(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            clEvent = itemView.findViewById(R.id.clEvent);
            ivEvent = itemView.findViewById(R.id.ivEvent);
        }
    }

    public class CalendarVH2 extends RecyclerView.ViewHolder {

        private TextView tvDate, tvDay, tvTitle, tvStartTime, tvEndTime;
        public ConstraintLayout clEvent;
        private ImageView ivEvent;

        public CalendarVH2(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            clEvent = itemView.findViewById(R.id.clEvent);
            ivEvent = itemView.findViewById(R.id.ivEvent);
        }
    }

    public class CalendarVH3 extends RecyclerView.ViewHolder {

        private TextView tvDate, tvDay, tvTitle;

        public CalendarVH3(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
