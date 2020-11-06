package sample;

public class Record {
    private String username;
    private Double score;

    public Record(String username, Double score){
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "username: " + username + "\n" + "score= " + score + "\n" ;
    }
}
