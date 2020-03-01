package com.udacity.builditbigger;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import io.reactivex.Single;

public final class JokeRepository {

    private static MyApi apiService = null;

    private static final MyApi.Builder builder =
            new MyApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            )
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

    private JokeRepository() {}

    public static Single<String> getJoke() {
        return Single.fromCallable(() -> {
            if (apiService == null)
                apiService = builder.build();

            return apiService.tellJoke().execute().getData();
        });
    }
}
