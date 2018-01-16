package com.example.test.nuvoco3.market;

import android.widget.EditText;
import android.widget.TextView;

public class DynamicPrice {
    private TextView mTextView;
    private EditText mEditTextRSP;
    private EditText mEditTextWSP;
    private EditText mEditTextStock;

    public DynamicPrice(TextView mTextView, EditText mEditTextRSP, EditText mEditTextWSP, EditText mEditTextStock) {
        this.mTextView = mTextView;
        this.mEditTextRSP = mEditTextRSP;
        this.mEditTextWSP = mEditTextWSP;
        this.mEditTextStock = mEditTextStock;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public EditText getEditTextRSP() {
        return mEditTextRSP;
    }

    public EditText getEditTextWSP() {
        return mEditTextWSP;
    }

    public EditText getEditTextStock() {
        return mEditTextStock;
    }
}
