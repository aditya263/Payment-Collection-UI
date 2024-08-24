package com.aditya.paymentcollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.aditya.paymentcollection.Adapter.PaymentCollectionAdapter;
import com.aditya.paymentcollection.Model.PaymentCollection;

import java.util.ArrayList;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class DashboardActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 1;

    RecyclerView recyclerView;
    PaymentCollectionAdapter paymentCollectionAdapter;
    ArrayList<PaymentCollection> collectionsList=new ArrayList<>();
    Intent callIntent;
    String employeeName,PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView=findViewById(R.id.recyclerView);

        collectionsList.add( new PaymentCollection("Amazon","Aditya","1000","7894562102","25.616570","85.113617") );
        collectionsList.add( new PaymentCollection("TCS","Atul","9000","9638526987","26.861759","80.913902") );
        collectionsList.add( new PaymentCollection("Wipro","Munish Ji","6500","9636547598","28.494860" , "77.146384") );
        collectionsList.add( new PaymentCollection("Cognizant","Ranjit Ji","1700","7854963285","25.610620","85.118039") );

        paymentCollectionAdapter = new PaymentCollectionAdapter(getApplicationContext(),collectionsList);
        recyclerView.setAdapter( paymentCollectionAdapter );

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //swipeExample();

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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(DashboardActivity.this,R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.bg_map_new)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(DashboardActivity.this,R.color.green))
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
        final Dialog dialog = new Dialog(this);
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

                if (ContextCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
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

    /*private void swipeExample(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                final int fromPos = viewHolder.getAdapterPosition();
//                final int toPos = target.getAdapterPosition();
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                // Show delete confirmation if swipped left
                if (swipeDir == ItemTouchHelper.LEFT) {

                } else if (swipeDir == ItemTouchHelper.RIGHT) {
                    // Show edit dialog
                    Toast.makeText(DashboardActivity.this, "Right swipe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        //p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                       // c.drawRect(background, p);
                        //icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mode_edit_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        //c.drawBitmap(icon, null, icon_dest, p);
                    } else if (dX < 0) {
                        //p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        //c.drawRect(background, p);
                        //icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        //c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX / 4, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }*/

}