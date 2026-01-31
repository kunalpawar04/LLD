package org.example;

import java.util.ArrayList;

/*
 * Observable (Subject)
 * - Maintains a list of observers
 * - Provides methods to add/remove observers
 * - Notifies observers when state changes
 */
interface Observable {
    void subscribe(Observer observer);      // Register an observer
    void unsubscribe(Observer observer);    // Remove an observer
    void notifySubscribers(String topicName); // Notify all observers
}

/*
 * Observer
 * - Gets notified when Observable changes
 * - Uses push model (data is passed directly)
 */
interface Observer {
    void update(String channelName, String topicName);
}

/*
 * Concrete Observable
 * - Implements Observable
 * - Represents the subject being observed (YouTube Channel)
 */
class Channel implements Observable {

    // List of all subscribers observing this channel
    private ArrayList<Observer> subscribers = new ArrayList<>();

    // Channel specific state
    private String channelName;

    public Channel(String channelName) {
        this.channelName = channelName;
    }

    /*
     * Business method
     * - Changes state (new video uploaded)
     * - Triggers notification to all observers
     */
    public void uploadVideo(String topicName) {
        System.out.println(topicName + " video uploaded");
        notifySubscribers(topicName);
    }

    // Add observer to the list
    @Override
    public void subscribe(Observer observer) {
        subscribers.add(observer);
    }

    // Remove observer from the list
    @Override
    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    /*
     * Notify all observers
     * - Pushes only required data
     * - Observers do not depend on Channel object
     */
    @Override
    public void notifySubscribers(String topicName) {
        for (Observer observer : subscribers) {
            observer.update(channelName, topicName);
        }
    }
}

/*
 * Concrete Observer
 * - Reacts to notifications from Channel
 * - Does NOT hold reference to Channel (decoupled)
 */
class Subscriber implements Observer {

    private String username;

    public Subscriber(String username) {
        this.username = username;
    }

    /*
     * Called automatically by Channel
     * - Receives pushed data
     * - Updates subscriber feed
     */
    @Override
    public void update(String channelName, String topicName) {
        System.out.println(
                "Hey " + username + ", " + channelName +
                        " channel uploaded video on " + topicName + " topic."
        );
    }
}

/*
 * Client code
 * - Creates Observable and Observers
 * - Subscribes / unsubscribes observers
 * - Triggers state change
 */
public class Main {
    public static void main(String[] args) {

        // Create subject
        Channel lldPrepChannel = new Channel("LLD Smashers");

        // Create observers
        Subscriber kunal = new Subscriber("Kunal");
        Subscriber abhishek = new Subscriber("Abhishek");

        // Register observers
        lldPrepChannel.subscribe(kunal);
        lldPrepChannel.subscribe(abhishek);

        // State change → notification
        lldPrepChannel.uploadVideo("Observer Design Pattern");
        System.out.println();

        // Unsubscribe one observer
        lldPrepChannel.unsubscribe(abhishek);

        // Only remaining observer gets notified
        lldPrepChannel.uploadVideo("Factory Design Pattern");
    }
}