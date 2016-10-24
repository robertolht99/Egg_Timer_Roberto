package com.example.robertoluishernandeztovar.egg_timer_roberto;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;

    TextView timerTextView;

    Boolean counterIsActive = false;

    Button controlButton;

    CountDownTimer countDownTimer;

    public void resetTimer() {
        timerTextView.setText("00:25");
        timerSeekBar.setProgress(25);
        countDownTimer.cancel();
        controlButton.setText("Go!");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }


    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = (int) secondsLeft % 60;

        String secondsString = Integer.toString(seconds);
        if (secondsString.length() < 2) {
            secondsString = "0" + secondsString;
        }

        String minutesString = Integer.toString(minutes);
        if (minutesString.length() < 2) {
            minutesString = "0" + minutesString;
        }

        timerTextView.setText(minutesString + ":" + secondsString);
    }

    public void controlTimer(View view) {

        if (!counterIsActive) {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controlButton.setText("Stop");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    timerTextView.setText("00:00");
                    resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();

                }
            }.start();

        } else {

            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controlButton = (Button)findViewById(R.id.controllerButton);

        timerSeekBar.setMax(600);

        timerSeekBar.setProgress(30);

        timerTextView.setEnabled(false);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
