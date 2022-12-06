package ths.learnjp.katahira;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class FlashView {

    public void startFlash(View view) {
        final Animation flash = new AlphaAnimation(1, 0);
        flash.setDuration(750);
        flash.setInterpolator(new LinearInterpolator());
        flash.setRepeatCount(Animation.INFINITE);
        flash.setRepeatMode(Animation.REVERSE);
        view.startAnimation(flash);
    }

    public void stopFlash(View view) {
        view.clearAnimation();
    }
}
