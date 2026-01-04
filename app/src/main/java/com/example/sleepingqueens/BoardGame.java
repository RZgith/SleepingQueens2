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
                gameModule.player1.get(i).setX((Width/5+10)*i+10);
                gameModule.player1.get(i).setY(height-(height/6));
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),gameModule.player1.get(i).getBitmap() );
                bitmap = Bitmap.createScaledBitmap(bitmap,Width/5-10,300,false);
                gameModule.player1.get(i).draw(canvas,bitmap);


            }
            else
            {

                gameModule.player2.get(i).setX((Width/5+10)*i+10);
                gameModule.player2.get(i).setY(height-(height/6));
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),gameModule.player2.get(i).getBitmap() );
                bitmap = Bitmap.createScaledBitmap(bitmap,Width/5-10,300,false);
                gameModule.player2.get(i).draw(canvas,bitmap);
            }

            Card deck=new Card("deck",R.drawable.regularback);
            deck.setY(height-4*(height/6));
            deck.setX(Width/2-(Width/5+15));//50
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),deck.getBitmap());
            bitmap = Bitmap.createScaledBitmap(bitmap,Width/5-10,300,false);
            deck.draw(canvas,bitmap);

            Card trush=new Card("trush",R.drawable.regularback);
            trush.setY(height-4*(height/6));
            trush.setX(Width/2+15);
            Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),trush.getBitmap());
            bitmap2 = Bitmap.createScaledBitmap(bitmap,Width/5-10,300,false);
            trush.draw(canvas,bitmap2);


        }
    }
}












