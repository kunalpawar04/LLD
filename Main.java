package org.example;

/*
This is a bad example without Prototype Design Pattern.

Ideal Prototype Pattern:
- Objects should be created by cloning an existing instance (prototype)
- Avoid repeated expensive initialization
- Only modify required properties after cloning

Issues in this implementation:
- Each Card object is created from scratch
 → Repeated execution of heavy setup methods like layout and styling

- Unnecessary duplication of work
 → Same layout and styles are applied again and again for every Card

- Performance inefficiency
 → Expensive operations (UI setup, styling) are repeated unnecessarily

- No reuse of existing object
 → Even though Cards share same structure, no base template is reused

In a proper design:
→ A base Card (prototype) would be created once
→ New cards would be cloned from it
→ Only dynamic fields like title, description, color would be changed
*/

class Card {
    public String title;
    public String description;
    public String color;

    Card(String t, String d, String c) {
        title = t;
        description = d;
        color = c;

        // Simulating heavy UI setup
        setupLayout();
        applyStyles();
    }

    void setupLayout() {
        System.out.println("Setting up layout (header, body, footer)");
    }

    void applyStyles() {
        System.out.println("Applying styles (padding, margin, font)");
    }

    void show() {
        System.out.println("Title: " + title + ", Desc: " + description + ", Color: " + color);
    }
};

public class Main {
    public static void main(String[] args) {
        Card c1 = new Card("Product 1", "Good product", "Blue");
        Card c2 = new Card("Product 2", "Better product", "Grey");
        Card c3 = new Card("Product 3", "Best product", "Black");
    }
}