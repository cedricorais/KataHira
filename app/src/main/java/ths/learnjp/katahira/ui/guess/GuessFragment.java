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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ths.learnjp.katahira.CharacterManager;
import ths.learnjp.katahira.Generate;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Toasts;
import ths.learnjp.katahira.databinding.FragmentGuessBinding;

public class GuessFragment extends Fragment {

    private FragmentGuessBinding binding;

    Button generateBtn, choice1Btn, choice2Btn, choice3Btn, resetBtn;
    Spinner optionsSpin;
    TextView scoreValue, attemptValue, shownChar;
/*    @SuppressLint("UseSwitchCompatOrMaterialCode")

    @SuppressLint("SetTextI18n")*/
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        GuessViewModel guessViewModel = new ViewModelProvider(this).get(GuessViewModel.class);

        binding = FragmentGuessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGuess;
//        guessViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        attemptValue = binding.attemptsValue;
        scoreValue = binding.scoreValue;
        shownChar = binding.showChar;

        String[] selected_option = CharacterManager.getCharaSetNames();

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
                String selected_chara_set = (String) parent.getItemAtPosition(position);
                CharacterManager.setCharaSet(selected_chara_set);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        Toasts toast = new Toasts();

        generateBtn = binding.generateChar;
        generateBtn.setOnClickListener(view -> {

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
            init();
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
        resetBtn.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    public void startSession() {
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

    public void generateAnswer(Toasts toast, String character_answer) {
        List<Button> choices = new ArrayList<>(Arrays.asList(choice1Btn, choice2Btn, choice3Btn));
        int rng = new Random().nextInt(3);

        Button correct_choice = choices.get(rng);

        String correct_answer = CharacterManager.getAnswer(character_answer);
        correct_choice.setText(correct_answer);
//        correct_choice.setBackgroundColor(Color.GREEN);
        Map temp_map = CharacterManager.tempRemoveCharFromSession(character_answer);

        checkAnswer(toast, correct_choice, correct_answer);

        choices.remove(correct_choice);
        for (Button incorrect_choice : choices) {
            String mali = Generate.getCharacterNoWeight(temp_map);
            incorrect_choice.setText(CharacterManager.getAnswer(mali));
            temp_map.remove(mali);

            checkAnswer(toast, incorrect_choice, correct_answer);
        }
    }

    public void checkAnswer(Toasts toast, Button choiceButton, String answer) {
        choiceButton.setOnClickListener(view -> {
            Button btn = (Button) view;
            String getTxt = btn.getText().toString();
            if (getTxt.equals(answer)) {
                choiceButton.setBackgroundColor(Color.GREEN);
                correctAnswer(toast, answer);
            } else {
                choiceButton.setBackgroundColor(Color.RED);
                wrongAnswer(toast, answer);
            }
        });
    }
    @SuppressLint("SetTextI18n") // TODO move process class

    public void correctAnswer(Toasts toast, String answer) {
        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));
        Global.session_score++;
        scoreValue.setText(Integer.toString(Global.session_score));

        CharacterManager.removeCharFromSession(answer);

        afterAnswer(toast);
    }

    public void wrongAnswer(Toasts toast, String correct_answer) {
        toast.showToast(this.getActivity(), "wrongAnswer", correct_answer);

        Global.session_attempts_left--;
        attemptValue.setText(Integer.toString(Global.session_attempts_left));

        CharacterManager.removeCharFromSession(correct_answer);

        afterAnswer(toast);
    }

    public void afterAnswer(Toasts toast) {
        generateBtn.setEnabled(true);
        choice1Btn.setEnabled(false);
        choice2Btn.setEnabled(false);
        choice3Btn.setEnabled(false);

        if (Global.session_attempts_left == 0) {
            generateBtn.setEnabled(false);
            toast.showToast(this.getActivity(), "zeroAttempts", null);
        }
    }

}
