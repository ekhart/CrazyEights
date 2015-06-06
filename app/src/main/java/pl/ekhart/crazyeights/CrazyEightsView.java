package pl.ekhart.crazyeights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import android.view.View;

/**
 * Created by Ekh on 2015-06-06.
 */
public class CrazyEightsView extends View {

    private Paint redPaint;
    private Pair<Integer, Integer> circlePlace;
    private float radius;

    public CrazyEightsView(Context context) {
        super(context);

        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);

        circlePlace = new Pair<>(100, 100);
        radius = 30;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawCircle(circlePlace.first, circlePlace.second, radius, redPaint);
    }
}
