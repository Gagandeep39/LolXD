package com.example.test.nuvoco3.helpers;

import com.example.test.nuvoco3.visits.JCP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddEvents {
    long mDate;

    public void addEvent(ArrayList<JCP> mJcpList) {

        try {
            String dateString = mJcpList.get(1).getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);

            mDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
