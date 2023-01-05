package ths.learnjp.katahira.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ths.learnjp.katahira.DBHelper;
import ths.learnjp.katahira.FlashView;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.UserModel;
import ths.learnjp.katahira.databinding.FragmentDashboardBinding;
import ths.learnjp.katahira.ui.home.HomeFragment;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    Button guessBtn, historyBtn;
    FloatingActionButton fab, addFab, editFab, deleteFab;
    ImageView profileHelp, rankHelp;
    NavController navController;
    Snackbar snackbar;
    Spinner profileSpin;
    TextView rankText, totalSessionText, dateTimeText, syllabaryText, mistakesText, scoreText, timeText,
            greetingText, phraseText, addFabText, editFabText, deleteFabText;
    RelativeLayout main;
    androidx.appcompat.widget.Toolbar tb1, tb2;
    View root;

    boolean isAllFabVisible;
    String[] options = new String[]{"Select"};
    final List<String> selectProfile = new ArrayList<>(Arrays.asList(options));

    DBHelper dbHelper;
    FlashView flashView  = new FlashView();

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        dbHelper = new DBHelper(getContext());

        profileSpin = binding.profiles;

        profileHelp = binding.profileHelp;
        rankHelp = binding.rankHelp;

        rankText = binding.rankValue;
        totalSessionText = binding.totalSessionValue;
        greetingText = binding.greetingValue;
        phraseText = binding.phraseValue;

        dateTimeText = binding.dateValue;
        syllabaryText = binding.syllabaryValue;
        mistakesText = binding.mistakesValue;
        scoreText = binding.scoreValue;
        timeText = binding.timeValue;

        addFabText = binding.addText;
        editFabText = binding.editText;
        deleteFabText = binding.deleteText;

        guessBtn = binding.guess;
        historyBtn = binding.showHistory;

        fab = binding.profileFab;
        addFab = binding.addFab;
        editFab = binding.editFab;
        deleteFab = binding.deleteFab;

        selectProfile.addAll(dbHelper.getProfiles());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, selectProfile) {
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_item);
        profileSpin.setAdapter(adapter);
        profileSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    snackbar = Snackbar.make(root, String.format(getString(R.string.profile_selected), selectedText), Snackbar.LENGTH_LONG);
                    snackbar.setAnchorView(R.id.nav_view).setAction(R.string.close, view1 -> snackbar.dismiss()).show();
                    selectedUser(selectedText);
                } else {
                    flashView.stopFlash(guessBtn);
                    setGlobalValues("noData", null);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        guessBtn.setOnClickListener(view -> {
            hideFabMenus();
            Intent intent = new Intent(getContext(), GuessActivity.class);
            startActivity(intent);
        });
        historyBtn.setOnClickListener(view -> {
            hideFabMenus();
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            startActivity(intent);
        });

        boolean hasData = dbHelper.checkTableData("USER_TABLE");
        if (hasData) {
            flashView.stopFlash(fab);
        } else {
            flashView.startFlash(fab);
            showHelp("profile", getActivity());
        }

        isAllFabVisible = false;
        hideFabMenus();

        fab.setOnClickListener(view -> {
            flashView.stopFlash(fab);
            if (!isAllFabVisible) {
                hideFabMenus();
            } else {
                showFabMenus();
            }
        });
        addFab.setOnClickListener(view -> {
            hideFabMenus();
            addProfile("new", null);
        });
        editFab.setOnClickListener(view -> {
            hideFabMenus();
            chooseProfile("edit");
        });
        deleteFab.setOnClickListener(view -> {
            hideFabMenus();
            chooseProfile("delete");
        });

        profileHelp.setOnClickListener(view -> {
            hideFabMenus();
            showHelp("profile", getActivity());
        });
        rankHelp.setOnClickListener(view -> {
            hideFabMenus();
            showHelp("rank", null);
        });

        main = binding.main;
        tb1 = binding.tb1;
        tb2 = binding.tb2;
        main.setOnClickListener(view -> hideFabMenus());
        profileSpin.setOnTouchListener((view, motionEvent) -> {
            hideFabMenus();
            return false;
        });
        tb1.setOnClickListener(view -> hideFabMenus());
        tb2.setOnClickListener(view -> hideFabMenus());

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
        if (Global.selectedProfile != null) {
            selectedUser(Global.selectedProfile);
        }

        if (HomeFragment.goToHome) {
            HomeFragment.goToHome = false;
            navController = Navigation.findNavController(root);
            navController.navigate(R.id.navigation_home);
        }
    }

    private void refreshFragment() {
        navController = Navigation.findNavController(root);
        navController.navigate(R.id.navigation_dashboard);
    }

    private void selectedUser(String selectedText) {
        Global.selectedProfile = selectedText;

        List<String> userData = new ArrayList<>(dbHelper.getProfileData(Global.selectedProfile));
        Global.selectedProfileId = Integer.parseInt(userData.get(0));
        List<String> combined = new ArrayList<>(userData);
        combined.addAll(dbHelper.getSessionData(Global.selectedProfileId));
        System.out.println(combined);
        try {
            flashView.stopFlash(guessBtn);
            guessBtn.setEnabled(true);
            historyBtn.setEnabled(true);
            setGlobalValues("hasData", combined);
        } catch (Exception e) {
            System.out.println(e);
            flashView.startFlash(guessBtn);
            setGlobalValues("noData", userData);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setGlobalValues(String tag, List<String> data) {
        switch (tag) {
            case "noData":
                if (data == null) {
                    Global.selectedProfile = null;
                    Global.rank = getResources().getString(R.string.n_a);
                    Global.session_perfect_kata = 0;
                    Global.session_perfect_hira = 0;
                    Global.greetings_progress = 0;
                    Global.phrases_progress = 0;

                    Global.greetings.clear();
                    Global.phrases.clear();
                    for (String key : Global.keys) {
                        Global.greetings.put(key, false);
                        Global.phrases.put(key, false);
                    }

                    guessBtn.setEnabled(false);
                } else {
                    getData(data);

                    guessBtn.setEnabled(true);
                }
                Global.total_session = 0;

                Global.syllabary = getResources().getString(R.string.n_a);
                Global.session_mistake = 0;
                Global.session_score = 0;
                Global.latestTime = getResources().getString(R.string.initTime);
                Global.dateTimeNow = getResources().getString(R.string.n_a);

                historyBtn.setEnabled(false);
                break;
            case "hasData":
                getData(data);

                Global.dateTimeNow = data.get(10);
                Global.syllabary = data.get(11);
                Global.session_mistake = Integer.parseInt(data.get(12));
                Global.session_score = Integer.parseInt(data.get(13));
                Global.latestTime = data.get(14);
                Global.total_session = Integer.parseInt(data.get(17));
                break;
        }

        rankText.setText(Global.rank);
        totalSessionText.setText(Integer.toString(Global.total_session));
        greetingText.setText(Global.greetings_progress + "/10");
        phraseText.setText(Global.phrases_progress + "/10");

        syllabaryText.setText(Global.syllabary);
        mistakesText.setText(Integer.toString(Global.session_mistake));
        scoreText.setText(Integer.toString(Global.session_score));
        timeText.setText(Global.latestTime);
        dateTimeText.setText(Global.dateTimeNow);
    }

    private void getData(List<String> data) {
        Global.rank = data.get(2);
        Global.session_perfect_kata = Integer.parseInt(data.get(3));
        Global.session_perfect_hira = Integer.parseInt(data.get(4));
        Global.greetings_progress = Integer.parseInt(data.get(5));
        Global.phrases_progress = Integer.parseInt(data.get(6));

        Global.greetings.clear();
        Global.phrases.clear();
        String greets = data.get(7).substring(1, data.get(7).length() - 1);
        String[] split_greets = greets.split(", ");
        for (String pair : split_greets) {
            String[] keyValue = pair.split("=");
            Global.greetings.put(keyValue[0], Boolean.valueOf(keyValue[1]));
        }
        String phrase = data.get(8).substring(1, data.get(8).length() - 1);
        String[] split_phrase = phrase.split(", ");
        for (String pair : split_phrase) {
            String[] keyValue = pair.split("=");
            Global.phrases.put(keyValue[0], Boolean.valueOf(keyValue[1]));
        }
        System.out.println(Global.greetings + " " + Global.phrases);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showFabMenus(){
        isAllFabVisible = false;
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24, getContext().getTheme()));
        addFab.show();
        editFab.show();
        deleteFab.show();

        addFabText.setVisibility(View.VISIBLE);
        editFabText.setVisibility(View.VISIBLE);
        deleteFabText.setVisibility(View.VISIBLE);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void hideFabMenus(){
        isAllFabVisible = true;
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_24, getContext().getTheme()));
        addFab.hide();
        editFab.hide();
        deleteFab.hide();

        addFabText.setVisibility(View.GONE);
        editFabText.setVisibility(View.GONE);
        deleteFabText.setVisibility(View.GONE);
    }

    private void addProfile(String mode, String profileText) {
        AlertDialog.Builder addBuilder = new AlertDialog.Builder(getActivity());
        final TextInputEditText inputText = new TextInputEditText(getContext());
        inputText.setHint(R.string.profile_name);
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
        addBuilder.setCancelable(false);

        String toastDone = null, toastOngoing = null;
        switch (mode) {
            case "new":
                addBuilder.setTitle(R.string.add_new_profile);
                addBuilder.setPositiveButton(R.string.add, null);
                toastDone = getString(R.string.profile_added);
                toastOngoing = getString(R.string.profile_exist);
                break;
            case "edit":
                addBuilder.setTitle(R.string.rename_profile);
                addBuilder.setPositiveButton(R.string.set, null);
                inputText.setText(profileText);
                toastDone = getString(R.string.profile_renamed);
                toastOngoing = getString(R.string.profile_unique);
                break;
        }
        addBuilder.setView(inputText);
        addBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        invalidDialog(addBuilder, inputText, mode, profileText, toastDone, toastOngoing, "addEdit", null, null);
    }

    private void chooseProfile(String mode) {
        selectProfile.remove("Select");

        AlertDialog.Builder chooseBuilder = new AlertDialog.Builder(getActivity());
        final int[] checkedItem = {-1};
        final String[] profileList = selectProfile.toArray(new String[0]);
        final String[] selectedItem = new String[1];
        chooseBuilder.setCancelable(false);

        if (profileList.length > 0){
            chooseBuilder.setSingleChoiceItems(profileList, checkedItem[0], (dialog, which) -> {
                checkedItem[0] = which;
                selectedItem[0] = profileList[which];
            });

            switch (mode) {
                case "edit":
                    chooseBuilder.setTitle(R.string.select_rename);
                    invalidDialog(chooseBuilder.setPositiveButton(R.string.select, null)
                            .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel()),
                            null, "edit", null, null, null,
                            "choose", checkedItem, selectedItem);
                    break;
                case "delete":
                    chooseBuilder.setTitle(R.string.select_delete);
                    invalidDialog(chooseBuilder.setPositiveButton(R.string.select, null)
                            .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel()),
                            null, "delete", null, null, null,
                            "choose", checkedItem, selectedItem);
                    break;
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.require_add), Toast.LENGTH_SHORT).show();
        }

        selectProfile.add(0, "Select");
    }

    private void invalidDialog(AlertDialog.Builder alert, TextInputEditText textInputEditText, String mode, String chosenProfile,
                              String toastDoneText, String toastOngoingText, String tag, int[] checked, String[] selectedProfile) {
        dbHelper = new DBHelper(getContext());
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

        final String finalToastDone = toastDoneText, finalToastOngoing = toastOngoingText;
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            switch (tag) {
                case "addEdit":
                    boolean closeDialog = textInputEditText.getText().toString().trim().isEmpty();
                    if (!closeDialog) {
                        alertSave(textInputEditText, mode, chosenProfile, alertDialog, finalToastDone, finalToastOngoing);
                    } else {
                        Toast.makeText(getContext(), R.string.empty_name, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "choose":
                    boolean check = checked[0] == -1;
                    if (!check && mode.equals("edit")) {
                        profileSpin.setSelection(0);
                        addProfile("edit", selectedProfile[0]);
                        alertDialog.dismiss();
                    } else if (!check && mode.equals("delete")) {
                        profileSpin.setSelection(0);
                        alertDelete(selectedProfile[0], alertDialog);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.require_select), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        });
    }

    private void alertDelete(String selected, AlertDialog alertDialog) {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
        confirmBuilder.setCancelable(false);
        confirmBuilder.setTitle(String.format(getString(R.string.confirm_delete), selected));
        confirmBuilder.setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
            dbHelper.deleteProfile(selected);
            snackbar = Snackbar.make(root, R.string.profile_deleted, Snackbar.LENGTH_LONG);
            snackbar.setAnchorView(R.id.nav_view).setAction(R.string.close, view -> snackbar.dismiss()).show();
            alertDialog.dismiss();
            refreshFragment();
        });
        confirmBuilder.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.cancel()).show();
        alertDialog.dismiss();
    }

    private void alertSave(TextInputEditText textInputEditText, String mode, String chosenProfile, AlertDialog alertDialog,
                           String finalToastDone, String finalToastOngoing) {
        Set<String> set = new HashSet<>(selectProfile);
        if(set.contains(textInputEditText.getText().toString())){
            Toast.makeText(getContext(), finalToastOngoing, Toast.LENGTH_SHORT).show();
        } else {
            switch (mode) {
                case "new":
                    Global.greetings.clear();
                    Global.phrases.clear();
                    for (String key : Global.keys) {
                        Global.greetings.put(key, false);
                        Global.phrases.put(key, false);
                    }
                    UserModel userModel = new UserModel(-1, textInputEditText.getText().toString(), "Beginner",
                            0, 0, 0, 0, Global.greetings, Global.phrases);
                    dbHelper.addOne("newUser", userModel, null);
                    refreshFragment();
                    break;
                case "edit":
                    dbHelper.updateData("name", chosenProfile, textInputEditText.getText().toString());
                    refreshFragment();
                    break;
            }
            snackbar = Snackbar.make(root, finalToastDone, Snackbar.LENGTH_LONG);
            snackbar.setAnchorView(R.id.nav_view).setAction(R.string.close, view -> snackbar.dismiss()).show();
            alertDialog.dismiss();
        }
    }

    private void showHelp(String tag, Activity activity) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(false);

        switch (tag) {
            case "profile":
                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.tutorial_layout, activity.findViewById(R.id.main), false);

                ImageView img = view.findViewById(R.id.image);
                Integer[] pics = new Integer[]{R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5};
                List<Integer> tutorialPics = new ArrayList<>(Arrays.asList(pics));
                img.setImageResource(tutorialPics.get(0));

                TextView text = view.findViewById(R.id.text);
                String[] texts = new String[]{getString(R.string.profile1),
                        getString(R.string.profile2), getString(R.string.profile3),
                        getString(R.string.profile4), getString(R.string.profile5)};
                List<String> tutorialText = new ArrayList<>(Arrays.asList(texts));
                text.setText(tutorialText.get(0));

                alert.setTitle(R.string.profiles_title);
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
                break;
            case "rank":
                alert.setTitle(R.string.rank_prerequisite);
                alert.setMessage(R.string.ranks);
                alert.setNeutralButton(R.string.close, (dialogInterface, i) -> dialogInterface.dismiss());
                alert.show();
                break;
        }
    }
}