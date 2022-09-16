package tech.codingclub.helix.entity;

public class LoginResponse {

    public Long id;
    public String message;
    public boolean is_logged_in;


    public LoginResponse(Long id, String message, boolean is_logged_in) {
        this.id = id;
        this.message = message;
        this.is_logged_in = is_logged_in;
    }


}
