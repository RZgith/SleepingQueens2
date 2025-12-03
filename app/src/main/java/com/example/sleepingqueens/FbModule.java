package com.example.sleepingqueens;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FbModule {
    FirebaseDatabase firebaseDatabase;
    private Context context;
    DatabaseReference mainDeck;

    public FbModule(Context context) {
        this.context = context;


        firebaseDatabase = FirebaseDatabase.getInstance("https://sleepingqueens-default-rtdb.firebaseio.com/");
        mainDeck = firebaseDatabase.getReference("mainDeck");
    }


}
