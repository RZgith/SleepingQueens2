package com.example.sleepingqueens;

import static androidx.core.util.TypedValueCompat.dpToPx;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class QueenDialog extends Dialog {
    private Context context;
    private ArrayList<Card> cards;
    private ArrayList<Card> q;
    private OnCardSelectedListener listener;

    public QueenDialog(@NonNull Context context, ArrayList<Card> cards,ArrayList<Card> q) {
        super(context);
        this.context=context;
        this.cards = cards;
        this.q = q;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// קריאה ל־Dialog המקורי – חובה לאתחול תקין
        BoardView boardView = new BoardView(getContext());
        // קובע שהתוכן של הדיאלוג יהיה View מותאם אישית
        // ה־BoardView מצייר את הקלפים על Canvas ומטפל בלחיצות
        setContentView(boardView);

        // קובעת גודל לדיאלוג (וגם ל־Canvas)
        if (getWindow() != null) {
            getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }
    }


    public interface OnCardSelectedListener {
        void onCardSelected(Card selectedCard, int position);

    }
    public void setOnCardSelectedListener(OnCardSelectedListener listener) {
        this.listener = listener;
    }
    private class BoardView extends View {

        private final int ROWS = 4;
        private final int COLS = 3;

        public BoardView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (cards == null || cards.size() != 12) return;
            int cardWidth = getWidth() / COLS;
            int cardHeight = getHeight() / ROWS;

            for (int i = 0; i < cards.size(); i++) {
                int row = i / COLS;
                int col = i % COLS;

                int left = col * cardWidth;
                int top = row * cardHeight;

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),cards.get(i).getBitmap());

                Bitmap scaledBitmap =Bitmap.createScaledBitmap(bitmap, cardWidth, cardHeight, true);

                canvas.drawBitmap(scaledBitmap, left, top, null);
            }
        }
        @Override
        public boolean onTouchEvent (MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                int x = (int) event.getX();
                int y = (int) event.getY();

                int cardWidth = getWidth() / COLS;
                int cardHeight = getHeight() / ROWS;

                int col = x / cardWidth;
                int row = y / cardHeight;

                int index = row * COLS + col;

                if (index >= 0 && index < cards.size()) {

                    Card selectedCard = cards.get(index);

                    // אם זה קלף ריק – לא עושים כלום
                    if (selectedCard.getType().equals("empty")) {
                        return true;
                    }
                    q.add(selectedCard);
                    // מחזירים את הקלף שנבחר
                    if (listener != null) {
                        listener.onCardSelected(selectedCard, index);
                    }

                    // מוציאים את הקלף ושמים קלף ריק
                    cards.set(index, new Card("empty",R.drawable.empty_card));
                    dismiss();

                }
            }

            return true;

        }

    }
}

