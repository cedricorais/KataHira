package ths.learnjp.katahira.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ths.learnjp.katahira.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    Button kataBtn, hiraBtn, greetingsBtn, phrasesBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        greetingsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GreetingsActivity.class);
            startActivity(intent);
        });
        phrasesBtn = binding.showPhrases;
        phrasesBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), PhrasesActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}