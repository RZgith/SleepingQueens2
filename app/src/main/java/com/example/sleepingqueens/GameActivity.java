package com.example.sleepingqueens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
private BoardGame boardGame;
private GameModule gameModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }
   //     if(getIntent().getIntExtra("player",1)==1)
   //         (gameModule).startGame();

        boardGame = new BoardGame(this);
        setContentView(boardGame);

    }
}