package com.example.test.nuvoco3.visits;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import java.util.List;

import static com.example.test.nuvoco3.helpers.Contract.VISIT_STATUS_COMPLETED;
import static com.example.test.nuvoco3.helpers.Contract.VISIT_STATUS_PENDING;
import static com.example.test.nuvoco3.helpers.Contract.VISIT_STATUS_PLANNED;

public class JCPDetailsAdapter extends RecyclerView.Adapter<JCPDetailsAdapter.ViewHolder> {
    private List<JCPDetails> mDetailsList;
    private Context mContext;

    public JCPDetailsAdapter(List<JCPDetails> mDetailsList, Context mContext) {
        this.mDetailsList = mDetailsList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_overview_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final JCPDetails mCurrentJCP = mDetailsList.get(position);
        holder.mTextViewJcpId.append(mCurrentJCP.getJcpId());
        Log.i("dc", "onBindViewHolder: " + mCurrentJCP.getOrder());
        holder.mTextViewName.append(mCurrentJCP.getCustomerName());
        holder.mTextViewObjective.append(mCurrentJCP.getObjective());
        holder.mTextViewVisitStatus.append(mCurrentJCP.getVisitStatus());
        holder.mViewDecorator.setBackgroundColor(mContext.getResources().getColor(selectColor(mCurrentJCP.getVisitStatus())));
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //RecordID  Customer ID  Customer Name   Date  Start TIME   END TIME Objective
                mContext.startActivity(new Intent(mContext, ViewVisitDetailsActivity.class)
                        .putExtra("JcpId", mCurrentJCP.getJcpId())
                        .putExtra("VisitRemark", mCurrentJCP.getVisitRemark())
                        .putExtra("VisitStatus", mCurrentJCP.getVisitStatus())
                        .putExtra("OrderTaken", mCurrentJCP.getOrder())
                        .putExtra("OrderQuantity", mCurrentJCP.getOrderQuantity())
                        .putExtra("OrderedProduct", mCurrentJCP.getProductName())
                        .putExtra("RecordId", mCurrentJCP.getRecordId()));
                ((CalendarActivity)mContext).finish();
            }
        });

    }

    private int selectColor(String visitStatus) {
        int color = Color.GREEN;
        switch (visitStatus) {
            case VISIT_STATUS_PLANNED:
                return R.color.indicator_blue;
            case VISIT_STATUS_PENDING:
                return R.color.indicator_yellow;
            case VISIT_STATUS_COMPLETED:
                return R.color.indicator_green;
            default:
                return R.color.indicator_purple;

        }
    }

    @Override
    public int getItemCount() {
        return mDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewJcpId, mTextViewName, mTextViewObjective, mTextViewVisitStatus;
        View mViewDecorator;
        LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewJcpId = itemView.findViewById(R.id.textViewJcpId);
            mTextViewName = itemView.findViewById(R.id.textViewName);
            mTextViewObjective = itemView.findViewById(R.id.textViewObjective);
            mViewDecorator = itemView.findViewById(R.id.viewDecorate);
            mTextViewVisitStatus = itemView.findViewById(R.id.textViewVisitStatus);
            mLinearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
