package ths.learnjp.katahira.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ths.learnjp.katahira.DBHelper;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.UserModel;
import ths.learnjp.katahira.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    Button guessBtn, historyBtn;
    FloatingActionButton fab, addFab, editFab, deleteFab;
    RecyclerView data;
    Spinner profileSpin;
    TextView rankTxt, totalSessionTxt, dateTimeTxt, syllabaryTxt, mistakesTxt, scoreTxt, timeTxt, addTxt, editTxt, deleteTxt;

    boolean isAllFabVisible;
    String[] options = new String[]{"Select"};
    final List<String> selectProfile = new ArrayList<>(Arrays.asList(options));

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        rankTxt = binding.rankValue;
        totalSessionTxt = binding.totalSessionValue;

        dateTimeTxt = binding.dateValue;
        syllabaryTxt = binding.syllabaryValue;
        mistakesTxt = binding.mistakesValue;
        scoreTxt = binding.scoreValue;
        timeTxt = binding.timeValue;

        addTxt = binding.addTxt;
        editTxt = binding.editTxt;
        deleteTxt = binding.deleteTxt;

        profileSpin = binding.profiles;

        guessBtn = binding.guess;
        historyBtn = binding.showHistory;

        fab = binding.profileFab;
        addFab = binding.addFab;
        editFab = binding.editFab;
        deleteFab = binding.deleteFab;

        data = binding.rv;

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
                    guessBtn.setEnabled(true);
                    Toast.makeText(getContext(), String.format("'%s' is now selected", selectedText), Toast.LENGTH_SHORT).show();
                    onResume();
                } else {
//                    guessBtn.setEnabled(false); // TODO remove comment
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // some code
            }
        });

        guessBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), GuessActivity.class);
            startActivity(intent);
        });
        historyBtn.setOnClickListener(view -> { // TODO show history menu
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            startActivity(intent);
            Global.total_session++; // TODO remove
        });

        isAllFabVisible = false;
        addFab.hide();
        editFab.hide();
        deleteFab.hide();

        addTxt.setVisibility(View.GONE);
        editTxt.setVisibility(View.GONE);
        deleteTxt.setVisibility(View.GONE);

        fab.setOnClickListener(view -> {
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

        return root;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onResume(){
        rankTxt.setText("bruh"); // TODO remove
        totalSessionTxt.setText(Integer.toString(Global.total_session));

        dateTimeTxt.setText(Global.dateTimeNow);
        syllabaryTxt.setText(Global.syllabary);
        mistakesTxt.setText(Integer.toString(Global.session_mistake));
        scoreTxt.setText(Integer.toString(Global.session_score));
        timeTxt.setText(Global.latestTime);

        hideFabMenus();
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void showFabMenus(){
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24, getContext().getTheme()));
        addFab.show();
        editFab.show();
        deleteFab.show();

        addTxt.setVisibility(View.VISIBLE);
        editTxt.setVisibility(View.VISIBLE);
        deleteTxt.setVisibility(View.VISIBLE);
        isAllFabVisible = false;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void hideFabMenus(){
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_24, getContext().getTheme()));
        addFab.hide();
        editFab.hide();
        deleteFab.hide();

        addTxt.setVisibility(View.GONE);
        editTxt.setVisibility(View.GONE);
        deleteTxt.setVisibility(View.GONE);
        isAllFabVisible = true;
    }

    public void addProfile(String mode, String text) {
        AlertDialog.Builder addBuilder = new AlertDialog.Builder(getActivity());
        final TextInputEditText inputText = new TextInputEditText(getContext());
        inputText.setHint(R.string.profile_name);
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);

        String toastDone = null, toastOngoing = null; // TODO rename?
        switch (mode) {
            case "new":
                addBuilder.setTitle(R.string.add_new_profile);
                addBuilder.setPositiveButton(R.string.add, null);
                toastDone = getString(R.string.profile_added);
                toastOngoing = getString(R.string.profile_exist);
                break;
            case "edit":
                addBuilder.setTitle(R.string.edit_profile);
                addBuilder.setPositiveButton(R.string.set, null);
                inputText.setText(text);
                toastDone = getString(R.string.profile_edited);
                toastOngoing = getString(R.string.profile_unique);
                break;
        }
        addBuilder.setView(inputText)
                .setCancelable(false)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.cancel();
                });

        invalidDialog(addBuilder, inputText, mode, text, toastDone, toastOngoing, "addedit", null, null);
    }
    public void chooseProfile(String mode) {
        selectProfile.remove("Select");
//        selectProfile.add("das");

        AlertDialog.Builder chooseBuilder = new AlertDialog.Builder(getActivity());
        final int[] checkedItem = {-1};
        final String[] listItems = selectProfile.toArray(new String[0]);
        final String[] selectedItem = new String[1];

        if (listItems.length > 0){
            chooseBuilder.setCancelable(false);
            chooseBuilder.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
                checkedItem[0] = which;
                selectedItem[0] = listItems[which];
            });

            switch (mode) {
                case "edit":
                    chooseBuilder.setTitle(R.string.select_edit);
                    invalidDialog(chooseBuilder.setPositiveButton(R.string.select, null)
                            .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel()),
                            null,
                            "edit",
                            null,
                            null,
                            null,
                            "choose",
                            checkedItem,
                            selectedItem);
                    break;
                case "delete":
                    chooseBuilder.setTitle(R.string.select_delete);
                    invalidDialog(chooseBuilder.setPositiveButton(R.string.select, null)
                            .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel()),
                            null,
                            "delete",
                            null,
                            null,
                            null,
                            "choose",
                            checkedItem,
                            selectedItem);
                    break;
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.require_add), Toast.LENGTH_SHORT).show();
        }

        selectProfile.add(0, "Select");
    }
    public void invalidDialog(AlertDialog.Builder alert,
                              TextInputEditText textInputEditText,
                              String mode,
                              String text,
                              String toastDoneText,
                              String toastOngoingText,
                              String mode1,
                              int[] check,
                              String[] select) {
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
        final String finalToast = toastDoneText, finalToast1 = toastOngoingText; // TODO rename?
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            switch (mode1) {
                case "addedit":
                    boolean wantToCloseDialog = textInputEditText.getText().toString().trim().isEmpty();
                    if (!wantToCloseDialog) {
                        Set<String> set = new HashSet<>(selectProfile);
                        if(set.contains(textInputEditText.getText().toString())){
                            Toast.makeText(getContext(), finalToast1, Toast.LENGTH_SHORT).show();
                        } else {
                            switch (mode) {
                                case "new":
                                    UserModel userModel = new UserModel(-1, textInputEditText.getText().toString().toString(), 0);
                                    DBHelper dbHelper = new DBHelper(getContext());
                                    Toast.makeText(getContext(), userModel.toString(), Toast.LENGTH_LONG).show();
                                    selectProfile.add(textInputEditText.getText().toString()); // TODO add to db
                                    break;
                                case "edit":
                                    selectProfile.set(selectProfile.indexOf(text), textInputEditText.getText().toString()); // TODO add to db
                                    break;
                            }

                            Toast.makeText(getContext(), finalToast, Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.empty_name, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "choose":
                    boolean idk = check[0] == -1;
                    if (!idk && mode.equals("edit")) {
                        addProfile("edit", select[0]);
                        alertDialog.dismiss();
                    } else if (!idk && mode.equals("delete")) {
                        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
                        confirmBuilder.setTitle(String.format(getString(R.string.confirm_delete), select[0]))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
                                    selectProfile.remove(select[0]); // TODO delete to db
                                    Toast.makeText(getContext(), R.string.profile_deleted, Toast.LENGTH_SHORT).show();
                                    profileSpin.setSelection(0);
                                    alertDialog.dismiss();
                                })
                                .setNegativeButton(getString(R.string.no), (dialogInterface, i) -> {
                                    dialogInterface.cancel();
                                }).show();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.require_select), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        });
    }
}