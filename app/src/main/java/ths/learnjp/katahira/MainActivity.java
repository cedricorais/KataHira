package ths.learnjp.katahira;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

// DELETE
import java.util.Map;

import ths.learnjp.katahira.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

//    public String[] language_names = CharaManager.getLanguages(getApplicationContext()); // ILIPAT SA DROP DOWN

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

        Toasts toast = new Toasts();
        toast.showToast(this, "Welcome!", 0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    public Map setLanguageDropDown() {
    //    String[] language_names = CharaManager.getLanguages(getApplicationContext());
        String selected_language = "Japanese"; // SELECTED LANGUAGE FROM DROPDOWN
        Map language = CharacterManager.loadLanguage(getApplicationContext(), selected_language);

        return language;
    }

    public void setCharaSetDropDown() {
//        for ()

    }
}