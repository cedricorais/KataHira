package ths.learnjp.katahira;

import android.content.Context;

import java.util.Map;

public class CharaManager {
    public static void getLanguages() {
        // Return list of languages in Languages.json
    }

    public static void getCharacters(String language, String chara_set, Context context) {
        // get character set in a syllabary/alphabet

        String language_filename = language + ".json";

        Map charaFile = Parser.LoadFile(context, language_filename);
    }
}
