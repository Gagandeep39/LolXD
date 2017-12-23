package com.example.test.nuvoco3.lead.viewcustomerdata;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.lead.viewcustomerdata.cusomerlistrecyclerview.Customer;

import java.util.List;

import static com.example.test.nuvoco3.lead.viewcustomerdata.ViewCustomerActivity.mCustomerArrayList;
import static com.example.test.nuvoco3.signup.LoginActivity.TAG;

/**
 * Created by gagandeep on 23/12/17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private Context mContext;
    private List<Customer> mCustomer;

    public CustomerAdapter(Context mContext, List<Customer> mCustomer) {
        this.mContext = mContext;
        this.mCustomer = mCustomer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Customer currentCustomer = mCustomer.get(position);
        int id = Integer.parseInt(currentCustomer.getCustomerId());
        holder.setIsRecyclable(false);
        holder.mCustomerName.append(currentCustomer.getCustomerName());
        holder.mCustomerArea.append(currentCustomer.getCustomerArea());
        holder.mCustomerType.append(currentCustomer.getCustomerId());
        holder.mCustomerCategory.append(currentCustomer.getCustomerCategory());
        holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + position);
                Log.i(TAG, "onClick: " +
                        mCustomerArrayList.get(position));
                mContext.startActivity(new Intent(mContext, CustomerDetailsActivity.class).putExtra("position", position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCustomer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCustomerName, mCustomerType, mCustomerArea, mCustomerCategory;
        CardView mCardView;
        ConstraintLayout mConstraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mCustomerName = itemView.findViewById(R.id.textViewCustomerName);
            mCustomerType = itemView.findViewById(R.id.textViewCustomerType);
            mCustomerCategory = itemView.findViewById(R.id.textViewCustomerCategory);
            mCustomerArea = itemView.findViewById(R.id.textViewCustomerArea);
            mCardView = itemView.findViewById(R.id.cardView);
            mConstraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
