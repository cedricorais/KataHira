package ths.learnjp.katahira;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

import ths.learnjp.katahira.ui.guess.GuessFragment;

public class Speech {
    public static final int REQUEST_CODE_SPEECH_INPUT = 1;
    static SpeechRecognizer speechRecognizer;
    static Intent speechRecognizerIntent;

    public static void setupSpeechRecognizer(Context context) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                System.out.println("ready for speech");

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if (matches != null)
                    System.out.println(matches.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                System.out.println("FUCK");
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    public static void startListening() {
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    public static void stoplistening() {
        speechRecognizer.stopListening();
    }
}
