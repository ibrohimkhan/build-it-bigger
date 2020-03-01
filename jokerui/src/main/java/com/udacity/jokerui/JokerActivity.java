package com.udacity.jokerui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class JokerActivity extends AppCompatActivity {

    public static final String JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joker);

        if (getIntent().hasExtra(JOKE)) {
            String joke = getIntent().getStringExtra(JOKE);

            if (TextUtils.isEmpty(joke)) return;

            TextView textView = findViewById(R.id.tv_joke);
            textView.setText(joke);
        }
    }
}
