package com.example.sleepingqueens;

import android.graphics.Bitmap;

public class CardQueen extends Card{
    private int points;
    public CardQueen(String type, Bitmap bitmap,int points) {
        super(type, bitmap);
        this.points=points;

    }

    public int getPoints() {
        return points;
    }
}
