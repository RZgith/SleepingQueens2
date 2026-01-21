package com.example.sleepingqueens;

import android.graphics.Bitmap;

public class CardQueen extends Card{
    private int points;
    public CardQueen(String type, int bitmap,int points) {
        super(type, bitmap);
        this.points=points;

    }

    public CardQueen() {
    }

    public int getPoints() {
        return points;
    }
}
