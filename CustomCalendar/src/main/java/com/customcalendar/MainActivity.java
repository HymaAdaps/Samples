package com.customcalendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.customcalendar.calenderlibrary.CompactCalendarView;
import com.customcalendar.calenderlibrary.Event;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements IParserListener<JsonElement> {

//    private AppBarLayout appBarLayout;

    private SimpleDateFormat selDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private CompactCalendarView compactCalendarView;

    private boolean isExpanded = false;
    private RecyclerView rvEvents;
    private TextView tvNoData;
    //    private NestedScrollView nesScroll;
    private List<SchedulerEvent> eventsList = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH) + 1;
    int year = calendar.get(Calendar.YEAR);
    private String userId = "C1D76407-A7FE-4F2F-BAEE-A21C153C779C";
    private String siteId = "2";
    private int currentSelMonth, currentSelYear;
    private CalendarAdapter calendarAdapter;
    private LinearLayoutManager layoutManager;
    private String[] monthNameArray = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_temp);

        rvEvents = findViewById(R.id.rvEvents);
        tvNoData = findViewById(R.id.tvNoData);
        compactCalendarView = findViewById(R.id.compactcalendar_view);

        // Force English
        compactCalendarView.setLocale(Locale.ENGLISH);

        compactCalendarView.setShouldDrawDaysHeader(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(selDateFormat.format(dateClicked));
                getDayEvents(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                currentSelMonth = firstDayOfNewMonth.getMonth();
                setSubtitle(selDateFormat.format(firstDayOfNewMonth));
                requestForCalender("");
            }
        });

        // Set current date to today
        setCurrentDate(new Date());

        rvEvents.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) rvEvents.getLayoutManager());
                int lastPos = layoutManager.findLastVisibleItemPosition();
                int comLastPos = layoutManager.findLastCompletelyVisibleItemPosition();
                Log.e("Tag", lastPos + " , " + comLastPos);
            }
        });
        Calendar calendar = Calendar.getInstance();
        currentSelMonth = calendar.get(Calendar.MONTH);
        Date date = new Date();
        requestForCalender(selDateFormat.format(date));
    }

    private void getDayEvents(Date date) {
        try {
            List<SchedulerEvent> tempEventList = new ArrayList<>();

            SimpleDateFormat spf = new SimpleDateFormat("MMM dd, yyyy");
            String myDate = spf.format(date);

            tempEventList.add(new SchedulerEvent(myDate));
            for (int i = 0; i < eventsList.size(); i++) {
                if (!TextUtils.isEmpty(eventsList.get(i).startdate)) {
                    Date eventDate = selDateFormat.parse(eventsList.get(i).startdate);
                    if (date.equals(eventDate)) {
                        tempEventList.add(eventsList.get(i));
                    }
                }
            }
            if (tempEventList != null && tempEventList.size() > 1) {
                calendarAdapter.addItems(tempEventList);
                tvNoData.setVisibility(View.GONE);
                rvEvents.setVisibility(View.VISIBLE);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
                rvEvents.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
//        getDummyData();
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setNestedScrollingEnabled(false);
        calendarAdapter = new CalendarAdapter(this);
        rvEvents.setAdapter(calendarAdapter);
        sortDataByDate(eventsList);
    }

    private void sortDataByDate(List<SchedulerEvent> eventsList) {
        Collections.sort(eventsList, new Comparator<SchedulerEvent>() {
            @Override
            public int compare(SchedulerEvent o1, SchedulerEvent o2) {
                return Long.compare(o1.timeInMillis, o2.timeInMillis);
            }
        });
        formateMonthEvents();
    }


    private void formateMonthEvents() {
        List<SchedulerEvent> tempEventList = new ArrayList<>();
        int prevDay = 0, curDay = 0;
        for (int i = 0; i < eventsList.size(); i++) {
            curDay = Integer.parseInt(eventsList.get(i).startdate.substring(eventsList.get(i).startdate.lastIndexOf("-") + 1, eventsList.get(i).startdate.length()));
            int endDay = Integer.parseInt(eventsList.get(i).enddate.substring(eventsList.get(i).enddate.lastIndexOf("-") + 1, eventsList.get(i).enddate.length()));
            Log.e("TAg", " curDay " + curDay + " endDay " + endDay);
            if (curDay != endDay) {
                int loopCount = endDay - curDay + 1;
                String savEndTime = eventsList.get(i).endtime;
                String savName = eventsList.get(i).name;
                eventsList.get(i).type = 1;
                eventsList.get(i).endtime = "";
                eventsList.get(i).name = savName + "(day1/" + loopCount + ")";
                tempEventList.add(eventsList.get(i));
                prevDay = curDay;
                for (int j = 1; j <= endDay - curDay; j++) {
                    SchedulerEvent schedulerEvent = new SchedulerEvent();
                    if (j < endDay - curDay) {
                        schedulerEvent.id = eventsList.get(i).id;
                        int day = curDay + j;
                        schedulerEvent.type = 1;
                        int dayCount = j + 1;
                        schedulerEvent.name = savName + "(day" + dayCount + "/" + loopCount + ")";
                        schedulerEvent.timeInMillis = milliseconds(year + "-" + month + "-" + day);
                        schedulerEvent.imgurl = eventsList.get(i).imgurl;
                        tempEventList.add(schedulerEvent);
                    } else {//multi event is end
                        schedulerEvent.id = eventsList.get(i).id;
                        int day = curDay + j;
                        schedulerEvent.type = 1;
                        int dayCount = j + 1;
                        schedulerEvent.endtime = "Untill " + savEndTime;
                        schedulerEvent.name = savName + "(day" + dayCount + "/" + loopCount + ")";
                        schedulerEvent.imgurl = eventsList.get(i).imgurl;
                        schedulerEvent.timeInMillis = milliseconds(year + "-" + month + "-" + day);
                        tempEventList.add(schedulerEvent);
                    }

                }
            } else if (prevDay != curDay) {
                if (prevDay != curDay) {
                    eventsList.get(i).type = 1;
                } else {
                    eventsList.get(i).type = 2;
                }
                tempEventList.add(eventsList.get(i));
                prevDay = curDay;
            }
        }
        Collections.sort(tempEventList, new Comparator<SchedulerEvent>() {
            @Override
            public int compare(SchedulerEvent o1, SchedulerEvent o2) {
                return Long.compare(o1.timeInMillis, o2.timeInMillis);
            }
        });
        eventsList.clear();
        eventsList.addAll(tempEventList);
        addHeaders(tempEventList);
        calendarAdapter.addItems(tempEventList);
        addEventsDot();
    }

    private void addHeaders(List<SchedulerEvent> tempEventList) {
        String monthName = monthNameArray[currentSelMonth];
        int first = 0, last = first + 7;
        int daysInMonth = CommonUtils.getNoOfDaysOfMonth(month, year);
        int curDay = 0;
        String curDate = monthName + " " + first + " - " + monthName + " " + last;
        tempEventList.add(0, new SchedulerEvent(curDate));
        for (int i = 0; i < tempEventList.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(tempEventList.get(i).timeInMillis);
            curDay = calendar.get(Calendar.DATE);
            if (curDay <= last) {
                //do nothing
            } else {
                first = last + 1;
                last = first + 7;
                if (last > daysInMonth)
                    last = daysInMonth;
                curDate = monthName + " " + first + " - " + monthName + " " + last;
                tempEventList.add(i, new SchedulerEvent(curDate));
            }
        }
    }


    private void setCurrentDate(Date date) {
        setSubtitle(selDateFormat.format(date));
        if (compactCalendarView != null) {
            compactCalendarView.setCurrentDate(date);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = findViewById(R.id.title);

        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    private void setSubtitle(String subtitle) {
        TextView datePickerTextView = findViewById(R.id.date_picker_text_view);

        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void requestForCalender(String startDate) {
        parseCalenderPros(loadJSONFromAsset(currentSelMonth));
//        CommonUtils.showLoadingDialog(this);
//        Call<JsonElement> call = CalendarApplication.getWsClientListener().getCalendarPro(userId, siteId, startDate);
//        new WSUtils().requestForJsonObject(this, WSUtils.REQ_GET_CALENDER_PRO, call, this);
    }

    @Override
    public void successResponse(int requestCode, JsonElement response) {
        CommonUtils.hideLoadingDialog();
        switch (requestCode) {
            case WSUtils.REQ_GET_CALENDER_PRO:
                parseCalenderPros(response.toString());
                break;
        }
    }

    public String loadJSONFromAsset(int month) {
        String json = null;
        try {
            InputStream is;
            if (month == 3) {
                is = getAssets().open("events.json");
            } else if (month < 3) {
                is = getAssets().open("events_mar.json");
            } else {
                is = getAssets().open("events_may.json");
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void parseCalenderPros(String response) {
        eventsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("items");

            for (int i = 0; i < array.length(); i++) {
                JSONObject c = array.getJSONObject(i);

                SchedulerEvent event = new Gson().fromJson(c.toString(), SchedulerEvent.class);
                if (event.imgurl.equals(""))
                    event.imgurl = "http://res.cloudinary.com/checkedincare/image/upload/v1504788572/scheduler/care-companion-new.png";

                event.timeInMillis = milliseconds(event.startdate);
                eventsList.add(event);
            }
//            setCalenderItems();
            setAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    @Override
    public void unSuccessResponse(int requestCode, JsonElement response) {

    }

    @Override
    public void errorResponse(int requestCode, String error) {

    }

    @Override
    public void noInternetConnection(int requestCode) {

    }

    private void addEventsDot() {
        compactCalendarView.removeAllEvents();
        for (int i = 0; i < eventsList.size()/*calendarAdapter.getItemCount()*/; i++) {
            if (eventsList.get(i).timeInMillis > 0) {
                compactCalendarView.addEvent(new Event(Color.argb(255, 169, 68, 65), eventsList.get(i).timeInMillis,
                        "Event at " + new Date(eventsList.get(i).timeInMillis)), true);
//                compactCalendarView.addEvents(Arrays.asList(new Event(Color.argb(255, 169, 68, 65), eventsList.get(i).timeInMillis,
//                        "Event at " + new Date(eventsList.get(i).timeInMillis))));
            }
        }
    }
//for now removing 3rd type
//            if (curDay <= last) {
//        if (prevDay != curDay) {
//            eventsList.get(i).type = 1;
//        } else {
//            eventsList.get(i).type = 2;
//        }
//        tempEventList.add(eventsList.get(i));
//        prevDay = curDay;
//    } else {
//        first = last + 1;
//        last = first + 7;
//        if (last > daysInMonth)
//            last = daysInMonth;
//        curDate = monthName + " " + first + " - " + monthName + " " + last;
//        tempEventList.add(new SchedulerEvent(curDate));
//
//        eventsList.get(i).type = 1;//else one loop items gets wasted
//        tempEventList.add(eventsList.get(i));
//        prevDay = curDay;
//    }

}
