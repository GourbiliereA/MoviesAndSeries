package gourbi.com.moviesandseries.model;

/**
 * Created by Alex GOURBILIERE on 14/01/2018.
 */

public class Actor {

    private String name;
    private String photo;
    private String role;

    public Actor(String name, String photo, String role) {
        this.name = name;
        if (photo != null) {
            this.photo = photo;
        }
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
