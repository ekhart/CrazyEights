package pl.ekhart.crazyeights;

import android.graphics.Bitmap;

/**
 * Created by Ekh on 2015-06-07.
 */
public class Card {

    private int id;
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
    }
}
