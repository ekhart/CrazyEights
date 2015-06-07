package pl.ekhart.crazyeights;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ekh on 2015-06-07.
 */
public class GameView extends View {

    private int screenWidth,
            screenHeight;
    private Context context;

    private List<Card> deck = new ArrayList<>(),
        myHand = new ArrayList<>(),
        oppHand = new ArrayList<>(),
        discardPile = new ArrayList<>();
    final int CARDS_TO_DEAL = 7;

    private int scaledCardWidth, scaledCardHeight;

    private float scale;
    private Paint blackPaint;

    private int myScore, oppScore;

    public GameView(Context context) {
        super(context);
        this.context = context;

        scale = context.getResources().getDisplayMetrics().density;
        blackPaint = getPaint();
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(scale * 15);
        return paint;
    }

    private void initCards() {
        for (int i = 0; i < 4; ++i) {
            for (int j = 102; j < 115; ++j) {
                int id = j + (i * 100);
                Card card = new Card(id);
                int resourceId = getResources()
                        .getIdentifier("card" + id, "drawable", context.getPackageName());
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
                scaledCardWidth = screenWidth / 8;
                scaledCardHeight = (int) (scaledCardWidth * 1.28);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledCardWidth, scaledCardHeight, false);
                card.setBitmap(scaledBitmap);
                deck.add(card);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        initCards();
        dealCards();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawScores(canvas);
        drawMyHand(canvas);
    }

    private void drawMyHand(Canvas canvas) {
        for (int i = 0; i < myHand.size(); ++i) {
            if (i < CARDS_TO_DEAL) {
                Bitmap bitmap = myHand.get(i).getBitmap();
                int left = i * (scaledCardWidth + 5);
                canvas.drawBitmap(bitmap, left, getCardTop(), null);
            }
        }
    }

    private int getCardTop() {
        return (int) (screenHeight
                - scaledCardHeight
                - blackPaint.getTextSize()
                - (50 * scale));
    }

    private void drawScores(Canvas canvas) {
        canvas.drawText("Computer Score: " + oppScore,
                10, blackPaint.getTextSize() + 10, blackPaint);
        canvas.drawText("My Score: " + 10,
                10, screenHeight - blackPaint.getTextSize(), blackPaint);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();

        return true;
    }

    private void drawCard(List<Card> handToDraw) {
        handToDraw.add(0, deck.get(0));
        deck.remove(0);
        if (deck.isEmpty()) {
            for (int i = discardPile.size() - 1; i > 0; --i) {
                deck.add(discardPile.get(0));
                discardPile.remove(0);
                Collections.shuffle(deck);
            }
        }
    }

    private void dealCards() {
        Collections.shuffle(deck);
        for (int i = 0; i < CARDS_TO_DEAL; ++i) {
            drawCard(myHand);
            drawCard(oppHand);
        }
    }
}
