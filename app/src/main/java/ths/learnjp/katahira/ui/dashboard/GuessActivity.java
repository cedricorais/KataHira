package ths.learnjp.katahira.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import ths.learnjp.katahira.DBHelper;
import ths.learnjp.katahira.FlashView;
import ths.learnjp.katahira.Generate;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Score;
import ths.learnjp.katahira.SessionModel;
import ths.learnjp.katahira.Time;
import ths.learnjp.katahira.Toasts;

public class GuessActivity extends AppCompatActivity {

    Button generateBtn, playBtn, choice1Btn, choice2Btn, choice3Btn, resetBtn, resultBtn;
    Spinner optionsSpin;
    TextView attemptValue, mistakesValue, scoreValue, timeValue, shownChar;
    TextToSpeech tts;

    boolean sessionStart = false, sessionSaved = false;

    DBHelper dbHelper;
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

        dbHelper = new DBHelper(this);

        List<String> userData = new ArrayList<>(dbHelper.getProfileData(Global.selectedProfile));
//        List<SessionModel> allSession = new ArrayList<>(dbHelper.getAllSessions(Integer.parseInt(userData.get(0)))); // TODO old
        List<String> allSession = new ArrayList<>(dbHelper.getAllSessions(Integer.parseInt(userData.get(0)))); // TODO new
        if (allSession.isEmpty()) {
            showAlertDialog("tutorial", this);
        }

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
            if (!sessionStart) {
                toasts.showToast(this, "start", null);
                sessionStart = true;
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
            if (sessionStart) {
                Time.pauseTime();
                flashView.startFlash(timeValue);
            }
            showAlertDialog("reset", null);
        });
        resultBtn.setOnClickListener(view -> showAlertDialog("result", null));

        init();
    }

    @Override
    public void onBackPressed() {
        if (Global.startTimer) {
            Time.pauseTime();
            flashView.startFlash(timeValue);
        }
        showAlertDialog("back", null);
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
                if (Global.startTimer) {
                    Time.pauseTime();
                    flashView.startFlash(timeValue);
                }
                showAlertDialog("tutorial", this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        sessionStart = false;
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
        choice1Btn.setBackgroundColor(Color.parseColor("#FF018786"));
        choice1Btn.setTextColor(Color.WHITE);
        choice2Btn.setEnabled(false);
        choice2Btn.setText("...");
        choice2Btn.setBackgroundColor(Color.parseColor("#FF018786"));
        choice2Btn.setTextColor(Color.WHITE);
        choice3Btn.setEnabled(false);
        choice3Btn.setText("...");
        choice3Btn.setBackgroundColor(Color.parseColor("#FF018786"));
        choice3Btn.setTextColor(Color.WHITE);

        flashView.startFlash(generateBtn);
        flashView.stopFlash(resetBtn);
        resetBtn.setEnabled(false);
        resultBtn.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void startSession() {
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
        choice1Btn.setBackgroundColor(Color.parseColor("#FF018786"));
        choice1Btn.setTextColor(Color.WHITE);
        choice2Btn.setEnabled(true);
        choice2Btn.setText("...");
        choice2Btn.setBackgroundColor(Color.parseColor("#FF018786"));
        choice2Btn.setTextColor(Color.WHITE);
        choice3Btn.setEnabled(true);
        choice3Btn.setText("...");
        choice3Btn.setBackgroundColor(Color.parseColor("#FF018786"));
        choice3Btn.setTextColor(Color.WHITE);

        resetBtn.setEnabled(true);
    }

    private void generateAnswer(String character_answer) {
        int rng = new Random().nextInt(3); // TODO

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

    private void checkAnswer(Button choiceButton, String answer_key) {
        choiceButton.setOnClickListener(view -> {
            Button btn = (Button) view;
            String getText = btn.getText().toString();
            String answer_value = CharacterManager.getAnswer(answer_key);
            if (getText.equals(answer_value)) {
                choiceButton.setBackgroundColor(Color.GREEN);
                choiceButton.setTextColor(Color.BLACK);
                correctAnswer(answer_key);
            } else {
                if (choice1Btn.getText().equals(answer_value)) {
                    choice1Btn.setBackgroundColor(Color.GREEN);
                    choice1Btn.setTextColor(Color.BLACK);
                } else if (choice2Btn.getText().equals(answer_value)) {
                    choice2Btn.setBackgroundColor(Color.GREEN);
                    choice2Btn.setTextColor(Color.BLACK);
                } else if (choice3Btn.getText().equals(answer_value)) {
                    choice3Btn.setBackgroundColor(Color.GREEN);
                    choice3Btn.setTextColor(Color.BLACK);
                }
                choiceButton.setBackgroundColor(Color.RED);
                wrongAnswer(answer_key);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void correctAnswer(String answer) {
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
    private void wrongAnswer(String correct_answer) {
        toasts.showToast(this, "wrongAnswer", correct_answer);

        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));
        Global.session_mistake++;
        mistakesValue.setText(Integer.toString(Global.session_mistake));

        Global.wrongChars.add(correct_answer);

//        CharacterManager.removeCharFromSession(correct_answer); // TODO duplicate char?
        afterAnswer();
    }

    private void afterAnswer() {
        playBtn.setEnabled(true);
        generateBtn.setEnabled(true);
        flashView.startFlash(generateBtn);

        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);

        if (Global.session_attempts_left == 0) {
            sessionStart = false;
            Global.total_session++;
            Time.pauseTime();
            generateBtn.setEnabled(false);
            flashView.stopFlash(timeValue);
            flashView.stopFlash(generateBtn);
            resultBtn.setVisibility(View.VISIBLE);
            showAlertDialog("result", null);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void showAlertDialog(String tag, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        switch (tag) {
            case "back":
                if (sessionStart) {
                    alert.setMessage(R.string.exit_not_saved);
                } else {
                    alert.setMessage(R.string.exit);
                }
                alert.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
//                    DashboardFragment.lastActivity = true;
                    super.onBackPressed();
                });
                alert.setNegativeButton(R.string.no, (dialogInterface, i) -> {
                    if (sessionStart) {
                        Global.startTimer = true;
                        Time.startTime(timeValue);
                    }
                    flashView.stopFlash(timeValue);
                    dialogInterface.cancel();
                });
                alert.show();
                break;
            case "tutorial":
                LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.tutorial_layout, ((Activity)context).findViewById(R.id.main));

                ImageView img = view.findViewById(R.id.image);
                Integer[] pics = new Integer[]{R.drawable.guess1, R.drawable.guess2, R.drawable.guess3, R.drawable.guess4, R.drawable.guess5, R.drawable.guess6, R.drawable.guess7};
                List<Integer> tutorialPics = new ArrayList<>(Arrays.asList(pics));
                img.setImageResource(tutorialPics.get(0));

                TextView text = view.findViewById(R.id.text);
                String[] texts = new String[]{getString(R.string.guess1), getString(R.string.guess2), getString(R.string.guess3), getString(R.string.guess4), getString(R.string.guess5), getString(R.string.guess6), getString(R.string.guess7)};
                List<String> tutorialText = new ArrayList<>(Arrays.asList(texts));
                text.setText(tutorialText.get(0));

                alert.setTitle(R.string.tutorial_title);
                alert.setView(view);
                alert.setPositiveButton(R.string.next, null);
                alert.setNegativeButton(R.string.previous, null);
                alert.setNeutralButton(R.string.close, ((dialogInterface, i) -> {
                    if (sessionStart) {
                        Global.startTimer = true;
                        Time.startTime(timeValue);
                    }
                    flashView.stopFlash(timeValue);
                    dialogInterface.cancel();
                }));

                final int[] index = {0};
                final AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.GONE);
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    index[0]++;
                    img.setImageResource(tutorialPics.get(index[0]));
                    text.setText(tutorialText.get(index[0]));
                    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.VISIBLE);
                    if (index[0] == tutorialPics.size() - 1) {
                        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.GONE);
                    }
                });
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
                    index[0]--;
                    img.setImageResource(tutorialPics.get(index[0]));
                    text.setText(tutorialText.get(index[0]));
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
                    if (index[0] == 0) {
                        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.GONE);
                    }
                });
                break;
            case "reset":
                alert.setTitle(R.string.reset);
                alert.setMessage(R.string.reset_dialog);
                alert.setPositiveButton(R.string.reset, (dialogInterface, i) -> {
                    sessionSaved = false;
                    toasts.showToast(this, "reset", null);
                    flashView.stopFlash(timeValue);
                    init();
                });
                alert.setNeutralButton(R.string.cancel, (dialogInterface, i) -> {
                    if (sessionStart) {
                        Global.startTimer = true;
                        Time.startTime(timeValue);
                    }
                    flashView.stopFlash(timeValue);
                    dialogInterface.cancel();
                });
                alert.show();
                break;
            case "result":
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                Date date = new Date();
                Global.dateTimeNow = formatter.format(date);
                Global.latestTime = (String) timeValue.getText();

                alert.setTitle(R.string.result);
                alert.setMessage(String.format("Syllabary: %s\n%s: %s\n%s: %s\n%s: %s\nWrong Characters: %s", Global.syllabary, this.getString(R.string.mistakes), Global.session_mistake, this.getString(R.string.score), Global.session_score, this.getString(R.string.time), Global.latestTime, Global.wrongChars));
                if (!sessionSaved) {
                    alert.setPositiveButton(R.string.save_close, (dialogInterface, i) -> {
                        flashView.startFlash(resetBtn);
                        toasts.showToast(this, "zeroAttempts", null);

                        List<String> userData = new ArrayList<>(dbHelper.getProfileData(Global.selectedProfile));
                        int perfect_k = Integer.parseInt(userData.get(3));
                        int perfect_h = Integer.parseInt(userData.get(4));
                        if (Global.session_mistake == 0 && (Global.syllabary.equals("Katakana") || Global.syllabary.equals("10 chars kata"))) {
                            perfect_k++;
                            dbHelper.updateData("perfect_kata", Global.selectedProfile, String.valueOf(perfect_k));
                        } else if (Global.session_mistake == 0 && (Global.syllabary.equals("Hiragana") || Global.syllabary.equals("10 chars hira"))) {
                            perfect_h++;
                            dbHelper.updateData("perfect_hira", Global.selectedProfile, String.valueOf(perfect_h));
                        }

                        if (perfect_k >= 5 && perfect_h >= 5) {
                            dbHelper.updateData("rank", Global.selectedProfile, "Master");
                        } else if (perfect_k >= 3 && perfect_h >= 3) {
                            dbHelper.updateData("rank", Global.selectedProfile, "Expert");
                        } else if (perfect_k >= 1 && perfect_h >= 1) {
                            dbHelper.updateData("rank", Global.selectedProfile, "Intermediate");
                        } else {
                            dbHelper.updateData("rank", Global.selectedProfile, "Beginner");
                        }
                        SessionModel sessionModel = new SessionModel(-1, Global.dateTimeNow, Global.syllabary, Global.session_mistake, Global.session_score, Global.latestTime, Global.wrongChars.toString(), Integer.parseInt(userData.get(0)));
                        dbHelper.addOne("addSession", null, sessionModel);
                    });
                    sessionSaved = true;
                } else {
                    alert.setPositiveButton(R.string.close, null);
                }
                alert.show();
                break;
        }
    }
}