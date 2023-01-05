package ths.learnjp.katahira.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Speech;

public class GreetingsActivity extends AppCompatActivity {

    Button audio1, audio2, audio3, audio4, audio5, audio6, audio7, audio8, audio9, audio10, mic1, mic2, mic3, mic4, mic5, mic6, mic7, mic8, mic9, mic10;
    ImageView done1, done2, done3, done4, done5, done6, done7, done8, done9, done10, profileHelp, rankHelp;
    TextToSpeech tts;
    TextView profileText, progressText, adjust;
    View layout;

    List<Button> audios, mics;
    List<ImageView> imgs;
    String[] phrases = {"はい", "いいえ", "お願いします", "ありがとう", "どういたしまして", "すみません", "ごめんなさい", "わかりません", "知りません", "ただいま"};
    public static boolean recorded = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);

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
        mics = new ArrayList<>(Arrays.asList(mic1, mic2, mic3, mic4, mic5, mic6, mic7, mic8, mic9, mic10));

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
        audios = new ArrayList<>(Arrays.asList(audio1, audio2, audio3, audio4, audio5, audio6, audio7, audio8, audio9, audio10));

        profileText = findViewById(R.id.currentProfileValue);
        progressText = findViewById(R.id.progressValue);
        adjust = findViewById(R.id.wc);

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
        rankHelp = findViewById(R.id.rankHelp);
        imgs = new ArrayList<>(Arrays.asList(done1, done2, done3, done4, done5, done6, done7, done8, done9, done10));

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth == 360) {
            adjust.setText(R.string.WcGmWnHf);
        } else {
            adjust.setText(R.string.indicator);
        }

        if (Global.selectedProfile == null) {
            profileText.setText(R.string.n_a);
            progressText.setText("0/10");

            Snackbar snackbar = Snackbar.make(layout, getString(R.string.require_profile), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(R.string.close, view -> snackbar.dismiss()).show();
            /*Snackbar.make(layout, getString(R.string.require_profile), Snackbar.LENGTH_INDEFINITE).setAction(R.string.title_dashboard, view -> { // TODO
                HomeFragment.lastActivity = true; // TODO
                onBackPressed();
            }).show();*/

            for (Button mic : mics) {
                mic.setEnabled(false);
            }

            showCheck(false, null, null);
        } else {
            profileText.setText(Global.selectedProfile);
            progressText.setText(Global.greetings_progress + "/10");
            if (Global.greetings_progress == 0){
                showTutorial();
            }

            for (Button mic : mics) {
                mic.setEnabled(true);
            }

            if (Objects.equals(Global.greetings.get("phrase0"), true)) {
                showCheck(true, done1, null);
            }
            if (Objects.equals(Global.greetings.get("phrase1"), true)) {
                showCheck(true, done2, null);
            }
            if (Objects.equals(Global.greetings.get("phrase2"), true)) {
                showCheck(true, done3, null);
            }
            if (Objects.equals(Global.greetings.get("phrase3"), true)) {
                showCheck(true, done4, null);
            }
            if (Objects.equals(Global.greetings.get("phrase4"), true)) {
                showCheck(true, done5, null);
            }
            if (Objects.equals(Global.greetings.get("phrase5"), true)) {
                showCheck(true, done6, null);
            }
            if (Objects.equals(Global.greetings.get("phrase6"), true)) {
                showCheck(true, done7, null);
            }
            if (Objects.equals(Global.greetings.get("phrase7"), true)) {
                showCheck(true, done8, null);
            }
            if (Objects.equals(Global.greetings.get("phrase8"), true)) {
                showCheck(true, done9, null);
            }
            if (Objects.equals(Global.greetings.get("phrase9"), true)) {
                showCheck(true, done10, null);
            }
        }

        TooltipCompat.setTooltipText(profileHelp, getString(R.string.help_profile));
        profileHelp.setOnClickListener(view -> profileHelp.performLongClick());
        TooltipCompat.setTooltipText(rankHelp, getString(R.string.help_progress));
        rankHelp.setOnClickListener(view -> rankHelp.performLongClick());

        Speech speech = new Speech();
        speech.setupSpeechRecognizer(this, layout);
        int indexMic = 0, indexAudio = 0;
        for (Button button : mics) {
            int finalIndex = indexMic;
            button.setOnClickListener(view -> {
                checkPermission();
                speech.stopListening();
                speech.startListening("greetings", phrases[finalIndex], imgs.get(finalIndex), progressText, "phrase" + finalIndex);
            });
            indexMic++;
        }

        tts = new TextToSpeech(getApplicationContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.JAPANESE);
            }
        });
        for (Button button : audios) {
            int finalIndex = indexAudio;
            button.setOnClickListener(view -> {
                speech.stopListening();
                Toast.makeText(this, getString(R.string.tts_playing), Toast.LENGTH_SHORT).show();
                tts.speak(phrases[finalIndex], TextToSpeech.QUEUE_FLUSH, null, null);
            });
            indexAudio++;
        }
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
                showTutorial();
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

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(GreetingsActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(GreetingsActivity.this, new String[]{ Manifest.permission.RECORD_AUDIO }, 0);
        }
    }

    public void showCheck(boolean show, ImageView img, Context context) {
        if (!show) {
            for (ImageView image : imgs) {
                image.setVisibility(View.GONE);
            }
        } else {
            img.setVisibility(View.VISIBLE);
            if (recorded) {
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(0, 0);
            }
        }
    }

    private void showTutorial() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tutorial_layout, this.findViewById(R.id.main), false);

        ImageView img = view.findViewById(R.id.image);
        Integer[] pics = new Integer[]{R.drawable.pronunciation1, R.drawable.pronunciation2, R.drawable.pronunciation3, R.drawable.pronunciation4};
        List<Integer> tutorialPics = new ArrayList<>(Arrays.asList(pics));
        img.setImageResource(tutorialPics.get(0));

        TextView text = view.findViewById(R.id.text);
        String[] texts = new String[]{getString(R.string.pronunciation1), getString(R.string.pronunciation2), getString(R.string.pronunciation3), getString(R.string.pronunciation4)};
        List<String> tutorialText = new ArrayList<>(Arrays.asList(texts));
        text.setText(tutorialText.get(0));

        alert.setTitle(R.string.pronunciation_title);
        alert.setView(view);
        alert.setPositiveButton(R.string.next, null);
        alert.setNegativeButton(R.string.previous, null);
        alert.setNeutralButton(R.string.close, ((dialogInterface, i) -> dialogInterface.dismiss()));

        final int[] index = {0};
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            index[0]++;
            img.setImageResource(tutorialPics.get(index[0]));
            text.setText(tutorialText.get(index[0]));
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(true);
            if (index[0] == tutorialPics.size() - 1) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
            }
        });
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
            index[0]--;
            img.setImageResource(tutorialPics.get(index[0]));
            text.setText(tutorialText.get(index[0]));
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
            if (index[0] == 0) {
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false);
            }
        });
    }
}