package ths.learnjp.katahira.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ths.learnjp.katahira.CharacterManager;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.Score;
import ths.learnjp.katahira.old.OldActivity;
import ths.learnjp.katahira.old.OldActivity2;

public class SettingsActivity extends AppCompatActivity {

    String[] selected_option;

    Spinner langSpin;
    @SuppressLint("UseSwitchCompatOrMaterialCode") // TODO remove switch
    Switch toggleTheme;
    Button old, old2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        selected_option = CharacterManager.getLanguages(getApplicationContext());

        langSpin = findViewById(R.id.lang);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, selected_option);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpin.setAdapter(adapter);
        langSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), selected_option[position], Toast.LENGTH_LONG).show();
                Snackbar.make(view, selected_option[position], Snackbar.LENGTH_LONG).show();
                if (position > 0) {
                    CharacterManager.setLanguage(getApplicationContext(), "Test"); // TODO test
                    CharacterManager.setCharaSet("10 chars kata");
                } else {
                    CharacterManager.setLanguage(getApplicationContext(), "Japanese");
                    CharacterManager.setCharaSet("Katakana");
                }
                Score.initializeScore();
                System.out.println(Global.chara_set);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        toggleTheme = findViewById(R.id.toggleTheme); // TODO remove switch
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                toggleTheme.setChecked(true);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                toggleTheme.setChecked(false);
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                System.out.println("idk");
                break;
        }
        toggleTheme.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //light
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //dark
            }
        });

        old = findViewById(R.id.old); // TODO remove old
        old.setOnClickListener(view -> {
            Intent intent = new Intent(this, OldActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        old2 = findViewById(R.id.old2); // TODO remove old
        old2.setOnClickListener(view -> {
            Intent intent = new Intent(this, OldActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        int[] textViews = {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5};
        String[] messages = {"one", "two", "three", "four", "five"};
        for (int i = 0; i < textViews.length; i++) {
            TextView tv = findViewById(textViews[i]);
            tv.setText(messages[i]);
        }
        /*String[] textArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for( int i = 0; i < textArray.length; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(textArray[i]);
            linearLayout.addView(textView);
        }*/
    }

    @Override
    public void onBackPressed() {
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