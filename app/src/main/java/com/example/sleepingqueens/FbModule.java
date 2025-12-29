package com.example.sleepingqueens;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FbModule {

    private static FbModule instance;
    private final Context context;

    FirebaseDatabase database;

    private FbModule( Context context) {
        database = FirebaseDatabase.getInstance("https://sleepingqueens-default-rtdb.firebaseio.com/");
        //database = FirebaseDatabase.getInstance();
        this.context = context;

        //this.records = MainActivity.records;

        // read the records from the Firebase and order them by the record from highest to lowest
        // limit to only 8 items
        Query myQuery = database.getReference("Decks");

        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GameModule.mainDeck.clear();  // clear the array list
                // 1. Get the DataSnapshot for the "MainDeck" node
                DataSnapshot mainDeckSnapshot = snapshot.child("mainDeck");
                // 2. Iterate through the children of the "MainDeck" node
                for (DataSnapshot userSnapshot : mainDeckSnapshot.getChildren()) {
                    // 'userSnapshot' now represents each child under "MainDeck"
                    Card currentCard = userSnapshot.getValue(Card.class);
                    GameModule.mainDeck.add(currentCard);
                }
                GameModule.queens.clear();  // clear the array list
                DataSnapshot queensSnapshot = snapshot.child("queens");
                for (DataSnapshot userSnapshot : queensSnapshot.getChildren()) {
                    Card currentCard = userSnapshot.getValue(Card.class);
                    GameModule.queens.add(currentCard);
                }
                GameModule.player1.clear();  // clear the array list
                DataSnapshot player1Snapshot = snapshot.child("player1");
                for (DataSnapshot userSnapshot : player1Snapshot.getChildren()) {
                    Card currentCard = userSnapshot.getValue(Card.class);
                    GameModule.player1.add(currentCard);
                }
                GameModule.player2.clear();  // clear the array list
                DataSnapshot player2Snapshot = snapshot.child("player2");
                for (DataSnapshot userSnapshot : player2Snapshot.getChildren()) {
                    Card currentCard = userSnapshot.getValue(Card.class);
                    GameModule.player2.add(currentCard);
                }
                GameModule.q1.clear();  // clear the array list
                DataSnapshot q1Snapshot = snapshot.child("q1");
                for (DataSnapshot userSnapshot : q1Snapshot.getChildren()) {
                    Card currentCard = userSnapshot.getValue(Card.class);
                    GameModule.q1.add(currentCard);
                }
                GameModule.q2.clear();  // clear the array list
                DataSnapshot q2Snapshot = snapshot.child("q2");
                for (DataSnapshot userSnapshot : q2Snapshot.getChildren()) {
                    Card currentCard = userSnapshot.getValue(Card.class);
                    GameModule.q2.add(currentCard);

                }
                GameModule.trash.clear();  // clear the array list
                DataSnapshot trashSnapshot = snapshot.child("trash");
                for (DataSnapshot userSnapshot : trashSnapshot.getChildren()) {
                    Card currentCard = userSnapshot.getValue(Card.class);
                    GameModule.trash.add(currentCard);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    public static FbModule getInstance(Context context) {
        if (null == instance) {
            instance = new FbModule(context);
        }
        return instance;
    }

    public void setDeck(ArrayList<Card> arrayList, String deckName)
    {
        DatabaseReference myRef = database.getReference(deckName); // push adds new Arrylist with unique value
        myRef.setValue(arrayList);
    }


}
