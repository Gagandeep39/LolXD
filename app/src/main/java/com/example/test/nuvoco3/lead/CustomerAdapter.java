package com.example.test.nuvoco3.lead;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import java.util.List;

import static com.example.test.nuvoco3.lead.ViewCustomerActivity.mCustomerArrayList;
import static com.example.test.nuvoco3.signup.LoginActivity.TAG;

/**
 * Created by gagandeep on 23/12/17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    ViewHolder mViewHolder;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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
                mContext.startActivity(new Intent(mContext, CustomerDetailsActivity.class).putExtra("position", position));

            }
        });
        mViewHolder = holder;
        holder.mImageExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + "clicked");
//                TransitionManager.beginDelayedTransition(mViewHolder.mCardView);
                holder.mGridLayout.setVisibility(View.VISIBLE);
                holder.mImageExpand.setVisibility(View.INVISIBLE);
                holder.mImageExpandLess.setVisibility(View.VISIBLE);

            }
        });

        holder.mImageExpandLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mGridLayout.setVisibility(View.GONE);
                holder.mImageExpand.setVisibility(View.VISIBLE);
                holder.mImageExpandLess.setVisibility(View.INVISIBLE);

            }
        });
        holder.mTextViewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, CustomerDetailsActivity.class).putExtra("position", position));
            }
        });
        holder.mTextViewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ViewCustomerContactActivity.class).putExtra("customerID", mCustomerArrayList.get(position).getCustomerId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCustomer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCustomerName, mCustomerType, mCustomerArea, mCustomerCategory;
        TextView mTextViewCustomer, mTextViewContacts;
        CardView mCardView;
        ConstraintLayout mConstraintLayout;
        GridLayout mGridLayout;
        ImageView mImageExpand, mImageExpandLess;

        public ViewHolder(View itemView) {
            super(itemView);
            mCustomerName = itemView.findViewById(R.id.textViewCustomerName);
            mCustomerType = itemView.findViewById(R.id.textViewCustomerType);
            mCustomerCategory = itemView.findViewById(R.id.textViewCustomerCategory);
            mCustomerArea = itemView.findViewById(R.id.textViewCustomerArea);
            mCardView = itemView.findViewById(R.id.cardView);
            mConstraintLayout = itemView.findViewById(R.id.constraintLayout);
            mImageExpand = itemView.findViewById(R.id.imageViewExpand);
            mImageExpandLess = itemView.findViewById(R.id.imageViewExpandLess);
            mGridLayout = itemView.findViewById(R.id.gridLayoutExpanded);
            mTextViewCustomer = itemView.findViewById(R.id.textViewCustomer);
            mTextViewContacts = itemView.findViewById(R.id.textViewContact);
        }
    }


}
