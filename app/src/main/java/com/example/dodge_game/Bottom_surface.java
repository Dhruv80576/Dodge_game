package com.example.dodge_game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Bottom_surface extends View {
    Paint bottompaint=new Paint();
    public Bottom_surface(Context context) {
        super(context);
        bottompaint.setColor((Color.BLACK));
        bottompaint.setStyle(Paint.Style.FILL);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(getLeft(),getHeight()-getHeight()/4,getRight(),getBottom(),bottompaint);
        super.onDraw(canvas);
    }

}
