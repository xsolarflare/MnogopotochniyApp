package com.example.homeworkorwhateveritshouldbe;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    private static final String TAG = "MainActivity";
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d(TAG, "WIP");

            try {
                for (int i = 0; i < 3; i++) {
                    Log.d(TAG, "Doing smth");

                    Data inputData = getInputData();
                    String stringValue = inputData.getString("keyA");
                    int intValue = inputData.getInt("keyB", 0);
                    Log.d(TAG, "String " + stringValue);
                    Log.d(TAG, "Int " + intValue);

                    Thread.sleep(1 * 1000);

                }
           } catch (InterruptedException e) {
                Log.e(TAG, "Oh no " + e.getMessage());
                return Result.failure();
            }

           Data output = new Data.Builder()
                   .putString("key3", "ReturningData")
                   .build();

           Log.d(TAG, "Worker finished");
           return Result.success(output);
    }
}
