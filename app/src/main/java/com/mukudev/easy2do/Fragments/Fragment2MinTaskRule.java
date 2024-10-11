package com.mukudev.easy2do.Fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mukudev.easy2do.R;

public class Fragment2MinTaskRule extends Fragment {
    private TextView timerTextView;
    private Button startTimerButton;

    //boolean to check if button is clicked
    private boolean isClicked = false;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2min_task_rule, container, false);

        timerTextView = view.findViewById(R.id.timerTextView);
        startTimerButton = view.findViewById(R.id.startTimerButton);

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //animation for button click
            public void onClick(View v) {
                startTimerButton.setScaleX(0.9f);
                startTimerButton.setScaleY(0.9f);

                // Restore the button size after a short delay
                startTimerButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTimerButton.setScaleX(1f);
                        startTimerButton.setScaleY(1f);
                    }
                }, 100);//animation for button click
                //check if the button is clicked and then only call startTimer
                if(!isClicked) startPreparationTimer();
            }
        });

        return view;
    }


    private void startPreparationTimer() {
        //on start set to true to avoid flickering of timer when button is clicked while it is running
        isClicked = true;
        // Set text to inform the user to get ready
        timerTextView.setText("Get ready in 5 seconds...");
        timerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

        isClicked = true; // Mark that the button has been clicked

        // Create a CountDownTimer for 5 seconds (5000 milliseconds)
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the text to show the remaining time
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                timerTextView.setText("Get ready in " + secondsRemaining + " seconds...");
            }

            @Override
            public void onFinish() {
                // After preparation time, start the main task timer
                startTimer();
            }
        }.start();
    }
    private void startTimer() {
        // Reset the timer text to 2 minutes
        timerTextView.setText("02:00");
        timerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,64);

        // Create a CountDownTimer for 2 minutes (120000 milliseconds)
        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer text every second
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                String timeFormatted = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60);
                timerTextView.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                // Timer finished, play sound
                timerTextView.setText("Time's up!");
                timerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
                playSound();
                startTimerButton.setText("Restart!");
                //on finish set to false so that the timer can start again on clicking button
                isClicked = false;
            }
        }.start();
    }

    private void playSound() {
        // Load the sound from resources (make sure to have a sound file in res/raw)

        //Sound Effect by UNIVERSFIELD from Pixabay
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.timer_end);
        mediaPlayer.start();

        // Release the media player resources after playback
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
        });
    }
}