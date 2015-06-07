package pl.ekhart.crazyeights;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ekh on 2015-06-06.
 */
public class TitleView extends View {

    private int screenWidth,
            screenHeight;

    private Bitmap titleGraphic,
        playButtonUp,
        playButtonDown;
    private int playButtonTop;
    private boolean playButtonPressed;

    private Context context;

    public TitleView(Context context) {
        super(context);
        this.context = context;

        titleGraphic = getBitmap(R.drawable.title_graphic);
        playButtonUp = getBitmap(R.drawable.play_button_up);
        playButtonDown = getBitmap(R.drawable.play_button_down);
    }

    private Bitmap getBitmap(int drawableId) {
        return BitmapFactory.decodeResource(getResources(), drawableId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        playButtonTop = (int) (screenHeight * .7);
        canvas.drawBitmap(titleGraphic, horizontalCenter(titleGraphic), 0, null);

        Bitmap playButton = playButtonPressed ? playButtonDown : playButtonUp;
        canvas.drawBitmap(playButton, horizontalCenter(playButton), playButtonTop, null);
    }

    private int horizontalCenter(Bitmap bitmap) {
        return (screenWidth - bitmap.getWidth()) / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                playButtonPressed = playButtonPressed(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                if (playButtonPressed) {
                    Intent gameIntent = new Intent(context, GameActivity.class);
                    context.startActivity(gameIntent);
                }
                playButtonPressed = false;
                break;
        }

        invalidate();

        return true;
    }

    private boolean playButtonPressed(int x, int y) {
        int center = horizontalCenter(playButtonUp);
        return x > center &&
            x < (center + playButtonUp.getWidth()) &&
            y > playButtonTop &&
            y < (playButtonTop + playButtonUp.getHeight());
    }
}
