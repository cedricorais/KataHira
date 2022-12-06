package ths.learnjp.katahira.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ths.learnjp.katahira.R;

public class PhrasesActivity extends AppCompatActivity {

    Button audio1, audio2, audio3, audio4, audio5, audio6, audio7, audio8, audio9, audio10, mic1, mic2, mic3, mic4, mic5, mic6, mic7, mic8, mic9, mic10;
    TextToSpeech tts;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        tts = new TextToSpeech(getApplicationContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.JAPANESE);
            }
        });
        test = findViewById(R.id.title1);

        audio1 = findViewById(R.id.audio1);
        audio1.setOnClickListener(view -> {
            tts.speak("おはようございます", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio2 = findViewById(R.id.audio2);
        audio2.setOnClickListener(view -> {
            tts.speak("こんばんは", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio3 = findViewById(R.id.audio3);
        audio3.setOnClickListener(view -> {
            tts.speak("おやすみなさい", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio4 = findViewById(R.id.audio4);
        audio4.setOnClickListener(view -> {
            tts.speak("おなまえはなんですか？", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio5 = findViewById(R.id.audio5);
        audio5.setOnClickListener(view -> {
            tts.speak("おげんきですか？", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio6 = findViewById(R.id.audio6);
        audio6.setOnClickListener(view -> {
            tts.speak("げんきです", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio7 = findViewById(R.id.audio7);
        audio7.setOnClickListener(view -> {
            tts.speak("はじめまして", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio8 = findViewById(R.id.audio8);
        audio8.setOnClickListener(view -> {
            tts.speak("ようこそ", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio9 = findViewById(R.id.audio9);
        audio9.setOnClickListener(view -> {
            tts.speak("こんにちは", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio10 = findViewById(R.id.audio10);
        audio10.setOnClickListener(view -> {
            tts.speak("さようなら", TextToSpeech.QUEUE_FLUSH, null, null);
        });

        mic1 = findViewById(R.id.mic1);
        mic1.setOnClickListener(view -> {
            startSpeak();
        });
        mic2 = findViewById(R.id.mic2);
        mic2.setOnClickListener(view -> {
            startSpeak();
        });
        mic3 = findViewById(R.id.mic3);
        mic3.setOnClickListener(view -> {
            startSpeak();
        });
        mic4 = findViewById(R.id.mic4);
        mic4.setOnClickListener(view -> {
            startSpeak();
        });
        mic5 = findViewById(R.id.mic5);
        mic5.setOnClickListener(view -> {
            startSpeak();
        });
        mic6 = findViewById(R.id.mic6);
        mic6.setOnClickListener(view -> {
            startSpeak();
        });
        mic7 = findViewById(R.id.mic7);
        mic7.setOnClickListener(view -> {
            startSpeak();
        });
        mic8 = findViewById(R.id.mic8);
        mic8.setOnClickListener(view -> {
            startSpeak();
        });
        mic9 = findViewById(R.id.mic9);
        mic9.setOnClickListener(view -> {
            startSpeak();
        });
        mic10 = findViewById(R.id.mic10);
        mic10.setOnClickListener(view -> {
            startSpeak();
        });
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startSpeak() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
            startActivityForResult(intent, 100);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Something went wrong..", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 || requestCode == RESULT_OK) {
            test.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
    }
}