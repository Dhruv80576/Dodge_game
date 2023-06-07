package com.example.dodge_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;

public class Barrier_canvas extends View {
    int current = 0;
    int x = 20;
    int y;
    Paint obstacle_paint = new Paint();
    Paint background_paint = new Paint();
    int height;
    ArrayList<RectF> arrayList = new ArrayList<>();
    int x_coordinate;
    Context context;
    Drawable d;
    Bitmap bitmape;
    public Barrier_canvas(Context context, ArrayList<RectF> arrayList,int x_coordinate,int speed) {
        super(context);
        this.context=context;
        this.height = height;
        this.arrayList = arrayList;
        obstacle_paint.setColor(Color.RED);
        obstacle_paint.setStyle(Paint.Style.FILL);
        background_paint.setColor(Color.parseColor("#33363d"));
        background_paint.setStyle(Paint.Style.FILL);
        this.x_coordinate=x_coordinate;
        d = ContextCompat.getDrawable(context, R.drawable.tree_vector);
        if(speed==1){
            x=10;
        } else if (speed==2) {
            x=20;
        }
        else if(speed==3){
            x=30;
        }
        bitmape = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.background_ima);

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmape,getLeft(),getTop(),null);
        for (int i = 0; i < arrayList.size()-1; i++) {
                arrayList.get(i).set(arrayList.get(i).left - x, arrayList.get(i).top, arrayList.get(i).right - x, arrayList.get(i).bottom);
                canvas.drawBitmap(drawableToBitmap(d),null,arrayList.get(i),null);
        }
        if (arrayList.get(current).right<x_coordinate){
            current+=1;
        }
        invalidate();
    }
    public void update_current(){
        if (arrayList.get(current).right<x_coordinate){
            current+=1;
        }
    }
    public void set_current(int num){
        this.current=num;
    }

}
