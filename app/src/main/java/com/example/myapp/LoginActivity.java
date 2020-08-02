package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActionBarDrawerToggle abdt;
    private FirebaseAuth firebaseAuth;
    //private SearchView search;
    private ImageView pro1,pro2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DrawerLayout dl =findViewById(R.id.dl);
        abdt= new ActionBarDrawerToggle(this, dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        LinearLayout gallery=findViewById(R.id.gallery1);
        LayoutInflater inflater= LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.item,gallery,false);
        //TextView textView=findViewById(R.id.withtext);
        //TextView textView1=findViewById(R.id.withtext1);
        pro1=findViewById(R.id.mag);
        pro2=findViewById(R.id.choc);
        ImageView imageView=view.findViewById(R.id.image1);
        imageView.setImageResource(R.drawable.shop1);
        ImageView imageView1=view.findViewById(R.id.image2);
        imageView1.setImageResource(R.drawable.shop2);
        ImageView imageView2=view.findViewById(R.id.image3);
        imageView2.setImageResource(R.drawable.shop3);
        ImageView imageView3=view.findViewById(R.id.image4);
        imageView3.setImageResource(R.drawable.shop4);
        ImageView imageView4=view.findViewById(R.id.image5);
        imageView3.setImageResource(R.drawable.shop5);
        gallery.addView(view);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ScanActivity.class));
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ScanActivity.class));
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ScanActivity.class));
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ScanActivity.class));
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ScanActivity.class));
            }
        });

        pro1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Pro1Activity.class));
            }
        });

        pro2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Pro2Activity.class));
            }
        });

        SearchView searchView=findViewById(R.id.searchview);
        NavigationView nav_view =findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();
                if(id==R.id.profilemenu){
                    Toast.makeText(LoginActivity.this, "My Profile",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }
                if(id==R.id.homemenu){
                    Toast.makeText(LoginActivity.this, "Home",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                }
                if(id==R.id.editprofilemenu){
                    Toast.makeText(LoginActivity.this, "Edit Profile",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }
                if(id==R.id.settingsmenu){
                    Toast.makeText(LoginActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, SettingsActivity.class));
                }

                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("EasyBill");

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String fullname= prefs.getString("full_name","");
        String email=prefs.getString("email_address","");

        Toast.makeText(this,fullname,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,email,Toast.LENGTH_SHORT).show();


        firebaseAuth = FirebaseAuth.getInstance();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logoutmenu: {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                return true;
            }
        }
            return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
