package com.example.sleepingqueens;

import android.graphics.Bitmap;

public class CardNumbers extends Card{
    private int number;
    public CardNumbers(String type, Bitmap bitmap,int num) {
        super(type, bitmap);
        this.number=num;
    }

    public int getNumber() {
        return number;
    }



}
