package com.example.ghostofart.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;

    public void resetTimer()
    {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);//seekbar has progress of 30 sec as before
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft)
    {
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;//take no of sec / 60 and round down. then get no of sec left over

        String secondString = Integer.toString(seconds);

        if (seconds <= 9)
        {
            secondString = "0" + secondString;
        }

        //update here
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString );
    }

    public void controlTimer(View view)
    {
        if (counterIsActive == false)
        {


            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop!!");//sets button as stop

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000)//delay carries on
            {

                @Override
                public void onTick(long millisUnilFinished) {
                    //on each tick, update value of timer
                    updateTimer((int) millisUnilFinished / 1000);
                }

                @Override
                public void onFinish()
                {
                    //update time timer textview to 0
                    resetTimer();
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.juicy);
                    mPlayer.start();
                }
            }.start();//dnt forget to start timer
        }
        else
        {
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.TimerSeekBar);
        timerTextView = (TextView)findViewById(R.id.TimerTextView);
        controllerButton = (Button) findViewById(R.id.ControllerButton);

        timerSeekBar.setMax(600);//slide to 10min
        timerSeekBar.setProgress(30);


        //timer updates
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }
}
