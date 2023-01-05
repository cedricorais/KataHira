package ths.learnjp.katahira;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

// DELETE
import java.util.Map;

import ths.learnjp.katahira.databinding.ActivityMainBinding;
import ths.learnjp.katahira.ui.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

//    public String[] language_names = CharaManager.getLanguages(getApplicationContext()); // ILIPAT SA DROP DOWN

    boolean doubleBackToExit = false;

    Map current_language;
    Map current_chara_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_guess, R.id.navigation_dashboard).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // TODO Update
        // Set Japanese/Katakana as default language.
//        CharacterManager.setLanguage(getApplicationContext(), "Japanese");
//        CharacterManager.setCharaSet("Katakana");
        CharacterManager.setLanguage(getApplicationContext(), "Test"); // TODO
        CharacterManager.setCharaSet("10 chars kata");
        // TODO DELETE
        Score.initializeScore();

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // TODO start light mode
        Toasts toast = new Toasts();
        toast.showToast(this, "Welcome!", null);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
//        menu.removeItem(R.id.settings); // TODO remove comment
        menu.removeItem(R.id.tutorial);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
//                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main); // TODO
//                navController.navigate(R.id.navigation_home);
                startActivity(intent);
                return true;
            case R.id.tutorial:
//                Toast.makeText(this, "tutorial", Toast.LENGTH_SHORT).show(); // TODO
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            System.exit(0);
            return;
        }
        this.doubleBackToExit = true;
        Toast.makeText(this, getString(R.string.prompt_exit), Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExit = false, 2000);
    }

/*    public Map setLanguageDropDown() {
    //    String[] language_names = CharaManager.getLanguages(getApplicationContext());
        String selected_language = "Japanese"; // SELECTED LANGUAGE FROM DROPDOWN
        Map language = CharacterManager.loadLanguage(getApplicationContext(), selected_language);

        return language;
    }

    public void setCharaSetDropDown() {
//        for ()

    }*/
}