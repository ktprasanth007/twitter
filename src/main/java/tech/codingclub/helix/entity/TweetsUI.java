package tech.codingclub.helix.entity;

public class TweetsUI extends Tweets {

    public String email;
    public String author_name;

    public TweetsUI(Tweets tweets, Member member) {
        this.id = tweets.id;
        this.author_id = tweets.author_id;
        this.created_at = tweets.created_at;
        this.message = tweets.message;
        this.email = member.email;
        this.author_name = member.firstName;
    }
}
