package ths.learnjp.katahira;

import java.util.Map;

public class UserModel {

    private int user_id;
    private String name;
    private String rank;
    private int perfect_kata;
    private int perfect_hira;
    private int greetings_done;
    private int phrases_done;
    private Map greetings;
    private Map phrases;

    public UserModel(int user_id, String name, String rank, int perfect_kata, int perfect_hira, int greetings_done, int phrases_done, Map greetings, Map phrases) {
        this.user_id = user_id;
        this.name = name;
        this.rank = rank;
        this.perfect_kata = perfect_kata;
        this.perfect_hira = perfect_hira;
        this.greetings_done = greetings_done;
        this.phrases_done = phrases_done;
        this.greetings = greetings;
        this.phrases = phrases;
    }

    @Override
    public String toString() {
        return "UserModel{" + "user_id=" + user_id + ", name='" + name + '\'' + ", rank='" + rank + '\'' + ", perfect_kata=" + perfect_kata + ", perfect_hira=" + perfect_hira + ", greetings=" + greetings_done + ", phrases=" + phrases_done + '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getPerfect_kata() {
        return perfect_kata;
    }

    public void setPerfect_kata(int perfect_kata) {
        this.perfect_kata = perfect_kata;
    }

    public int getPerfect_hira() {
        return perfect_hira;
    }

    public void setPerfect_hira(int perfect_hira) {
        this.perfect_hira = perfect_hira;
    }

    public int getGreetings_done() {
        return greetings_done;
    }

    public void setGreetings_done(int greetings_done) {
        this.greetings_done = greetings_done;
    }

    public int getPhrases_done() {
        return phrases_done;
    }

    public void setPhrases_done(int phrases_done) {
        this.phrases_done = phrases_done;
    }

    public Map getGreetings() {
        return greetings;
    }

    public void setGreetings(Map greetings) {
        this.greetings = greetings;
    }

    public Map getPhrases() {
        return phrases;
    }

    public void setPhrases(Map phrases) {
        this.phrases = phrases;
    }
}
