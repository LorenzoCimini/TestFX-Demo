package model;

public class User {
    private final String username;

    private final String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object object){
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        if (this == object) return true;

        User user = (User) object;
        return this.username.equals(user.username) == user.password.equals(this.password);
    }
}
