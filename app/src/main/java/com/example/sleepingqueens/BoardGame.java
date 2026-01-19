package com.example.sleepingqueens;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BoardGame extends View {
    private final Context context;
    private final GameModule gameModule;
    private int Width,height;
    private boolean firstTime=true;
    private final int player;
    private final ArrayList<Integer> selectedCardsNum = new ArrayList<Integer>();

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

        // Paint לציור המלבן (רקע הכפתור)
        Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(Color.GRAY);          // צבע המלבן
        rectPaint.setStyle(Paint.Style.FILL);    // ציור עם מילוי מלא

        // Paint לציור הטקסט
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);          // צבע הטקסט
        textPaint.setTextSize(42f);               // גודל הטקסט בפיקסלים
        textPaint.setTextAlign(Paint.Align.CENTER); // יישור אופקי למרכז

        // קואורדינטות המלבן על המסך
        float left = 0;   // צד שמאל של המלבן
        float top = height-4*(height/6);    // צד עליון של המלבן
        float right = left+300;  // צד ימין של המלבן
        float bottom = top+160; // צד תחתון של המלבן

        // ציור המלבן על ה־Canvas
        canvas.drawRect(left, top, right, bottom, rectPaint);

        // חישוב נקודת האמצע האופקית של המלבן
        float centerX = (left + right) / 2;

        // חישוב נקודת האמצע האנכית של המלבן
        // descent() ו־ascent() משמשים ליישור אנכי מדויק של הטקסט
        float centerY = (top + bottom) / 2
                - (textPaint.descent() + textPaint.ascent()) / 2;

        // ציור הטקסט במרכז המלבן
        canvas.drawText("Exercise", centerX, centerY, textPaint);

            if(player==1) {
                for (int i = 0; i < 5; i++) {
                    GameModule.player1.get(i).setX((Width / 5 + 10) * i + 10);
                    GameModule.player1.get(i).setY(height - (height / 6));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), GameModule.player1.get(i).getBitmap());
                    bitmap = Bitmap.createScaledBitmap(bitmap, Width / 5 - 10, 300, false);
                    GameModule.player1.get(i).draw(canvas, bitmap);
                }
                if (GameModule.q1 !=null){
                    //ציור של הקלפי מלכות של השחקן
                    for (int i = 0; i < GameModule.q1.size(); i++) {
                        if(i>5){
                            //בנפרד בגלל שצריך לצייר את זה בשורה נפרדת
                            GameModule.q1.get(i).setY(height - 3*(height / 6));
                            GameModule.q1.get(i).setX((Width / 5 + 10) * (i-5) + 10);
                        }
                        else {
                            GameModule.q1.get(i).setX((Width / 5 + 10) * i + 10);
                            GameModule.q1.get(i).setY(height - 2*(height / 6));
                        }
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), GameModule.q1.get(i).getBitmap());
                        bitmap = Bitmap.createScaledBitmap(bitmap, Width / 5 - 10, 300, false);
                        GameModule.q1.get(i).draw(canvas, bitmap);
                    }
                }
            }
            else {
                for (int i = 0; i < 5; i++) {
                    GameModule.player2.get(i).setX((Width / 5 + 10) * i + 10);
                    GameModule.player2.get(i).setY(height - (height / 6));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), GameModule.player2.get(i).getBitmap());
                    bitmap = Bitmap.createScaledBitmap(bitmap, Width / 5 - 10, 300, false);
                    GameModule.player2.get(i).draw(canvas, bitmap);
                }
                if (GameModule.q1 !=null)
                {


                }
            }

            Card deck=new Card("deck",R.drawable.regularback);
            deck.setY(height-4*(height/6));
            deck.setX(Width/2-(Width/5+15));//50
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),deck.getBitmap());
            bitmap = Bitmap.createScaledBitmap(bitmap,Width/5-10,300,false);
            deck.draw(canvas,bitmap);

           /* if(gameModule.trash!=null){
                int c=gameModule.trash.size()-1;
                gameModule.trash.get(c).setY(height-4*(height/6));
                gameModule.trash.get(c).setX(Width/2+15);
                Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),gameModule.trash.get(c).getBitmap());
                bitmap2 = Bitmap.createScaledBitmap(bitmap,Width/5-10,300,false);
                gameModule.trash.get(c).draw(canvas,bitmap2);
            }*/





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
            if (selectedCard==-1 & !(x>0 & x<300
                    & y>height-4*(height/6) & y<(height-4*(height/6)+160)))
            {
                //אם מה שנלחץ אינו קלף וגם לא הכפתור של ה-exercise
                return true;
            }

            if(player==1 )
            {
                if (x>0 & x<300
                    & y>height-4*(height/6) & y<(height-4*(height/6)+160))
                {
                    //אם השחקן לחץ על exercise
                    if (selectedCardsNum.size() == 1) {
                        gameModule.ChangeCard(1, selectedCardsNum.get(0));
                        //השחקן רוצב לזרוק קלף אחד
                    }
                    else {
                        //השחקן רוצה לעשות תרגיל או לזרוק דאבל
                        /*if (selectedCardsNum.size() == 2 & gameModule.DoubleNum((CardNumbers) GameModule.player1.get(selectedCardsNum.get(0)), (CardNumbers) GameModule.player1.get(selectedCardsNum.get(1))))
                        {
                            for (int i = 0; i < selectedCardsNum.size(); i++) {
                                gameModule.ChangeCard(1, selectedCardsNum.get(i));
                            }
                        }*/

                       /* if (selectedCardsNum.size() == 3)
                            gameModule.AddExercise((CardNumbers)gameModule.player1.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(2)),null,null);
                        if (selectedCardsNum.size() == 4)
                            gameModule.AddExercise((CardNumbers)gameModule.player1.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(2)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(3)),null);
                        if (selectedCardsNum.size() == 5)
                            gameModule.AddExercise((CardNumbers)gameModule.player1.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(2)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(3)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(4)));
*/
                        //חילוף של כל הקלפים שהשחקן בחר
                      /*  for (int i = 0; i < selectedCardsNum.size(); i++) {
                            gameModule.ChangeCard(1, selectedCardsNum.get(i));
                        }*/
                    }
                    //מחיקה של הערכים על מנת התחלה של תור חדש
                    int num = selectedCardsNum.size();
                    for (int i = 0; i < num; i++)
                    {
                        selectedCardsNum.remove(0);

                    }
                }
                else
                {
                    //בחירה של השחקן במלך על מנת לקנות מלכה
                    if (GameModule.player1.get(selectedCard).getType().equals("king")) {
                        QueenDialog dialog = new QueenDialog(context, GameModule.queens, GameModule.q1);
                        dialog.show();
                        gameModule.ChangeCard(1, selectedCard);
                    }
                    else
                    {
                        //בחירה של השחקן באביר כדי לגנות מלכה
                        //בחירה של השחקן במספר או
                        // לזרוק קלף ללא ביצוע הפעולה (כאשר לשחקן השני אין מלכות או שלא הופעל אביר מהצד השני)
                        selectedCardsNum.add(selectedCard);

                    }

                }
            }
            else
                {
                    //שחקן 2

                }
            //מחיקה של הערכים בפיירבייס על מנת לשים את המערכים מחדש
            gameModule.DecksClear();
            //השמה מחדש של הערכין בפיירבייס
            gameModule.SetApdateDecks();
            //ציור מחדש של הלוח
            invalidate();
        }
        // מציין שטיפלנו בנגיעה
        return true;

    }



}












