package com.example.sleepingqueens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Card {
    private String type;
    private float x,y;
    private int bitmap;

    public Card(String type, int bitmap) {
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

    public int getBitmap() {
        return bitmap;
    }
    public void draw(Canvas canvas,Bitmap bitmap){
        canvas.drawBitmap(bitmap,x,y,null);
    }

}
