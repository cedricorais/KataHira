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
import java.util.Objects;

import ths.learnjp.katahira.ui.home.GreetingsActivity;
import ths.learnjp.katahira.ui.home.PhrasesActivity;

public class Speech {

    ImageView image;
    Snackbar snackbar;
    TextView progress;

    static SpeechRecognizer speechRecognizer;
    static Intent speechRecognizerIntent;
    public static String activity, evaluate, key;

    public void setupSpeechRecognizer(Context context, View view) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ja_JP");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                snackbar = Snackbar.make(view, R.string.listen_ongoing, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(context.getString(R.string.stop), view -> onEndOfSpeech()).show();
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
                Snackbar.make(view, context.getString(R.string.listen_end), Snackbar.LENGTH_LONG).show();
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
                        Toast.makeText(context, context.getString(R.string.correct_pronunciation), Toast.LENGTH_LONG).show();
                        switch (activity) {
                            case "greetings":
                                if (!Objects.equals(Global.greetings.get(key), true)) {
                                    Global.greetings.put(key, true);
                                    Global.greetings_progress++;
                                    dbHelper.updateData("greetings_done", Global.selectedProfile, String.valueOf(Global.greetings_progress));
                                    dbHelper.updateData("greetings", Global.selectedProfile, String.valueOf(Global.greetings));
                                    progress.setText(Global.greetings_progress + "/10");

                                    GreetingsActivity.recorded = true;
                                    GreetingsActivity greetingsActivity = new GreetingsActivity();
                                    greetingsActivity.showCheck(true, image, context);
                                }
                                break;
                            case "phrases":
                                if (!Objects.equals(Global.phrases.get(key), true)) {
                                    Global.phrases.put(key, true);
                                    Global.phrases_progress++;
                                    dbHelper.updateData("phrases_done", Global.selectedProfile, String.valueOf(Global.phrases_progress));
                                    dbHelper.updateData("phrases", Global.selectedProfile, String.valueOf(Global.phrases));
                                    progress.setText(Global.phrases_progress + "/10");

                                    PhrasesActivity.recorded = true;
                                    PhrasesActivity phrasesActivity = new PhrasesActivity();
                                    phrasesActivity.showCheck(true, image, context);
                                }
                                break;
                        }
                    } else {
                        Toast.makeText(context, R.string.incorrect_pronunciation, Toast.LENGTH_LONG).show();
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

    public void startListening(String act, String phrase, ImageView img, TextView prog, String keyPhrase) {
        activity = act;
        evaluate = phrase;
        key = keyPhrase;
        image = img;
        progress = prog;
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    public void stopListening() {
        speechRecognizer.stopListening();
    }
}
