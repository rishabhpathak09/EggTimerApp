package com.example.rishabh.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar time;
    TextView disp;
    CountDownTimer ctimer;
    public void updateTime(int progress){
        int minutes = (int)progress/60;
        int seconds = progress-minutes*60;

        String secString = Integer.toString(seconds);
        if (seconds<=9)
            secString = "0"+Integer.toString(seconds);

        disp.setText(Integer.toString(minutes)+":"+secString);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button go = (Button) findViewById(R.id.controller);
        time = (SeekBar)findViewById(R.id.timeSeek);
        disp = (TextView)findViewById(R.id.timeDisp);

        time.setMax(600); //10mins in seconds
        time.setProgress(30);

        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(go.getText()=="Stop"){
                    time.setEnabled(true);
                    ctimer.cancel();
                    disp.setText("0:30");
                    go.setText("GO!");
                    time.setProgress(30);
                }
                else {
                    ctimer = new CountDownTimer(time.getProgress() * 1000 + 100, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateTime((int) millisUntilFinished / 1000);
                            go.setText("Stop");
                            time.setEnabled(false);
                        }

                        @Override
                        public void onFinish() {
                            disp.setText("0:00");
                            time.setProgress(0);
                            MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                            mplayer.start();
                            go.setText("GO!");
                            time.setEnabled(true);
                        }
                    }.start();
                }

            }
        });
    }
}
