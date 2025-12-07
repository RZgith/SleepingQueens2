package com.example.sleepingqueens;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FbModule {
    FirebaseDatabase firebaseDatabase;

    DatabaseReference mainDeck;
    private Context context;

    public FbModule(Context context) {
        this.context=context;


        firebaseDatabase = FirebaseDatabase.getInstance("https://sleepingqueens-default-rtdb.firebaseio.com/");
        mainDeck = firebaseDatabase.getReference("mainDeck");
    }


}
