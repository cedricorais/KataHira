package ths.learnjp.katahira;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Parser {
    static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;

        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    public Map LoadFile(Context context, String fileName) {
        Map<?, ?> chara_set;
        Gson gson = new Gson();

        String jsonFileString = getJsonFromAssets(context, fileName);

        try {
            chara_set = gson.fromJson(jsonFileString, Map.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return chara_set;
    }
}
