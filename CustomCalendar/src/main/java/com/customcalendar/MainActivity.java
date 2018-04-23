package com.customcalendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
    private SimpleDateFormat selDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private CompactCalendarView compactCalendarView;

    private RecyclerView rvEvents;
    private TextView tvNoData;
    private List<SchedulerEvent> eventsList, eventsMainList;
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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("id")) {
            int id = bundle.getInt("id");
            String title = bundle.getString("title");
            String message = bundle.getString("message");

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.create().show();
        } else {
//            requestForCalender(selDateFormat.format(date));
        }
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

    private int prevDate = 0;

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
        eventsMainList = new ArrayList<>();
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
            eventsMainList.addAll(eventsList);
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
        for (int i = 0; i < eventsList.size(); i++) {
            if (eventsList.get(i).timeInMillis > 0) {
                compactCalendarView.addEvent(new Event(Color.argb(255, 169, 68, 65), eventsList.get(i).timeInMillis,
                        "Event at " + new Date(eventsList.get(i).timeInMillis)), true);
//                compactCalendarView.addEvents(Arrays.asList(new Event(Color.argb(255, 169, 68, 65), eventsList.get(i).timeInMillis,
//                        "Event at " + new Date(eventsList.get(i).timeInMillis))));
            }
        }
        for (int i = 0; i < eventsMainList.size(); i++) {
            setReminder(this, eventsMainList.get(i).starttime, eventsMainList.get(i).id, eventsMainList.get(i).name, eventsMainList.get(i).description);
        }
    }

    public void setReminder(Context context, String time, int id, String title, String message) {
        String[] separated = time.split(":");
        int hour = Integer.parseInt(separated[0]);
        int min = Integer.parseInt(separated[1]);

        Calendar calendar = Calendar.getInstance();
        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(Calendar.HOUR_OF_DAY, hour);
        setcalendar.set(Calendar.MINUTE, min);
        setcalendar.set(Calendar.SECOND, 0);
        // cancel already scheduled reminders
        cancelReminder(context, AlarmReceiver.class, id);

        if (setcalendar.before(calendar))
            setcalendar.add(Calendar.DATE, 1);
        long timeDelay = setcalendar.getTimeInMillis() - (5 * 60 * 1000);
//        int timeDelay = (int) (setcalendar.getTimeInMillis() - (5 * 60 * 1000));
        Log.e("mils", timeDelay + "");

        // Enable a receiver
        ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        bundle.putString("message", message);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setExact(AlarmManager.RTC_WAKEUP, timeDelay, pendingIntent);
    }

    public void cancelReminder(Context context, Class<?> cls, int id) {
        // Disable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
