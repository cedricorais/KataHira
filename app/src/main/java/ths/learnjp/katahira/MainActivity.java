package ths.learnjp.katahira;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static class globalVar {
        public static String[] kata = {"ア", "力", "サ", "タ", "ナ"};
        public static String[] hira = {"あ", "か", "さ", "た", "な"};
        public static String[] roma = {"a", "ka", "sa", "ta", "na"};
//        public static String[] kata = {"ア", "力", "サ", "タ", "ナ", "ハ", "マ", "ヤ", "ラ", "ワ", "ガ", "ザ", "ダ", "バ", "パ",
//                                        "イ", "キ", "シ", "チ", "二", "ヒ", "ミ", "リ", "ギ", "ジ", "ヂ", "ビ", "ピ",
//                                        "ウ", "ク", "ス", "ツ", "ヌ", "フ", "ム", "ユ", "ル", "ン", "グ", "ズ", "ヅ", "ブ", "プ",
//                                        "エ", "ケ", "セ", "テ", "ネ", "へ", "メ", "レ", "ゲ", "ゼ", "デ", "べ", "ぺ",
//                                        "オ", "コ", "ソ", "ト", "ノ", "ホ", "モ", "ヨ", "ロ", "ヲ", "ゴ", "ゾ", "ド", "ボ", "ポ"};
//        public static String[] hira = {"あ", "か", "さ", "た", "な", "は", "ま", "や", "ら", "わ", "が", "ざ", "だ", "ば", "ぱ",
//                                        "い", "き", "し", "ち", "に", "ひ", "み", "り", "ぎ", "じ", "ぢ", "び", "ぴ",
//                                        "う", "く", "す", "つ", "ぬ", "ふ", "む", "ゆ", "る", "ん", "ぐ", "ず", "づ", "ぶ", "ぷ",
//                                        "え", "け", "せ", "て", "ね", "へ", "め", "れ", "げ", "ぜ", "で", "べ", "ぺ",
//                                        "お", "こ", "そ", "と", "の", "ほ", "も", "よ", "ろ", "を", "ご", "ぞ", "ど", "ぼ", "ぽ"};
//        public static String[] roma = {"a", "ka", "sa", "ta", "na", "ha", "ma", "ya", "ra", "wa", "ga", "za", "da", "ba", "pa",
//                                        "i", "ki", "shi", "chi", "ni", "hi", "mi", "ri", "gi", "ji", "dji", "bi", "pi",
//                                        "u", "ku", "su", "tsu", "nu", "fu", "mu", "yu", "ru", "n", "gu", "zu", "dzu", "bu", "pu",
//                                        "e", "ke", "se", "te", "ne", "he", "me", "re", "ge", "ze", "de", "be", "pe",
//                                        "o", "ko", "so", "to", "no", "ho", "mo", "yo", "ro", "wo", "go", "zo", "do", "bo", "po"};
        public static List<String> kataChars = new ArrayList<>(Arrays.asList(kata));
        public static List<String> hiraChars = new ArrayList<>(Arrays.asList(hira));
        public static List<String> romaChars = new ArrayList<>(Arrays.asList(roma));

        public static int attempts = romaChars.size(), score = 0;
    }

    Button generateBtn, choice1Btn, choice2Btn, choice3Btn, resetBtn, kataBtn, hiraBtn;
    Spinner optionsSpin;
    TextView scoreValue, attemptValue, shownChar;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch toggleTheme;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attemptValue = findViewById(R.id.attemptsValue);
        scoreValue = findViewById(R.id.scoreValue);

        String[] selected_option = new String[]{getString(R.string.option1), getString(R.string.option2), getString(R.string.option3)};
//    final List<String> select = new ArrayList<>(Arrays.asList(selected_option));
        optionsSpin = findViewById(R.id.options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, selected_option) {
            @Override
            public boolean isEnabled(int position) {
//                if(position == 0) {
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
                if(position == 0){
                    txtView.setTextColor(Color.GRAY);
                }
                else {
                    txtView.setTextColor(Color.GRAY);
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
//                if(position > 0) {
//                    // some code
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        shownChar = findViewById(R.id.showChar);

        generateBtn = findViewById(R.id.generateChar);
        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int random = new Random().nextInt(((globalVar.attempts - 1)) + 1);
                switch (optionsSpin.getSelectedItemPosition()) {
                    case 0:
                        noOptionToast();
                        break;
                    case 1:
                        startSession();
                        attemptValue.setText(Integer.toString(globalVar.attempts));
                        shownChar.setText(globalVar.kataChars.get(random));
                        generateAnswers(random);
                        break;
                    case 2:
                        startSession();
                        attemptValue.setText(Integer.toString(globalVar.attempts));
                        shownChar.setText(globalVar.hiraChars.get(random));
                        generateAnswers(random);
                        break;
                }
            }
        });

        choice1Btn = findViewById(R.id.choice1);
        choice2Btn = findViewById(R.id.choice2);
        choice3Btn = findViewById(R.id.choice3);

        resetBtn = findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] kata = {"ア", "力", "サ", "タ", "ナ"};
                String[] hira = {"あ", "か", "さ", "た", "な"};
                String[] roma = {"a", "ka", "sa", "ta", "na"};
//                String[] kata = {"ア", "力", "サ", "タ", "ナ", "ハ", "マ", "ヤ", "ラ", "ワ", "ガ", "ザ", "ダ", "バ", "パ",
//                                "イ", "キ", "シ", "チ", "二", "ヒ", "ミ", "リ", "ギ", "ジ", "ヂ", "ビ", "ピ",
//                                "ウ", "ク", "ス", "ツ", "ヌ", "フ", "ム", "ユ", "ル", "ン", "グ", "ズ", "ヅ", "ブ", "プ",
//                                "エ", "ケ", "セ", "テ", "ネ", "へ", "メ", "レ", "ゲ", "ゼ", "デ", "べ", "ぺ",
//                                "オ", "コ", "ソ", "ト", "ノ", "ホ", "モ", "ヨ", "ロ", "ヲ", "ゴ", "ゾ", "ド", "ボ", "ポ"};
//                String[] hira = {"あ", "か", "さ", "た", "な", "は", "ま", "や", "ら", "わ", "が", "ざ", "だ", "ば", "ぱ",
//                                "い", "き", "し", "ち", "に", "ひ", "み", "り", "ぎ", "じ", "ぢ", "び", "ぴ",
//                                "う", "く", "す", "つ", "ぬ", "ふ", "む", "ゆ", "る", "ん", "ぐ", "ず", "づ", "ぶ", "ぷ",
//                                "え", "け", "せ", "て", "ね", "へ", "め", "れ", "げ", "ぜ", "で", "べ", "ぺ",
//                                "お", "こ", "そ", "と", "の", "ほ", "も", "よ", "ろ", "を", "ご", "ぞ", "ど", "ぼ", "ぽ"};
//                String[] roma = {"a", "ka", "sa", "ta", "na", "ha", "ma", "ya", "ra", "wa", "ga", "za", "da", "ba", "pa",
//                                "i", "ki", "shi", "chi", "ni", "hi", "mi", "ri", "gi", "ji", "dji", "bi", "pi",
//                                "u", "ku", "su", "tsu", "nu", "fu", "mu", "yu", "ru", "n", "gu", "zu", "dzu", "bu", "pu",
//                                "e", "ke", "se", "te", "ne", "he", "me", "re", "ge", "ze", "de", "be", "pe",
//                                "o", "ko", "so", "to", "no", "ho", "mo", "yo", "ro", "wo", "go", "zo", "do", "bo", "po"};
                List<String> kataChars = new ArrayList<>(Arrays.asList(kata));
                List<String> hiraChars = new ArrayList<>(Arrays.asList(hira));
                List<String> romaChars = new ArrayList<>(Arrays.asList(roma));

                globalVar.kataChars = kataChars;
                globalVar.hiraChars = hiraChars;
                globalVar.romaChars = romaChars;
                globalVar.attempts = romaChars.size();
                globalVar.score = 0;

                attemptValue.setText("0");
                optionsSpin.setSelection(0);
                scoreValue.setText(Integer.toString(globalVar.score));

                choice1Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));
                choice2Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));
                choice3Btn.setBackgroundColor(Color.parseColor("#FF6200EE"));

                initialLaunch();
            }
        });

        kataBtn = findViewById(R.id.showKata);
        kataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KatakanaCharacters.class);
                startActivity(intent);
            }
        });

        hiraBtn = findViewById(R.id.showHira);
        hiraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HiraganaCharacters.class);
                startActivity(intent);
            }
        });

        toggleTheme = findViewById(R.id.toggle);
        toggleTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });

        Toasts toast = new Toasts();
//        toast.welcomeToast();
        welcomeToast();
        initialLaunch();

    }

    public void initialLaunch() {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        scoreValue.setText("0");
        attemptValue.setText("0");
        optionsSpin.setEnabled(true);
        shownChar.setText(R.string.default_char);
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
        resetBtn.setEnabled(false);
        kataBtn.setEnabled(true);
        hiraBtn.setEnabled(true);
    }

    public void startSession() {
        optionsSpin.setEnabled(false);
//        generateBtn.setEnabled(false);
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
        kataBtn.setEnabled(false);
        hiraBtn.setEnabled(false);
    }

    public void generateAnswers(int random) {
//        int rng = new Random().nextInt(((3 - 1) - 0) + 1) + 0;
        int rng = new Random().nextInt(1); //3
        if(rng == 0) {
            String[] roma1 = {"a", "ka", "sa", "ta", "na"};
            List<String> roma1Chars = new ArrayList<>(Arrays.asList(roma1));

            choice1Btn.setText(globalVar.romaChars.get(random));
            choice1Btn.setBackgroundColor(Color.GREEN);
            roma1Chars.remove(random);

            int mali1 = new Random().nextInt(roma1Chars.size());
            choice2Btn.setText(roma1Chars.get(mali1));
            roma1Chars.remove(mali1);

            int mali2 = new Random().nextInt(roma1Chars.size());
            choice3Btn.setText(roma1Chars.get(mali2));
            roma1Chars.remove(mali2);

            checkAnswer(random);
            System.out.println("rng: " + rng + " kata: " + globalVar.kataChars + " roma: " + globalVar.romaChars  + " roma1: " + roma1Chars + " mali1: " + mali1 + " mali2:  " + mali2);
        }
        else if(rng == 1) {
            String[] roma1 = {"a", "ka", "sa", "ta", "na"};
            List<String> roma1Chars = new ArrayList<>(Arrays.asList(roma1));

            choice2Btn.setText(globalVar.romaChars.get(random));
            choice2Btn.setBackgroundColor(Color.GREEN);
            roma1Chars.remove(random);

            int mali1 = new Random().nextInt(roma1Chars.size());
            choice1Btn.setText(roma1Chars.get(mali1));
            roma1Chars.remove(mali1);

            int mali2 = new Random().nextInt(roma1Chars.size());
            choice3Btn.setText(roma1Chars.get(mali2));
            roma1Chars.remove(mali2);

            checkAnswer(random);
            System.out.println("rng: " + rng + " kata: " + globalVar.kataChars + " roma: " + globalVar.romaChars  + " roma1: " + roma1Chars + " mali1: " + mali1 + " mali2:  " + mali2);
        }
        else if (rng == 2) {
            String[] roma1 = {"a", "ka", "sa", "ta", "na"};
            List<String> roma1Chars = new ArrayList<>(Arrays.asList(roma1));

            choice3Btn.setText(globalVar.romaChars.get(random));
            choice3Btn.setBackgroundColor(Color.GREEN);
            roma1Chars.remove(random);

            int mali1 = new Random().nextInt(roma1Chars.size());
            choice1Btn.setText(roma1Chars.get(mali1));
            roma1Chars.remove(mali1);

            int mali2 = new Random().nextInt(roma1Chars.size());
            choice2Btn.setText(roma1Chars.get(mali2));
            roma1Chars.remove(mali2);

            checkAnswer(random);
            System.out.println("rng: " + rng + " kata: " + globalVar.kataChars + " roma: " + globalVar.romaChars  + " roma1: " + roma1Chars + " mali1: " + mali1 + " mali2:  " + mali2);
        }
    }

    @SuppressLint("SetTextI18n")
    public void correctAnswer(int random) {
        correctAnswerToast();

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

    @SuppressLint("SetTextI18n")
    public void wrongAnswer(int random) {
        wrongAnswerToast(random);

        globalVar.attempts--;
        attemptValue.setText(Integer.toString(globalVar.attempts));

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

    public void checkAnswer(int random) {
        choice1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button)view;
                String getTxt = btn.getText().toString();
                if(getTxt.equals(globalVar.romaChars.get(random))){
                    choice1Btn.setBackgroundColor(Color.GREEN);
                    correctAnswer(random);
                }
                else {
                    choice1Btn.setBackgroundColor(Color.RED);
                    wrongAnswer(random);
                }
            }
        });

        choice2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button)view;
                String getTxt = btn.getText().toString();
                if(getTxt.equals(globalVar.romaChars.get(random))){
                    choice2Btn.setBackgroundColor(Color.GREEN);
                    correctAnswer(random);
                }
                else {
                    choice2Btn.setBackgroundColor(Color.RED);
                    wrongAnswer(random);
                }
            }
        });

        choice3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button)view;
                String getTxt = btn.getText().toString();
                if(getTxt.equals(globalVar.romaChars.get(random))){
                    choice3Btn.setBackgroundColor(Color.GREEN);
                    correctAnswer(random);
                }
                else {
                    choice3Btn.setBackgroundColor(Color.RED);
                    wrongAnswer(random);
                }
            }
        });
    }

    public void welcomeToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(R.string.welcome_toast);
        toastImage.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
    }

    public void noOptionToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(R.string.invalid_option_toast);
        toastImage.setImageResource(R.drawable.ic_baseline_error_24);
    }

    public void correctAnswerToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(R.string.correct_toast);
        toastImage.setImageResource(R.drawable.ic_baseline_done_24);
    }

    public void wrongAnswerToast(int random) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(String.format(getString(R.string.wrong_toast), globalVar.romaChars.get(random)));
        toastImage.setImageResource(R.drawable.ic_baseline_clear_24);
    }

    public void zeroAttemptsToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(R.string.zero_attempts_toast);
        toastImage.setImageResource(R.drawable.ic_baseline_error_24);
    }

}