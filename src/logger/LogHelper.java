package com.example.isilu.notes.logger;

import android.util.Log;

public class LogHelper {
    private static final String TAG = "tag";

    public static void log(String message) {
        Log.d(TAG, message);
    }

    public static void log(Class clazz, String method) {
        Log.d(TAG, clazz.getSimpleName() + "::" + method + "()");
    }
}
