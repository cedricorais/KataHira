package ths.learnjp.katahira.ui.guess;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ths.learnjp.katahira.CharacterManager;
import ths.learnjp.katahira.Generate;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Score;
import ths.learnjp.katahira.Time;
import ths.learnjp.katahira.Toasts;
import ths.learnjp.katahira.databinding.FragmentGuessBinding;

public class GuessFragment extends Fragment {

    private FragmentGuessBinding binding;

    Button generateBtn, choice1Btn, choice2Btn, choice3Btn, resetBtn;
    Spinner optionsSpin;
    TextView scoreValue, attemptValue, timeValue, shownChar;
    Timer timer;
    TimerTask timerTask;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        GuessViewModel guessViewModel = new ViewModelProvider(this).get(GuessViewModel.class);

        binding = FragmentGuessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGuess;
//        guessViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Toasts toast = new Toasts();

        attemptValue = binding.attemptsValue;
        scoreValue = binding.scoreValue;
        timeValue = binding.timeValue;
        shownChar = binding.showChar;

        String[] selected_option = CharacterManager.getCharaSetNames();
        optionsSpin = binding.options;
//    final List<String> select = new ArrayList<>(Arrays.asList(selected_option));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, selected_option) {
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
                stopFlash(optionsSpin);
                startFlash(generateBtn);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        generateBtn = binding.generateChar;
        generateBtn.setOnClickListener(view -> {
            stopFlash(generateBtn);
            startSession();

            String character = Generate.getCharacter();
            shownChar.setText(character);
            generateAnswer(toast, character);
        });

        choice1Btn = binding.choice1;
        choice2Btn = binding.choice2;
        choice3Btn = binding.choice3;

        resetBtn = binding.reset;
        resetBtn.setOnClickListener(view -> {
            Time.pauseTime();

            AlertDialog.Builder resetAlert = new AlertDialog.Builder(getActivity());
            resetAlert.setTitle(R.string.reset);
            resetAlert.setMessage("Are you sure you want to reset?");
            resetAlert.setCancelable(false);
            resetAlert.setPositiveButton(R.string.reset, (dialogInterface, i) -> {
                init();
            });
            resetAlert.setNeutralButton(R.string.cancel, (dialogInterface, i) -> {
                Time.startTime(timeValue);
            });
            resetAlert.show();
        });

        init();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    public void init() {
        if (timerTask != null) {
            Time.pauseTime();
            Global.time = 0.0;
            timeValue.setText(Time.formatTime(0, 0));
        }

        CharacterManager.resetSession();

        scoreValue.setText(Integer.toString(Global.session_score));
        attemptValue.setText("0");

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

        stopFlash(resetBtn);
        resetBtn.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    public void startSession() {
        timer = new Timer();
        if (!Global.startTimer) {
//            toast.showToast(this.getActivity(), "start", 0);
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

    private void startTime(TextView textView) {
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
    private void pauseTime() {
        Global.startTimer = false;
        timerTask.cancel();
    }
    private String getTimerText() {
        int rounded = (int) Math.round(Global.time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
//        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes);
    }
    @SuppressLint("DefaultLocale")
    private String formatTime(int seconds, int minutes) {
        return String.format("%02dm", minutes) + " : " + String.format("%02ds", seconds);
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
        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));
        Global.session_score++;
        scoreValue.setText(Integer.toString(Global.session_score));

        Score.addCharaScore(answer, 1);

        CharacterManager.removeCharFromSession(answer); // TODO duplicate char
        afterAnswer(toast);
    }

    @SuppressLint("SetTextI18n")
    public void wrongAnswer(Toasts toast, String correct_answer) {
        toast.showToast(this.getActivity(), "wrongAnswer", correct_answer);

        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));

        Global.wrongChars.add(correct_answer); // TODO add kata/hira to wrongchar list

        CharacterManager.removeCharFromSession(correct_answer); // TODO duplicate char
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
            startFlash(resetBtn);
            toast.showToast(this.getActivity(), "zeroAttempts", null);

            AlertDialog.Builder resultAlert = new AlertDialog.Builder(getActivity());
            resultAlert.setTitle(R.string.result);
            resultAlert.setMessage(String.format("%s: %s\n%s: %s\nWrong Characters: %s", this.getString(R.string.score), Global.session_score, this.getString(R.string.time), timeValue.getText(), Global.wrongChars));
            resultAlert.setCancelable(false);
            resultAlert.setPositiveButton(R.string.close, (dialogInterface, i) -> {
                //
            });
            resultAlert.show();
        }
    }

}