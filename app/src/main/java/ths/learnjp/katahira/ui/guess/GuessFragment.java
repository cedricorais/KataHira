package ths.learnjp.katahira.ui.guess;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Toasts;
import ths.learnjp.katahira.databinding.FragmentGuessBinding;

public class GuessFragment extends Fragment {

    private FragmentGuessBinding binding;

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

        public static boolean charCheck; // TODO debug: check toggle
    }

    Button generateBtn, choice1Btn, choice2Btn, choice3Btn, resetBtn;
    Spinner optionsSpin;
    TextView scoreValue, attemptValue, shownChar;
    @SuppressLint("UseSwitchCompatOrMaterialCode") // TODO debug: toggle 5 chars
    Switch toggleChars;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        GuessViewModel guessViewModel = new ViewModelProvider(this).get(GuessViewModel.class);

        binding = FragmentGuessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGuess;
//        guessViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        attemptValue = binding.attemptsValue;
        scoreValue = binding.scoreValue;
        shownChar = binding.showChar;

        String[] selected_option = new String[]{getString(R.string.option1), getString(R.string.option2), getString(R.string.option3)};
        optionsSpin = binding.options;
//    final List<String> select = new ArrayList<>(Arrays.asList(selected_option));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, selected_option) {
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
//                if (position > 0) {
//                    // some code
//                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        Toasts toast = new Toasts();

        generateBtn = binding.generateChar;
        generateBtn.setOnClickListener(view -> {
            int random = new Random().nextInt(((globalVars.attempts - 1)) + 1);
            switch (optionsSpin.getSelectedItemPosition()) {
                case 0:
                    toast.showToast(this.getActivity(), "noOption", 0);
                    break;
                case 1:
                    startSession();
                    shownChar.setText(globalVars.kataChars.get(random));
                    generateAnswer(toast, random);
                    break;
                case 2:
                    startSession();
                    shownChar.setText(globalVars.hiraChars.get(random));
                    generateAnswer(toast, random);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + optionsSpin.getSelectedItemPosition());
            }
        });

        choice1Btn = binding.choice1;
        choice2Btn = binding.choice2;
        choice3Btn = binding.choice3;

        resetBtn = binding.reset;
        resetBtn.setOnClickListener(view -> {
            reset();
            init();
            toggleChars.setChecked(false); // TODO debug: toggle 5 chars
            toggleChars.setEnabled(true); // TODO debug: toggle 5 chars
        });

        toggleChars = binding.test; // TODO debug: toggle 5 chars
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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    public void init() {
        scoreValue.setText(Integer.toString(globalVars.score));
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
        resetBtn.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    public void startSession() {
        attemptValue.setText(Integer.toString(globalVars.attempts));
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
        toggleChars.setEnabled(false); // TODO debug: toggle 5 chars
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
        choice1Btn.setBackgroundColor(Color.GREEN); // TODO debug: show correct answer
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
        choice2Btn.setBackgroundColor(Color.GREEN); // TODO debug: show correct answer
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
        choice3Btn.setBackgroundColor(Color.GREEN); // TODO debug: show correct answer
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
            if (getTxt.equals(globalVars.romaChars.get(random))){
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
            if (getTxt.equals(globalVars.romaChars.get(random))){
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
            if (getTxt.equals(globalVars.romaChars.get(random))){
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
        toast.showToast(this.getActivity(), "correctAnswer", random);

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

        if (globalVars.attempts == 0) {
            generateBtn.setEnabled(false);
            toast.showToast(this.getActivity(), "zeroAttempts", 0);
        }
    }
    @SuppressLint("SetTextI18n") // TODO move process class
    public void wrongAnswer(Toasts toast, int random) {
        toast.showToast(this.getActivity(), "wrongAnswer", random);

        globalVars.attempts--;
        attemptValue.setText(Integer.toString(globalVars.attempts));

        generateBtn.setEnabled(true);
        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);

        if (globalVars.attempts == 0) {
            generateBtn.setEnabled(false);
            toast.showToast(this.getActivity(), "zeroAttempts", 0);
        }
    }
}