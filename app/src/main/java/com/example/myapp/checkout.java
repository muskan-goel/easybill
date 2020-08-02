package com.example.myapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class checkout extends AppCompatActivity {

    CardView gpay;
    CardView phone;
    CardView paytm;
    CardView icici;
    CardView hdfc;
    CardView bhim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("EasyBill");
        gpay=findViewById(R.id.P_GPay);
        paytm=findViewById(R.id.P_paytm);
        phone=findViewById(R.id.P_phone);
        icici=findViewById(R.id.P_icici);
        hdfc=findViewById(R.id.P_hdfc);
        bhim=findViewById(R.id.P_bhim);
        gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mychoice=new AlertDialog.Builder(checkout.this);
                mychoice.setTitle("SUCCESS!");
                mychoice.setMessage("Amount Paid.");
                mychoice.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(checkout.this,"Successful Payment", Toast.LENGTH_SHORT).show();
                    }
                });
                mychoice.show();
            }


        });
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mychoice=new AlertDialog.Builder(checkout.this);
                mychoice.setTitle("SUCCESS!");
                mychoice.setMessage("Amount Paid.");
                mychoice.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(checkout.this,"Successful Payment", Toast.LENGTH_SHORT).show();
                    }
                });
                mychoice.show();
            }


        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mychoice=new AlertDialog.Builder(checkout.this);
                mychoice.setTitle("SUCCESS!");
                mychoice.setMessage("Amount Paid.");
                mychoice.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(checkout.this,"Successful Payment", Toast.LENGTH_SHORT).show();
                    }
                });
                mychoice.show();
            }


        });
        icici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mychoice=new AlertDialog.Builder(checkout.this);
                mychoice.setTitle("SUCCESS!");
                mychoice.setMessage("Amount Paid.");
                mychoice.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(checkout.this,"Successful Payment", Toast.LENGTH_SHORT).show();
                    }
                });
                mychoice.show();
            }


        });
        hdfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mychoice=new AlertDialog.Builder(checkout.this);
                mychoice.setTitle("SUCCESS!");
                mychoice.setMessage("Amount Paid.");
                mychoice.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(checkout.this,"Successful Payment", Toast.LENGTH_SHORT).show();
                    }
                });
                mychoice.show();
            }
        });
        bhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mychoice=new AlertDialog.Builder(checkout.this);
                mychoice.setTitle("SUCCESS!");
                mychoice.setMessage("Amount Paid.");
                mychoice.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(checkout.this,"Successful Payment", Toast.LENGTH_SHORT).show();
                    }
                });
                mychoice.show();
            }


        });



    }
}