package com.learnjp.katahira;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static class Global {
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
    }

    Button choice1Btn, choice2Btn, choice3Btn, kataBtn, hiraBtn, generateBtn, resetBtn, submitBtn;
    EditText answerVal;
    Spinner optionsSpin;
    TextView scoreVal, attemptsVal, shownChar;

    String[] selected_option = new String[]{"Select Option", "Katakana to Romaji", "Hiragana to Romaji"};
    final List<String> select = new ArrayList<>(Arrays.asList(selected_option));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choice1Btn = findViewById(R.id.choice1);
        choice2Btn = findViewById(R.id.choice2);
        choice3Btn = findViewById(R.id.choice3);
        kataBtn = findViewById(R.id.showKata);
        hiraBtn = findViewById(R.id.showHira);
        generateBtn = findViewById(R.id.generateChar);
        resetBtn = findViewById(R.id.reset);
        submitBtn = findViewById(R.id.submitChar);

        answerVal = findViewById(R.id.answerEdt);

        optionsSpin = findViewById(R.id.options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, selected_option) {
            @Override
            public boolean isEnabled(int position) {
                if(position == 0) {
                    return  false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView txtView = (TextView) view;
                if(position == 0){
                    txtView.setTextColor(Color.GRAY);
                }
                else {
                    txtView.setTextColor(Color.BLACK);
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
                if(position > 0) {
                    // some code
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        attemptsVal = (TextView) findViewById(R.id.attemptsValTxt);
        scoreVal = (TextView) findViewById(R.id.scoreValTxt);
        shownChar = (TextView) findViewById(R.id.showChar);

        welcomeToast();

        initialLaunch();

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionsSpin.getSelectedItemPosition() == 0) {
                    nooptionToast();
//                    zeroAttemptsToast(); // debug purpose
                }
                else if(optionsSpin.getSelectedItemPosition() == 1) {
                    startSession();

                    int random = new Random().nextInt(((Global.attempts - 1)) + 1);
                    attemptsVal.setText(Integer.toString(Global.attempts));
                    shownChar.setText(Global.kataChars.get(random));
                    answerVal.setText(Global.romaChars.get(random)); // debug purpose

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(answerVal.getText().toString().isEmpty()) {
                                emptyAnsToast();
                            }
                            else if(answerVal.getText().toString().toLowerCase(Locale.ROOT).equals(Global.romaChars.get(random))) {
                                correctAnsToast();

                                Global.attempts--;
                                attemptsVal.setText(Integer.toString(Global.attempts));
                                Global.score++;
                                scoreVal.setText(Integer.toString(Global.score));
                                Global.kataChars.remove(random);
                                Global.romaChars.remove(random);

                                answerVal.setEnabled(false);
                                generateBtn.setEnabled(true);
                                kataBtn.setEnabled(true);
                                hiraBtn.setEnabled(true);
                                submitBtn.setEnabled(false);
                            }
                            else {
                                Global.attempts--;
                                attemptsVal.setText(Integer.toString(Global.attempts));

                                answerVal.setEnabled(false);
                                generateBtn.setEnabled(true);
                                kataBtn.setEnabled(true);
                                hiraBtn.setEnabled(true);
                                submitBtn.setEnabled(false);

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));

                                TextView toastText = layout.findViewById(R.id.toast_text);
                                ImageView toastImage = layout.findViewById(R.id.toast_image);

                                toastText.setText(String.format("Wrong. Answer should be '%s'", Global.romaChars.get(random)));
                                toastImage.setImageResource(R.drawable.ic_baseline_clear_24);

                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.BOTTOM, 0, 65);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
                            }
                            if(Global.attempts == 0) {
                                generateBtn.setEnabled(false);
                                zeroAttemptsToast();
                            }
                        }
                    });
                }
                else if(optionsSpin.getSelectedItemPosition() == 2) {
                    startSession();

                    int random = new Random().nextInt(((Global.attempts - 1)) + 1);
                    attemptsVal.setText(Integer.toString(Global.attempts));
                    shownChar.setText(Global.hiraChars.get(random));
                    answerVal.setText(Global.romaChars.get(random)); // debug purpose

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(answerVal.getText().toString().isEmpty()) {
                                emptyAnsToast();
                            } else if(answerVal.getText().toString().toLowerCase(Locale.ROOT).equals(Global.romaChars.get(random))) {
                                correctAnsToast();

                                Global.attempts--;
                                attemptsVal.setText(Integer.toString(Global.attempts));
                                Global.score++;
                                scoreVal.setText(Integer.toString(Global.score));
                                Global.hiraChars.remove(random);
                                Global.romaChars.remove(random);

                                answerVal.setEnabled(false);
                                generateBtn.setEnabled(true);
                                kataBtn.setEnabled(true);
                                hiraBtn.setEnabled(true);
                                submitBtn.setEnabled(false);
                            } else {
                                Global.attempts--;
                                attemptsVal.setText(Integer.toString(Global.attempts));

                                answerVal.setEnabled(false);
                                generateBtn.setEnabled(true);
                                kataBtn.setEnabled(true);
                                hiraBtn.setEnabled(true);
                                submitBtn.setEnabled(false);

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));

                                TextView toastText = layout.findViewById(R.id.toast_text);
                                ImageView toastImage = layout.findViewById(R.id.toast_image);

                                toastText.setText(String.format("Wrong. Answer should be '%s'", Global.romaChars.get(random)));
                                toastImage.setImageResource(R.drawable.ic_baseline_clear_24);

                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.BOTTOM, 0, 65);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
                            }
                            if(Global.attempts == 0) {
                                generateBtn.setEnabled(false);
                                zeroAttemptsToast();
                            }
                        }
                    });

//                    int rng = new Random().nextInt(((3 - 1) - 0) + 1) + 0;
//                    switch(rng) {
//                        case 0:
//                            //Set option 1 to answer
//                            int rng1 = new Random().nextInt(((roma.length - 1) - 0) + 1) + 0;
//                            choice1Btn.setText(roma[rng1]);
//                            break;
//
//                        case 1:
//                            //Set option 2 to answer
//                            int rng2 = new Random().nextInt(((roma.length - 1) - 0) + 1) + 0;
//                            choice2Btn.setText(roma[rng2]);
//                            break;
//
//                        case 2:
//                            //Set option 3 to answer
//                            int rng3 = new Random().nextInt(((roma.length - 1) - 0) + 1) + 0;
//                            choice3Btn.setText(roma[rng3]);
//                            break;
//                    }
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                Global.kataChars = kataChars;
                Global.hiraChars = hiraChars;
                Global.romaChars = romaChars;
                Global.attempts = romaChars.size();
                Global.score = 0;

                answerVal.getText().clear();
                attemptsVal.setText("0");
                optionsSpin.setSelection(0);
                scoreVal.setText(Integer.toString(Global.score));
                shownChar.setText("〇");

                initialLaunch();
            }
        });

        kataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KatakanaCharacters.class);
                startActivity(intent);
            }
        });

        hiraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HiraganaCharacters.class);
                startActivity(intent);
            }
        });

    }

    public void initialLaunch() {
        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);
        answerVal.setEnabled(false);
        generateBtn.setEnabled(true);
        kataBtn.setEnabled(true);
        hiraBtn.setEnabled(true);
        optionsSpin.setEnabled(true);
        resetBtn.setEnabled(false);
        submitBtn.setEnabled(false);
    }

    public void startSession() {
        choice1Btn.setEnabled(true);
        choice2Btn.setEnabled(true);
        choice3Btn.setEnabled(true);
        answerVal.getText().clear();
        answerVal.setEnabled(true);
        generateBtn.setEnabled(false);
        kataBtn.setEnabled(false);
        hiraBtn.setEnabled(false);
        optionsSpin.setEnabled(false);
        resetBtn.setEnabled(true);
        submitBtn.setEnabled(true);
    }

    public void welcomeToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText("Hello!");
        toastImage.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void nooptionToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText("Please select an option.");
        toastImage.setImageResource(R.drawable.ic_baseline_error_24);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void emptyAnsToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText("Please input an answer.");
        toastImage.setImageResource(R.drawable.ic_baseline_error_24);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void correctAnsToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText("Correct! Generate again.");
        toastImage.setImageResource(R.drawable.ic_baseline_done_24);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void zeroAttemptsToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText("No Attempts left. Press Reset All to try again.");
        toastImage.setImageResource(R.drawable.ic_baseline_error_24);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}