package com.example.iguest.hermes;

import com.parse.Parse;

/**
 * Created by bruceng on 3/10/16.
 */
public class parseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //This will only be called once in your app's entire lifecycle.
        Parse.initialize(this);
    }
}
