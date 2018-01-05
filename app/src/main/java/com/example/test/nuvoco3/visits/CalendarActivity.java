package com.example.test.nuvoco3.visits;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.test.nuvoco3.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.test.nuvoco3.helpers.Contract.VISIT_STATUS_COMPLETED;
import static com.example.test.nuvoco3.helpers.Contract.VISIT_STATUS_PENDING;
import static com.example.test.nuvoco3.helpers.Contract.VISIT_STATUS_PLANNED;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "Calendar Activity";
    CompactCalendarView mCalendarView;
    TextView mTextViewMonth;
    ArrayList<String> mEvent;
    ArrayAdapter mDateAdapter;
    ArrayList<JCPDetails> mJCPList;
    boolean isChecked = false;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM  yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initializeViews();
        initializeVariables();
        calendarOperations();


        List<Event> mEventList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {

            mEventList.add(createEvent());
        }

        mCalendarView.addEvents(mEventList);

    }

    private void calendarOperations() {
        mCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
//                List<Event> bookingsFromMap = mCalendarView.getEvents(dateClicked);
////                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
//                if (bookingsFromMap != null) {
//                    Log.d(TAG, bookingsFromMap.toString());
//                    mEvent.clear();
//                    for (Event booking : bookingsFromMap) {
//                       mEvent.add((String) booking.getData());
//                    }
//                    mDateAdapter.notifyDataSetChanged();
//                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mTextViewMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
    }

    private void initializeVariables() {


    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mCalendarView = findViewById(R.id.compactcalendar_view);
        mTextViewMonth = findViewById(R.id.textViewMonth);
        mTextViewMonth.setText(dateFormatForMonth.format(mCalendarView.getFirstDayOfCurrentMonth()));

    }


    private Event createEvent() {
        int mColor = Color.YELLOW;
        Event mEvent;
        long mDate = 0;

        for (int i = 0; i < 50; i++) {
            mDate = getLongDate(mJCPList.get(i).getDate());
            String mVisitStatus = mJCPList.get(i).getStatus();
            if (mVisitStatus.equals(VISIT_STATUS_PLANNED)) {
                mColor = Color.BLUE;
            } else if (mVisitStatus.equals(VISIT_STATUS_PENDING)) {
                mColor = Color.RED;
            } else if (mVisitStatus.equals(VISIT_STATUS_COMPLETED)) {
                mColor = Color.GREEN;
            }
        }
        mEvent = new Event(mColor, mDate);


        return mEvent;

    }

    private long getLongDate(String string) {
        long mDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(string);

            mDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_customer_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.checkable_menu);
        checkable.setChecked(isChecked);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
//        else if (item.getItemId() == R.id.checkable_menu) {
//            isChecked = !item.isChecked();
//            item.setChecked(isChecked);
//            mCustomerList.clear();
//            populateCustomers();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


}
