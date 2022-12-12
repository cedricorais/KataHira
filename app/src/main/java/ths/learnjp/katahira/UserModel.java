package ths.learnjp.katahira;

public class UserModel {

    private int user_id;
    private String name;
    private String rank;

    public UserModel(int id, String name, String rank) {
        this.user_id = id;
        this.name = name;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "UserModel{" + "id=" + user_id + ", name='" + name + '\'' + ", rank='" + rank + '\'' + '}';
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
}
