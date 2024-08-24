package com.aditya.paymentcollection.Adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.paymentcollection.Model.PaymentCollection;
import com.aditya.paymentcollection.R;
import com.aditya.paymentcollection.UserDetailActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PaymentCollectionAdapter extends RecyclerView.Adapter<PaymentCollectionAdapter.ViewHolder>
{
    private ArrayList<PaymentCollection> collectionList;
    private Context context;



    public PaymentCollectionAdapter(Context context, ArrayList<PaymentCollection> collectionList) {
        this.collectionList = collectionList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.list_payment_collection, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.position=i;
        final PaymentCollection data = collectionList.get( i );
        String companyName=data.getTxtCompanyName();
        String employeeName=data.getTxtEmployeeName();

        String firstLetter = String.valueOf(employeeName.charAt(0));

        String amount=data.getTxtAmount();
        String phoneNum=data.getPhoneNum();
        double latitude= Double.parseDouble(data.getLatitude());
        double longitude= Double.parseDouble(data.getLongitude());

        String fullAddress = null;

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            fullAddress=address+city+state+country+postalCode;

        } catch (IOException e) {
            e.printStackTrace();
        }



        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        Log.d("YourResponse","companyName"+companyName);
        Log.d("YourResponse","employeeName"+employeeName);

        viewHolder.txtCompanyName.setText(companyName);
        viewHolder.img_icon.setBackgroundColor(randomAndroidColor);
        //viewHolder.img_icon.setBackground(context.getResources().getDrawable(R.drawable.img));
        viewHolder.imgtext.setText(firstLetter);

        viewHolder.txtEmployeeName.setText(employeeName);
        viewHolder.txtAmount.setText(amount);

        String finalFullAddress = fullAddress;
        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, UserDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("employeeName", employeeName);
                intent.putExtra("amount", amount);
                intent.putExtra("phoneNum", phoneNum);
                intent.putExtra("fullAddress", finalFullAddress);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout img_icon;
        TextView txtCompanyName,txtEmployeeName,txtAmount,imgtext;
        private int position=0;
        RelativeLayout rlItem;

        private ViewHolder(View view) {
            super(view);

            txtAmount=view.findViewById(R.id.txtAmount);
            txtCompanyName=view.findViewById(R.id.txtCompanyName);
            txtEmployeeName=view.findViewById(R.id.txtEmployeeName);
            img_icon=view.findViewById(R.id.img_icon);
            imgtext=view.findViewById(R.id.imgtext);
            rlItem=view.findViewById(R.id.rlItem);

        }
    }



}

