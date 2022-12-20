package ths.learnjp.katahira;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Generate {
    public static String getCharacter() {
        double weight_sum = 0;
        Map weights = new HashMap();

        Map chara_set_score = Score.getCharaSetSessionScore();

        Iterator<String> score_iterator = chara_set_score.keySet().iterator();

        while(score_iterator.hasNext()) {
//            weight_sum += score_iterator.next();
            String character = score_iterator.next();
            double score = Double.valueOf((int) chara_set_score.get(character));
            double weight = 1 / score;

            weight_sum += weight;

            weights.put(character, weight);
        }

        Iterator<String> chara_iterator = chara_set_score.keySet().iterator();

        double random = getRandomNumber() * weight_sum;

        while(chara_iterator.hasNext()) {
            String character = chara_iterator.next();
            double weight = (double) weights.get(character);

            if (random < weight) {
                return character;
            }
            random -= weight;
        }

        throw new RuntimeException("No character generated");
    }

    public static String getCharacterNoWeight(Map chara_set) {
        int random = (int) Math.floor(Math.random()*chara_set.size());

        String[] key_set = (String[]) chara_set.keySet().toArray(new String[0]);

        return key_set[random];
    }

    public static double getRandomNumber() {
        double trandom = (double)(System.currentTimeMillis()%100);
        double random = trandom/100 ;
        return random;
    }
}

