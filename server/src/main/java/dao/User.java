package dao;

public class User {

    private String id;
    private String password;
    private String authtoken;

    public User(String id, String password, String authtoken) {
        this.id = id;
        this.password = password;
        this.authtoken = authtoken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

}
