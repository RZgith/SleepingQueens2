package com.example.sleepingqueens;

import android.content.Context;
import android.view.View;

public class BoardGame extends View {
    private Context context;
    private GameModule gameModule;
    private int Width,height;

    public BoardGame(Context context) {
        super(context);
        this.context=context;

        gameModule= new GameModule(context);
        gameModule.startGame();

    }








}
