package tech.codingclub.helix.entity;

/**
 * Created by hackme on 2/7/18.
 */
public class Followers {

    public Long user_id;
    public Long following_id;

    public Followers(Long user_id, Long following_id) {
        this.user_id = user_id;
        this.following_id = following_id;
    }

}
