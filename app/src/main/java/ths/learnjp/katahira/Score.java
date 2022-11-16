package ths.learnjp.katahira;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Score {
    static HashMap score = new HashMap();
    public static void setCharaScore(String character, int new_score) {
        score.putIfAbsent(Global.language_name, new HashMap());
        Map language_score = (HashMap) score.get(Global.language_name);

        language_score.putIfAbsent(Global.chara_set_name, new HashMap());
        Map chara_set_score = (HashMap) language_score.get(Global.chara_set_name);

        chara_set_score.put(character, new_score);
    }

    public static void resetScore() {
        Map chara_set = Global.chara_set;
        Iterator<String> it = chara_set.keySet().iterator();

        while(it.hasNext()) {
            String key = (String) it.next();
            setCharaScore(key, 1);
        }
    }

    public static Map getCharaSetScore() {
        if (score.containsKey(Global.language_name)) {
            Map language_score = (HashMap) score.get(Global.language_name);
            if (language_score.containsKey(Global.chara_set_name)) {
                return (Map) language_score.get(Global.chara_set_name);
            }
            throw new RuntimeException("Chara set not found by scorer.");
        }
        throw new RuntimeException("Language not found by scorer.");
    }

    public static void loadScoreFromFile(int userID) {

    }
}
