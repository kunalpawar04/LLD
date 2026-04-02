package org.example;

import java.util.ArrayList;
import java.util.List;

/*
 ---------------------------------------
 Memento Design Pattern
 ---------------------------------------
 - Captures and stores an object's state without exposing internal details
 - Allows restoring object to a previous state (undo functionality)
 - Follows separation of concerns

 ROLES:
 - Originator  → Creates and restores state (Application)
 - Memento     → Stores snapshot (Memento)
 - CareTaker   → Manages history (CareTaker)

 KEY IDEA:
 - Always store a COPY of data (not reference ie Deep Copy)
 - Enables safe rollback without affecting original object
*/


/*
 ORIGINATOR
 - Holds actual data/state
 - Creates snapshot (Memento)
 - Restores state from Memento
*/
class Application {
    public ArrayList<String> data;

    public Application() {
        data = new ArrayList<>();
    }

    // Create snapshot of current state
    public Memento createMemento() {
        return new Memento(data);
    }

    // Restore previous state
    public void restoreMemento(Memento memento) {
        data = memento.getSnapShot();
    }

    // Utility method to print current data
    public void displayData() {
        if (data.isEmpty()) {
            System.out.println("No data to display!");
            return;
        }

        System.out.println("Current data:");
        for (String currData : data) {
            System.out.println(currData);
        }
        System.out.println();
    }
}


/*
 MEMENTO
 - Stores snapshot of Application state
 - Should be immutable (no external modification)
 - Uses deep copy to preserve state
*/
class Memento {
    private ArrayList<String> backupData;

    public Memento(ArrayList<String> data) {
        // IMPORTANT: Deep copy (not reference)
        this.backupData = new ArrayList<>(data);
    }

    // Return a copy to maintain encapsulation
    public ArrayList<String> getSnapShot() {
        return new ArrayList<>(backupData);
    }
}


/*
 CARETAKER
 - Manages history of snapshots
 - Does NOT modify snapshot content
 - Controls undo (rollback) and commit operations
*/
class CareTaker {
    private List<Memento> history = new ArrayList<>();

    // Save current state before making changes
    public void save(Application application) {
        history.add(application.createMemento());
    }

    // Undo last change → restore previous state
    public void rollback(Application application) {
        if (history.isEmpty()) {
            System.out.println("No history to rollback!");
            return;
        }

        // Remove last snapshot and restore it
        Memento lastState = history.removeLast();
        application.restoreMemento(lastState);
        System.out.println("Rolling back safely done :)");
    }

    // Commit → discard last snapshot (will not be needed anymore as we are finalizing the changes)
    public void commit() {
        if (history.isEmpty()) {
            System.out.println("Nothing to commit!");
            return;
        }

        history.removeLast();
        System.out.println("Changes committed successfully.");
    }
}


/*
 CLIENT (MAIN)
 - Uses Application and CareTaker
 - Demonstrates save, rollback, and commit operations
*/
public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        CareTaker caretaker = new CareTaker();

        // Initial changes
        app.data.add("1st change");
        app.data.add("2nd change");

        // Save state before making risky changes
        caretaker.save(app);

        // New changes (may fail)
        app.data.add("3rd change");
        app.data.add("4th change");

        System.out.println("Before rollback:");
        app.displayData();

        System.out.println("Some error occurred! Rolling back to previous changes :(");
        // Rollback to previous safe state
        caretaker.rollback(app);

        System.out.println("After rollback:");
        app.displayData();

        // Save again before next changes
        caretaker.save(app);

        app.data.add("5th change");
        app.data.add("6th change");

        // Commit → finalize changes (cannot undo now)
        caretaker.commit();

        app.displayData();
    }
}
