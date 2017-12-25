package com.example.test.nuvoco3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.nuvoco3.market.BrandPriceActivity;
import com.example.test.nuvoco3.market.InsertGeneralMarketActivity;

/**
 * Created by gagandeep on 20/12/17.
 */

public class FragmentMarket extends Fragment {
    Button mButtonBrandPrice, mButton2, mButton3, mButtonGeneralMarket;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentmarket_layout, container, false);
        mButtonBrandPrice = v.findViewById(R.id.button);
        mButton2 = v.findViewById(R.id.button2);
        mButton3 = v.findViewById(R.id.button3);
        mButtonGeneralMarket = v.findViewById(R.id.button4);

        mButtonBrandPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), BrandPriceActivity.class));
            }
        });
        mButtonGeneralMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), InsertGeneralMarketActivity.class));
            }
        });









        return v;
    }
}
