package com.example.sleepingqueens;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import androidx.annotation.NonNull;

public class BoardGame extends View {
    private Context context;
    private GameModule gameModule;
    private int Width,height;
    private boolean firstTime=true;
    private String player;

    public BoardGame(Context context) {
        super(context);
        this.context=context;

        gameModule= new GameModule(context);
        gameModule.startGame();
        //player=

    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (firstTime){
            Width=canvas.getWidth();
            height=canvas.getHeight();
            gameModule.startGame();

        }
        firstTime=false;
    }
}












