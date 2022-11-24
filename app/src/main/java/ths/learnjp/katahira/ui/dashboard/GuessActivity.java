package ths.learnjp.katahira.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import ths.learnjp.katahira.CharacterManager;
import ths.learnjp.katahira.Generate;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Score;
import ths.learnjp.katahira.Time;
import ths.learnjp.katahira.Toasts;

public class GuessActivity extends AppCompatActivity {

    Button generateBtn, choice1Btn, choice2Btn, choice3Btn, resetBtn;
    Spinner optionsSpin;
    TextView attemptValue, mistakesValue, scoreValue, timeValue, shownChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        Toasts toast = new Toasts();

        attemptValue = findViewById(R.id.attemptsValue);
        mistakesValue = findViewById(R.id.mistakesValue);
        scoreValue = findViewById(R.id.scoreValue);
        timeValue = findViewById(R.id.timeValue);
        shownChar = findViewById(R.id.showChar);

        String[] selected_option = CharacterManager.getCharaSetNames();
        optionsSpin = findViewById(R.id.options);
//    final List<String> select = new ArrayList<>(Arrays.asList(selected_option));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, selected_option) {
            /*@Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    return false;
//                } else {
//                    return true;
//                }
                return position != 0;
            }*/
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView txtView = (TextView) view;
                /*if (position == 0) {
                    txtView.setTextColor(Color.GRAY);
                } else {
//                    getTheme();
                    int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    switch (currentNightMode) {
                        case Configuration.UI_MODE_NIGHT_NO:
                            // Night mode is not active, we're in day time
//                            System.out.println("light");
                            txtView.setTextColor(Color.BLACK); // TODO set text color
                            break;
                        case Configuration.UI_MODE_NIGHT_YES:
                            // Night mode is active, we're at night!
//                            System.out.println("dark");
                            txtView.setTextColor(Color.WHITE); // TODO set text color
                            break;
                        case Configuration.UI_MODE_NIGHT_UNDEFINED:
                            // We don't know what mode we're in, assume notnight
                            System.out.println("idk");
                            break;
                    }
                }*/
                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_item);
        optionsSpin.setAdapter(adapter);
        optionsSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_chara_set = (String) parent.getItemAtPosition(position);
                CharacterManager.setCharaSet(selected_chara_set);

                Score.initializeScore(); // TODO LOAD SCORE
//                if (Objects.equals(selected_chara_set, "Katakana")) {
//                    CharacterManager.setCharaSet("Katakana");
//                } else {
//                    CharacterManager.setCharaSet("Hiragana");
//                }
                startFlash(generateBtn);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        generateBtn = findViewById(R.id.generateChar);
        generateBtn.setOnClickListener(view -> {
            stopFlash(generateBtn);
            startSession(toast);

            String character = Generate.getCharacter();
            shownChar.setText(character);
            generateAnswer(toast, character);
        });

        choice1Btn = findViewById(R.id.choice1);
        choice2Btn = findViewById(R.id.choice2);
        choice3Btn = findViewById(R.id.choice3);

        resetBtn = findViewById(R.id.reset);
        resetBtn.setOnClickListener(view -> {
            Time.pauseTime();
            showAlertDialog("reset", null);
        });

        init();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog("back", null);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    public void init() {
        CharacterManager.resetSession();

        attemptValue.setText(R.string.zero);
        mistakesValue.setText(Integer.toString(Global.session_mistake));
        scoreValue.setText(Integer.toString(Global.session_score));
        timeValue.setText(Time.formatTime(0, 0));

        optionsSpin.setEnabled(true);
        optionsSpin.setSelection(0);
        shownChar.setText(R.string.default_character);
        generateBtn.setEnabled(true);

        choice1Btn.setEnabled(false);
        choice1Btn.setText("...");
        choice1Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));
        choice2Btn.setEnabled(false);
        choice2Btn.setText("...");
        choice2Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));
        choice3Btn.setEnabled(false);
        choice3Btn.setText("...");
        choice3Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));

        startFlash(generateBtn);
        stopFlash(resetBtn);
        resetBtn.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    public void startSession(Toasts toast) {
        Time.timer = new Timer();
        if (!Global.startTimer) {
            toast.showToast(this, "start", null);
            Time.startTime(timeValue);
        }

        attemptValue.setText(Integer.toString(Global.session_attempts_left));

        optionsSpin.setEnabled(false);
//        generateBtn.setEnabled(false); // TODO debug: enable genbtn

        choice1Btn.setEnabled(true);
        choice1Btn.setText("...");
        choice1Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));
        choice2Btn.setEnabled(true);
        choice2Btn.setText("...");
        choice2Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));
        choice3Btn.setEnabled(true);
        choice3Btn.setText("...");
        choice3Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));

        resetBtn.setEnabled(true);
    }

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
    public void showAlertDialog(String tag, Toasts toast) {
//        Time.pauseTime(); // TODO java.lang.NullPointerException: Attempt to invoke virtual method 'boolean java.util.TimerTask.cancel()' on a null object reference
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        switch (tag) {
            case "back":
                alert.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
//                            finish();
                            if (getFragmentManager().getBackStackEntryCount() > 0) {
                                getFragmentManager().popBackStack();
                                return;
                            }
                            super.onBackPressed();
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {
                            /*Global.startTimer = true; // TODO resume timer
                            Time.startTime(timeValue);*/
                        });
                break;
            case "reset":
                alert.setTitle(R.string.reset)
                        .setMessage("Are you sure you want to reset?")
                        .setCancelable(false)
                        .setPositiveButton(R.string.reset, (dialogInterface, i) -> {
//                            toast.showToast(this, "reset", null); // TODO wtf
                            init();
                        })
                        .setNeutralButton(R.string.cancel, (dialogInterface, i) -> {
                            Global.startTimer = true;
                            Time.startTime(timeValue);
                        });
                break;
            case "result":
                alert.setTitle(R.string.result)
                        .setMessage(String.format("%s: %s\n%s: %s\n%s: %s\nWrong Characters: %s", this.getString(R.string.mistakes), Global.session_mistake, this.getString(R.string.score), Global.session_score, this.getString(R.string.time), timeValue.getText(), Global.wrongChars))
                        .setCancelable(false)
                        .setPositiveButton(R.string.close, (dialogInterface, i) -> {
                            startFlash(resetBtn);
                            toast.showToast(this, "zeroAttempts", null);
                        });
                break;
        }
        alert.show();
    }

    public void generateAnswer(Toasts toast, String character_answer) {
        int rng = new Random().nextInt(3);

        List<Button> choices = new ArrayList<>(Arrays.asList(choice1Btn, choice2Btn, choice3Btn));
        Button correct_choice = choices.get(rng);
        String correct_answer = CharacterManager.getAnswer(character_answer);
        correct_choice.setText(correct_answer);
        correct_choice.setBackgroundColor(Color.GREEN); // TODO debug highlight answer
        Map temp_map = CharacterManager.tempRemoveCharFromCharaSet(character_answer);

        checkAnswer(toast, correct_choice, character_answer);
        choices.remove(correct_choice);
        for (Button incorrect_choice : choices) {
            String mali = Generate.getCharacterNoWeight(temp_map);
            String mali_value = CharacterManager.getAnswer(mali);
            incorrect_choice.setText(mali_value);
            temp_map.remove(mali);
            checkAnswer(toast, incorrect_choice, character_answer);
        }
    }

    public void checkAnswer(Toasts toast, Button choiceButton, String answer_key) {
        choiceButton.setOnClickListener(view -> {
            Button btn = (Button) view;
            String getTxt = btn.getText().toString();
            String answer_value = CharacterManager.getAnswer(answer_key);
            if (getTxt.equals(answer_value)) {
                choiceButton.setBackgroundColor(Color.GREEN);
                correctAnswer(toast, answer_key);
            } else {
                choiceButton.setBackgroundColor(Color.RED);
                wrongAnswer(toast, answer_key);
            }
        });
    }

    @SuppressLint("SetTextI18n") // TODO move process class
    public void correctAnswer(Toasts toast, String answer) {
        toast.showToast(this, "correctAnswer", answer);

        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));
        Global.session_score++;
        scoreValue.setText(Integer.toString(Global.session_score));
        Score.addCharaScore(answer, 10);

        CharacterManager.removeCharFromSession(answer); // TODO duplicate char
        afterAnswer(toast);
    }

    @SuppressLint("SetTextI18n")
    public void wrongAnswer(Toasts toast, String correct_answer) {
        toast.showToast(this, "wrongAnswer", correct_answer);

        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));
        Global.session_mistake++;
        mistakesValue.setText(Integer.toString(Global.session_mistake));

        Global.wrongChars.add(correct_answer); // TODO add kata/hira to wrongchar list

//        CharacterManager.removeCharFromSession(correct_answer); // TODO duplicate char
        afterAnswer(toast);
    }

    public void afterAnswer(Toasts toast) {
        generateBtn.setEnabled(true);
        startFlash(generateBtn);

        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);

        if (Global.session_attempts_left == 0) {
            Time.pauseTime();
            generateBtn.setEnabled(false);
            stopFlash(generateBtn);
            showAlertDialog("result", toast);
        }
    }
}