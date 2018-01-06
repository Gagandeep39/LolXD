package com.example.test.nuvoco3.visits;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        JCPDetails mCurrentJCP = mDetailsList.get(position);
        holder.mTextViewJcpId.append(mCurrentJCP.getJcpId());
        holder.mTextViewName.append(mCurrentJCP.getCustomerName());
        holder.mTextViewObjective.append(mCurrentJCP.getObjective());
        holder.mViewDecorator.setBackgroundColor(selectColor(mCurrentJCP.getVisitStatus()));

    }

    private int selectColor(String visitStatus) {
        int color = Color.GREEN;
        switch (visitStatus) {
            case VISIT_STATUS_PLANNED:
                return Color.BLUE;
            case VISIT_STATUS_PENDING:
                return Color.RED;
            case VISIT_STATUS_COMPLETED:
                return Color.GREEN;
            default:
                return
                        color;

        }
    }

    @Override
    public int getItemCount() {
        return mDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewJcpId, mTextViewName, mTextViewObjective;
        View mViewDecorator;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewJcpId = itemView.findViewById(R.id.textViewJcpId);
            mTextViewName = itemView.findViewById(R.id.textViewName);
            mTextViewObjective = itemView.findViewById(R.id.textViewObjective);
            mViewDecorator = itemView.findViewById(R.id.viewDecorate);
        }
    }
}
