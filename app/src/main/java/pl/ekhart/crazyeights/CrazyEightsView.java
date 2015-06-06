package pl.ekhart.crazyeights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ekh on 2015-06-06.
 */
public class CrazyEightsView extends View {

    private Paint redPaint;
    private int circleX, circleY;
    private float radius;

    public CrazyEightsView(Context context) {
        super(context);

        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);

        circleX = 100;
        circleY = 100;
        radius = 30;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(circleX, circleY, radius, redPaint);
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
                circleX = x;
                circleY = y;
                break;

            case MotionEvent.ACTION_UP:
                circleX = x;
                circleY = y;
                break;


        }

        invalidate();

        return true;
    }
}
