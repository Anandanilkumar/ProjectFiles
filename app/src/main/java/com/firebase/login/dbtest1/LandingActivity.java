package com.firebase.login.dbtest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pkmmte.view.CircularImageView;

import java.util.HashMap;
import java.util.Map;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuthInstance;
    FirebaseUser currentUser;

    FirebaseFirestore db ;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private static  String Username;
    private static  String Useremail;
    private static String photoURL;
    private static String phoneNumber;

    Button regBUtton;
    Button changeStatusButton;

    TextView greetingTextView;
    TextView emailTextView;
    CircularImageView circularImageView;
    EditText phoneNumberInput;

    Map<String, Object> user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        circularImageView = findViewById(R.id.profileimage);
        greetingTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.usermailid);
        phoneNumberInput = findViewById(R.id.phoneNumber);
        regBUtton = findViewById(R.id.registerPhone);
        changeStatusButton = findViewById(R.id.gotostatus);

        findViewById(R.id.log_out_button).setOnClickListener(this);
        regBUtton.setOnClickListener(this);
        changeStatusButton.setOnClickListener(this);

        firebaseAuthInstance = FirebaseAuth.getInstance();
        currentUser = firebaseAuthInstance.getCurrentUser();
        Username = currentUser.getDisplayName();
        Useremail = currentUser.getEmail();
        photoURL = currentUser.getPhotoUrl().toString();

        Glide.with(this).load(photoURL)
                .into(circularImageView);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuthInstance.getCurrentUser()==null){
                    //user logged out
                    Intent mainPageIntent = new Intent(getApplicationContext(),Firstpage.class);
                    startActivity(mainPageIntent);
                }
            }
        };

        db = FirebaseFirestore.getInstance();

        user = new HashMap<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuthInstance.addAuthStateListener(mAuthListener);

        greetingTextView.setText("Hello, "+Username);
        emailTextView.setText(Useremail);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.log_out_button :  Log.w("FBAUTH",FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
                                        firebaseAuthInstance.signOut();
                                        break;
            case R.id.registerPhone :   phoneNumber = phoneNumberInput.getText().toString();
                                        user.put("Name",currentUser.getDisplayName());
                                        user.put("Email",Useremail);
                                        user.put("PhoneNumber",phoneNumber);
                                        user.put("Status","");
                                        db.collection("users").document(currentUser.getUid())
                                                .set(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {


                                                        Log.d("FIRESTORE", "DocumentSnapshot added with ID: ");
                                                        Intent statusSetIntent = new Intent(getApplicationContext(),StatusSetActivity.class);
                                                        startActivity(statusSetIntent);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("FIRESTORE", "Error adding document", e);
                                                    }
                                                });
                                        break;
            case R.id.gotostatus : Intent statusSetActivityIntent = new Intent(getApplicationContext(),StatusSetActivity.class);
                                     startActivity(statusSetActivityIntent);
                                     break;

        }
    }
}

