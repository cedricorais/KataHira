package ths.learnjp.katahira.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    Button kataBtn, hiraBtn, greetingsBtn, phrasesBtn;
    NavController navController;
    View root;

    public static boolean lastActivity = false, goToHome = false; // TODO

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        kataBtn = binding.showKata;
        kataBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), KatakanaCharactersActivity.class);
            startActivity(intent);
        });
        hiraBtn = binding.showHira;
        hiraBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), HiraganaCharactersActivity.class);
            startActivity(intent);
        });
        greetingsBtn = binding.showGreetings;
        greetingsBtn.setOnClickListener(view -> startIntent("greetings"));
        phrasesBtn = binding.showPhrases;
        phrasesBtn.setOnClickListener(view -> startIntent("phrases"));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (GreetingsActivity.recorded) {
            GreetingsActivity.recorded = false;
            startIntent("greetings");
        }
        if (PhrasesActivity.recorded) {
            PhrasesActivity.recorded = false;
            startIntent("phrases");
        }
    }

    private void startIntent(String tag) {
        refreshFragment();
        Intent intent = null;
        switch (tag) {
            case "greetings":
                intent = new Intent(getActivity(), GreetingsActivity.class);
                break;
            case "phrases":
                intent = new Intent(getActivity(), PhrasesActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void refreshFragment() {
        navController = Navigation.findNavController(root);
        if (Global.selectedProfile != null) {
            goToHome = true;
            navController.navigate(R.id.homeToDash);
        }
        /*Toast.makeText(getContext(), "home " + lastActivity, Toast.LENGTH_SHORT).show(); // TODO
        if (lastActivity) {
            lastActivity = false;
            navController.navigate(R.id.homeToDash); // TODO fix: disables home when clicked but pressing back returns to home
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                navController.navigate(R.id.dashToHome);
                navController.navigate(R.id.navigation_home);
            }, 2000);
        }*/
    }
}