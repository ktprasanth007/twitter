package tech.codingclub.helix.entity;

public class Tweets {

    public String message;
    public Long id;
    public Long created_at;
    public Long author_id;

    public Tweets(String message, Long id, Long created_at, Long author_id) {
        this.message = message;
        this.id = id;
        this.created_at = created_at;
        this.author_id = author_id;
    }

    public Tweets() {

    }
}
