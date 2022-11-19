package ths.learnjp.katahira.old;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Toasts;

public class OldActivity2 extends AppCompatActivity {

    public static class globalVars {
        public static String[] kata = {"ア", "力", "サ", "タ", "ナ", "ハ", "マ", "ヤ", "ラ", "ワ", "ガ", "ザ", "ダ", "バ", "パ",
                "イ", "キ", "シ", "チ", "二", "ヒ", "ミ", "リ", "ギ", "ジ", "ヂ", "ビ", "ピ",
                "ウ", "ク", "ス", "ツ", "ヌ", "フ", "ム", "ユ", "ル", "ン", "グ", "ズ", "ヅ", "ブ", "プ",
                "エ", "ケ", "セ", "テ", "ネ", "へ", "メ", "レ", "ゲ", "ゼ", "デ", "べ", "ぺ",
                "オ", "コ", "ソ", "ト", "ノ", "ホ", "モ", "ヨ", "ロ", "ヲ", "ゴ", "ゾ", "ド", "ボ", "ポ"};
        public static String[] hira = {"あ", "か", "さ", "た", "な", "は", "ま", "や", "ら", "わ", "が", "ざ", "だ", "ば", "ぱ",
                "い", "き", "し", "ち", "に", "ひ", "み", "り", "ぎ", "じ", "ぢ", "び", "ぴ",
                "う", "く", "す", "つ", "ぬ", "ふ", "む", "ゆ", "る", "ん", "ぐ", "ず", "づ", "ぶ", "ぷ",
                "え", "け", "せ", "て", "ね", "へ", "め", "れ", "げ", "ぜ", "で", "べ", "ぺ",
                "お", "こ", "そ", "と", "の", "ほ", "も", "よ", "ろ", "を", "ご", "ぞ", "ど", "ぼ", "ぽ"};
        public static String[] roma = {"a", "ka", "sa", "ta", "na", "ha", "ma", "ya", "ra", "wa", "ga", "za", "da", "ba", "pa",
                "i", "ki", "shi", "chi", "ni", "hi", "mi", "ri", "gi", "ji", "dji", "bi", "pi",
                "u", "ku", "su", "tsu", "nu", "fu", "mu", "yu", "ru", "n", "gu", "zu", "dzu", "bu", "pu",
                "e", "ke", "se", "te", "ne", "he", "me", "re", "ge", "ze", "de", "be", "pe",
                "o", "ko", "so", "to", "no", "ho", "mo", "yo", "ro", "wo", "go", "zo", "do", "bo", "po"};
        public static List<String> kataChars = new ArrayList<>(Arrays.asList(kata));
        public static List<String> hiraChars = new ArrayList<>(Arrays.asList(hira));
        public static List<String> romaChars = new ArrayList<>(Arrays.asList(roma));

        public static int attempts = romaChars.size(), score = 0;
        public static ArrayList<String> wrong = new ArrayList<String>();

        public static boolean charCheck; // TODO debug: check toggle
    }

    Button generateBtn, choice1Btn, choice2Btn, choice3Btn, resetBtn;
    Spinner optionsSpin;
    TextView attemptValue, scoreValue, timeValue, shownChar;
    Timer timer;
    TimerTask timerTask;
    @SuppressLint("UseSwitchCompatOrMaterialCode") // TODO debug: toggle 5 chars
    Switch toggleChars;

    boolean timerStart = false;
    double time = 0.0;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old2);

        Toasts toast = new Toasts();

        attemptValue = findViewById(R.id.attemptsValue);
        scoreValue = findViewById(R.id.scoreValue);
        timeValue = findViewById(R.id.timeValue);
        shownChar = findViewById(R.id.showChar);

        String[] selected_option = new String[]{getString(R.string.option1), getString(R.string.option2), getString(R.string.option3)};
        optionsSpin = findViewById(R.id.options);
//    final List<String> select = new ArrayList<>(Arrays.asList(selected_option));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, selected_option) {
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    return false;
//                } else {
//                    return true;
//                }
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView txtView = (TextView) view;
                if (position == 0) {
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
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_item);
        optionsSpin.setAdapter(adapter);
        optionsSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    flashStop(optionsSpin);
                    flashButton(generateBtn);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        generateBtn = findViewById(R.id.generateChar);
        generateBtn.setOnClickListener(view -> {
            flashStop(generateBtn);
            int random = new Random().nextInt(((globalVars.attempts - 1)) + 1);
//            int random = new Random().nextInt(globalVars.attempts);
            switch (optionsSpin.getSelectedItemPosition()) {
                case 0:
//                    toast.showToast(this.getActivity(), "noOption", 0);
                    break;
                case 1:
                    startSession(toast);
                    shownChar.setText(globalVars.kataChars.get(random));
                    generateAnswer(toast, random);
                    break;
                case 2:
                    startSession(toast);
                    shownChar.setText(globalVars.hiraChars.get(random));
                    generateAnswer(toast, random);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + optionsSpin.getSelectedItemPosition());
            }
        });

        choice1Btn = findViewById(R.id.choice1);
        choice2Btn = findViewById(R.id.choice2);
        choice3Btn = findViewById(R.id.choice3);

        resetBtn = findViewById(R.id.reset);
        resetBtn.setOnClickListener(view -> {
            timerStart = false;
            timerTask.cancel();

            AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
            resetAlert.setTitle(R.string.reset);
            resetAlert.setMessage("Are you sure you want to reset?");
            resetAlert.setCancelable(false);
            resetAlert.setPositiveButton(R.string.reset, (dialogInterface, i) -> {
//                toast.showToast(this.getActivity(), "reset", 0);
                reset();
                init();
                toggleChars.setChecked(false); // TODO debug: toggle 5 chars =====================
                toggleChars.setEnabled(true); // TODO debug: toggle 5 chars ======================
            });
            resetAlert.setNeutralButton(R.string.cancel, (dialogInterface, i) -> {
                timerStart = true;
                startTime();
            });
            resetAlert.show();
        });

        toggleChars = findViewById(R.id.test); // TODO debug: toggle 5 chars ================================
        toggleChars.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                String[] kata = {"ア", "力", "サ", "タ", "ナ"};
                String[] hira = {"あ", "か", "さ", "た", "な"};
                String[] roma = {"a", "ka", "sa", "ta", "na"};
                List<String> kataChars = new ArrayList<>(Arrays.asList(kata));
                List<String> hiraChars = new ArrayList<>(Arrays.asList(hira));
                List<String> romaChars = new ArrayList<>(Arrays.asList(roma));

                globalVars.kataChars = kataChars;
                globalVars.hiraChars = hiraChars;
                globalVars.romaChars = romaChars;
                globalVars.attempts = romaChars.size();

                globalVars.charCheck = true;
            } else {
                reset();
                globalVars.charCheck = false;
            }
        });

        init();
    }

    @SuppressLint("SetTextI18n")
    public void init() {
        if (timerTask != null) {
            timerTask.cancel();
            time = 0.0;
            timerStart = false;
            timeValue.setText(formatTime(0,0));
        }

        attemptValue.setText(R.string.zero);
        scoreValue.setText(Integer.toString(globalVars.score));
        timeValue.setText(R.string.initTime);

        shownChar.setText(R.string.default_character);
        optionsSpin.setEnabled(true);
        optionsSpin.setSelection(0);
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

        flashButton(optionsSpin);
        flashStop(resetBtn);
        resetBtn.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    public void startSession(Toasts toast) {
        timer = new Timer();
        if (!timerStart) {
//            toast.showToast(this.getActivity(), "start", 0);
            timerStart = true;
            startTime();
        }

        attemptValue.setText(Integer.toString(globalVars.attempts));
        optionsSpin.setEnabled(false);
//        generateBtn.setEnabled(false); // TODO debug: enable genbtn ================================

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
        toggleChars.setEnabled(false); // TODO debug: toggle 5 chars =============================
    }

    private void startTime() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(() -> {
                    timeValue.setText(getTimerText());
                    time++;
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }
    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
//        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes);
    }
    @SuppressLint("DefaultLocale")
    private String formatTime(int seconds, int minutes) {
        return String.format("%02dm", minutes) + " : " + String.format("%02ds", seconds);
    }

    public void flashButton(View view) {
        final Animation flash = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        flash.setDuration(500); // duration - half a second
        flash.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        flash.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        flash.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        view.startAnimation(flash);
    }
    public void flashStop(View view) {
        view.clearAnimation();
    }

    public void reset() {
        String[] kata = {"ア", "力", "サ", "タ", "ナ", "ハ", "マ", "ヤ", "ラ", "ワ", "ガ", "ザ", "ダ", "バ", "パ",
                "イ", "キ", "シ", "チ", "二", "ヒ", "ミ", "リ", "ギ", "ジ", "ヂ", "ビ", "ピ",
                "ウ", "ク", "ス", "ツ", "ヌ", "フ", "ム", "ユ", "ル", "ン", "グ", "ズ", "ヅ", "ブ", "プ",
                "エ", "ケ", "セ", "テ", "ネ", "へ", "メ", "レ", "ゲ", "ゼ", "デ", "べ", "ぺ",
                "オ", "コ", "ソ", "ト", "ノ", "ホ", "モ", "ヨ", "ロ", "ヲ", "ゴ", "ゾ", "ド", "ボ", "ポ"};
        String[] hira = {"あ", "か", "さ", "た", "な", "は", "ま", "や", "ら", "わ", "が", "ざ", "だ", "ば", "ぱ",
                "い", "き", "し", "ち", "に", "ひ", "み", "り", "ぎ", "じ", "ぢ", "び", "ぴ",
                "う", "く", "す", "つ", "ぬ", "ふ", "む", "ゆ", "る", "ん", "ぐ", "ず", "づ", "ぶ", "ぷ",
                "え", "け", "せ", "て", "ね", "へ", "め", "れ", "げ", "ぜ", "で", "べ", "ぺ",
                "お", "こ", "そ", "と", "の", "ほ", "も", "よ", "ろ", "を", "ご", "ぞ", "ど", "ぼ", "ぽ"};
        String[] roma = {"a", "ka", "sa", "ta", "na", "ha", "ma", "ya", "ra", "wa", "ga", "za", "da", "ba", "pa",
                "i", "ki", "shi", "chi", "ni", "hi", "mi", "ri", "gi", "ji", "dji", "bi", "pi",
                "u", "ku", "su", "tsu", "nu", "fu", "mu", "yu", "ru", "n", "gu", "zu", "dzu", "bu", "pu",
                "e", "ke", "se", "te", "ne", "he", "me", "re", "ge", "ze", "de", "be", "pe",
                "o", "ko", "so", "to", "no", "ho", "mo", "yo", "ro", "wo", "go", "zo", "do", "bo", "po"};
        List<String> kataChars = new ArrayList<>(Arrays.asList(kata));
        List<String> hiraChars = new ArrayList<>(Arrays.asList(hira));
        List<String> romaChars = new ArrayList<>(Arrays.asList(roma));

        globalVars.kataChars = kataChars;
        globalVars.hiraChars = hiraChars;
        globalVars.romaChars = romaChars;
        globalVars.attempts = romaChars.size();
        globalVars.score = 0;
        globalVars.wrong.removeAll(globalVars.wrong);
    }

    public List<String> roma1five() { // TODO debug: toggle 5 chars
        String[] roma1 = {"a", "ka", "sa", "ta", "na"};
        return new ArrayList<>(Arrays.asList(roma1));
    }
    public List<String> roma1all() {
        String[] roma1 = {"a", "ka", "sa", "ta", "na", "ha", "ma", "ya", "ra", "wa", "ga", "za", "da", "ba", "pa",
                "i", "ki", "shi", "chi", "ni", "hi", "mi", "ri", "gi", "ji", "dji", "bi", "pi",
                "u", "ku", "su", "tsu", "nu", "fu", "mu", "yu", "ru", "n", "gu", "zu", "dzu", "bu", "pu",
                "e", "ke", "se", "te", "ne", "he", "me", "re", "ge", "ze", "de", "be", "pe",
                "o", "ko", "so", "to", "no", "ho", "mo", "yo", "ro", "wo", "go", "zo", "do", "bo", "po"};
        return new ArrayList<>(Arrays.asList(roma1));
    }
    public void generateAnswer(Toasts toast, int random) {
        int rng = new Random().nextInt(3); //3 or int rng = new Random().nextInt(((3 - 1) - 0) + 1) + 0;
        switch (rng) {
            case 0:
                if (globalVars.charCheck) { // TODO debug: toggle 5 chars
                    choice1(toast, random, roma1five());
                } else {
                    choice1(toast, random, roma1all());
                }
                break;
            case 1:
                if (globalVars.charCheck) { // TODO debug: toggle 5 chars
                    choice2(toast, random, roma1five());
                } else {
                    choice2(toast, random, roma1all());
                }
                break;
            case 2:
                if (globalVars.charCheck) { // TODO debug: toggle 5 chars
                    choice3(toast, random, roma1five());
                } else {
                    choice3(toast, random, roma1all());
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rng);
        }
    }

    public void choice1(Toasts toast, int random, @NonNull List<String> roma1Chars) {
        choice1Btn.setText(globalVars.romaChars.get(random));
        choice1Btn.setBackgroundColor(Color.GREEN); // TODO debug: show correct answer ===========
        roma1Chars.remove(random);

        int mali1 = new Random().nextInt(roma1Chars.size());
        choice2Btn.setText(roma1Chars.get(mali1));
        roma1Chars.remove(mali1);

        int mali2 = new Random().nextInt(roma1Chars.size());
        choice3Btn.setText(roma1Chars.get(mali2));
        roma1Chars.remove(mali2);

        checkAnswer(toast, random);
    }
    public void choice2(Toasts toast, int random, @NonNull List<String> roma1Chars) {
        choice2Btn.setText(globalVars.romaChars.get(random));
        choice2Btn.setBackgroundColor(Color.GREEN); // TODO debug: show correct answer ===========
        roma1Chars.remove(random);

        int mali1 = new Random().nextInt(roma1Chars.size()); // TODO rename var
        choice1Btn.setText(roma1Chars.get(mali1));
        roma1Chars.remove(mali1);

        int mali2 = new Random().nextInt(roma1Chars.size()); // TODO rename var
        choice3Btn.setText(roma1Chars.get(mali2));
        roma1Chars.remove(mali2);

        checkAnswer(toast, random);
    }
    public void choice3(Toasts toast, int random, @NonNull List<String> roma1Chars) {
        choice3Btn.setText(globalVars.romaChars.get(random));
        choice3Btn.setBackgroundColor(Color.GREEN); // TODO debug: show correct answer ===========
        roma1Chars.remove(random);

        int mali1 = new Random().nextInt(roma1Chars.size()); // TODO rename var
        choice1Btn.setText(roma1Chars.get(mali1));
        roma1Chars.remove(mali1);

        int mali2 = new Random().nextInt(roma1Chars.size()); // TODO rename var
        choice2Btn.setText(roma1Chars.get(mali2));
        roma1Chars.remove(mali2);

        checkAnswer(toast, random);
    }

    public void checkAnswer(Toasts toast, int random) { // TODO move process class
        choice1Btn.setOnClickListener(view -> {
            Button btn = (Button)view;
            String getTxt = btn.getText().toString();
            if (getTxt.equals(globalVars.romaChars.get(random))) {
                choice1Btn.setBackgroundColor(Color.GREEN);
                correctAnswer(toast, random);
            } else {
                choice1Btn.setBackgroundColor(Color.RED);
                wrongAnswer(toast, random);
            }
        });
        choice2Btn.setOnClickListener(view -> {
            Button btn = (Button)view;
            String getTxt = btn.getText().toString();
            if (getTxt.equals(globalVars.romaChars.get(random))) {
                choice2Btn.setBackgroundColor(Color.GREEN);
                correctAnswer(toast, random);
            } else {
                choice2Btn.setBackgroundColor(Color.RED);
                wrongAnswer(toast, random);
            }
        });
        choice3Btn.setOnClickListener(view -> {
            Button btn = (Button)view;
            String getTxt = btn.getText().toString();
            if (getTxt.equals(globalVars.romaChars.get(random))) {
                choice3Btn.setBackgroundColor(Color.GREEN);
                correctAnswer(toast, random);
            } else {
                choice3Btn.setBackgroundColor(Color.RED);
                wrongAnswer(toast, random);
            }
        });
    }
    @SuppressLint("SetTextI18n") // TODO move process class
    public void correctAnswer(Toasts toast, int random) {
//        toast.showToast(this.getActivity(), "correctAnswer", random);

        globalVars.attempts--;
        attemptValue.setText(Integer.toString(globalVars.attempts));
        globalVars.score++;
        scoreValue.setText(Integer.toString(globalVars.score));

        if (optionsSpin.getSelectedItemPosition() == 1) {
            globalVars.kataChars.remove(random);
        } else {
            globalVars.hiraChars.remove(random);
        }
        globalVars.romaChars.remove(random);

        generateBtn.setEnabled(true);
        flashButton(generateBtn);
        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);

//        for (String find : globalVars.wrong) {
//            if (find.matches(globalVars.kataChars.get(random))) {
//                globalVars.wrong.remove(find);
//            }
//        }
//        System.out.println(globalVars.wrong);

        zeroAttempts(toast);
    }
    @SuppressLint("SetTextI18n") // TODO move process class
    public void wrongAnswer(Toasts toast, int random) {
//        toast.showToast(this.getActivity(), "wrongAnswer", random);

        globalVars.attempts--;
        attemptValue.setText(Integer.toString(globalVars.attempts));

        if (optionsSpin.getSelectedItemPosition() == 1) {
            globalVars.wrong.add(globalVars.kataChars.get(random));
        } else {
            globalVars.wrong.add(globalVars.hiraChars.get(random));
        }

        generateBtn.setEnabled(true);
        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);

        zeroAttempts(toast);
    }
    public void zeroAttempts(Toasts toast) {
        if (globalVars.attempts == 0) {
//            toast.showToast(this.getActivity(), "zeroAttempts", 0);

            timerStart = false;
            timerTask.cancel();

            generateBtn.setEnabled(false);
            flashStop(generateBtn);
            flashButton(resetBtn);

            AlertDialog.Builder resultAlert = new AlertDialog.Builder(this);
            resultAlert.setTitle(R.string.result);
            resultAlert.setMessage(String.format("%s: %s\n%s: %s\nWrong Characters: %s", this.getString(R.string.score), globalVars.score, this.getString(R.string.time), timeValue.getText(), globalVars.wrong));
            resultAlert.setCancelable(false);
            resultAlert.setPositiveButton(R.string.close, (dialogInterface, i) -> {
                //
            });
            resultAlert.show();
        }
    }
}