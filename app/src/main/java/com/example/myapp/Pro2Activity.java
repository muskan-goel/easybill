package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Pro2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro2);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("EasyBill");

        LinearLayout gallery=findViewById(R.id.gallery1);
        LayoutInflater inflater= LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.item,gallery,false);
        ImageView imageView=view.findViewById(R.id.image1);
        imageView.setImageResource(R.drawable.shop1);
        ImageView imageView1=view.findViewById(R.id.image2);
        imageView1.setImageResource(R.drawable.shop2);
        ImageView imageView2=view.findViewById(R.id.image3);
        imageView2.setImageResource(R.drawable.shop3);
        ImageView imageView3=view.findViewById(R.id.image4);
        imageView3.setImageResource(R.drawable.shop4);
        gallery.addView(view);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pro2Activity.this, ScanActivity.class));
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pro2Activity.this, ScanActivity.class));
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pro2Activity.this, ScanActivity.class));
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pro2Activity.this, ScanActivity.class));
            }
        });
    }
}