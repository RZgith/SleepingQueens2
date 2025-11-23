package com.example.sleepingqueens;

import android.graphics.Bitmap;

public class Card {
    private String type;
    private float x,y;
    private Bitmap bitmap;

    public Card(String type, float x, float y, Bitmap bitmap) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
    }

    public String getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
