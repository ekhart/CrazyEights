package pl.ekhart.crazyeights;

import android.graphics.Bitmap;

/**
 * Created by Ekh on 2015-06-07.
 */
public class Card {

    private int id,
        suit,
        rank;
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
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }
}
