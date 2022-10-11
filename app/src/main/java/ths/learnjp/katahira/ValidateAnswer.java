package ths.learnjp.katahira;

import android.annotation.SuppressLint;

public class ValidateAnswer extends OldActivity {
    @SuppressLint("SetTextI18n")
    public void correctAnswer(int random) {
        globalVars.attempts--;
        attemptValue.setText(Integer.toString(globalVars.attempts));
        globalVars.score++;
        scoreValue.setText(Integer.toString(globalVars.score));

        if (optionsSpin.getSelectedItemPosition() == 1){
            globalVars.kataChars.remove(random);
        } else {
            globalVars.hiraChars.remove(random);
        }
        globalVars.romaChars.remove(random);

        generateBtn.setEnabled(true);
        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);
        kataBtn.setEnabled(true);
        hiraBtn.setEnabled(true);

        if (globalVars.attempts == 0) {
            generateBtn.setEnabled(false);
            zeroAttemptsToast();
        }
    }

    public void hi(int random) {
        System.out.println("test random:  " + random);
    }

}
