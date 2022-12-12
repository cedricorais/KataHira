package ths.learnjp.katahira.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
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

    ListView listView; // TODO convert to table
    TableLayout tableLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.lv); // TODO convert to table

        DBHelper dbHelper = new DBHelper(this);
        List<String> userData = new ArrayList<>(dbHelper.getProfileData(Global.selectedProfile));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getAllSessions(Integer.parseInt(userData.get(0))));
        listView.setAdapter(arrayAdapter);

        tableLayout = findViewById(R.id.table);
        String[] tableHeader = {"Session ID", "Syllabary", "Mistakes", "Score", "Time", "Wrong Characters", "Date & Time", "User ID"};
        TableRow rowHead = new TableRow(this);
        for (int rowCount = 0; rowCount < tableHeader.length; rowCount++) {
            TextView columns = new TextView(this);
            columns.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            columns.setBackground(getDrawable(R.drawable.border_table));
            columns.setGravity(Gravity.CENTER);
//            columns.setTextColor(Color.RED); // TODO change
            columns.setTextSize(10); // TODO change
            columns.setPadding(5, 5, 5, 5);
            columns.setText(tableHeader[rowCount]);

            rowHead.addView(columns);
        }
        tableLayout.addView(rowHead);

        List<SessionModel> allSession = new ArrayList<>(dbHelper.getAllSessions(Integer.parseInt(userData.get(0))));
        /*String[] asd = {"Session ID", "Session ID", "Session ID", "Syllabary", "Syllabary", "Syllabary", "Mistakes", "Mistakes", "Mistakes", "Score", "Score", "Score", "Time", "Time", "Time", "Wrong Characters", "Wrong Characters", "Wrong Characters", "Date & Time", "Date & Time", "Date & Time", "User ID", "User ID", "User ID"};
        List<String> sessionList = new ArrayList<>(Arrays.asList(asd));*/ // TODO vertical rows test
        List<String> sessionList = new ArrayList<>();
        for (int a = 0; a < allSession.size(); a++) {
            sessionList.addAll(Arrays.asList(allSession.get(a).toString().split(",(?![^\\(\\[]*[\\]\\)])")));
        }
        System.out.println(sessionList.size() + " " + sessionList); // TODO remove

        for (int rowCount = 0; rowCount < allSession.size(); rowCount++) {
            TableRow row = new TableRow(this);

            for (int columnCount = 0; columnCount < sessionList.size(); columnCount++) {
                TextView columns = new TextView(this);
                columns.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                columns.setGravity(Gravity.CENTER);
                columns.setTextColor(Color.GRAY); // TODO change
                columns.setTextSize(10); // TODO change
                columns.setPadding(5, 5, 5, 5);
                columns.setText(sessionList.get(columnCount)); // TODO fix text dupes

                row.addView(columns);
            }

            /*TextView columns = new TextView(this); // TODO vertical rows test
            columns.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            columns.setGravity(Gravity.CENTER);
            columns.setTextColor(Color.WHITE); // TODO change
            columns.setTextSize(10); // TODO change
            columns.setPadding(5, 5, 5, 5);
            columns.setText(sessionList.get(rowCount)); // TODO text dupes
            row.addView(columns);

            TextView columns1 = new TextView(this);
            columns1.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            columns1.setGravity(Gravity.CENTER);
            columns1.setTextColor(Color.WHITE); // TODO change
            columns1.setTextSize(10); // TODO change
            columns1.setPadding(5, 5, 5, 5);
            columns1.setText(sessionList.get(rowCount + 3)); // TODO text dupes
            row.addView(columns1);*/

            tableLayout.addView(row);
        }

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
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