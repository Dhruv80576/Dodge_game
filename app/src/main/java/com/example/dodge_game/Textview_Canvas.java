package com.example.dodge_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

public class Textview_Canvas extends View {
    Paint text_paint = new Paint();
    Paint bc_paint=new Paint();

    public Textview_Canvas(Context context) {
        super(context);
        Typeface typeface= ResourcesCompat.getFont(context,R.font.font_main_textview);

        text_paint.setTypeface(typeface);
        text_paint.setTextSize(250);
        bc_paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("MOUNTAIN RUN",200,350, text_paint);
        invalidate();
    }
}
