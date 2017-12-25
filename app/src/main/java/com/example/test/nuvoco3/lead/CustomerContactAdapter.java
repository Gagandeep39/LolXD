package com.example.test.nuvoco3.lead;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import java.util.List;

/**
 * Created by gagandeep on 24/12/17.
 */

public class CustomerContactAdapter extends RecyclerView.Adapter<CustomerContactAdapter.ViewHolder> {
    private static final String TAG = "CustomerContact Adapter Activity";
    private Context mContext;
    private List<CustomerContact> mCustomerContactList;

    public CustomerContactAdapter(Context mContext, List<CustomerContact> mCustomerContactList) {
        this.mContext = mContext;
        this.mCustomerContactList = mCustomerContactList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_contact_list, parent, false);
        return new ViewHolder(v);
    }

    //Holds the item in a Single View
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CustomerContact mCurrentContact = mCustomerContactList.get(position);
        holder.setIsRecyclable(false);
        holder.mTextViewCustomerId.append(mCurrentContact.getCustomerId());
        holder.mTextViewName.append(mCurrentContact.getCustomerContactName());
        holder.mTextViewPhone.append(mCurrentContact.getCustomerContactPhone());
        holder.mTextViewEmail.append(mCurrentContact.getCustomerContactEmail());
    }

    //  Provides count of Items in The View
    @Override
    public int getItemCount() {
        return mCustomerContactList.size();
    }

    //  Initialize Items in a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewName, mTextViewPhone, mTextViewEmail, mTextViewCustomerId;
        ConstraintLayout mConstraintLayout;

        //  Initialize Widgets in a single Item View
        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewCustomerId = itemView.findViewById(R.id.textViewContactId);
            mTextViewName = itemView.findViewById(R.id.textViewContactName);
            mTextViewPhone = itemView.findViewById(R.id.textViewContactPhone);
            mTextViewEmail = itemView.findViewById(R.id.textViewContactEmail);
            mConstraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
