package ths.learnjp.katahira;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Time {

    public static Timer timer = new Timer();
    public static TimerTask timerTask;
    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    public static void startTime(TextView textView) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(() -> {
                    Global.startTimer = true;
                    textView.setText(getTimerText());
                    Global.time++;
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }

    public static void pauseTime() {
        Global.startTimer = false;
        timerTask.cancel();
    }

    public static String getTimerText() {
        int rounded = (int) Math.round(Global.time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
//        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes);
    }

    @SuppressLint("DefaultLocale")
    public static String formatTime(int seconds, int minutes) {
        return String.format("%02dm", minutes) + " : " + String.format("%02ds", seconds);
    }
}
