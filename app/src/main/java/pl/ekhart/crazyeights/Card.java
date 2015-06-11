package pl.ekhart.crazyeights;

import android.graphics.Bitmap;

/**
 * Created by Ekh on 2015-06-07.
 */
public class Card {

    private int id;
    private int suit;
    private int rank;
    private int scoreValue;
    private Bitmap bitmap;

    public int getId() {
        return id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Card(int id) {
        this.id = id;
        suit = Math.round((id / 100) * 100);
        rank = id - suit;
        scoreValue = getScoreValue(rank);
    }

    private int getScoreValue(int rank) {
        switch (rank) {
            case 8: return 50;
            case 14: return 1;
        }
        if (rank > 9 && rank < 14)
            return 10;
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
