package pl.ekhart.crazyeights;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ekh on 2015-06-06.
 */
public class TitleView extends View {

    private Bitmap titleGraphic;
    private int screenWidth, screenHeight;

    public TitleView(Context context) {
        super(context);

        titleGraphic = BitmapFactory.decodeResource(
                getResources(), R.drawable.title_graphic);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int horizontalCenter = (screenWidth - titleGraphic.getWidth()) / 2;
        canvas.drawBitmap(titleGraphic, horizontalCenter, 0, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
}
