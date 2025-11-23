package com.example.sleepingqueens;

import android.graphics.Bitmap;

public class CardQueen extends Card{
    private int points;
    public CardQueen(String type, float x, float y, Bitmap bitmap,int points) {
        super(type, x, y, bitmap);
        this.points=points;

    }

    public int getPoints() {
        return points;
    }
}
