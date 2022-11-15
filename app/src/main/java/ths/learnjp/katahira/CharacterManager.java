package ths.learnjp.katahira;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CharacterManager {
    public static String[] getLanguages(Context context) {
        // Return list of languages in Languages.json
        Map languageMap = Parser.LoadFile(context, "Languages.json");

        List<String> list = (List<String>) languageMap.get("list");

        String[] languageList = list.toArray(new String[0]);

        return languageList;
    }

    public static Map loadLanguage(Context context, String language_name) {
        Map language = Parser.LoadFile(context, language_name + ".json");

        return language;
    }

/*    public static Map getCharacters(String language, String _chara_set, Context context) {
        // get character set in a syllabary/alphabet

        String language_filename = language + ".json";

        Map charaFile = Parser.LoadFile(context, language_filename);

        Map chara_set = (Map) ((Map) charaFile.get("chara_sets")).get(_chara_set);

        return chara_set;
    }*/
}
