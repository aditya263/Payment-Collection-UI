package com.aditya.paymentcollection.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aditya.paymentcollection.Adapter.PaymentHistoryAdapter;
import com.aditya.paymentcollection.Model.PaymentCollectHistory;
import com.aditya.paymentcollection.R;

import java.util.ArrayList;

public class CollectionFragment extends Fragment {

    RecyclerView recyclerView;
    PaymentHistoryAdapter paymentHistoryAdapter;
    ArrayList<PaymentCollectHistory> collectionsList=new ArrayList<>();
    EditText searchField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_collection, container, false);

        recyclerView=v.findViewById(R.id.recyclerView);
        searchField=v.findViewById(R.id.searchField);

        collectionsList.add( new PaymentCollectHistory("Amazon","Yesterday","1000") );
        collectionsList.add( new PaymentCollectHistory("TCS","26/10/2020","9000") );
        collectionsList.add( new PaymentCollectHistory("Wipro","21/10/2020","6500") );
        collectionsList.add( new PaymentCollectHistory("Cognizant","20/10/2020","1700") );

        paymentHistoryAdapter = new PaymentHistoryAdapter(getContext(),collectionsList);
        recyclerView.setAdapter( paymentHistoryAdapter );

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });

        return v;
    }

    void filter(String text){
        ArrayList<PaymentCollectHistory> temp = new ArrayList();
        for(PaymentCollectHistory d: collectionsList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getCompanyName().toLowerCase().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        paymentHistoryAdapter.updateList(temp);
    }
}