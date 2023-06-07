package com.example.dodge_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Setting extends AppCompatActivity {
    TextView textView_1;
    TextView textView_2;
    TextView textView_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SharedPreferences sharedPreferences=getSharedPreferences("Settings",MODE_PRIVATE);
        int speed=sharedPreferences.getInt("speed",1);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        textView_1 = findViewById(R.id.textview_1x);
        textView_2 = findViewById(R.id.textview_2x);
        textView_3 = findViewById(R.id.textview_3x);
        if(speed==1){
            textView_1.setBackgroundResource(R.drawable.background_textview_grey);
        }
        else if(speed==2){
            textView_2.setBackgroundResource(R.drawable.background_textview_grey);

        } else if (speed==3) {
            textView_3.setBackgroundResource(R.drawable.background_textview_grey);
        }
        textView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_1.setBackgroundResource(R.drawable.background_textview_grey);
                textView_2.setBackgroundResource(R.drawable.background_textview);
                textView_3.setBackgroundResource(R.drawable.background_textview);
                editor.putInt("speed",1);
                editor.commit();
            }
        });
        textView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_1.setBackgroundResource(R.drawable.background_textview);
                textView_2.setBackgroundResource(R.drawable.background_textview_grey);
                textView_3.setBackgroundResource(R.drawable.background_textview);
                editor.putInt("speed",2);
                editor.commit();
            }
        });
        textView_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_1.setBackgroundResource(R.drawable.background_textview);
                textView_2.setBackgroundResource(R.drawable.background_textview);
                textView_3.setBackgroundResource(R.drawable.background_textview_grey);
                editor.putInt("speed",3);
                editor.commit();
            }
        });
    }
}