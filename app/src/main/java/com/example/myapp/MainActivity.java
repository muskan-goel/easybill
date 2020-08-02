package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText Username, Password;
    private Button Login;
    private TextView ForgotPass, SignUp, Info;
    private CheckBox ShowPass;
    private int counter=5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static Animation shakeAnimation;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("EasyBill");

        if(!isNetworkOn(this)) {
            Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show();
        }else {
            Username = findViewById(R.id.etname);
            Password = findViewById(R.id.etpass);
            Login = findViewById(R.id.btlog);
            ForgotPass = findViewById(R.id.etforpass);
            SignUp = findViewById(R.id.etsignup);
            Info = findViewById(R.id.etinfo);
            ShowPass =  findViewById(R.id.etshowpass);
            shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);

            Info.setText("No. of attempts remaining: 5");

            firebaseAuth = FirebaseAuth.getInstance();
            progressDialog = new ProgressDialog(this);
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

            ShowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                }
            });

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (loginTask())
                        validate(Username.getText().toString(), Password.getText().toString());

                }
            });


            SignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, RegisterationActivity.class));
                }
            });

            ForgotPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, PasswordActivity.class));
                }
            });
        }
    }

    private void validate(String name,String pass){

        progressDialog.setMessage("Signing in");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(name, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                   Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                }else{
                    Login.startAnimation(shakeAnimation);
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No. of attempts remaining: "+ counter);
                    progressDialog.dismiss();
                    if(counter==0){
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }
    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private Boolean loginTask(){
        Boolean result= false;
        String p=Password.getText().toString();
        String m= Username.getText().toString();
       // Pattern pat = Pattern.compile(Utils.regEx);
      //  Matcher em = pat.matcher(m);
        if ((p.isEmpty()) || (m.isEmpty())) {
            Toast.makeText(MainActivity.this, "Enter both the credentials", Toast.LENGTH_SHORT).show();
        } else {
            if (p.length() == 1 || p.length() == 2 || p.length() == 3 || p.length() == 4) {
                Password.setError("should have length atleast 5");
            } else if(!validEmail(m)){
                Toast.makeText(MainActivity.this, "Your Email-Id is Invalid.", Toast.LENGTH_SHORT).show();
            } else{
                result=true;
            }
        }
        return result;

    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailflag = firebaseUser.isEmailVerified();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
//
//        if(emailflag){
//            finish();
//            startActivity(new Intent(MainActivity.this,LoginActivity.class));
//        }else{
//            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT).show();
//            firebaseAuth.signOut();
//        }
    }

    public boolean isNetworkOn(@NonNull Context context) { ConnectivityManager connMgr =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}
