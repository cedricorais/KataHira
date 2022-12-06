package ths.learnjp.katahira.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import ths.learnjp.katahira.CharacterManager;
import ths.learnjp.katahira.FlashView;
import ths.learnjp.katahira.Generate;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Rng;
import ths.learnjp.katahira.Score;
import ths.learnjp.katahira.Time;
import ths.learnjp.katahira.Toasts;

public class GuessActivity extends AppCompatActivity {

    Button generateBtn, playBtn, choice1Btn, choice2Btn, choice3Btn, resetBtn, resultBtn;
    Spinner optionsSpin;
    TextView attemptValue, mistakesValue, scoreValue, timeValue, shownChar;
    TextToSpeech tts;
    boolean start = false;

    FlashView flashView  = new FlashView();
    Toasts toasts = new Toasts();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        attemptValue = findViewById(R.id.attemptsValue);
        mistakesValue = findViewById(R.id.mistakesValue);
        scoreValue = findViewById(R.id.scoreValue);
        timeValue = findViewById(R.id.timeValue);
        shownChar = findViewById(R.id.showChar);

        optionsSpin = findViewById(R.id.options);

        playBtn = findViewById(R.id.play);
        generateBtn = findViewById(R.id.generateChar);

        choice1Btn = findViewById(R.id.choice1);
        choice2Btn = findViewById(R.id.choice2);
        choice3Btn = findViewById(R.id.choice3);

        resetBtn = findViewById(R.id.reset);
        resultBtn = findViewById(R.id.result);

        String[] selected_option = CharacterManager.getCharaSetNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, selected_option) {
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
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
                Global.syllabary = selected_chara_set;
                flashView.startFlash(generateBtn);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        tts = new TextToSpeech(getApplicationContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.JAPANESE);
            }
        });

        generateBtn.setOnClickListener(view -> {
            flashView.stopFlash(generateBtn);
            startSession();
            if (!start) {
                toasts.showToast(this, "start", null);
                start = true;
            }

            String character = Generate.getCharacter();
            shownChar.setText(character);
            playBtn.setOnClickListener(view1 -> {
                Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
                tts.speak(character, TextToSpeech.QUEUE_ADD, null, null);
            });
            generateAnswer(character);
        });

        resetBtn.setOnClickListener(view -> {
            Time.pauseTime();
            flashView.startFlash(timeValue);
            showAlertDialog("reset");
        });

        resultBtn.setOnClickListener(view -> {
            showAlertDialog("result");
        });

        init();
    }

    @Override
    public void onBackPressed() {
        if (Global.startTimer) {
            Time.pauseTime();
            flashView.startFlash(timeValue);
        }
        showAlertDialog("back");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        menu.removeItem(R.id.settings);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.tutorial:
                Toast.makeText(this, "tutorial", Toast.LENGTH_SHORT).show(); // TODO show tutorial menu
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    public void init() {
        start = false;
        CharacterManager.resetSession();

        attemptValue.setText(R.string.zero);
        mistakesValue.setText(Integer.toString(Global.session_mistake));
        scoreValue.setText(Integer.toString(Global.session_score));
        timeValue.setText(Time.formatTime(0, 0));

        optionsSpin.setEnabled(true);
        optionsSpin.setSelection(0);
        shownChar.setText(R.string.default_character);
        playBtn.setEnabled(false);
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

        flashView.startFlash(generateBtn);
        flashView.stopFlash(resetBtn);
        resetBtn.setEnabled(false);
        resultBtn.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    public void startSession() {
        Time.timer = new Timer();
        if (!Global.startTimer) {
            Time.startTime(timeValue);
        }

        attemptValue.setText(Integer.toString(Global.session_attempts_left));
        flashView.stopFlash(timeValue);

        optionsSpin.setEnabled(false);
        playBtn.setEnabled(false);
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

    public void generateAnswer(String character_answer) {
        int rng = new Random().nextInt(3);

        Rng rand = new Rng(3); // TODO rng class
        System.out.println(rand.nextInt()); // TODO rng class test: only 0-1 displays not 2

        List<Button> choices = new ArrayList<>(Arrays.asList(choice1Btn, choice2Btn, choice3Btn));
        Button correct_choice = choices.get(rng);
        String correct_answer = CharacterManager.getAnswer(character_answer);
        correct_choice.setText(correct_answer);
        correct_choice.setBackgroundColor(Color.GREEN); // TODO debug: highlight answer
        Map temp_map = CharacterManager.tempRemoveCharFromCharaSet(character_answer);

        checkAnswer(correct_choice, character_answer);
        choices.remove(correct_choice);
        for (Button incorrect_choice : choices) {
            String mali = Generate.getCharacterNoWeight(temp_map);
            String mali_value = CharacterManager.getAnswer(mali);
            incorrect_choice.setText(mali_value);
            temp_map.remove(mali);
            checkAnswer(incorrect_choice, character_answer);
        }
    }

    public void checkAnswer(Button choiceButton, String answer_key) {
        choiceButton.setOnClickListener(view -> {
            Button btn = (Button) view;
            String getTxt = btn.getText().toString();
            String answer_value = CharacterManager.getAnswer(answer_key);
            if (getTxt.equals(answer_value)) {
                choiceButton.setBackgroundColor(Color.GREEN);
                correctAnswer(answer_key);
            } else {
                if (choice1Btn.getText().equals(answer_value)) {
                    choice1Btn.setBackgroundColor(Color.GREEN);
                } else if (choice2Btn.getText().equals(answer_value)) {
                    choice2Btn.setBackgroundColor(Color.GREEN);
                } else if (choice3Btn.getText().equals(answer_value)) {
                    choice3Btn.setBackgroundColor(Color.GREEN);
                }
                choiceButton.setBackgroundColor(Color.RED);
                wrongAnswer(answer_key);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void correctAnswer(String answer) {
        toasts.showToast(this, "correctAnswer", answer);

        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));
        Global.session_score++;
        scoreValue.setText(Integer.toString(Global.session_score));
        Score.addCharaScore(answer, 10);

        CharacterManager.removeCharFromSession(answer); // TODO duplicate char?
        afterAnswer();
    }

    @SuppressLint("SetTextI18n")
    public void wrongAnswer(String correct_answer) {
        toasts.showToast(this, "wrongAnswer", correct_answer);

        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));
        Global.session_mistake++;
        mistakesValue.setText(Integer.toString(Global.session_mistake));

        Global.wrongChars.add(correct_answer);

//        CharacterManager.removeCharFromSession(correct_answer); // TODO duplicate char?
        afterAnswer();
    }

    public void afterAnswer() {
        playBtn.setEnabled(true);
        generateBtn.setEnabled(true);
        flashView.startFlash(generateBtn);

        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);

        if (Global.session_attempts_left == 0) {
            start = false;
            Global.total_session++;
            Time.pauseTime();
            generateBtn.setEnabled(false);
            flashView.stopFlash(timeValue);
            flashView.stopFlash(generateBtn);
            resultBtn.setVisibility(View.VISIBLE);
            showAlertDialog("result");
        }
    }

    public void showAlertDialog(String tag) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        switch (tag) {
            case "back":
                alert.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            if (getFragmentManager().getBackStackEntryCount() > 0) {
                                getFragmentManager().popBackStack();
                                return;
                            }
                            super.onBackPressed();
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {
                            if (start) {
                                Global.startTimer = true;
                                Time.startTime(timeValue);
                            }
                            flashView.stopFlash(timeValue);
                            dialogInterface.cancel();
                        });
                break;
            case "reset":
                alert.setTitle(R.string.reset)
                        .setMessage("Are you sure you want to reset?")
                        .setCancelable(false)
                        .setPositiveButton(R.string.reset, (dialogInterface, i) -> {
                            toasts.showToast(this, "reset", null);
                            flashView.stopFlash(timeValue);
                            init();
                        })
                        .setNeutralButton(R.string.cancel, (dialogInterface, i) -> {
                            Global.startTimer = true;
                            Time.startTime(timeValue);
                            flashView.stopFlash(timeValue);
                            dialogInterface.cancel();
                        });
                break;
            case "result":
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                Date date = new Date();
                Global.dateTimeNow= formatter.format(date);
                Global.latestTime = (String) timeValue.getText();
                alert.setTitle(R.string.result)
                        .setMessage(String.format("Syllabary: %s\n%s: %s\n%s: %s\n%s: %s\nWrong Characters: %s", Global.syllabary, this.getString(R.string.mistakes), Global.session_mistake, this.getString(R.string.score), Global.session_score, this.getString(R.string.time), Global.latestTime, Global.wrongChars))
                        .setCancelable(false)
                        .setPositiveButton(R.string.close, (dialogInterface, i) -> {
                            flashView.startFlash(resetBtn);
                            toasts.showToast(this, "zeroAttempts", null);
                        });
                break;
        }
        alert.show();
    }
}