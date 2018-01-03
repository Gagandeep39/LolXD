package com.example.test.nuvoco3.market;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.nuvoco3.R;

import java.util.List;

public class BrandPriceDetailsAdapter extends RecyclerView.Adapter<BrandPriceDetailsAdapter.ViewHolder> {
    private Context mContext;
    private List<BrandPrice> mPriceList;

    public BrandPriceDetailsAdapter(Context mContext, List<BrandPrice> mPriceList) {
        this.mContext = mContext;
        this.mPriceList = mPriceList;
    }


    @Override
    public BrandPriceDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_price_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BrandPriceDetailsAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        BrandPrice mCurrentPrice = mPriceList.get(position);
        holder.mTextViewBrand.append(mCurrentPrice.getBrand());
        holder.mTextViewProduct.append(mCurrentPrice.getProduct());
        holder.mTextViewRemark.append(mCurrentPrice.getRemarks());
        holder.mTextViewWSP.append(mCurrentPrice.getWSP());
        holder.mTextViewRSP.append(mCurrentPrice.getRSP());
        holder.mTextViewStock.append(mCurrentPrice.getStock());

    }

    @Override
    public int getItemCount() {
        return mPriceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewBrand, mTextViewProduct, mTextViewRemark, mTextViewWSP, mTextViewRSP, mTextViewStock;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewBrand = itemView.findViewById(R.id.textViewBrand);
            mTextViewProduct = itemView.findViewById(R.id.textViewProduct);
            mTextViewStock = itemView.findViewById(R.id.textViewStock);
            mTextViewWSP = itemView.findViewById(R.id.textViewWSP);
            mTextViewRemark = itemView.findViewById(R.id.textViewRemark);
            mTextViewRSP = itemView.findViewById(R.id.textViewRSP);
        }
    }
}
