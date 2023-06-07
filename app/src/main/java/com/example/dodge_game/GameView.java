package com.example.dodge_game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class GameView extends View {

    // below we are creating variables for our paint
    Paint ball_paint;
    int x;
    int speed_real;
    int y;
    int tap = 0;
    // and a floating variable for our left arc.
    float arcLeft;
    int round = 0;
    int coordinates_x;
    int coordinate_y;
    Drawable d;


    @SuppressLint("ResourceAsColor")
    public GameView(Context context, int speed) {
        super(context);
        d = ContextCompat.getDrawable(context, R.drawable.jogging_man);
        ball_paint = new Paint();
        ball_paint.setColor(Color.parseColor("#964B00"));
        ball_paint.setStyle(Paint.Style.FILL);
        if (speed == 1) {
            speed_real=10;
        } else if (speed == 2) {
            speed_real=20;
        } else if (speed == 3) {
            speed_real=30;
        }
    }
    // below method is use to generate px from DP.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        coordinates_x = getWidth() / 2 - 200;
        coordinate_y = getBottom() - getHeight() / 4 - 50 - y;
        if (tap == 1) {
            if (y >= 0 && y <= 300 && round == 0) {
                y += speed_real;
                if (y == 300) {
                    round = 1;
                }
            }
            if (round == 1) {
                y -= speed_real;
            }
            if (round == 1 && y == 0) {
                round = 0;
                tap = 0;
            }
            canvas.drawCircle(coordinates_x, coordinate_y, 50, ball_paint);
        } else if (tap == 0) {
            canvas.drawCircle(coordinates_x, coordinate_y, 50, ball_paint);
        }
        invalidate();
    }

    public int getCoordinates_x() {
        return coordinates_x;
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public int getCoordinate_y() {
        return coordinate_y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tap = 1;
        return super.onTouchEvent(event);
    }
}

