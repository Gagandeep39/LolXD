package com.example.test.nuvoco3.viewcustomerdata.cusomerlistrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import java.util.List;

import static com.example.test.nuvoco3.InsertCustomerActivity.TAG;

/**
 * Created by Administrator on 15-12-2017.
 */

public class DisplayCustomerAdapter extends RecyclerView.Adapter<DisplayCustomerAdapter.ViewHolder> {

    private Context context;
    private List<Customer> list;

    public DisplayCustomerAdapter(Context context, List<Customer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Customer currentCustomer = list.get(position);


        holder.textViewName.append(currentCustomer.getCustomerName());
        holder.textViewType.append(currentCustomer.getCustomerType());
        holder.textViewCategory.append(currentCustomer.getCustomerCategory());
        holder.textViewAddress.append(currentCustomer.getCustomerAddress());
        holder.textViewArea.append(currentCustomer.getCustomerArea());
        holder.textViewDistrict.append(currentCustomer.getCustomerDistrict());
        holder.textViewState.append(currentCustomer.getCustomerState());
        holder.textViewPhone.append(currentCustomer.getCustomerPhoneno());
        holder.textViewEmailId.append(currentCustomer.getCustomerEmailId());
        holder.textViewStatus.append(currentCustomer.getCustomerStatus());
        holder.textViewCreatedBy.append(currentCustomer.getCustomerCreatedBy());
        holder.textViewCreatedOn.append(currentCustomer.getCustomerCreatedOn());
        holder.textViewUpdatedBy.append(currentCustomer.getCustomerUpdatedBy());
        holder.textViewUpdatedOn.append(currentCustomer.getCustomerUpdatedOn());
        Log.i(TAG, "onBindViewHolder: LOLOLOL");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName, textViewType, textViewCategory, textViewAddress, textViewArea, textViewDistrict, textViewState, textViewPhone, textViewEmailId, textViewStatus, textViewCreatedOn, textViewCreatedBy, textViewUpdatedOn, textViewUpdatedBy;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewArea = itemView.findViewById(R.id.textViewArea);
            textViewDistrict = itemView.findViewById(R.id.textViewDistrict);
            textViewState = itemView.findViewById(R.id.textViewState);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewEmailId = itemView.findViewById(R.id.textViewEmailId);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewCreatedBy = itemView.findViewById(R.id.textViewCreatedBy);
            textViewCreatedOn = itemView.findViewById(R.id.textViewCreatedOn);
            textViewUpdatedBy = itemView.findViewById(R.id.textViewUpdatedBy);
            textViewUpdatedOn = itemView.findViewById(R.id.textViewUpdatedOn);
        }
    }
}
