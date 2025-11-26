package com.example.sleepingqueens;

import android.graphics.Bitmap;

public class Card {
    private String type;
    private float x,y;
    private Bitmap bitmap;

    public Card(String type, Bitmap bitmap) {
        this.type = type;
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
