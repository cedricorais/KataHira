package ths.learnjp.katahira.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ths.learnjp.katahira.DBHelper;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;

public class PhrasesActivity extends AppCompatActivity {

    Button audio1, audio2, audio3, audio4, audio5, audio6, audio7, audio8, audio9, audio10, mic1, mic2, mic3, mic4, mic5, mic6, mic7, mic8, mic9, mic10;
    TextToSpeech tts;
    TextView profileText, test;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        dbHelper = new DBHelper(this);

        mic1 = findViewById(R.id.mic1);
        mic2 = findViewById(R.id.mic2);
        mic3 = findViewById(R.id.mic3);
        mic4 = findViewById(R.id.mic4);
        mic5 = findViewById(R.id.mic5);
        mic6 = findViewById(R.id.mic6);
        mic7 = findViewById(R.id.mic7);
        mic8 = findViewById(R.id.mic8);
        mic9 = findViewById(R.id.mic9);
        mic10 = findViewById(R.id.mic10);

        audio1 = findViewById(R.id.audio1);
        audio2 = findViewById(R.id.audio2);
        audio3 = findViewById(R.id.audio3);
        audio4 = findViewById(R.id.audio4);
        audio5 = findViewById(R.id.audio5);
        audio6 = findViewById(R.id.audio6);
        audio7 = findViewById(R.id.audio7);
        audio8 = findViewById(R.id.audio8);
        audio9 = findViewById(R.id.audio9);
        audio10 = findViewById(R.id.audio10);

        profileText = findViewById(R.id.currentProfileValue);
        test = findViewById(R.id.title1);

        tts = new TextToSpeech(getApplicationContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.JAPANESE);
            }
        });

        if (Global.selectedProfile == null) {
            profileText.setText(R.string.n_a);
            Toast.makeText(this, "Select a profile first in dashboard menu.", Toast.LENGTH_LONG).show();
            mic1.setEnabled(false);
            mic2.setEnabled(false);
            mic3.setEnabled(false);
            mic4.setEnabled(false);
            mic5.setEnabled(false);
            mic6.setEnabled(false);
            mic7.setEnabled(false);
            mic8.setEnabled(false);
            mic9.setEnabled(false);
            mic10.setEnabled(false);
        } else {
            profileText.setText(Global.selectedProfile);
            mic1.setEnabled(true);
            mic2.setEnabled(true);
            mic3.setEnabled(true);
            mic4.setEnabled(true);
            mic5.setEnabled(true);
            mic6.setEnabled(true);
            mic7.setEnabled(true);
            mic8.setEnabled(true);
            mic9.setEnabled(true);
            mic10.setEnabled(true);
        }

        mic1.setOnClickListener(view -> {
            startSpeak();
        });
        mic2.setOnClickListener(view -> {
            startSpeak();
        });
        mic3.setOnClickListener(view -> {
            startSpeak();
        });
        mic4.setOnClickListener(view -> {
            startSpeak();
        });
        mic5.setOnClickListener(view -> {
            startSpeak();
        });
        mic6.setOnClickListener(view -> {
            startSpeak();
        });
        mic7.setOnClickListener(view -> {
            startSpeak();
        });
        mic8.setOnClickListener(view -> {
            startSpeak();
        });
        mic9.setOnClickListener(view -> {
            startSpeak();
        });
        mic10.setOnClickListener(view -> {
            startSpeak();
        });

        audio1.setOnClickListener(view -> {
            Global.phrases_progress++; // TODO
            dbHelper.updateData("phrases", Global.selectedProfile, String.valueOf(Global.phrases_progress)); // TODO
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("おはようございます", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio2.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("こんばんは", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio3.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("おやすみなさい", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio4.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("おなまえはなんですか？", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio5.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("おげんきですか？", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio6.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("げんきです", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio7.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("はじめまして", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio8.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("ようこそ", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio9.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("こんにちは", TextToSpeech.QUEUE_ADD, null, null);
        });
        audio10.setOnClickListener(view -> {
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak("さようなら", TextToSpeech.QUEUE_ADD, null, null);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        menu.removeItem(R.id.settings);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.tutorial:
                Toast.makeText(this, "tutorial", Toast.LENGTH_SHORT).show(); // TODO
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSpeak() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
            startActivityForResult(intent, 100);
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong..", Toast.LENGTH_LONG).show();
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