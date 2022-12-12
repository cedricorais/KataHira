package ths.learnjp.katahira;

public class SessionModel {

    private int session_id;
    private String syllabary;
    private int mistakes;
    private int score;
    private String time;
    private String wrong_chars;
    private String date_time;
    private int user_id;

    public SessionModel(int session_id, String syllabary, int mistakes, int score, String time, String wrong_chars, String date_time, int user_id) {
        this.session_id = session_id;
        this.syllabary = syllabary;
        this.mistakes = mistakes;
        this.score = score;
        this.time = time;
        this.wrong_chars = wrong_chars;
        this.date_time = date_time;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        /*return "SessionModel{" +
                "session_id=" + session_id +
                ", syllabary='" + syllabary + '\'' +
                ", mistakes=" + mistakes +
                ", score=" + score +
                ", time='" + time + '\'' +
                ", wrong_chars='" + wrong_chars + '\'' +
                ", date_time='" + date_time + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';*/
        return session_id + ", " + syllabary + ", " + mistakes + ", " + score + ", " + time + ", " + wrong_chars + ", " + date_time + ", " + user_id;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public String getSyllabary() {
        return syllabary;
    }

    public void setSyllabary(String syllabary) {
        this.syllabary = syllabary;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWrong_chars() {
        return wrong_chars;
    }

    public void setWrong_chars(String wrong_chars) {
        this.wrong_chars = wrong_chars;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
