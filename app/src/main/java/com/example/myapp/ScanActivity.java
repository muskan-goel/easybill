package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ScanActivity extends AppCompatActivity implements PaymentResultListener {

    Button scanBtn;
    TextView a;
    TextView b;
    DatabaseReference ref;
    String TAG="PAYMENT ERROR";
    Button additem;
    Button pay;
    int amt=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Checkout.preload(getApplicationContext());
        final Activity activity = this;

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("EasyBill");

        a = (TextView) findViewById(R.id.name);
        b = (TextView) findViewById(R.id.cost);


        scanBtn = (Button) findViewById(R.id.scanBtn);
        additem = (Button) findViewById(R.id.additem);
        pay = (Button) findViewById(R.id.pay);

        scanBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);

                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null) {
                Toast.makeText(this, "Scanning Cancelled", Toast.LENGTH_LONG).show();
            }
            else{

                Toast.makeText(this,result.getContents(), Toast.LENGTH_LONG).show();

                ref = FirebaseDatabase.getInstance().getReference().child("Product").child(result.getContents().toString());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name=dataSnapshot.child("Name").getValue().toString();
                        final String cost ;
                        cost = dataSnapshot.child("Cost").getValue().toString();
                        final int cs;
                        cs = Integer.parseInt(cost);
                        additem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //int amt =0;
                                amt = amt+ cs;
                                Intent intent = new Intent(ScanActivity.this, Pay.class);
                                intent.putExtra("Amount",amt);

                            }
                        });

                        a.setText(name);
                        b.setText(cost);

                        pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               startPayment();
                              //  openPay();

                            }
                        });
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
    public String amt(){
        Intent intent = new Intent(this, Pay.class);

        return null;
    }

    public void openPay() {
        Intent intent = new Intent(this, Pay.class);
        startActivity(intent);
    }
    public void startPayment() {

        Checkout checkout = new Checkout();
       // checkout.setKeyID("<YOUR_KEY_ID>");

        checkout.setImage(R.drawable.rzp);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            String paym=b.getText().toString();
            double total=Double.parseDouble(paym);
            total=total*100;

            options.put("name", "EasyBill");
            options.put("description", "Order #123456");

            options.put("currency", "INR");

            options.put("amount", total);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(ScanActivity.this,"Payment Successfull", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(ScanActivity.this,"Payment Denied", Toast.LENGTH_SHORT).show();
    }
}