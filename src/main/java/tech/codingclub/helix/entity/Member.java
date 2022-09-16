package tech.codingclub.helix.entity;

/**
 * Created by hackme on 2/7/18.
 */
public class Member extends MemberBase {

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public Boolean getIs_verified() {
        return is_verified;
    }

    public boolean isIs_followed() {
        return is_followed;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIs_image_uploaded() {
        return is_image_uploaded;
    }

    public String firstName;
    public String lastName;
    public String email;
    public String role;
    public String password;
    public String image;
    public String token;
    public Boolean is_verified;
    public Long id;

    public boolean is_followed = false;

    public boolean is_image_uploaded = false;
}
