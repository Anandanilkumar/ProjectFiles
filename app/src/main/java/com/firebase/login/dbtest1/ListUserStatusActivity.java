package com.firebase.login.dbtest1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListUserStatusActivity extends AppCompatActivity {

    CollectionReference mCollectionRef;

    private List<UserData> docMapList;

    private RecyclerView recyclerView;

    private UserListAdapter userListAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        mCollectionRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                docMapList.clear();
                for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                    docMapList.add(document.toObject(UserData.class));
                    Log.w("FSDATA", "arraylist is working @onStart()");
                }
                for (UserData singleUser : docMapList)
                    Log.w("FSDATA", "@onStart() NAME " + singleUser.getName());
                userListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_status);

        docMapList = new ArrayList<>();
        recyclerView = findViewById(R.id.userlistview);
        userListAdapter = new UserListAdapter(docMapList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(userListAdapter);

        mCollectionRef = FirebaseFirestore.getInstance().collection("users");

        /* mCollectionRef
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                            docMapList.add(document.toObject(UserData.class));
                            Log.w("ARRAYLIST", "arraylist is working");
                        }
                        for(UserData singleUser : docMapList)
                            Log.w("FSDATA","NAME "+singleUser.getName());
                        userListAdapter.notifyDataSetChanged();
                    }
                }); */
    }
}
