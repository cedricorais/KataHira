package ths.learnjp.katahira;

import android.annotation.SuppressLint;
import android.widget.Button;

public class ValidateAnswer extends MainActivity {
    @SuppressLint("SetTextI18n")
    public void correctAnswer(int random) {
        globalVar.attempts--;
        attemptValue.setText(Integer.toString(globalVar.attempts));
        globalVar.score++;
        scoreValue.setText(Integer.toString(globalVar.score));

        if(optionsSpin.getSelectedItemPosition() == 1){
            globalVar.kataChars.remove(random);
        } else {
            globalVar.hiraChars.remove(random);
        }
        globalVar.romaChars.remove(random);

        generateBtn.setEnabled(true);
        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);
        kataBtn.setEnabled(true);
        hiraBtn.setEnabled(true);

        if(globalVar.attempts == 0) {
            generateBtn.setEnabled(false);
            zeroAttemptsToast();
        }
    }

    public void hi(int random) {
        System.out.println("test random:  " + random);
    }

}
