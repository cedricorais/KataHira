package ths.learnjp.katahira;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Generate {
    public static String getCharacter() {
        int weight_sum = 0;

        Map chara_set_score = Score.getCharaSetSessionScore();

        Iterator<Integer> score_iterator = chara_set_score.values().iterator();

        while(score_iterator.hasNext()) {
            weight_sum += score_iterator.next();
        }

        Iterator<String> chara_iterator = chara_set_score.keySet().iterator();

        int random = (int) Math.floor(Math.random()*(weight_sum+1));

        while(chara_iterator.hasNext()) {
            String character = chara_iterator.next();
            int chara_score = (int) chara_set_score.get(character);

            random += chara_score;

            if (random >= chara_score) {
                return character;
            }

        }

        throw new RuntimeException("No character generated");
    }

    public static String getCharacterNoWeight(Map chara_set) {
        int random = (int) Math.floor(Math.random()*chara_set.size());

        String[] key_set = (String[]) chara_set.keySet().toArray(new String[0]);


        return key_set[random];
    }
}
