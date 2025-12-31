package com.example.sleepingqueens;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BoardGame extends View {
    private Context context;
    private GameModule gameModule;
    private int Width,height;
    private boolean firstTime=true;
    private int player;

    public BoardGame(Context context,int player) {
        super(context);
        this.context=context;
        gameModule= new GameModule(context);
        this.player=player;
        if(player==1){
            gameModule.startGame();


        }

    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (firstTime){
            Width=canvas.getWidth();
            height=canvas.getHeight();

        }
        firstTime=false;
        for (int i = 0; i < 5; i++) {
            if(player==1)
            {
                //לעשות שלוקחים את החפיסה מהFB במקום פשוט את החפיסה
                ArrayList<Card> player1=gameModule.getDeckFromFB();
                player1.get(i).setX((Width/5+10)*i);
                player1.get(i).setY(height-350);
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),gameModule.player1.get(i).getBitmap() );
                bitmap = Bitmap.createScaledBitmap(bitmap,Width/5-10,300,false);
                player1.get(i).draw(canvas,bitmap);

            }
            else
            {
                //לעשות שלוקחים את החפיסה מהFB במקום פשוט את החפיסה
                gameModule.player2.get(i).setX((Width/5+10)*i);
                gameModule.player2.get(i).setY(height-350);
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),gameModule.player2.get(i).getBitmap() );
                bitmap = Bitmap.createScaledBitmap(bitmap,Width/5-10,300,false);
                gameModule.player2.get(i).draw(canvas,bitmap);
            }

        }
    }
}












