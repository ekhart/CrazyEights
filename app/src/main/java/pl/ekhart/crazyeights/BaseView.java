package pl.ekhart.crazyeights;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ekh on 2015-06-07.
 */
public class BaseView extends View {
    public BaseView(Context context) {
        super(context);
        setKeepScreenOn(true);
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

}
