package com.example.sleepingqueens;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
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

            if(player==1) {
                for (int i = 0; i < 5; i++) {
                    gameModule.player1.get(i).setX((Width / 5 + 10) * i + 10);
                    gameModule.player1.get(i).setY(height - (height / 6));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), gameModule.player1.get(i).getBitmap());
                    bitmap = Bitmap.createScaledBitmap(bitmap, Width / 5 - 10, 300, false);
                    gameModule.player1.get(i).draw(canvas, bitmap);
                }
                if (gameModule.q1!=null){
                    //ציור של הקלפי מלכות של השחקן
                    for (int i = 0; i < gameModule.q1.size(); i++) {
                        if(i>5){
                            //בנפרד בגלל שצריך לצייר את זה בשורה נפרדת
                            gameModule.q1.get(i).setY(height - 3*(height / 6));
                            gameModule.q1.get(i).setX((Width / 5 + 10) * (i-5) + 10);
                        }
                        else {
                            gameModule.q1.get(i).setX((Width / 5 + 10) * i + 10);
                            gameModule.q1.get(i).setY(height - 2*(height / 6));
                        }
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), gameModule.q1.get(i).getBitmap());
                        bitmap = Bitmap.createScaledBitmap(bitmap, Width / 5 - 10, 300, false);
                        gameModule.q1.get(i).draw(canvas, bitmap);
                    }
                }
            }
            else {
                for (int i = 0; i < 5; i++) {
                    gameModule.player2.get(i).setX((Width / 5 + 10) * i + 10);
                    gameModule.player2.get(i).setY(height - (height / 6));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), gameModule.player2.get(i).getBitmap());
                    bitmap = Bitmap.createScaledBitmap(bitmap, Width / 5 - 10, 300, false);
                    gameModule.player2.get(i).draw(canvas, bitmap);
                }
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

        if (gameModule.q1!=null)
        {

        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // קבלת סוג הנגיעה
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // מיקום הנגיעה על המסך
            float x = event.getX();
            float y = event.getY();
            int selectedCard=-1;
            for (int i = 0; i <5 ; i++) {
                if(x>((Width/5+10)*i+10) & x<((Width/5+10)*i+10)+(Width/5-10)
                        & y>(height-(height/6)) & y<((height-(height/6))+300))
                {
                    //אם הוא בחר באחד הקלפים
                    selectedCard=i;
                }
            }
            if (selectedCard==-1)
                return true;
            if(player==1) {
                if (gameModule.player1.get(selectedCard).getType().equals("king")) {
                    QueenDialog dialog = new QueenDialog(context, gameModule.queens, gameModule.q1);
                    dialog.show();
                }
                else
                {
                    //אם זה מספר להכניס לתוך מערך
                    //אם נלחץ כפתור של סיים תור אז לבדוק מה יש בתוך המערך - כולל הפעולה של המלך
                    invalidate(); // הגורם ל‑onDraw להיקרא שוב
                }


            }
        }

        return true; // מציין שטיפלנו בנגיעה
    }



}












