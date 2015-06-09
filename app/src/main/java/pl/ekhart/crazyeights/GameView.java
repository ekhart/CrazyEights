package pl.ekhart.crazyeights;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Bitmap cardBack;

    private boolean myTurn;

    private int movingCardId = -1,
        movingX, movingY;

    private int validRank = 8,
        validSuit = 0;

    public GameView(Context context) {
        super(context);
        this.context = context;

        scale = context.getResources().getDisplayMetrics().density;
        blackPaint = getPaint();
        //myTurn = new Random().nextBoolean();
        myTurn = true;
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
                Bitmap scaledBitmap = getScaledCardBitmap(bitmap);
                card.setBitmap(scaledBitmap);
                deck.add(card);
            }
        }
    }

    private Bitmap getScaledCardBitmap(Bitmap bitmap) {
        scaledCardWidth = screenWidth / 8;
        scaledCardHeight = (int) (scaledCardWidth * 1.28);
        return Bitmap.createScaledBitmap(bitmap, scaledCardWidth, scaledCardHeight, false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        initCards();
        loadCardBack();
        dealCards();
        drawCard(discardPile);

        Card card = discardPile.get(0);
        validSuit = card.getSuit();
        validRank = card.getRank();
    }

    private void loadCardBack() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
        cardBack = getScaledCardBitmap(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawScores(canvas);
        //drawMyHand(canvas);
        drawOppHand(canvas);
        drawDrawPile(canvas);
        drawDiscardPile(canvas);
        moveCard(canvas);
    }

    private void moveCard(Canvas canvas) {
        for (int i = 0; i < myHand.size(); ++i) {
            Bitmap bitmap = myHand.get(i).getBitmap();
            boolean isMovingCard = i == movingCardId;
            int left = isMovingCard ? movingX : getCardLeft(i),
                top = isMovingCard ? movingY : getCardTop();
                canvas.drawBitmap(bitmap, left, top, null);
        }
        invalidate();
    }

    private void drawDiscardPile(Canvas canvas) {
        if (!discardPile.isEmpty()) {
            Bitmap topCard = discardPile.get(0).getBitmap();
            int left = screenWidth / 2 + 10,
                top = (screenHeight / 2) - (cardBack.getHeight() / 2);
            canvas.drawBitmap(topCard, left, top, null);
        }
    }

    private void drawDrawPile(Canvas canvas) {
        int left = (screenWidth / 2) - cardBack.getWidth() - 10,
            top = (screenHeight / 2) - (cardBack.getHeight() / 2);
        canvas.drawBitmap(cardBack, left, top, null);
    }

    private void drawOppHand(Canvas canvas) {
        for (int i = 0; i < myHand.size(); ++i) {
                int left = (int) (i * (scale * 5)),
                    top = (int) (blackPaint.getTextSize() + (50 * scale));
                canvas.drawBitmap(cardBack, left, top, null);
        }
    }

    private void drawMyHand(Canvas canvas) {
        for (int i = 0; i < myHand.size(); ++i) {
            if (i < CARDS_TO_DEAL) {
                Bitmap bitmap = myHand.get(i).getBitmap();
                int left = getCardLeft(i);
                canvas.drawBitmap(bitmap, left, getCardTop(), null);
            }
        }
    }

    private int getCardLeft(int i) {
        return i * (scaledCardWidth + 5);
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
        canvas.drawText("My Score: " + myScore,
                10, screenHeight - blackPaint.getTextSize(), blackPaint);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setMovingIdIfMyTurn(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                movingX = x - offset(30);
                movingY = y - offset(70);
                break;

            case MotionEvent.ACTION_UP:
                checkValidPlay(x, y);
                movingCardId = -1;
                break;
        }

        invalidate();

        return true;
    }

    private void checkValidPlay(int x, int y) {
        if (movingCardId > -1 && isPile(x, y)) {
            Card card = myHand.get(movingCardId);
            int rank = card.getRank(),
                suit = card.getSuit();
            if (rank == 8 || rank == validRank || suit == validSuit) {
                validRank = rank;
                validSuit = suit;
                discardPile.add(0, card);
                myHand.remove(movingCardId);
                if (validRank == 8)
                    showChooseSuitDialog();
            }
        }
    }

    private boolean isPile(int x, int y) {
        int offset = (int) (100 * scale),
                width = screenWidth / 2,
                height = screenHeight / 2;
        return x > width - offset &&
                x < width + offset &&
                y > height - offset &&
                y < height + offset;
    }

    private int offset(int i) {
        return (int) (i * scale);
    }

    private void setMovingIdIfMyTurn(int x, int y) {
        if (!myTurn) {
            return;
        }

        for (int i = 0; i < CARDS_TO_DEAL; ++i) {
            if (cardClicked(i, x, y)) {
                movingCardId = i;
                movingX = x - offset(30);
                movingY = y - offset(70);
            }
        }
    }

    private boolean cardClicked(int i, int x, int y) {
        int width = getCardLeft(i);
        return x > width &&
            x < (width + scaledCardWidth) &&
            y > getCardTop();
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

    private void showChooseSuitDialog() {
        final Dialog chooseSuit = new Dialog(context);
        chooseSuit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        chooseSuit.setContentView(R.layout.choose_suit_dialog);

        final Spinner suitSpinner = (Spinner) chooseSuit.findViewById(R.id.suitSpinner);

        int spinnerItem = android.R.layout.simple_spinner_item;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.suits, spinnerItem);

        int dropdownItem = android.R.layout.simple_spinner_dropdown_item;
        adapter.setDropDownViewResource(dropdownItem);

        suitSpinner.setAdapter(adapter);

        Button okButton = (Button) chooseSuit.findViewById(R.id.okButton);
        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View view) {
                validSuit = getSelectedSuit();
                chooseSuit.dismiss();
                Toast.makeText(context, "You chose " + getSuitText(), Toast.LENGTH_SHORT)
                        .show();
            }

            private int getSelectedSuit() {
                return (suitSpinner.getSelectedItemPosition() + 1) * 100;
            }
        };
        okButton.setOnClickListener(listener);
        chooseSuit.show();
    }

    private String getSuitText() {
        switch (validSuit) {
            case 100: return "Diamonds";
            case 200: return "Clubs";
            case 300: return "Hearts";
            case 400: return "Spades";
        }
        return "";
    }
}
