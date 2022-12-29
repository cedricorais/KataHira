package ths.learnjp.katahira;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ths.learnjp.katahira.ui.home.GreetingsActivity;
import ths.learnjp.katahira.ui.home.PhrasesActivity;

public class Speech {

    ImageView image;
    Snackbar snackbar;
    TextView progress;

    static SpeechRecognizer speechRecognizer;
    static Intent speechRecognizerIntent;
    public static String activity, evaluate;

    public void setupSpeechRecognizer(Context context, View view) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ja_JP");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                snackbar = Snackbar.make(view, "Listening...", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Stop", view -> onEndOfSpeech()).show();
            }

            @Override
            public void onBeginningOfSpeech() {
                //
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                //
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                //
            }

            @Override
            public void onEndOfSpeech() {
                snackbar.dismiss();
                Snackbar.make(view, "Listening ended.", Snackbar.LENGTH_LONG).show();
                stopListening();
            }

            @Override
            public void onError(int error) {
                //
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResults(Bundle results) {
                DBHelper dbHelper = new DBHelper(context);
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    if (matches.get(0).equals(evaluate)) {
                        Toast.makeText(context, "Correct Pronunciation.", Toast.LENGTH_SHORT).show();
                        switch (activity) {
                            case "greetings":
                                Global.greetings_progress++; // TODO
                                dbHelper.updateData("greetings", Global.selectedProfile, String.valueOf(Global.greetings_progress)); // TODO
                                progress.setText(Global.greetings_progress + "/10"); // TODO

                                GreetingsActivity greetingsActivity = new GreetingsActivity();
                                greetingsActivity.showCheck(true, image);
                                break;
                            case "phrases":
                                Global.phrases_progress++; // TODO
                                dbHelper.updateData("phrases", Global.selectedProfile, String.valueOf(Global.phrases_progress)); // TODO
                                progress.setText(Global.phrases_progress + "/10"); // TODO

                                PhrasesActivity phrasesActivity = new PhrasesActivity();
                                phrasesActivity.showCheck(true, image);
                                break;
                        }
                    } else {
                        Toast.makeText(context, "Incorrect Pronunciation, try again.", Toast.LENGTH_SHORT).show();
                    }
                } /*else {
                    Toast.makeText(context, " Sorry, didn't catch that. Please, try again.", Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                //
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                //
            }
        });
    }

    public void startListening(String act, String phrase, ImageView img, TextView prog) {
        activity = act;
        evaluate = phrase;
        image = img;
        progress = prog;
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    public void stopListening() {
        speechRecognizer.stopListening();
    }
}
