package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Pay extends AppCompatActivity {
    TextView amt;
    Button chkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("EasyBill");
        amt = (TextView) findViewById(R.id.amt);
        amt.setText(getIntent().getStringExtra("Amount"));

        chkout = (Button) findViewById(R.id.chkout);
        chkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment();
            }
        });



    }
    public void payment(){
        Intent intent = new Intent(this, checkout.class);
        startActivity(intent);

    }

}