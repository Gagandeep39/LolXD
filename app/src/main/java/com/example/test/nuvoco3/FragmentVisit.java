package com.example.test.nuvoco3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by gagandeep on 20/12/17.
 */

public class FragmentVisit extends Fragment {
    Button mButton1, mButton2, mButton3, mButton4;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentvisit_layout, container, false);

        mButton1 = v.findViewById(R.id.button);
        mButton2 = v.findViewById(R.id.button2);
        mButton3 = v.findViewById(R.id.button3);
        mButton4 = v.findViewById(R.id.button4);


        return v;
    }
}
