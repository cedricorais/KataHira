package ths.learnjp.katahira;

public class UserModel {

    private int user_id;
    private String name;
    private String rank;
    private int greetings;
    private int phrases;

    public UserModel(int user_id, String name, String rank, int greetings, int phrases) {
        this.user_id = user_id;
        this.name = name;
        this.rank = rank;
        this.greetings = greetings;
        this.phrases = phrases;
    }

    @Override
    public String toString() {
        return "UserModel{" + "user_id=" + user_id + ", name='" + name + '\'' + ", rank='" + rank + '\'' + ", greetings=" + greetings + ", phrases=" + phrases + '}';
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

    public int getGreetings() {
        return greetings;
    }

    public void setGreetings(int greetings) {
        this.greetings = greetings;
    }

    public int getPhrases() {
        return phrases;
    }

    public void setPhrases(int phrases) {
        this.phrases = phrases;
    }
}
