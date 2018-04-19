package com.adaps.ain043.samples.customrecyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.databinding.ActivityGroupRecycleBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ain043 on 4/17/2018 at 8:23 AM
 */

public class GroupRecyclerActivity extends AppCompatActivity {
    private ActivityGroupRecycleBinding binding;
    private SimpleAdapter simpleAdapter;

    String[] sCheeseStrings = new String[]{"one", "two", "three", "four", "five", "six"};
    private ChatEGRecyclerAdapter adapter;
    List<Message> messages;
    List<Events> eventsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_recycle);

        initComponents();
        Log.e("millis", milliseconds("2018-04-11") + " ");
    }

    public long milliseconds(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void initComponents() {
//        setAdapter();
//        setMyAdapter();
//        getDummyData();
        append();
        setOwnAdapter();
    }

    private List<EventGroupList> eventGroupLists = new ArrayList<>();

    void append() {
        if (eventGroupLists.size() < 0) {
            int count = 31;
            long today = CalendarUtils.today();
            for (int i = -count; i < count; i++) {
                EventGroupList groupList = new EventGroupList();
                groupList.date = CalendarUtils.toDayString(this, today + DateUtils.DAY_IN_MILLIS * i);
                eventGroupLists.add(groupList);
            }
        } else {
//            int count = 31;
//            long daysMillis = eventGroupLists.size() * DateUtils.DAY_IN_MILLIS;
//            int inserted = 0;
//            for (int i = 0; i < count; i++) {
//                EventGroupList first = eventGroupLists.get(i);
//
//                EventGroupList last = new EventGroupList(this, first.mTimeMillis + daysMillis);
//                inserted += last.itemCount() + 1;
//                eventGroupLists.add(last);
//            }
        }
    }

    private void setOwnAdapter() {
        eventsList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Events events = new Events();
            events.name = "A " + i + " Event";
            events.date = CalendarUtils.toDayString(this, i + DateUtils.DAY_IN_MILLIS * i);
            events.dateNo = i;
            events.day = "Sun";
            eventsList.add(events);
        }
        Events events = new Events();
        events.name = "SEv Event";
        events.dateNo = 10;
        events.date = CalendarUtils.toDayString(this, 10 + DateUtils.DAY_IN_MILLIS * 10);
        ;
        events.day = "Sun";
        eventsList.add(events);

        events = new Events();
        events.name = "Eigh Event";
        events.dateNo = 11;
        events.date = CalendarUtils.toDayString(this, 11 + DateUtils.DAY_IN_MILLIS * 11);
        ;
        events.day = "Sun";
        eventsList.add(events);

        events = new Events();
        events.name = "Nine Event";
        events.dateNo = 12;
        events.date = CalendarUtils.toDayString(this, 12 + DateUtils.DAY_IN_MILLIS * 12);
        ;
        events.day = "Sun";
        eventsList.add(events);

        events = new Events();
        events.name = "Ten Event";
        events.dateNo = 13;
        events.date = CalendarUtils.toDayString(this, 13 + DateUtils.DAY_IN_MILLIS * 13);
        ;
        events.day = "Sun";
        eventsList.add(events);

        Collections.sort(eventsList, new Comparator<Events>() {
            @Override
            public int compare(Events o1, Events o2) {
                return Long.compare(o1.dateNo, o2.dateNo);
            }
        });

        binding.rvGroup.setLayoutManager(new LinearLayoutManager(this));
//        binding.rvGroup.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Your RecyclerView.Adapter
        OwnAdapter adapter = new OwnAdapter(eventsList);

        binding.rvGroup.setAdapter(adapter);
    }

    private void getDummyData() {
        messages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Message message = new Message();
            if (i < 10) message.date = 1523763165000L;
            else message.date = 1524022365000L;
            message.message = "message at " + i;
            messages.add(message);
        }
    }

    private void setMyAdapter() {
        binding.rvGroup.setLayoutManager(new LinearLayoutManager(this));
        binding.rvGroup.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Your RecyclerView.Adapter
        adapter = new ChatEGRecyclerAdapter(messages);

        binding.rvGroup.setAdapter(adapter);
    }

    private void setAdapter() {
        binding.rvGroup.setLayoutManager(new LinearLayoutManager(this));
        binding.rvGroup.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Your RecyclerView.Adapter
        simpleAdapter = new SimpleAdapter(this, sCheeseStrings);


        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Section 1"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5, "Section 2"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(12, "Section 3"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14, "Section 4"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(20, "Section 5"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this, R.layout.section, R.id.section_text, simpleAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        binding.rvGroup.setAdapter(mSectionedAdapter);
    }
}
