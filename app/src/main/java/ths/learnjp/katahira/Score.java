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

        if (Global.score_session_set.isEmpty()) {
            resetScoreSessionSet();
        }

        Global.score_session_set.put(character, new_score);
    }

    public static void addCharaScore(String character_key, int value){
        score.putIfAbsent(Global.language_name, new HashMap());
        Map language_score = (HashMap) score.get(Global.language_name);

        language_score.putIfAbsent(Global.chara_set_name, new HashMap());
        Map chara_set_score = (HashMap) language_score.get(Global.chara_set_name);

        int new_score = ((int) chara_set_score.get(character_key)) + value;

        chara_set_score.put(character_key, new_score);

        if (Global.score_session_set.isEmpty()) {
            resetScoreSessionSet();
        }

        Global.score_session_set.put(character_key, new_score);
    }

    public static void initializeScore() {
        Map chara_set = Global.chara_set;
        Iterator<String> it = chara_set.keySet().iterator();

        while(it.hasNext()) {
            String key = (String) it.next();
            setCharaScore(key, 1);
        }
    }

    public static Map getCharaSetSessionScore() {
        return Global.score_session_set;
    }

    public static void resetScoreSessionSet() {
        Global.score_session_set = new HashMap<>((Map) ((Map) score.get(Global.language_name)).get(Global.chara_set_name));
    }


    public static void loadScoreFromFile(int userID) {
        initializeScore();
    }
}
