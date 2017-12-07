package com.example.shubhangi.gpsone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SampleSubcat extends AppCompatActivity {
    RecyclerView rv;
    List<SubCategory> ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_subcat);
        rv=findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ls=new ArrayList<>();
        ls.add(new SubCategory("Cooks",R.drawable.cooks));
        ls.add(new SubCategory("AC Service and Repair",R.drawable.ac_service_and_repair));
        SubCategoryAdapter subadapter=new SubCategoryAdapter(ls);
        rv.setAdapter(subadapter);
    }
}
