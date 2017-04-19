package please.picture.com.pictureplease.Entity;

import retrofit2.http.Body;

/**
 * Created by jeka on 19.04.17.
 */

public class User {
    String id_user;
    String email;
    String pass;

    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getId() {
        return id_user;
    }
}
