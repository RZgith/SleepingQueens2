package com.example.sleepingqueens;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class GameModule {
    private ArrayList <Card> mainDeck, queens, player1,player2,q1,q2;

    public void setMainDeck()
    {
        mainDeck=new ArrayList<Card>();
        for (int i = 0; i <8 ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.king);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)200, (int)200, true);
            Card c1=new Card("king",bitmap);
            mainDeck.add(c1);
        }

        for (int i = 0; i <4 ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.knight);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)180, (int)250, true);
            Card c1=new Card("knight",bitmap);
            mainDeck.add(c1);
        }

        for (int i = 0; i <4 ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.dragon);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)180, (int)250, true);
            Card c1=new Card("dragon",bitmap);
            mainDeck.add(c1);
        }
        int[] NunCardsPhoto= {R.drawable.num1, R.drawable.num2,R.drawable.num3,R.drawable.num4,R.drawable.num5,R.drawable.num6,R.drawable.num7,R.drawable.num8,R.drawable.num9,R.drawable.num10};
        for (int i = 1; i <11 ; i++) {
            for (int j = 0; j < 4; j++) {
                Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),NunCardsPhoto[i]);
                bitmap = Bitmap.createScaledBitmap(bitmap, (int)180, (int)250, true);
                CardNumbers c1=new CardNumbers("num",bitmap,i);
                mainDeck.add(c1);
            }
        }
    }
    public void setQueens()
    {
        queens=new ArrayList<Card>();
        int[] QueenCardsPhoto= {R.drawable.queen1, R.drawable.queen2,R.drawable.queen3,R.drawable.queen4,R.drawable.queen5,R.drawable.queen6,R.drawable.queen7,R.drawable.queen8,R.drawable.queen9,R.drawable.queen10,R.drawable.queen11,R.drawable.queen12};
        int[] QueenCardsPoints= {10,5,15,10,20,15,5,15,5,5,10,10};
        for (int i = 1; i <13 ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),QueenCardsPhoto[i]);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)200, (int)200, true);
            CardQueen c1=new CardQueen("Queen",bitmap,QueenCardsPoints[i]);
            mainDeck.add(c1);
        }
    }
    public boolean AddExercise(CardNumbers c1, CardNumbers c2, CardNumbers c3, CardNumbers c4, CardNumbers c5)
    {
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

}
