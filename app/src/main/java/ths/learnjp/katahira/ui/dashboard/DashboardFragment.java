package ths.learnjp.katahira.ui.dashboard;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ths.learnjp.katahira.CharacterManager;
import ths.learnjp.katahira.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        List <String> list = new ArrayList();
        list.add("behold");
        list.add("bend");
        list.add("bet");

//        List <String> listClone = new ArrayList<String>();
        for (String string : list) {
//            if(string.matches("(?i)(bea).*")){
            if(string.matches("bet")){
//                listClone.add(string);
                list.remove("bet");
            }
        }
        System.out.println(list);

//        ObjectAnimator colorAnim = ObjectAnimator.ofInt(null, "textColor", Color.BLACK, Color.WHITE); //you can change colors


        Button flash = binding.flash, stop = binding.stop, asd = binding.asd;
        flash.setOnClickListener(view -> {
            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
            animation.setDuration(500); // duration - half a second
            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
            animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
            animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
            asd.startAnimation(animation);
//            asd(colorAnim, asd2);
        });
        stop.setOnClickListener(view -> {
            asd.clearAnimation();

//            asd1(colorAnim, asd2);
        });

        return root;
    }

    private void asd(ObjectAnimator colorAnim, Button button) {

    }
    private void asd1(ObjectAnimator colorAnim, Button button) {
        colorAnim = ObjectAnimator.ofInt(button, "textColor", Color.BLACK, Color.WHITE); //you can change colors
        colorAnim.end();
        colorAnim.cancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}