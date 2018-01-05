package com.example.test.nuvoco3.visits;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import java.util.List;

public class JCPAdapter extends RecyclerView.Adapter<JCPAdapter.ViewHolder> {
    private Context mContext;
    private List<JCP> mJcpList;

    public JCPAdapter(Context mContext, List<JCP> mJcpList) {
        this.mContext = mContext;
        this.mJcpList = mJcpList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_jcp_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JCP mCurrentJCP = mJcpList.get(position);
        final String mCustomerName = mCurrentJCP.getCustomerName();
        final String mStartTime = mCurrentJCP.getCustomerName();
        final String mEndTime = mCurrentJCP.getEndTime();
        final String mObjective = mCurrentJCP.getObjective();
        final String mJcpId = mCurrentJCP.getRecordId();
        holder.mTextViewName.setText(mCustomerName);
        holder.mTextViewStartTime.setText(mStartTime);
        holder.mTextViewEndTime.setText(mEndTime);
        holder.mTextViewObjective.setText(mObjective);
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, VisitDetailsActivity.class).putExtra("CustomerName", mCustomerName).putExtra("StartTime", mStartTime).putExtra("EndTime", mEndTime).putExtra("Objective", mObjective).putExtra("JcpId", mJcpId));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mJcpList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewName, mTextViewStartTime, mTextViewEndTime, mTextViewObjective;
        LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.textViewCustomer);
            mTextViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            mTextViewEndTime = itemView.findViewById(R.id.textViewEndTime);
            mTextViewObjective = itemView.findViewById(R.id.textViewObjective);
            mLinearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
