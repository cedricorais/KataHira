package ths.learnjp.katahira;

public class UserModel {

    private int id;
    private String name;
    private int total_session;

    public UserModel(int id, String name, int total_session) {
        this.id = id;
        this.name = name;
        this.total_session = total_session;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", total_session=" + total_session +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal_session() {
        return total_session;
    }

    public void setTotal_session(int total_session) {
        this.total_session = total_session;
    }
}
