package com.example.sleepingqueens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
private BoardGame boardGame;
public int player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }
        player=getIntent().getIntExtra("player",1);
        boardGame = new BoardGame(this,player);
        setContentView(boardGame);

    }


}