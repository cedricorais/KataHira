package ths.learnjp.katahira.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ths.learnjp.katahira.DBHelper;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;
import ths.learnjp.katahira.SessionModel;

public class HistoryActivity extends AppCompatActivity {

//    ListView listView; // TODO convert to table
    TableLayout tableLayout;

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DBHelper dbHelper = new DBHelper(this);
        List<String> userData = new ArrayList<>(dbHelper.getProfileData(Global.selectedProfile));

        /*listView = findViewById(R.id.lv); // TODO convert to table
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getAllSessions(Integer.parseInt(userData.get(0))));
        listView.setAdapter(arrayAdapter);*/

        tableLayout = findViewById(R.id.table);
//        String[] tableHeader = {"Session ID", "Date & Time", "Syllabary", "Mistakes", "Score", "Time", "Wrong Characters", "User ID"}; // TODO old
        String[] tableHeader = {"Date & Time", "Syllabary", "Mistakes", "Score", "Wrong Characters", "Time"}; // TODO new
        TableRow rowHead = new TableRow(this);
        for (String rowCount : tableHeader) {
            TextView columns = new TextView(this);
            columns.setGravity(Gravity.CENTER);
            columns.setTextAppearance(com.google.android.material.R.attr.textAppearanceHeadline6);
            columns.setTypeface(null, Typeface.BOLD);
            columns.setTextSize(18); // TODO change
            columns.setPadding(5, 5, 5, 5);
            columns.setText(rowCount);

            rowHead.addView(columns);
        }
        tableLayout.addView(rowHead);

        int columnCount = tableHeader.length;

        for (int rowCount = 0; rowCount < Global.total_session; rowCount++) { // TODO new
            TableRow row = new TableRow(this);

            List<String> allSession = new ArrayList<>(dbHelper.getAllSessions(Integer.parseInt(userData.get(0)))); // TODO new
            for (int nextColumn = columnCount * rowCount, i = 0; i < columnCount; i++, nextColumn++) { // TODO new
                TextView columns = new TextView(this);
                if (nextColumn == 5) {
                    columns.setLayoutParams(new TableRow.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
                } else {
                    columns.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
                columns.setTextAppearance(com.google.android.material.R.attr.colorSecondary);
                columns.setGravity(Gravity.CENTER);
                columns.setTextSize(18); // TODO change
                columns.setPadding(5, 5, 5, 5);
//                columns.setText(sessionList.get(nextColumn)); // TODO old
                columns.setText(allSession.get(nextColumn)); // TODO new

                row.addView(columns);
            }
            tableLayout.addView(row);
        }
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