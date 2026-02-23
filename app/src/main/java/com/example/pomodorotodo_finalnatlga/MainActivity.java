package com.example.pomodorotodo_finalnatlga;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView timerText;
    private Button startButton;
    private EditText taskInput;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 1500000; // 25 minutes
    private boolean timerRunning;

    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timerText);
        startButton = findViewById(R.id.startButton);
        Button resetButton = findViewById(R.id.resetButton);
        Button addTaskButton = findViewById(R.id.addTaskButton);
        taskInput = findViewById(R.id.taskInput);
        ListView taskListView = findViewById(R.id.taskListView);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);

        updateTimerText();

        startButton.setOnClickListener(v -> {
            if (timerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        resetButton.setOnClickListener(v -> resetTimer());

        addTaskButton.setOnClickListener(v -> {
            String task = taskInput.getText().toString().trim();
            if (!task.isEmpty()) {
                taskList.add(task);
                adapter.notifyDataSetChanged();
                taskInput.setText("");
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startButton.setText("▶");
            }
        }.start();

        timerRunning = true;
        startButton.setText("❚❚");
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startButton.setText("▶");
    }

    private void resetTimer() {
        timeLeftInMillis = 1500000;
        updateTimerText();
        startButton.setText("▶");
        timerRunning = false;
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),
                "%02d:%02d", minutes, seconds);

        timerText.setText(timeFormatted);
    }
}
