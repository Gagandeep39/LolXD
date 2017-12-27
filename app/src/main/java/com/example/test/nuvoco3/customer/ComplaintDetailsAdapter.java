package com.example.test.nuvoco3.customer;

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
 * Created by gagandeep on 27/12/17.
 */

public class ComplaintDetailsAdapter extends RecyclerView.Adapter<ComplaintDetailsAdapter.ViewHolder> {
    private Context mContext;
    private List<ComplaintDetails> mComplaintList;

    public ComplaintDetailsAdapter(Context mContext, List<ComplaintDetails> mComplaintList) {
        this.mContext = mContext;
        this.mComplaintList = mComplaintList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ComplaintDetails mCurrentComplaint = mComplaintList.get(position);

        holder.mTextViewStatus.append(mCurrentComplaint.getComplaintStatus());
        holder.mTextViewDate.append(mCurrentComplaint.getDate());
        holder.mTextViewComplaintId.append(mCurrentComplaint.getComplaintId());
        holder.mTextViewCustomerName.append(mCurrentComplaint.getCustomerName());
        holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mComplaintList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewDate, mTextViewComplaintId, mTextViewCustomerName, mTextViewStatus;
        ConstraintLayout mConstraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewCustomerName = itemView.findViewById(R.id.textViewCustomerName);
            mTextViewComplaintId = itemView.findViewById(R.id.textViewComplaintId);
            mTextViewDate = itemView.findViewById(R.id.textViewDate);
            mTextViewStatus = itemView.findViewById(R.id.textViewStatus);
            mConstraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
