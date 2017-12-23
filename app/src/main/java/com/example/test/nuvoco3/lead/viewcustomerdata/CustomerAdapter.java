package com.example.test.nuvoco3.lead.viewcustomerdata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.lead.viewcustomerdata.cusomerlistrecyclerview.Customer;

import java.util.List;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Customer currentCustomer = mCustomer.get(position);
        holder.setIsRecyclable(false);
        holder.mCustomerName.append(currentCustomer.getCustomerName());
        holder.mCustomerArea.append(currentCustomer.getCustomerArea());
        holder.mCustomerType.append(currentCustomer.getCustomerType());
        holder.mCustomerCategory.append(currentCustomer.getCustomerCategory());

    }

    @Override
    public int getItemCount() {
        return mCustomer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCustomerName, mCustomerType, mCustomerArea, mCustomerCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            mCustomerName = itemView.findViewById(R.id.textViewCustomerName);
            mCustomerType = itemView.findViewById(R.id.textViewCustomerType);
            mCustomerCategory = itemView.findViewById(R.id.textViewCustomerCategory);
            mCustomerArea = itemView.findViewById(R.id.textViewCustomerArea);
        }
    }
}
