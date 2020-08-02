package com.example.myapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterationActivity extends AppCompatActivity {
    private EditText name, Pass, confirm_pass, phone, email;
    private Button register;
    private TextView log;
    private CheckBox terms;
    private FirebaseAuth firebaseAuth;
    private  DataSnapshot dataSnapshot;
    String n,p,c,ph,em;
    ImageView userprofilepic;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userprofilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        setupUIViews();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("EasyBill");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        userprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String user_email = email.getText().toString().trim();
                    String user_pass = Pass.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                               //sendEmailVerification();
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(RegisterationActivity.this,"Successfully Registered! Upload complete.",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegisterationActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(RegisterationActivity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterationActivity.this, MainActivity.class));
            }
        });
    }


        private void setupUIViews(){
            name=findViewById(R.id.etfn);
            Pass=findViewById(R.id.etpass);
            confirm_pass=findViewById(R.id.etconfirmpass);
            phone=findViewById(R.id.etphoneno);
            email=findViewById(R.id.etemail);
           // loc=findViewById(R.id.etloc);
            register=findViewById(R.id.etaccount);
            log=findViewById(R.id.etmember);
            //signup=(TextView)findViewById(R.id.etsign);
            terms=findViewById(R.id.etterm);
            userprofilepic = findViewById(R.id.etprofile);

        }
    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

        private Boolean validate(){
            boolean result=false;
            n= name.getText().toString();
            p= Pass.getText().toString();
            c= confirm_pass.getText().toString();
            ph= phone.getText().toString();
            em= email.getText().toString();
           // l= loc.getText().toString();

            if(n.isEmpty() || p.isEmpty() || c.isEmpty() || ph.isEmpty() || em.isEmpty()){
                Toast.makeText(this,"Please enter all the details.",Toast.LENGTH_SHORT).show();
            }
            else if(!validEmail(em)){
                Toast.makeText(this, "Your Email-Id is Invalid.", Toast.LENGTH_SHORT).show();
            }

            else if(!p.equals(c)){
                Toast.makeText(this,"Both password doesn't match.",Toast.LENGTH_SHORT).show();
            }
            else if(!terms.isChecked()){
                Toast.makeText(this,"Please select Terms and Conditions.",Toast.LENGTH_SHORT).show();
            }
            else{
                result=true;
            }
            return result;


        }

        private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        firebaseAuth.signOut();
                        Toast.makeText(RegisterationActivity.this,"Successfully Registered! Email Verification Sent..",Toast.LENGTH_SHORT).show();
                        //firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegisterationActivity.this,ScanActivity.class));
                     }else{
                        Toast.makeText(RegisterationActivity.this,"Verification Email not sent..",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        }
        private void sendUserData() {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name=dataSnapshot.child("Name").getValue().toString();
                        String email1 = dataSnapshot.child("Email").getValue().toString();
                        UserProfile userProfile = new UserProfile(em,ph,n);
                        myRef.setValue(userProfile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");
                UploadTask uploadTask = imageReference.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterationActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(RegisterationActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
}
