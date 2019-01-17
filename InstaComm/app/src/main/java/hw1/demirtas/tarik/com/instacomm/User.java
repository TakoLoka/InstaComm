package hw1.demirtas.tarik.com.instacomm;

public class User {
    private String username;
    private String password;
    private int type;
    private int uses;

    public User( String username, String password, int type, int uses) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.uses = uses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }
}
