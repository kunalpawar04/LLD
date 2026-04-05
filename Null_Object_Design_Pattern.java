package org.example;
import java.util.ArrayList;
import java.util.Arrays;

/*
 NULL OBJECT DESIGN PATTERN:
 ---------------------------------------
 - Avoids null checks by returning a default object
 - Provides same interface as real object
 - Eliminates NullPointerException / crashes
*/

/*
 COMMON INTERFACE
 Both RealUser and NullUser will implement this
*/
interface User {
    String getName();
    void displayName();
}

/*
 REAL OBJECT
 Represents actual user with data
*/
class RealUser implements User {
    private String name;

    public RealUser(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void displayName() {
        System.out.println("User name: " + this.name);
    }
}

/*
 NULL OBJECT
 Represents absence of user
 - No null pointer
 - Safe default behavior
*/
class NullUser implements User {
    private static final NullUser instance = new NullUser();

    private NullUser() {}

    // Singleton Design Pattern
    public static NullUser getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        // No user found
        return "";
    }

    @Override
    public void displayName() {
        System.out.println("No user exists!");
    }
}

/*
 FACTORY / SERVICE
 Returns RealUser or NullUser (never null)
*/
class UserFactory {
    private static ArrayList<String> users = new ArrayList<>(Arrays.asList("Kunal", "Chaitanya", "Abhishek"));

    public static User getUser(String name) {
        for(String currName: users) {
            if(name.equals(currName)) return new RealUser(name);
        }

        // Instead of returning null
        return NullUser.getInstance();
    }
}

/*
 CLIENT CODE
 No need for null checks
*/
public class Main {
    public static void main(String[] args) {
        User user1 = UserFactory.getUser("Kunal");
        // User: Kunal
        user1.displayName();

        User user2 = UserFactory.getUser("Unknown");
        // No user exists! (Didn't throw error or exception)
        user2.displayName();
    }
}
