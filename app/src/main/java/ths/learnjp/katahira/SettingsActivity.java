package ths.learnjp.katahira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import ths.learnjp.katahira.old.OldActivity;
import ths.learnjp.katahira.old.OldActivity2;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        langSpin.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selected_option);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpin.setAdapter(adapter);

        toggleTheme = findViewById(R.id.toggleTheme); // TODO remove switch
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(getApplicationContext(),selected_option[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //
    }
}