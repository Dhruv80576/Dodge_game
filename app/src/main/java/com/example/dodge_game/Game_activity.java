package com.example.dodge_game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Game_activity extends AppCompatActivity {
    int height = 0;
    int frame_rate;
    int temp = 0;
    ArrayList<RectF> arrayList = new ArrayList<>();
    LinearLayout bottom_view;
    LinearLayout barrier_view;
    int succs = 0;
    boolean condition = true;
    GameView gameView;
    Barrier_canvas barrier_canvas;
    SharedPreferences sharedPreferences;
    int speed;
    int mainscore=0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        speed = sharedPreferences.getInt("speed", 1);
        if (speed == 1) {
            frame_rate = 10;
        } else if (speed == 2) {
            frame_rate = 1;

        } else if (speed == 3) {
            frame_rate = 1;
            ;
        }
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        // Configure the behavior of the hidden system bars.
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Add a listener to update the behavior of the toggle fullscreen button when
        // the system bars are hidden or revealed.
        getWindow().getDecorView().setOnApplyWindowInsetsListener((view, windowInsets) -> {
            // You can hide the caption bar even when the other system bars are visible.
            // To account for this, explicitly check the visibility of navigationBars()
            // and statusBars() rather than checking the visibility of systemBars().
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                        || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())) {
                    // Hide both the status bar and the navigation bar.
                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
                }
            }
            return view.onApplyWindowInsets(windowInsets);
        });
        CoordinatorLayout linearLayout = findViewById(R.id.background_view);
        bottom_view = findViewById(R.id.bottom_view);
        barrier_view = findViewById(R.id.barrier_view);

        gameView = new GameView(Game_activity.this, speed);
        Bottom_surface bottom_surface = new Bottom_surface(Game_activity.this);
        linearLayout.addView(gameView);
        bottom_view.addView(bottom_surface);
        make_barrier();
        barrier_canvas = new Barrier_canvas(Game_activity.this, arrayList, gameView.coordinates_x, speed);
        barrier_view.addView(barrier_canvas);
        ScheduledExecutorService scheduleTaskExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {

                if (arrayList.get(barrier_canvas.current).left + 50 >= gameView.coordinates_x - 50 && arrayList.get(barrier_canvas.current).left <= gameView.coordinates_x + 50 && gameView.coordinate_y + 50 > arrayList.get(barrier_canvas.current).top) {
                    System.out.print("Success\n\n\n\n\n\n");
                    scheduleTaskExecutor.shutdownNow();
                    Game_activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            vibrate();
                            barrier_view.removeView(barrier_canvas);
                            AlertDialog.Builder builder = new AlertDialog.Builder(Game_activity.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(Game_activity.this).inflate(R.layout.result_dialog, viewGroup, false);
                            dialogView.findViewById(R.id.home_result).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Game_activity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                            int score=mainscore*500+barrier_canvas.current * 100;
                            TextView textView = (TextView) dialogView.findViewById(R.id.score_textview);
                            textView.setText("Your Score: " + score);
                            builder.setView(dialogView);
                            AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setCancelable(false);
                            dialog.show();
                        }
                    });
                } else if (barrier_canvas.current != temp) {
                    if (barrier_canvas.current == 5) {
                        make_barrier();
                        mainscore++;
                        barrier_canvas.set_current(0);
                        if (barrier_canvas.current!=temp){
                            mainscore++;
                            temp=barrier_canvas.current;
                        }
                        System.out.println("Succ\n\n\n\n\n");
                    }
                }
            }

        }, 0, frame_rate, TimeUnit.MILLISECONDS);
    }

    public void make_barrier() {
        arrayList.clear();
        int temp = 0;
        int a = 0;
        while (temp <= 5) {
            if (temp == 0) {

            } else {
                a += random();
            }
            RectF rect = new RectF(2500 + a, 700, 2600 + a, 800);
            arrayList.add(temp, rect);
            temp++;
        }
    }

    public int random() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int temp = random.nextInt(1200, 1800);
        return temp;
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }


}


