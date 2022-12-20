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
    public static final String USER_RANK = "RANK";
    public static final String USER_GREETINGS = "USER_GREETINGS";
    public static final String USER_PHRASES = "USER_PHRASES";

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
        String createUserTable = "CREATE TABLE " + USER_TABLE + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT, " + USER_RANK + " TEXT, " + USER_GREETINGS + " INTEGER, " + USER_PHRASES + " INTEGER)";
        sqLiteDatabase.execSQL(createUserTable);
        String createSessionTable = "CREATE TABLE " + SESSION_TABLE + " (" + SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SYLLABARY + " TEXT, " + MISTAKES + " INTEGER, " + SCORE + " INTEGER, " + TIME + " TEXT, " + WRONG_CHARS + " TEXT, " + DATE_TIME + " TEXT, " + USER_ID + " INTEGER, " + " FOREIGN KEY(USER_ID) REFERENCES USER_TABLE (USER_ID)  ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createSessionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
                cv.put(USER_GREETINGS, userModel.getGreetings());
                cv.put(USER_PHRASES, userModel.getPhrases());
                db.insert(USER_TABLE, null, cv);
                cv.clear();
                break;
            case "addSession":
                cv.put(SYLLABARY, sessionModel.getSyllabary());
                cv.put(MISTAKES, sessionModel.getMistakes());
                cv.put(SCORE, sessionModel.getScore());
                cv.put(TIME, sessionModel.getTime());
                cv.put(WRONG_CHARS, sessionModel.getWrong_chars());
                cv.put(DATE_TIME, sessionModel.getDate_time());
                cv.put(USER_ID, sessionModel.getUser_id());
                db.insert(SESSION_TABLE, null, cv);
                break;
        }
    }

//    public void editProfile(String profile, String newData) {
    public void updateData(String tag, String profile, String newData) { // TODO
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String table = null;
        String where = null;
        switch (tag) {
            case "name":
//                cv.put(USER_NAME, newData);
//                table = USER_TABLE;
//                where = USER_NAME;
                cv.put(SCORE, 71); // TODO remove
                table = SESSION_TABLE; // TODO remove
                where = SESSION_ID; // TODO remove
                break;
            case "rank": // TODO
                cv.put(USER_RANK, newData);
                table = SESSION_TABLE;
                where = SESSION_ID;
                break;
            case"greetings":
                cv.put(USER_GREETINGS, newData);
                table = USER_TABLE;
                where = USER_NAME;
                break;
            case"phrases":
                table = USER_TABLE;
                where = USER_NAME;
                cv.put(USER_PHRASES, newData);
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
                int greetings = cursor.getInt(3);
                int phrases = cursor.getInt(4);

                String[] data = {String.valueOf(userId), userName, userRank, String.valueOf(greetings), String.valueOf(phrases)};
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
                String syllabary = cursor.getString(1);
                int mistakes = cursor.getInt(2);
                int score = cursor.getInt(3);
                String time = cursor.getString(4);
                String wrongChars = cursor.getString(5);
                String dateTime = cursor.getString(6);
                int userId = cursor.getInt(7);

                int numRows = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + SESSION_TABLE + " WHERE " + USER_ID + " = " + user_id, null);

                String[] data = {String.valueOf(sessionId), syllabary, String.valueOf(mistakes), String.valueOf(score), time, wrongChars, dateTime, String.valueOf(userId), String.valueOf(numRows)};
                stringList.addAll(Arrays.asList(data));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return stringList;
    }

    public List<SessionModel> getAllSessions(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<SessionModel> returnList = new ArrayList<>();

        String getAllQuery = "SELECT * FROM " + SESSION_TABLE + " WHERE " + USER_ID + " = " + user_id + " ORDER BY DATE_TIME DESC";
        Cursor cursor = db.rawQuery(getAllQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int sessionID = cursor.getInt(0); // TODO remove
                String syllabary = cursor.getString(1);
                int mistakes = cursor.getInt(2);
                int score = cursor.getInt(3);
                String time = cursor.getString(4);
                String wrongChars = cursor.getString(5);
                String dateTime = cursor.getString(6);
                int userID = cursor.getInt(7); // TODO remove

                SessionModel sessionModel = new SessionModel(sessionID, syllabary, mistakes, score, time, wrongChars, dateTime, userID);
                returnList.add(sessionModel);
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
