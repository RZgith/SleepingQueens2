package com.example.sleepingqueens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
private BoardGame boardGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        boardGame = new BoardGame(this);
        setContentView(boardGame);

    }
}