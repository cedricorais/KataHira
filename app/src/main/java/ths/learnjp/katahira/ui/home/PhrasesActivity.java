package ths.learnjp.katahira.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Speech;

public class PhrasesActivity extends AppCompatActivity {

    Button audio1, audio2, audio3, audio4, audio5, audio6, audio7, audio8, audio9, audio10, mic1, mic2, mic3, mic4, mic5, mic6, mic7, mic8, mic9, mic10;
    ImageView done1, done2, done3, done4, done5, done6, done7, done8, done9, done10, profileHelp;
    TextToSpeech tts;
    TextView profileText, progressText, adjust1, adjust2, adjust3;
    View layout;

    String[] phrases = {"おはようございます", "こんばんは", "おやすみなさい", "お名前は何ですか", "お元気ですか", "元気です", "初めまして", "ようこそ", "こんにちは", "さようなら"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        layout = findViewById(android.R.id.content);

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
        progressText = findViewById(R.id.progressValue);
        adjust1 = findViewById(R.id.gm);
        adjust2 = findViewById(R.id.wn);
        adjust3 = findViewById(R.id.hf);

        done1 = findViewById(R.id.done1);
        done2 = findViewById(R.id.done2);
        done3 = findViewById(R.id.done3);
        done4 = findViewById(R.id.done4);
        done5 = findViewById(R.id.done5);
        done6 = findViewById(R.id.done6);
        done7 = findViewById(R.id.done7);
        done8 = findViewById(R.id.done8);
        done9 = findViewById(R.id.done9);
        done10 = findViewById(R.id.done10);
        profileHelp = findViewById(R.id.profileHelp);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth == 360) {
            adjust1.setText(R.string.WcGmWnHf);
            adjust2.setText(R.string.WcGmWnHf);
            adjust3.setText(R.string.WcGmWnHf);
        } else {
            adjust1.setText(R.string.indicator);
            adjust2.setText(R.string.indicator);
            adjust3.setText(R.string.indicator);
        }

        if (Global.selectedProfile == null) {
            profileText.setText(R.string.n_a);
            progressText.setText("0/10"); // TODO

            Snackbar snackbar = Snackbar.make(layout, getString(R.string.require_profile), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(R.string.dismiss, view -> snackbar.dismiss()).show();
            /*Snackbar.make(layout, getString(R.string.require_profile), Snackbar.LENGTH_INDEFINITE).setAction(R.string.title_dashboard, view -> { // TODO
                HomeFragment.lastActivity = true; // TODO
                finish();
            }).show();*/

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

            showCheck(false, null);
        } else {
            profileText.setText(Global.selectedProfile);
            progressText.setText(Global.phrases_progress + "/10"); // TODO
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

            showCheck(false, null); // TODO
        }
        
        profileHelp.setOnClickListener(view -> Toast.makeText(this, "help", Toast.LENGTH_SHORT).show());

        Speech speech = new Speech();
        speech.setupSpeechRecognizer(this, layout);
        mic1.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[0], done1, progressText);
        });
        mic2.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[1], done2, progressText);
        });
        mic3.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[2], done3, progressText);
        });
        mic4.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[3], done4, progressText);
        });
        mic5.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[4], done5, progressText);
        });
        mic6.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[5], done6, progressText);
        });
        mic7.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[6], done7, progressText);
        });
        mic8.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[7], done8, progressText);
        });
        mic9.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[8], done9, progressText);
        });
        mic10.setOnClickListener(view -> {
            checkPermission();
            speech.stopListening();
            speech.startListening("phrases", phrases[9], done10, progressText);
        });

        tts = new TextToSpeech(getApplicationContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.JAPANESE);
            }
        });

        audio1.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[0], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio2.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[1], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio3.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[2], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio4.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[3], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio5.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[4], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio6.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[5], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio7.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[6], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio8.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[7], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio9.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[8], TextToSpeech.QUEUE_FLUSH, null, null);
        });
        audio10.setOnClickListener(view -> {
            speech.stopListening();
            Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
            tts.speak(phrases[9], TextToSpeech.QUEUE_FLUSH, null, null);
        });
    }

    @Override
    public void onBackPressed() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(layout, getString(R.string.granted_perm), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(layout, getString(R.string.request_perm), Snackbar.LENGTH_LONG).setAction(R.string.grant, view -> checkPermission()).show();
        }
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(PhrasesActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(PhrasesActivity.this, new String[]{ Manifest.permission.RECORD_AUDIO }, 0);
        }
    }

    public void showCheck(boolean show, View view) {
        if (!show) {
            done1.setVisibility(View.GONE);
            done2.setVisibility(View.GONE);
            done3.setVisibility(View.GONE);
            done4.setVisibility(View.GONE);
            done5.setVisibility(View.GONE);
            done6.setVisibility(View.GONE);
            done7.setVisibility(View.GONE);
            done8.setVisibility(View.GONE);
            done9.setVisibility(View.GONE);
            done10.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}