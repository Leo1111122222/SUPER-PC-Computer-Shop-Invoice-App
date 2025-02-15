package com.example.myapplication21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class InfoActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        MaterialButton sales = findViewById(R.id.sales);
        MaterialButton purchases = findViewById(R.id.purchases);
        MaterialButton mosales = findViewById(R.id.mosales);

        MaterialButton mostsuppliers = findViewById(R.id.mostSuppliers);
        MaterialButton customers = findViewById(R.id.customers2);


        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InfoActivity.this, allSales.class));
            }
        });

        purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InfoActivity.this, allPurchases.class));
            }
        });

        mosales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InfoActivity.this, moreSales.class));
            }
        });

        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InfoActivity.this, moreCostomer.class));
            }
        });
        mostsuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoActivity.this,MostSuppliers.class));
            }
        });

        Button btn = findViewById(R.id.backbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, HomeActivity.class));
            }
        });


    }
}

