package org.example;

import java.util.*;

/*
 Mediator Design Pattern:
 ---------------------------------------
 Goal:
 - Reduce tight coupling between multiple objects (Users)
 - Centralize communication logic in one place (Mediator)

 Key Idea:
 - Objects (Users) should NOT communicate directly
 - All communication should go through Mediator

 Benefits:
 - Loose coupling (Users don’t know about each other)
 - Centralized control (easy to add logging, filtering, etc.)
 - Better scalability and maintainability

 In this example:
 - MediatorImpl → Handles all communication logic
 - User → Only sends requests via Mediator (no direct interaction)
*/

interface Mediator {

    // Broadcast message to all connected users
    void sendAll(String senderName);

    // Send message to a specific user
    void sendTo(String senderName, String recipientName);

    // Mute a user for a specific requester
    void muteForUser(String userRequester, String targetUser);

    // Add connection between two users
    void addForUser(String userRequest, String userReceiver);
}

class MediatorImpl implements Mediator {

    /*
     * Stores all registered users
     * Helps validate if a user exists
     */
    private HashMap<String, User> users;

    /*
     * userMap:
     * - Maintains connections
     * - user -> set of users they can communicate with
     *
     * Example:
     * Kunal -> [Abhishek, Chaitanya]
     */
    private HashMap<String, HashSet<String>> userMap;

    /*
     * muteMap:
     * - Tracks muted users per user
     *
     * Example:
     * Kunal -> [Chaitanya] (means Kunal muted Chaitanya)
     */
    private HashMap<String, HashSet<String>> muteMap;

    public MediatorImpl() {
        users = new HashMap<>();
        userMap = new HashMap<>();
        muteMap = new HashMap<>();
    }

    /*
     * Register user in the system
     * Initializes connection and mute lists
     */
    public void registerUser(User user) {
        users.put(user.name, user);
        userMap.put(user.name, new HashSet<>());
        muteMap.put(user.name, new HashSet<>());
    }

    /*
     * Broadcast message:
     * - Sends message from sender to all connected users
     * - Skips muted users
     */
    @Override
    public void sendAll(String senderName) {
        System.out.println(senderName + " sends to all:");

        HashSet<String> receivers = userMap.getOrDefault(senderName, new HashSet<>());
        HashSet<String> muted = muteMap.getOrDefault(senderName, new HashSet<>());

        for (String receiver : receivers) {
            if (!muted.contains(receiver)) {
                System.out.println("[" + senderName + " -> " + receiver + "] Hello from " + senderName);
            }
        }
    }

    /*
     * Direct message:
     * Flow:
     * 1. Check if recipient exists
     * 2. Check if muted
     * 3. Deliver message
     */
    @Override
    public void sendTo(String senderName, String recipientName) {

        if (!users.containsKey(recipientName)) {
            System.out.println("User not found: " + recipientName);
            return;
        }

        if (muteMap.getOrDefault(senderName, new HashSet<>()).contains(recipientName)) {
            System.out.println("User " + recipientName + " is muted by " + senderName);
            return;
        }

        System.out.println("[" + senderName + " -> " + recipientName + "] Hello " + recipientName + " from " + senderName);
    }

    /*
     * Mute functionality:
     * - Adds targetUser to requester’s mute list
     * - Future messages to that user will be blocked
     */
    @Override
    public void muteForUser(String userRequester, String targetUser) {
        muteMap.putIfAbsent(userRequester, new HashSet<>());
        muteMap.get(userRequester).add(targetUser);
    }

    /*
     * Add connection:
     * - Allows userRequest to communicate with userReceiver
     */
    @Override
    public void addForUser(String userRequest, String userReceiver) {
        userMap.putIfAbsent(userRequest, new HashSet<>());
        userMap.get(userRequest).add(userReceiver);
    }
}

/*
 * User class (Colleague in Mediator Pattern)
 *
 * Key Point:
 * - User does NOT communicate directly with other users
 * - Delegates all operations to Mediator
 */
class User {
    public String name;
    private Mediator mediator;

    public User(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    // Broadcast message via mediator
    void sendAll() {
        mediator.sendAll(name);
    }

    // Send message to specific user via mediator
    void send(String recipientName) {
        mediator.sendTo(name, recipientName);
    }

    // Mute a user via mediator
    void mute(String targetUser) {
        mediator.muteForUser(name, targetUser);
    }

    // Add connection via mediator
    void add(String user) {
        mediator.addForUser(name, user);
    }
}

public class Main {
    public static void main(String[] args) {

        // Create mediator (central controller)
        MediatorImpl mediator = new MediatorImpl();

        // Create users (colleagues)
        User u1 = new User("Kunal", mediator);
        User u2 = new User("Abhishek", mediator);
        User u3 = new User("Swapnil", mediator);

        // Register users in mediator
        mediator.registerUser(u1);
        mediator.registerUser(u2);
        mediator.registerUser(u3);

        // Establish connections
        u1.add("Abhishek");
        u1.add("Swapnil");

        // Broadcast message
        u1.sendAll();

        // Mute and test direct messaging
        u1.mute("Swapnil");
        u1.send("Swapnil"); // blocked

        // Broadcast again (Swapnil skipped)
        u1.sendAll();
    }
}
