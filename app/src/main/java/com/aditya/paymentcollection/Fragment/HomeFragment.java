package com.aditya.paymentcollection.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.aditya.paymentcollection.Adapter.PaymentCollectionAdapter;
import com.aditya.paymentcollection.Model.PaymentCollection;
import com.aditya.paymentcollection.R;

import java.util.ArrayList;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class HomeFragment extends Fragment {

    private static final int REQUEST_PHONE_CALL = 1;

    RecyclerView recyclerView;
    PaymentCollectionAdapter paymentCollectionAdapter;
    ArrayList<PaymentCollection> collectionsList=new ArrayList<>();
    Intent callIntent;
    String employeeName,PhoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView=v.findViewById(R.id.recyclerView);

        collectionsList.add( new PaymentCollection("Amazon","Aditya","1000","7894562102","25.616570","85.113617") );
        collectionsList.add( new PaymentCollection("TCS","Atul","9000","9638526987","26.861759","80.913902") );
        collectionsList.add( new PaymentCollection("Wipro","Munish Ji","6500","9636547598","28.494860" , "77.146384") );
        collectionsList.add( new PaymentCollection("Cognizant","Ranjit Ji","1700","7854963285","25.610620","85.118039") );

        paymentCollectionAdapter = new PaymentCollectionAdapter(getContext(),collectionsList);
        recyclerView.setAdapter( paymentCollectionAdapter );

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return v;
    }

    ItemTouchHelper.Callback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position= viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.LEFT:
                    final PaymentCollection data = collectionsList.get( position );
                    Float latitude= Float.valueOf(data.getLatitude());
                    Float longitude= Float.valueOf(data.getLongitude());

                    //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f", latitude, longitude);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri));
                    startActivity(intent);
                    paymentCollectionAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    break;

                case ItemTouchHelper.RIGHT:
                    final PaymentCollection dataCall = collectionsList.get( position );

                    employeeName=dataCall.getTxtEmployeeName();
                    PhoneNumber=dataCall.getPhoneNum();

                    callAlertBox();
                    paymentCollectionAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.bg_map_new)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.green))
                    .addSwipeRightActionIcon(R.drawable.bg_call_new)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX / 4, dY, actionState, isCurrentlyActive);

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                }
                else
                {

                }
                return;
            }
        }
    }

    public void callAlertBox() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox_confirm_call);

        dialog.setCancelable( false );

        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);
        TextView txtName=dialog.findViewById(R.id.txtName);
        TextView txtNumber=dialog.findViewById(R.id.txtNumber);
        txtName.setText(employeeName);
        txtNumber.setText(PhoneNumber);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+PhoneNumber));

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else {
                    startActivity(callIntent);
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}