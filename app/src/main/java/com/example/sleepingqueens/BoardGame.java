package com.example.sleepingqueens;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class BoardGame extends View {
    private  Context context;

    private static GameModule gameModule;
    //ערך סטטי על מנת שיהיה אפשר להשתמש בו בפעולה סטטית.
    private int Width;
    private int height;
    private static int myQnumber=0;
    protected static boolean firstTime=true;
    protected boolean choosing=false,middleGame;
    private static int player;
    public static boolean IsFbRead = false;
    Handler handler;
    ThreadGame threadGame;
    Card deck1;
    Card movingCard; // הקלף שיוצא מהיד אל ה-Trash
    int selectedCard=-1;
    private boolean isRun = false;
    private final ArrayList<Integer> selectedCardsNum = new ArrayList<Integer>();
    private int counter = 0;


    public BoardGame(Context context,int player) {
        super(context);
        this.context=context;
        gameModule= new GameModule(context);
        this.player=player;
        this.setBackgroundResource(R.drawable.game_background);
        if (player==1 && firstTime){
            middleGame=true;
            gameModule.startGame1();
        }

        else if (player==1 && !firstTime) {
            gameModule.secondStartGame1();
            firstTime=true;
            middleGame=true;
        }
        if(player==2 && !middleGame){
            gameModule.startGame2();
            firstTime=true;
        }


        deck1 = new Card("deck", R.drawable.regularback);
        movingCard = new Card("moving", R.drawable.regularback);

        threadGame = new ThreadGame();
        threadGame.start(); // runs as thread the run() method


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if (selectedCardsNum.isEmpty()) {
                    isRun = false;
                    return false;
                }

                counter++;

                int currentIdx = selectedCardsNum.get(0);
                List<CardNumbers> currentPlayerHand = (player == 1) ? GameModule.player1 : GameModule.player2;

                if (currentIdx < currentPlayerHand.size()) {
                    CardNumbers cardInHand = currentPlayerHand.get(currentIdx);

                    // --- הגדרת הקלף שנזרק (רק בפריים הראשון) ---
                    if (counter == 1) {
                        // נותנים ל-movingCard את המיקום ההתחלתי של הקלף ביד
                        movingCard.setX(cardInHand.getX());
                        movingCard.setY(cardInHand.getY());
                        //  לוקחים את התמונה של הקלף הנוכחי ביד
                        movingCard.setBitmap(cardInHand.getBitmap());
                    }

                    // --- אנימציה 1: מהקופה ליד (deck1) ---
                    float targetX = cardInHand.getX();
                    float targetY = cardInHand.getY();
                    deck1.setX(deck1.getX() + (targetX - deck1.getX()) / (11 - counter));
                    deck1.setY(deck1.getY() + (targetY - deck1.getY()) / (11 - counter));

                    // --- אנימציה 2: מהיד ל-Trash (movingCard) ---
                    float trashX = Width / 2 + 15;
                    float trashY = height - 4 * (height / 6);
                    movingCard.setX(movingCard.getX() + (trashX - movingCard.getX()) / (11 - counter));
                    movingCard.setY(movingCard.getY() + (trashY - movingCard.getY()) / (11 - counter));
                }

                if (counter == 10) {
                    counter = 0;
                    gameModule.ChangeCard(player, currentIdx);
                    selectedCardsNum.remove(0);

                    // איפוס
                    deck1.setX(Width / 2 - (Width / 5 + 15));
                    deck1.setY(height - 4 * (height / 6));
                    movingCard.setX(-500); // העלמה מהמסך

                    if (selectedCardsNum.isEmpty()) {
                        isRun = false;
                        Apdate();
                        isWin();
                    }
                }

                invalidate();
                return true;
            }
        });


    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (firstTime){
            Width=canvas.getWidth();
            height=canvas.getHeight();
            deck1.setX(Width / 2 - (Width / 5 + 15));
            deck1.setY(height - 4 * (height / 6));
        }
        firstTime=false;

        // הגנה: אם הנתונים טרם נקראו מ-Firebase, לא מציירים כלום
        if(!IsFbRead) return;

        // --- ציור כפתור Exercise ---
        Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(Color.GRAY);
        float left = 0, top = height-4*(height/6), right = 300, bottom = top+160;
        canvas.drawRect(left, top, right, bottom, rectPaint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(42f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Exercise", (left + right) / 2, (top + bottom) / 2 - (textPaint.descent() + textPaint.ascent()) / 2, textPaint);

        // --- ציור קלפי שחקן 1 ---
        if (player == 1) {
            // בדיקה שהרשימה קיימת ואינה ריקה לפני הגישה אליה
            if (GameModule.player1 != null && !GameModule.player1.isEmpty()) {
                for (int i = 0; i < GameModule.player1.size(); i++) {
                    // הגנה נוספת ליתר ביטחון למקרה שהרשימה קטנה מ-5
                    CardNumbers card = GameModule.player1.get(i);
                    card.setX((Width / 5 ) * i + 10);
                    card.setY(height - (height / 6));

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), card.getBitmap());
                    bitmap = Bitmap.createScaledBitmap(bitmap, Width / 5 - 10, 300, false);
                    card.draw(canvas, bitmap);
                }
            }

            // ציור המלכות (שימוש ב-size())
            drawQueensList(canvas, GameModule.q1, height - 2 * (height / 6), height - 3 * (height / 6));
            drawQueensList(canvas, GameModule.q2, 0, (height / 6)); // מלכות יריב מוצגות למעלה
        }
        // --- ציור קלפי שחקן 2 ---
        else {
            if (GameModule.player2 != null && !GameModule.player2.isEmpty()) {
                for (int i = 0; i < GameModule.player2.size(); i++) {
                    CardNumbers card = GameModule.player2.get(i);
                    card.setX((Width / 5 ) * i + 10);
                    card.setY(height - (height / 6));

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), card.getBitmap());
                    bitmap = Bitmap.createScaledBitmap(bitmap, Width / 5 - 10, 300, false);
                    card.draw(canvas, bitmap);
                }
            }

            drawQueensList(canvas, GameModule.q2, height - 2 * (height / 6), height - 3 * (height / 6));
            drawQueensList(canvas, GameModule.q1, 0, (height / 6));
        }

        // --- ציור הקופה (Deck) ---
        Card deck = new Card("deck", R.drawable.regularback);
        deck.setY(height - 4 * (height / 6));
        deck.setX(Width / 2 - (Width / 5 + 15));
        Bitmap deckBitmap = BitmapFactory.decodeResource(getResources(), deck.getBitmap());
        deckBitmap = Bitmap.createScaledBitmap(deckBitmap, Width / 5 - 10, 300, false);
        deck.draw(canvas, deckBitmap);

        //קלף של חבילה להזיז אותו בMOVE
        deck1.draw(canvas,deckBitmap);
        /*deck1.setY(height - 4 * (height / 6));
        deck1.setX(Width / 2 - (Width / 5 + 15));*/

        // --- ציור ערימת הזריקה (Trash) ---
        // בדיקה כפולה: 1) שהרשימה קיימת בזיכרון (לא Null) ו-2) שיש בה לפחות קלף אחד (לא ריקה)
        if (gameModule.trash != null && !gameModule.trash.isEmpty()) {
            // השגת האינדקס של הקלף האחרון
            int lastIndex = gameModule.trash.size() - 1;
            Card trashCard = gameModule.trash.get(lastIndex);

            // קביעת מיקום הקלף (מימין לקופה)
            trashCard.setY(height - 4 * (height / 6));
            trashCard.setX(Width / 2 + 15);

            // טעינת התמונה ושינוי גודל
            Bitmap trashBitmap = BitmapFactory.decodeResource(getResources(), trashCard.getBitmap());
            Bitmap scaledTrash = Bitmap.createScaledBitmap(trashBitmap, Width / 5 - 10, 300, false);

            // ציור הקלף
            trashCard.draw(canvas, scaledTrash);

            // חשוב: שחרור ה-Bitmap מהזיכרון כדי למנוע קריסות (מאחר ואנחנו יוצרים אותו בתוך ה-Draw)
            trashBitmap.recycle();
        }
        // ציור האנימציה של הקלף שנזרק ל-Trash
        if (isRun && movingCard.getX() != -500) {
            // טעינת התמונה הדינמית (התמונה של הקלף שהיה ביד)
            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), movingCard.getBitmap());
            if (mBitmap != null) {
                Bitmap scaledM = Bitmap.createScaledBitmap(mBitmap, Width / 5 - 10, 300, false);
                movingCard.draw(canvas, scaledM);
                // לא לשכוח recycle אם אתה יוצר ביטמפ חדש כל פעם ב-draw
                mBitmap.recycle();
            }
        }
    }

    // פונקציית עזר לציור מלכות למניעת כפל קוד וקריסות
    private void drawQueensList(Canvas canvas, ArrayList<CardQueen> queens, float yRow1, float yRow2) {
        if (queens != null) {
            for (int i = 0; i < queens.size(); i++) {
                CardQueen q = queens.get(i);
                if (i >= 5) {
                    q.setY(yRow2);
                    q.setX((Width / 5 ) * (i - 5) + 10);
                } else {
                    q.setX((Width / 5 ) * i + 10);
                    q.setY(yRow1);
                }
                Bitmap qBitmap = BitmapFactory.decodeResource(getResources(), q.getBitmap());
                qBitmap = Bitmap.createScaledBitmap(qBitmap, Width / 5 - 10, 300, false);
                q.draw(canvas, qBitmap);
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d("Roni", "whatturn: counter: " + GameModule.turnCounter + " player: " + player);
        // קבלת סוג הנגיעה
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            // אם זה לא התור של השחקן - אנחנו פשוט מחזירים true (כדי לצרוך את האירוע) ולא עושים כלום
            if (GameModule.turnCounter % 2 != player % 2) {
                // אופציונלי: אפשר להוסיף כאן Toast.makeText שיודיע "זה לא התור שלך"
                return true;
            }
            // מיקום הנגיעה על המסך
            float x = event.getX();
            float y = event.getY();
            selectedCard=-1;
            for (int i = 0; i <5 ; i++) {
                if(x>((Width/5)*i+10) && x<((Width/5)*i+10)+(Width/5)
                        & y>(height-(height/6)) & y<((height-(height/6))+300))
                {
                    //אם הוא בחר באחד הקלפים
                    selectedCard=i;
                }
            }
            if (selectedCard==-1 && !(x>0 && x<300
                    & y>height-4*(height/6) & y<(height-4*(height/6)+160)))
            {
                //אם מה שנלחץ אינו קלף וגם לא הכפתור של ה-exercise
                //בחר לפני באביר
                if(choosing){
                    if(player==2) {
                        for (int i = 0; i < gameModule.q1.size(); i++) {

                            if (x >= gameModule.q1.get(i).getX() && x <= (gameModule.q1.get(i).getX() + (Width / 5) - 10) &&
                                    y >= gameModule.q1.get(i).getY() && y <= (gameModule.q1.get(i).getY() + 300)) {
                                gameModule.q2.add(gameModule.q1.remove(i));
                                myQnumber++;
                                choosing = false;
                                Apdate();
                                isWin();
                            }
                        }
                    }
                    else
                    {
                        //בחר לפני באביר
                        for (int i = 0; i < gameModule.q2.size(); i++) {

                            if (x >= gameModule.q2.get(i).getX() && x <= (gameModule.q2.get(i).getX() + (Width / 5) - 10) &&
                                    y >= gameModule.q2.get(i).getY() && y <= (gameModule.q2.get(i).getY() + 300)) {

                                gameModule.q1.add(gameModule.q2.remove(i));
                                myQnumber++;
                                choosing = false;
                                Apdate();
                                isWin();
                            }
                        }

                    }

                }
                return true;

            }

            if(player==1 )
            {
                Log.d("Roni", "1" +" player: " + player);

                if (x>0 & x<300
                    & y>height-4*(height/6) && y<(height-4*(height/6)+160) )
                {
                    //אם השחקן לחץ על exercise
                    Log.d("Roni",  " exercise1" );
                    if (selectedCardsNum.size() == 1) {
                        isRun = true;
                       // gameModule.ChangeCard(1, selectedCardsNum.get(0));
                        //השחקן רוצב לזרוק קלף אחד
                    }
                    else {
                        //השחקן רוצה לעשות תרגיל או לזרוק דאבל
                        if (selectedCardsNum.size() == 2 && gameModule.DoubleNum((CardNumbers) GameModule.player1.get(selectedCardsNum.get(0)), (CardNumbers) GameModule.player1.get(selectedCardsNum.get(1))))
                        {
                            for (int i = 0; i < 2; i++) {
                                isRun = true;
                                //gameModule.ChangeCard(1, selectedCardsNum.get(i));
                            }
                        }
                        else if (selectedCardsNum.size() == 3 &&  gameModule.AddExercise((CardNumbers)gameModule.player1.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(2)),null,null))
                        {
                            for (int i = 0; i < 3; i++) {

                                isRun = true;
                                //gameModule.ChangeCard(1, selectedCardsNum.get(i));
                            }
                        }
                        else if (selectedCardsNum.size() == 4 && gameModule.AddExercise((CardNumbers)gameModule.player1.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(2)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(3)),null))
                        {
                            for (int i = 0; i < 4; i++) {

                                isRun = true;
                                //gameModule.ChangeCard(1, selectedCardsNum.get(i));
                            }
                        }
                        else if (selectedCardsNum.size() == 5 && gameModule.AddExercise((CardNumbers)gameModule.player1.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(2)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(3)),(CardNumbers)gameModule.player1.get(selectedCardsNum.get(4))))
                        {

                            for (int i = 0; i < 5; i++) {
                                deck1.setX(Width / 2 - (Width / 5 + 15));
                                deck1.setY(height - 4 * (height / 6));
                                isRun = true;
                                //    gameModule.ChangeCard(1, selectedCardsNum.get(i));
                            }
                        }
                        else {
                            // התרגיל שגוי - מנקים בחירה ומאפשרים לבחור מחדש
                            selectedCardsNum.clear();
                            Toast.makeText(context, "תרגיל לא חוקי, נסה שוב!", Toast.LENGTH_SHORT).show();
                            invalidate(); // רענון המסך
                            return true;
                        }

                    }
                    //מחיקה של הערכים על מנת התחלה של תור חדש
                    //selectedCardsNum.clear();

                }
                else
                {
                    //בחירה של השחקן במלך על מנת לקנות מלכה
                    if (GameModule.player1.get(selectedCard).getType().equals("king")) {
                        Log.d("Roni",  " king" );

                        QueenDialog dialog = new QueenDialog(context, gameModule.queens, gameModule.q1);
                        dialog.show();
                        selectedCardsNum.add(selectedCard);
                        isRun=true;
                        //gameModule.ChangeCard(1, selectedCard);
                        //Apdate();
                        //isWin();
                    }
                    else if (GameModule.player1.get(selectedCard).getType().equals("knight"))
                    {
                        if(gameModule.q2.size()!=0)
                        {
                            choosing = true;
                            Toast.makeText(context, "בחר מלכה מקלפיו של השחקן השני", Toast.LENGTH_SHORT).show();
                            //isRun = true;
                            gameModule.ChangeCard(1, selectedCard);
                            //selectedCardsNum.add(selectedCard);
                            ApdateQueen();
                            return true;
                        }
                        selectedCardsNum.add(selectedCard);
                        isRun = true;
                        //gameModule.ChangeCard(1, selectedCard);


                    }
                    else if (GameModule.player1.get(selectedCard).getType().equals("dragon"))
                    {
                        if((myQnumber>gameModule.q1.size() && player==1) &&
                                gameModule.trash.get(gameModule.trash.size()-1).getType().equals("knight"))
                        {
                            gameModule.q1.add(gameModule.q2.remove(gameModule.q2.size()-1));
                        }
                        selectedCardsNum.add(selectedCard);
                        isRun = true;
                        //gameModule.ChangeCard(1, selectedCard);

                    }
                    else
                    {
                        //בחירה של השחקן באביר כדי לגנוב מלכה
                        //בחירה של השחקן במספר או
                        // לזרוק קלף ללא ביצוע הפעולה (כאשר לשחקן השני אין מלכות או שלא הופעל אביר מהצד השני)
                        for (int i = 0; i < selectedCardsNum.size(); i++) {
                            if(selectedCard==selectedCardsNum.get(i))
                            {
                                //אם הקלף כבר נבחר בפעם השניה שנוגעים בו הבחירה בו מתבטלת
                                selectedCardsNum.remove(i);
                                return true;
                            }

                        }
                        selectedCardsNum.add(selectedCard);
                        return true;

                    }

                }
            }
            if(player==2)
                {
                    Log.d("Roni", "2"+ " player: " + player);
                    //שחקן 2
                    if (x>0 & x<300
                            & y>height-4*(height/6) & y<(height-4*(height/6)+160))
                    {
                        Log.d("Roni",  " exercise2" );
                        //אם השחקן לחץ על exercise
                        if (selectedCardsNum.size() == 1) {
                            isRun = true;
                            //gameModule.ChangeCard(2, selectedCardsNum.get(0));
                            //השחקן רוצב לזרוק קלף אחד
                        }
                        else {
                            //השחקן רוצה לעשות תרגיל או לזרוק דאבל
                            if (selectedCardsNum.size() == 2 & gameModule.DoubleNum((CardNumbers) GameModule.player2.get(selectedCardsNum.get(0)), (CardNumbers) GameModule.player2.get(selectedCardsNum.get(1))))
                            {
                                for (int i = 0; i < 2; i++) {
                                    isRun = true;
                                    //gameModule.ChangeCard(2, selectedCardsNum.get(i));
                                }
                            }
                            else if (selectedCardsNum.size() == 3 &&  gameModule.AddExercise((CardNumbers)gameModule.player2.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(2)),null,null))
                            {
                                for (int i = 0; i < 3; i++) {
                                    isRun = true;
                                    //gameModule.ChangeCard(2, selectedCardsNum.get(i));
                                }
                            }
                            else if (selectedCardsNum.size() == 4 && gameModule.AddExercise((CardNumbers)gameModule.player2.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(2)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(3)),null))
                            {
                                for (int i = 0; i < 4; i++) {
                                    isRun = true;
                                    //gameModule.ChangeCard(2, selectedCardsNum.get(i));
                                }
                            }
                            else if (selectedCardsNum.size() == 5 && gameModule.AddExercise((CardNumbers)gameModule.player2.get(selectedCardsNum.get(0)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(1)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(2)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(3)),(CardNumbers)gameModule.player2.get(selectedCardsNum.get(4))))
                            {

                                for (int i = 0; i < 5; i++) {
                                    isRun = true;
                                    //gameModule.ChangeCard(2, selectedCardsNum.get(i));
                                }
                            }
                            else {
                                // התרגיל שגוי - מנקים בחירה ומאפשרים לבחור מחדש
                                selectedCardsNum.clear();
                                Toast.makeText(context, "תרגיל לא חוקי, נסה שוב!", Toast.LENGTH_SHORT).show();
                                invalidate(); // רענון המסך
                                return true;
                            }

                        }
                        //מחיקה של הערכים על מנת התחלה של תור חדש
                        //selectedCardsNum.clear();
                    }
                    else
                    {

                        //בחירה של השחקן במלך על מנת לקנות מלכה
                        if (GameModule.player2.get(selectedCard).getType().equals("king")) {
                            Log.d("Roni", "king2 ");
                            QueenDialog dialog = new QueenDialog(context, gameModule.queens, gameModule.q2);
                            dialog.show();
                            selectedCardsNum.add(selectedCard);
                            isRun = true;
                            //gameModule.ChangeCard(2, selectedCard);
                            //Apdate();
                            //isWin();
                        }
                        else if (GameModule.player2.get(selectedCard).getType().equals("knight"))
                        {//בחירה של השחקן באביר כדי לגנות מלכה
                            if(gameModule.q1.size()!=0)
                            {
                                choosing = true;
                                Toast.makeText(context, "בחר מלכה מקלפיו של השחקן השני", Toast.LENGTH_SHORT).show();
                                //selectedCardsNum.add(selectedCard);
                                gameModule.ChangeCard(2, selectedCard);
                                ApdateQueen();
                                return true;
                            }
                            selectedCardsNum.add(selectedCard);
                            isRun = true;
                            //gameModule.ChangeCard(2, selectedCard);
                        }
                        else if (GameModule.player2.get(selectedCard).getType().equals("dragon"))
                        {
                            if((myQnumber>gameModule.q2.size() && player==2) &&
                                    gameModule.trash.get(gameModule.trash.size()-1).getType().equals("knight"))
                            {
                                gameModule.q2.add(gameModule.q1.remove(gameModule.q1.size()-1));
                            }
                            selectedCardsNum.add(selectedCard);
                            isRun = true;
                            //gameModule.ChangeCard(2, selectedCard);
                            //Apdate();
                            //isWin();

                        }
                        else
                        {
                            //בחירה של השחקן במספר או
                            // לזרוק קלף ללא ביצוע הפעולה (כאשר לשחקן השני אין מלכות או שלא הופעל אביר מהצד השני)
                            for (int i = 0; i < selectedCardsNum.size(); i++) {
                                if(selectedCard==selectedCardsNum.get(i))
                                {
                                    //אם הקלף כבר נבחר בפעם השניה שנוגעים בו הבחירה בו מתבטלת
                                    selectedCardsNum.remove(i);
                                    return true;
                                }

                            }
                            selectedCardsNum.add(selectedCard);
                            return true;
                        }

                    }

                }
            //Apdate();
            //isWin();



        }
        // מציין שטיפלנו בנגיעה
        return true;

    }


    public void SetNewMove() {
        //ציור מחדש של הלוח
        invalidate();
        if(( (myQnumber>gameModule.q1.size() && player==1) || (myQnumber>gameModule.q2.size() && player==2) )
        && gameModule.trash!=null && !gameModule.trash.isEmpty())
        {
            if(gameModule.trash.get(gameModule.trash.size()-1).getType().equals("knight"))
                Toast.makeText(context, "נגנבה לך מלכה! השתמש בדרקון אם יש לך על מנת להחזיר אותה", Toast.LENGTH_SHORT).show();
        }
    }
    public static void Apdate(){
        if (player==1)
            myQnumber=gameModule.q1.size();
        else
            myQnumber=gameModule.q2.size();
        GameModule.turnCounter=1-GameModule.turnCounter;
        //מחיקה של הערכים בפיירבייס על מנת לשים את המערכים מחדש
        gameModule.DecksClear();
        //השמה מחדש של הערכין בפיירבייס
        gameModule.SetApdateDecks();
    }

    private void isWin(){
        if (gameModule.Win()>0){
            int win=gameModule.Win();
            if (win==1){
                middleGame=false;
                Toast.makeText(context, "player 1 won!!", Toast.LENGTH_SHORT).show();
                if(GameModule.turnCounter % 2 == player % 2)
                    GameModule.turnCounter=1-GameModule.turnCounter;
                ApdateQueen();
                //GameModule.NewGame();
                WinDialog dialog = new WinDialog(context, player, win);
                dialog.show();
            }
            else if (win==2) {
                middleGame=false;
                Toast.makeText(context, "player 2 won!!", Toast.LENGTH_SHORT).show();
                if(GameModule.turnCounter % 2 == player % 2)
                    GameModule.turnCounter=1-GameModule.turnCounter;
                ApdateQueen();
                //GameModule.NewGame();
                WinDialog dialog = new WinDialog(context, player, win);
                dialog.show();
            }
            else
                Toast.makeText(context, "YOU BOTH ARE IN A TIE! keep playing to win", Toast.LENGTH_SHORT).show();
        }

    }
    public static void ApdateQueen() {
        //נקנה מלכה ולכן מעדכן את מספר המלכות עבור השחקן
        if (player==1)
            myQnumber=gameModule.q1.size();
        else
            myQnumber=gameModule.q2.size();
        gameModule.DecksClear();
        //השמה מחדש של הערכין בפיירבייס
        gameModule.SetApdateDecks();
    }

    private class ThreadGame extends Thread{
        @Override
        public void run() {
            super.run();
            while (true)
            {
                try {
                    sleep(40);
                    if(isRun)
                        handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}












