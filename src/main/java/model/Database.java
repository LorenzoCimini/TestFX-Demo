package model;

import java.util.ArrayList;

public class Database{
    private static final Database database = new Database();

    private final ArrayList<User> users = new ArrayList<>();

    private Database(){
        users.add(new User("Lorenzo", "Password9@"));
        users.add(new User("Username", "Password1#"));
        users.add(new User("User", "Password3$"));
        users.add(new User("Pippo", "Password7_"));
        users.add(new User("Pluto", "Password5%"));
    }


    public static Database getDatabase(){
        return database;
    }


    public boolean login(User user) {
        for(User currentUser: users){
            if(currentUser.equals(user)) return true;
        }
        return false;
    }
}
