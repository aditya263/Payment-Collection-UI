package com.aditya.paymentcollection.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.paymentcollection.Model.PaymentCollectHistory;
import com.aditya.paymentcollection.R;

import java.util.ArrayList;
import java.util.Random;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder>
{
    private ArrayList<PaymentCollectHistory> collectionList;
    private Context context;



    public PaymentHistoryAdapter(Context context, ArrayList<PaymentCollectHistory> collectionList) {
        this.collectionList = collectionList;
        this.context = context;
    }


    @NonNull
    @Override
    public PaymentHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.list_fragment_collection, viewGroup, false);
        return new PaymentHistoryAdapter.ViewHolder(view);
    }

    public void updateList(ArrayList<PaymentCollectHistory> list){
        collectionList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.position=i;
        final PaymentCollectHistory data = collectionList.get( i );
        String companyName=data.getCompanyName();

        String firstLetter = String.valueOf(companyName.charAt(0));

        String amount=data.getAmount();
        String date=data.getDate();

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        viewHolder.txtCompanyName.setText(companyName);
        viewHolder.img_icon.setBackground(context.getResources().getDrawable(R.drawable.img));
        viewHolder.img_icon.setBackgroundColor(randomAndroidColor);
        viewHolder.imgtext.setText(firstLetter);
        viewHolder.date.setText(date);
        viewHolder.txtAmount.setText(amount);


    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout img_icon;

        TextView txtCompanyName,date,txtAmount,imgtext;
        private int position=0;
        RelativeLayout rlItem;

        private ViewHolder(View view) {
            super(view);

            txtAmount=view.findViewById(R.id.txtAmount);
            txtCompanyName=view.findViewById(R.id.txtCompanyName);
            date=view.findViewById(R.id.date);
            img_icon=view.findViewById(R.id.img_icon);
            imgtext=view.findViewById(R.id.imgtext);
            rlItem=view.findViewById(R.id.rlItem);

        }
    }

}


