package ths.learnjp.katahira;

import android.content.Context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CharacterManager {
    public static String[] getLanguages(Context context) {
        // Return list of languages in Languages.json
        Map languageMap = Parser.LoadFile(context, "Languages.json");

        List<String> list = (List<String>) languageMap.get("list");

        String[] languageList = list.toArray(new String[0]);

        return languageList;
    }

    public static void setLanguage(Context context, String language_name) {
        Map language = Parser.LoadFile(context, language_name + ".json");

        Global.language = language;
        Global.language_name = language_name;
        // TODO Save language settings in file
    }

    public static void setCharaSet(String _chara_set) {
        Map chara_sets = (Map) Global.language.get("chara_sets");
        Map chara_set = (Map) chara_sets.get(_chara_set);
//        List<String> chara_set_list = (List<String>) chara_sets.get(_chara_set);
//        String[] chara_set = chara_set_list.toArray(new String[0]);
        Global.chara_set = chara_set;
        Global.chara_set_name = _chara_set;

        Global.session_set = new HashMap<>(chara_set);
        Global.score_session_set = new HashMap();
    }

    public static String[] getCharaSetNames() {
        Map chara_sets = (Map) Global.language.get("chara_sets");
        String[] string_chara_sets = Arrays.stream(chara_sets.keySet().toArray()).toArray(String[]::new);
        return string_chara_sets;
    }

    public static void resetSession() {
        Global.session_set = new HashMap<>(Global.chara_set);
        Global.score_session_set = new HashMap();
        Global.session_attempts_left = Global.session_set.size();
        Global.session_score = 0;
    }

    public static void removeCharFromSession(String key) {
        Global.session_set.remove(key);
        Global.score_session_set.remove(key);

    }

    public static Map tempRemoveCharFromCharaSet(String char_to_remove) {
        Map temp_map = new HashMap<>(Global.chara_set);
        temp_map.remove(char_to_remove);

        return temp_map;
    }

    public static Map createTempCharaSet() {
        Map temp_map = new HashMap<>(Global.session_set);
        return temp_map;
    }

    public static String getAnswer(String key) {
        return (String) Global.chara_set.get(key);
    }

/*    public static Map getCharacters(String language, String _chara_set, Context context) {
        // get character set in a syllabary/alphabet

        String language_filename = language + ".json";

        Map charaFile = Parser.LoadFile(context, language_filename);

        Map chara_set = (Map) ((Map) charaFile.get("chara_sets")).get(_chara_set);

        return chara_set;
    }*/
}
