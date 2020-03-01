package com.udacity.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.udacity.jokerui.JokerActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    // The Idling Resource which will be null in production.
    private CountingIdlingResource idlingResource = new CountingIdlingResource(TAG);

    private MainViewModel viewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pb_loader);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.joke.observe(this, joke -> {
            progressBar.setVisibility(View.GONE);

            Intent intent = new Intent(this, JokerActivity.class);
            intent.putExtra(JokerActivity.JOKE, joke);
            startActivity(intent);

            if (idlingResource != null)
                idlingResource.decrement();
        });
    }

    public void tellJoke(View view) {
        progressBar.setVisibility(View.VISIBLE);

        if (idlingResource != null)
            idlingResource.increment();

        viewModel.getJoke();
    }

    /**
     * Only called from test, creates and returns a new {@link CountingIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public CountingIdlingResource getIdlingResource() {
        return idlingResource;
    }
}
