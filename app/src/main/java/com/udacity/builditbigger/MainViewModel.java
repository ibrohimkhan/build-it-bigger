package com.udacity.builditbigger;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private CompositeDisposable disposable = new CompositeDisposable();
    MutableLiveData<String> joke = new MutableLiveData<>();

    public void getJoke() {
        disposable.add(
                JokeRepository.getJoke()
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::notifyUI, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
        joke.postValue("There is no joke!");
    }

    private void notifyUI(String data) {
        joke.postValue(data);
    }
}
