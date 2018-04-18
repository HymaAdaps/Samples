package com.customcalendar;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements IParserListener<JsonElement> {

    private AppBarLayout appBarLayout;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH);

    private CompactCalendarView compactCalendarView;

    private boolean isExpanded = false;
    private RecyclerView rvEvents;
    private ImageView arrow;
    private NestedScrollView nesScroll;
    private List<SchedulerEvent> eventsList = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    private int TYPE_PRIMARY = 1;
    private int TYPE_SECONDARY = 2;
    private int TYPE_THRICEARY = 3;
    private String userId = "C1D76407-A7FE-4F2F-BAEE-A21C153C779C";
    private String siteId = "2";
    private int currentSelMonth, currentSelYear;
    private CalendarAdapter calendarAdapter;
    private LinearLayoutManager layoutManager;

    public enum State {
        EXPANDED,
        COLLAPSED,
    }

    public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {


        private State mCurrentState = State.EXPANDED;

        @Override
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED);
                }
                mCurrentState = State.EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED);
                }
                mCurrentState = State.COLLAPSED;
            }
        }

        public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("CompactCalendarViewToolbar");

        rvEvents = findViewById(R.id.rvEvents);
        nesScroll = findViewById(R.id.nesScroll);

        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                showHideCalendar();
            }
        });

        // Set up the CompactCalendarView
        compactCalendarView = findViewById(R.id.compactcalendar_view);

        // Force English
        compactCalendarView.setLocale(TimeZone.getDefault(), /*Locale.getDefault()*/Locale.ENGLISH);

        compactCalendarView.setShouldDrawDaysHeader(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                currentSelMonth = firstDayOfNewMonth.getMonth();
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        // Set current date to today
        setCurrentDate(new Date());

        arrow = findViewById(R.id.date_picker_arrow);

        RelativeLayout datePickerButton = findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHideCalendar();
            }
        });
        nesScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {

                    layoutManager = ((LinearLayoutManager) rvEvents.getLayoutManager());
                    int visibleItemCount = layoutManager.findFirstVisibleItemPosition();
                    int totalItemCount = layoutManager.findLastVisibleItemPosition();

                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        int pastVisiblesItems = layoutManager.findLastCompletelyVisibleItemPosition();

                        Log.e("positios", " pastVisiblesItems " + pastVisiblesItems);
//working call for another month change lable as well  it may not work
                    }
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        currentSelMonth = calendar.get(Calendar.MONTH);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        requestForCalender(formatter.format(date));
    }

    private void showHideCalendar() {
        float rotation = isExpanded ? 0 : 180;
        ViewCompat.animate(arrow).rotation(rotation).start();

        isExpanded = !isExpanded;
        appBarLayout.setExpanded(isExpanded, true);
    }

    private void setAdapter() {
//        getDummyData();
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setNestedScrollingEnabled(false);
        calendarAdapter = new CalendarAdapter(this);
        rvEvents.setAdapter(calendarAdapter);
        sortDataByDate();
    }

    private void sortDataByDate() {
        Collections.sort(eventsList, new Comparator<SchedulerEvent>() {
            @Override
            public int compare(SchedulerEvent o1, SchedulerEvent o2) {
                return Long.compare(o1.timeInMillis, o2.timeInMillis);
            }
        });
        formateMonthEvents();
    }

    String[] monthNameArray = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    private void formateMonthEvents() {
        Calendar calendar = Calendar.getInstance();
        String monthName = monthNameArray[currentSelMonth - 1];
        List<SchedulerEvent> tempEventList = new ArrayList<>();
        int first = 0, last = first + 7;
        int daysInMonth = getNoOfDaysOfMonth();
        int prevDay = 0, curDay = 0;
        String curDate = monthName + " " + first + " - " + monthName + " " + last;
        tempEventList.add(new SchedulerEvent(curDate));
        for (int i = 0; i < eventsList.size(); i++) {
            curDay = Integer.parseInt(eventsList.get(i).startdate.substring(eventsList.get(i).startdate.lastIndexOf("-") + 1, eventsList.get(i).startdate.length()));
            if (curDay <= last) {
                if (prevDay != curDay) {
                    eventsList.get(i).type = 1;
                } else {
                    eventsList.get(i).type = 2;
                }
                tempEventList.add(eventsList.get(i));
                prevDay = curDay;
            } else {
                first = last + 1;
                last = first + 7;
                if (last > daysInMonth)
                    last = daysInMonth;
                curDate = monthName + " " + first + " - " + monthName + " " + last;
                tempEventList.add(new SchedulerEvent(curDate));

                eventsList.get(i).type = 1;//else one loop items gets wasted
                tempEventList.add(eventsList.get(i));
                prevDay = curDay;
            }
        }
        calendarAdapter.addItems(tempEventList);
    }

    private void getDummyData() {

        Calendar currCal = Calendar.getInstance();
        for (int i = 0; i < getNoOfDaysOfMonth(); i++) {
            Events events = new Events();
            currCal.set(year, month, i);
            events.timeInMillis = currCal.getTimeInMillis();
            events.title = "Event at 3" + String.valueOf(i);
            if (i % 3 == 0) {
                events.type = TYPE_PRIMARY;
            } else if (i % 3 == 1) {
                events.type = TYPE_SECONDARY;
            } else if (i % 3 == 2) {
                events.type = TYPE_THRICEARY;
            }
//            eventsList.add(events);
        }
    }

    private int getNoOfDaysOfMonth() {
        switch (month % 2) {
            case 1:
                return 31;
            case 0:
                if (month == 2) {
                    if (year % 4 == 0) {
                        return 29;
                    } else {
                        return 28;
                    }
                } else {
                    return 30;
                }
        }
        return 0;
    }

    private void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));
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
        parseCalenderPros(loadJSONFromAsset());
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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("events.json");
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
}
