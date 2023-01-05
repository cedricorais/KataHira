package ths.learnjp.katahira.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ths.learnjp.katahira.DBHelper;
import ths.learnjp.katahira.Global;
import ths.learnjp.katahira.R;

public class HistoryActivity extends AppCompatActivity {

    TableLayout tableLayout;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DBHelper dbHelper = new DBHelper(this);
        List<String> userData = new ArrayList<>(dbHelper.getProfileData(Global.selectedProfile));

        tableLayout = findViewById(R.id.table);
        String[] tableHeader = {"Date & Time", "Syllabary", "Mistakes", "Score", "Time", "Wrong Characters"};
        TableRow rowHead = new TableRow(this);
        for (String rowCount : tableHeader) {
            TextView columns = new TextView(this);
            columns.setGravity(Gravity.CENTER);
            columns.setTextAppearance(com.google.android.material.R.attr.textAppearanceHeadline6);
            columns.setTypeface(null, Typeface.BOLD);
            columns.setTextSize(18);
            columns.setPadding(5, 5, 5, 5);
            columns.setText(rowCount);

            rowHead.addView(columns);
        }
        tableLayout.addView(rowHead);

        int columnCount = tableHeader.length;

        for (int rowCount = 0; rowCount < Global.total_session; rowCount++) {
            TableRow row = new TableRow(this);

            List<String> allSession = new ArrayList<>(dbHelper.getAllSessions(Integer.parseInt(userData.get(0))));
            for (int nextColumn = columnCount * rowCount, i = 0; i < columnCount; i++, nextColumn++) {
                TextView columns = new TextView(this);
                columns.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                if (i == 5) {
                    columns.setMaxWidth(1000);
                }
                columns.setTextAppearance(com.google.android.material.R.attr.colorSecondary);
                columns.setGravity(Gravity.CENTER);
                columns.setTextSize(18);
                columns.setPadding(5, 5, 5, 5);
                columns.setText(allSession.get(nextColumn));

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