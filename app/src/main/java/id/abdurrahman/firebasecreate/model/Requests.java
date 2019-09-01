package id.abdurrahman.firebasecreate.model;

import java.io.Serializable;

public class Requests implements Serializable {

    private String username;
    private String email;
    private String status;
    private String key;

    public Requests() {

    }

    public Requests(String usernm, String emaill, String stts) {
        this.username = usernm;
        this.email = emaill;
        this.status = stts;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override

    public String toString() {
        return " " + username + "\n" +
                " " + email + "\n" +
                " " + status;
    }
}
