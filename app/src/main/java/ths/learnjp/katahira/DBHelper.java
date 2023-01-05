package ths.learnjp.katahira;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_RANK = "USER_RANK";
    public static final String USER_PERFECTS_KATA = "USER_PERFECTS_KATA";
    public static final String USER_PERFECTS_HIRA = "USER_PERFECTS_HIRA";
    public static final String GREETINGS_DONE = "GREETINGS_DONE";
    public static final String PHRASES_DONE = "PHRASES_DONE";
    public static final String GREETINGS = "GREETINGS";
    public static final String PHRASES = "PHRASES";

    public static final String SESSION_TABLE = "SESSION_TABLE";
    public static final String SESSION_ID = "SESSION_ID";
    public static final String TIME = "TIME";
    public static final String DATE_TIME = "DATE_TIME";
    public static final String SYLLABARY = "SYLLABARY";
    public static final String MISTAKES = "MISTAKES";
    public static final String SCORE = "SCORE";
    public static final String WRONG_CHARS = "WRONG_CHARS";

    public DBHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE " + USER_TABLE + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT, " + USER_RANK + " TEXT, " + USER_PERFECTS_KATA + " INTEGER, " + USER_PERFECTS_HIRA + " INTEGER, " + GREETINGS_DONE + " INTEGER, " + PHRASES_DONE + " INTEGER, " + GREETINGS + " TEXT, " + PHRASES + " TEXT)";
        sqLiteDatabase.execSQL(createUserTable);
        String createSessionTable = "CREATE TABLE " + SESSION_TABLE + " (" + SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE_TIME + " TEXT, " + SYLLABARY + " TEXT, " + MISTAKES + " INTEGER, " + SCORE + " INTEGER, " + TIME + " TEXT, " + WRONG_CHARS + " TEXT, " + USER_ID + " INTEGER, " + " FOREIGN KEY(USER_ID) REFERENCES USER_TABLE (USER_ID)  ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createSessionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SESSION_TABLE);

        onCreate(sqLiteDatabase);
    }

    public boolean checkTableData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();

        String countQuery = "SELECT COUNT(*) FROM " + table;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int getCount = cursor.getInt(0);

        return getCount > 0;
    }

    public void addOne(String tag, UserModel userModel, SessionModel sessionModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        switch (tag) {
            case "newUser":
                cv.put(USER_NAME, userModel.getName());
                cv.put(USER_RANK, userModel.getRank());
                cv.put(USER_PERFECTS_KATA, userModel.getPerfect_kata());
                cv.put(USER_PERFECTS_HIRA, userModel.getPerfect_hira());
                cv.put(GREETINGS_DONE, userModel.getGreetings_done());
                cv.put(PHRASES_DONE, userModel.getPhrases_done());
                cv.put(GREETINGS, String.valueOf(userModel.getGreetings()));
                cv.put(PHRASES, String.valueOf(userModel.getPhrases()));
                db.insert(USER_TABLE, null, cv);
                cv.clear();
                break;
            case "addSession":
                cv.put(DATE_TIME, sessionModel.getDate_time());
                cv.put(SYLLABARY, sessionModel.getSyllabary());
                cv.put(MISTAKES, sessionModel.getMistakes());
                cv.put(SCORE, sessionModel.getScore());
                cv.put(TIME, sessionModel.getTime());
                cv.put(WRONG_CHARS, sessionModel.getWrong_chars());
                cv.put(USER_ID, sessionModel.getUser_id());
                db.insert(SESSION_TABLE, null, cv);
                break;
        }
    }

    public void updateData(String tag, String profile, String newData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String table = null, where = null;
        switch (tag) {
            case "name":
                cv.put(USER_NAME, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
            case "rank":
                cv.put(USER_RANK, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
            case "perfect_kata":
                cv.put(USER_PERFECTS_KATA, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
            case "perfect_hira":
                cv.put(USER_PERFECTS_HIRA, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
            case"greetings_done":
                cv.put(GREETINGS_DONE, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
            case"phrases_done":
                cv.put(PHRASES_DONE, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
            case"greetings":
                cv.put(GREETINGS, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
            case"phrases":
                cv.put(PHRASES, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
        }
        db.update(table, cv, where + " = ?", new String[]{profile});
    }

    public List<String> getProfiles() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> list = new ArrayList<>();

        String getUserNamesQuery = "SELECT " + USER_NAME + " FROM " + USER_TABLE;
        Cursor cursor = db.rawQuery(getUserNamesQuery, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public List<String> getProfileData(String user_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> stringList = new ArrayList<>();

        String get1Query = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + " = '" + user_name + "'";
        Cursor cursor = db.rawQuery(get1Query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int userId = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userRank = cursor.getString(2);
                int perfect_kata = cursor.getInt(3);
                int perfect_hira = cursor.getInt(4);
                int greetings_done = cursor.getInt(5);
                int phrases_done = cursor.getInt(6);
                String greetings = cursor.getString(7);
                String phrases = cursor.getString(8);

                String[] data = {String.valueOf(userId), userName, userRank, String.valueOf(perfect_kata), String.valueOf(perfect_hira), String.valueOf(greetings_done), String.valueOf(phrases_done), greetings, phrases};
                stringList.addAll(Arrays.asList(data));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return stringList;
    }

    public List<String> getSessionData(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> stringList = new ArrayList<>();

        String get1Query = "SELECT * FROM " + SESSION_TABLE + " WHERE " + USER_ID + " = " + user_id + " ORDER BY DATE_TIME DESC LIMIT 1";
        Cursor cursor = db.rawQuery(get1Query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int sessionId = cursor.getInt(0);
                String dateTime = cursor.getString(1);
                String syllabary = cursor.getString(2);
                int mistakes = cursor.getInt(3);
                int score = cursor.getInt(4);
                String time = cursor.getString(5);
                String wrongChars = cursor.getString(6);
                int userId = cursor.getInt(7);

                int numRows = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + SESSION_TABLE + " WHERE " + USER_ID + " = " + user_id, null);

                String[] data = {String.valueOf(sessionId), dateTime, syllabary, String.valueOf(mistakes), String.valueOf(score), time, wrongChars, String.valueOf(userId), String.valueOf(numRows)};
                stringList.addAll(Arrays.asList(data));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return stringList;
    }

    public List<String> getAllSessions(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnList = new ArrayList<>();

        String getAllQuery = "SELECT * FROM " + SESSION_TABLE + " WHERE " + USER_ID + " = " + user_id + " ORDER BY DATE_TIME DESC";
        Cursor cursor = db.rawQuery(getAllQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String dateTime = cursor.getString(1);
                String syllabary = cursor.getString(2);
                int mistakes = cursor.getInt(3);
                int score = cursor.getInt(4);
                String time = cursor.getString(5);
                String wrongChars = cursor.getString(6);

                String[] data = {dateTime, syllabary, String.valueOf(mistakes), String.valueOf(score), time, wrongChars};
                returnList.addAll(Arrays.asList(data));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public void deleteProfile(String user_name) {
        SQLiteDatabase db = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM " + USER_TABLE + " WHERE " + USER_NAME + " = '" + user_name + "'";
        db.rawQuery("PRAGMA foreign_keys = ON", null);
        Cursor cursor = db.rawQuery(deleteQuery, null);
        cursor.moveToFirst();

        cursor.close();
        db.close();
    }
}
