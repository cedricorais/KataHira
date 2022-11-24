package ths.learnjp.katahira.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Locale;

import ths.learnjp.katahira.R;

public class GreetingsActivity extends AppCompatActivity {

    Button audio1, audio2, audio3, audio4, audio5, audio6, audio7, audio8, audio9, audio10, mic1, mic2, mic3, mic4, mic5, mic6, mic7, mic8, mic9, mic10;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);

        tts = new TextToSpeech(getApplicationContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.JAPANESE);
            }
        });

        audio1 = findViewById(R.id.audio1);
        audio1.setOnClickListener(view -> {
            tts.speak("はい", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio2 = findViewById(R.id.audio2);
        audio2.setOnClickListener(view -> {
            tts.speak("いいえ", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio3 = findViewById(R.id.audio3);
        audio3.setOnClickListener(view -> {
            tts.speak("おねがいします", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio4 = findViewById(R.id.audio4);
        audio4.setOnClickListener(view -> {
            tts.speak("ありがとう", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio5 = findViewById(R.id.audio5);
        audio5.setOnClickListener(view -> {
            tts.speak("どういたしまして", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio6 = findViewById(R.id.audio6);
        audio6.setOnClickListener(view -> {
            tts.speak("すみません", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio7 = findViewById(R.id.audio7);
        audio7.setOnClickListener(view -> {
            tts.speak("ごめんなさい", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio8 = findViewById(R.id.audio8);
        audio8.setOnClickListener(view -> {
            tts.speak("わかりません", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio9 = findViewById(R.id.audio9);
        audio9.setOnClickListener(view -> {
            tts.speak("しりません", TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio10 = findViewById(R.id.audio10);
        audio10.setOnClickListener(view -> {
            tts.speak("ただいま", TextToSpeech.QUEUE_FLUSH, null, null);
        });

        mic1 = findViewById(R.id.mic1);
        mic1.setOnClickListener(view -> {
            //
        });
        mic2 = findViewById(R.id.mic2);
        mic2.setOnClickListener(view -> {
            //
        });
        mic3 = findViewById(R.id.mic3);
        mic3.setOnClickListener(view -> {
            //
        });
        mic4 = findViewById(R.id.mic4);
        mic4.setOnClickListener(view -> {
            //
        });
        mic5 = findViewById(R.id.mic5);
        mic5.setOnClickListener(view -> {
            //
        });
        mic6 = findViewById(R.id.mic6);
        mic6.setOnClickListener(view -> {
            //
        });
        mic7 = findViewById(R.id.mic7);
        mic7.setOnClickListener(view -> {
            //
        });
        mic8 = findViewById(R.id.mic8);
        mic8.setOnClickListener(view -> {
            //
        });
        mic9 = findViewById(R.id.mic9);
        mic9.setOnClickListener(view -> {
            //
        });
        mic10 = findViewById(R.id.mic10);
        mic10.setOnClickListener(view -> {
            //
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
}