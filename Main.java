package org.example;

import java.util.*;

/*
 * This is a simplified / imperfect version of the Mediator Design Pattern.
 *
 * Ideal Mediator Pattern:
 * - Users (colleagues) should NOT talk to each other directly
 * - A central Mediator handles all communication
 *
 * In this example:
 * - Each User partially acts like a mediator for its own peers (not ideal)
 * - Still useful to understand interaction control, muting, broadcasting, etc.
 */

class User {
    public String name; // Unique identifier for user

    private List<User> peers;

    private HashMap<String, User> peerByUserName;

    public HashMap<String, Boolean> isUserMuted;

    public User(String name) {
        this.name = name;
        this.peers = new ArrayList<>();
        this.peerByUserName = new HashMap<>();
        this.isUserMuted = new HashMap<>();
    }

    public boolean isMuted(String name) {
        return isUserMuted.getOrDefault(name, false);
    }

    void addPeer(User user) {
        peers.add(user);
        peerByUserName.put(user.name, user);
    }

    void muteUser(String name) {
        if (peerByUserName.containsKey(name)) {
            isUserMuted.put(name, true);
        }
    }

    void unmuteUser(String name) {
        isUserMuted.put(name, false);
    }

    void sendAll() {
        System.out.println(name + " send it to all:");
        for (User user : peers) {
            if (!isMuted(user.name)) {
                System.out.println("[" + name + " -> " + user.name + "] Hello from " + name);
            }
        }
    }

    void send(String recipientName) {
        User recipient = peerByUserName.get(recipientName);

        if (recipient == null) {
            System.out.println("User not found: " + recipientName);
            return;
        }

        if (isMuted(recipientName)) {
            System.out.println("User " + recipientName + " is muted");
            return;
        }

        System.out.println("[" + name + " -> " + recipientName + "] Hello " + recipientName + " from " + name);
    }
}

public class Main {

    public static void main(String[] args) {

        // Creating users (colleagues)
        User user1 = new User("Kunal");
        User user2 = new User("Abhishek");
        User user3 = new User("Chaitanya");

        // Establishing connections
        user1.addPeer(user2);
        user1.addPeer(user3);

        // Broadcast message
        user1.sendAll();

        // Mute a user and try sending message
        user1.muteUser("Chaitanya");
        user1.send("Chaitanya");   // Will be blocked
        user1.sendAll();           // Chaitanya will not receive

        // Another user managing their own peers
        user2.addPeer(user3);
        user2.addPeer(user3); // duplicate (no check currently)
        user2.sendAll();
    }
}