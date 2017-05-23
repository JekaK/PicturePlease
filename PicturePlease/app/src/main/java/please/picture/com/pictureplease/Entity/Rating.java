package please.picture.com.pictureplease.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jeka on 23.05.17.
 */

public class Rating {

    @SerializedName("login_name")
    @Expose
    private String loginName;
    @SerializedName("Photo")
    @Expose
    private String photo;
    @SerializedName("c")
    @Expose
    private String c;

    public Rating(String loginName, String photo, String c) {
        this.loginName = loginName;
        this.photo = photo;
        this.c = c;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}
