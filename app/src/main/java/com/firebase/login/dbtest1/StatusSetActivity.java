package com.firebase.login.dbtest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusSetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spinner;
    private String item;
    private String fireStoreUID;

    Button changeStatus,listUser;

    private DocumentReference mDocRef;

    private FirebaseUser currentUser;

    Map<String,Object> status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_set);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        changeStatus = findViewById(R.id.changestatus);
        changeStatus.setOnClickListener(this);

        listUser = findViewById(R.id.listuser);
        listUser.setOnClickListener(this);

        List<String> categories = new ArrayList <String>();
        categories.add("Busy");
        categories.add("Travelling");
        categories.add("Meeting");
        categories.add("Sleeping");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        status = new HashMap<>();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        fireStoreUID = currentUser.getUid();

        mDocRef = FirebaseFirestore.getInstance().collection("users").document(fireStoreUID);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.listuser: Intent listUserIntent = new Intent(getApplicationContext(),ListUserStatusActivity.class);
                              startActivity(listUserIntent);
                               break;

            case R.id.changestatus: status.put("Status",item);
                                    mDocRef.update(status).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.w("STATUSWRITESS","Status write successful");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("STATUSWRITESS","Status write failed");
                                        }
                                    });
                                    break;
        }
    }
}
