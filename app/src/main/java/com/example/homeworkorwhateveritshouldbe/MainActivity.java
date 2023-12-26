package com.example.homeworkorwhateveritshouldbe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.homeworkorwhateveritshouldbe.databinding.ActivityMainBinding;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    private UUID id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Sleeping for 3 seconds");

                try {
                    Thread.sleep(3 * 1000);
                    Log.d(TAG, "End of sleeping");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.btnStartSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Data myData = new Data.Builder()
                        .putString("keyA", "value1")
                        .putInt("keyB", 10)
                        .build();

                OneTimeWorkRequest oneTimeWorkRequest =
                        new OneTimeWorkRequest
                                .Builder(MyWorker.class)
                                .setInputData(myData)
                                .build();

               id = oneTimeWorkRequest.getId();

                WorkManager
                        .getInstance(getApplicationContext())
                        .enqueue(oneTimeWorkRequest);
            }
        });

        binding.btnGetOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id != null) {
                    WorkManager
                            .getInstance(getApplicationContext())
                            .getWorkInfoByIdLiveData(id)
                            .observe(MainActivity.this, new Observer<WorkInfo>() {
                                @Override
                                public void onChanged(WorkInfo workInfo) {
                                    if (workInfo != null) {
                                        Log.d(TAG, "workInfo state " + workInfo.getState());
                                        String msg = workInfo.getOutputData().getString("key3");
                                        Log.d(TAG, "Result message " + msg);
                                    }
                                }
                            });
                }

            }
        });
    }
}