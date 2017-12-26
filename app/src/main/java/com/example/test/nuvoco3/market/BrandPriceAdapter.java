package com.example.test.nuvoco3.market;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import java.util.List;

/**
 * Created by gagandeep on 26/12/17.
 */

public class BrandPriceAdapter extends RecyclerView.Adapter<BrandPriceAdapter.ViewHolder> {
    private Context mContext;
    private List<BrandPrice> mBrandPrice;

    public BrandPriceAdapter(Context mContext, List<BrandPrice> mBrandPrice) {
        this.mContext = mContext;
        this.mBrandPrice = mBrandPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_price_info_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BrandPrice mCurrentBrandPrice = mBrandPrice.get(position);
        holder.setIsRecyclable(false);
        holder.mBrand.append(mCurrentBrandPrice.getCustomer());
        holder.mCounter.append(mCurrentBrandPrice.getCounter());
        holder.mStock.append(mCurrentBrandPrice.getStock());
        holder.mProduct.append(mCurrentBrandPrice.getProduct());
    }

    @Override
    public int getItemCount() {
        return mBrandPrice.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCounter, mBrand, mProduct, mStock;

        public ViewHolder(View itemView) {
            super(itemView);
            mCounter = itemView.findViewById(R.id.textViewCounter);
            mBrand = itemView.findViewById(R.id.textViewBrand);
            mStock = itemView.findViewById(R.id.textViewStock);
            mProduct = itemView.findViewById(R.id.textViewProduct);
        }
    }
}
