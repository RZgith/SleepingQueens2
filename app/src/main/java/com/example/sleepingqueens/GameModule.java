package com.example.sleepingqueens;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Random;

public class GameModule {
    public static ArrayList <Card> mainDeck=new ArrayList<Card>(),trash=new ArrayList<Card>(), queens=new ArrayList<Card>(),
            player1=new ArrayList<Card>(),player2=new ArrayList<Card>(),q1=new ArrayList<Card>(),q2=new ArrayList<Card>();
    private Context context;
    private FbModule instace;

    public GameModule(Context context) {
        this.context=context;
    }

    private void setMainDeck()
    {
        //הכנסת קלפים לחפיסה

        for (int i = 0; i <8 ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.king);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)200, (int)200, true);
            Card c1=new Card("king",bitmap);
            mainDeck.add(c1);
        }

        for (int i = 0; i <4 ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.knight);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)180, (int)250, true);
            Card c1=new Card("knight",bitmap);
            mainDeck.add(c1);
        }

        for (int i = 0; i <4 ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.dragon);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)180, (int)250, true);
            Card c1=new Card("dragon",bitmap);
            mainDeck.add(c1);
        }
        int[] NunCardsPhoto= {R.drawable.num1, R.drawable.num2,R.drawable.num3,R.drawable.num4,R.drawable.num5,R.drawable.num6,R.drawable.num7,R.drawable.num8,R.drawable.num9,R.drawable.num10};
        for (int i = 1; i <11 ; i++) {
            for (int j = 0; j < 4; j++) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),NunCardsPhoto[i-1]);
                bitmap = Bitmap.createScaledBitmap(bitmap, (int)180, (int)250, true);
                CardNumbers c1=new CardNumbers("num",bitmap,i);
                mainDeck.add(c1);
            }
        }
    }
    private void setQueens()
    {
        //בניית חפיסת מלכות
        int[] QueenCardsPhoto= {R.drawable.queen1, R.drawable.queen2,R.drawable.queen3,R.drawable.queen4,R.drawable.queen5,R.drawable.queen6,R.drawable.queen7,R.drawable.queen8,R.drawable.queen9,R.drawable.queen10,R.drawable.queen11,R.drawable.queen12};
        int[] QueenCardsPoints= {10,5,15,10,20,15,5,15,5,5,10,10};
        for (int i = 1; i <13 ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),QueenCardsPhoto[i-1]);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)200, (int)200, true);
            CardQueen c1=new CardQueen("Queen",bitmap,QueenCardsPoints[i-1]);
            queens.add(c1);
        }
    }
    public boolean AddExercise(CardNumbers c1, CardNumbers c2, CardNumbers c3, CardNumbers c4, CardNumbers c5)
    {
        //הורדת קלפים של תרגילים
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(c1.getNumber());
        nums.add(c2.getNumber());
        nums.add(c3.getNumber());
        if(c4!=null)
            nums.add(c4.getNumber());
        if(c5!=null)
            nums.add(c5.getNumber());

        for (int i = 0; i < nums.size(); i++)
        {
            int target = nums.get(i);
            int sum = 0;

            // סכום כולם חוץ מ-i
            for (int j = 0; j < nums.size(); j++)
            {
                if (j != i)
                {
                    sum += nums.get(j);
                }
            }
            if (sum == target)
                return true;
        }
        return false;
    }

    public void shuffle() {
        //Collections.shuffle(mainDeck);

            ArrayList<Card> temp = new ArrayList<>();
            Random rnd = new Random();
            for (int i = 0; i < mainDeck.size(); i++) {
                int x = rnd.nextInt(mainDeck.size());
                temp.add(mainDeck.get(x));
                mainDeck.remove(x);
            }
            for (int i = 0; i < temp.size(); i++) {
                mainDeck.add(temp.remove(i));
            }




    }
    public void shuffleQueens() {
        //Collections.shuffle(queens);
        ArrayList<Card> temp = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < queens.size(); i++) {
            int x = rnd.nextInt(queens.size());
            temp.add(queens.get(x));
            queens.remove(x);
        }
        for (int i = 0; i < temp.size(); i++) {
            queens.add(temp.remove(i));
        }


    }

    public void startGame() {
        setMainDeck();
        setQueens();
        shuffle();
        shuffleQueens();
        for (int i = 0; i < 5; i++) {
            player1.add(mainDeck.remove(0));
            player2.add(mainDeck.remove(0));
        }
        instace = FbModule.getInstance(context);
        instace.setDeck(mainDeck,"mainDeck");
        instace.setDeck(queens,"queens");
        instace.setDeck(q1,"q1");
        instace.setDeck(player1,"player1");
        instace.setDeck(q2,"q2");
        instace.setDeck(player2,"player2");
        instace.setDeck(trash,"trash");

    }
}
