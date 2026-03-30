package org.example;

/*
Prototype Design Pattern:
- Create one fully initialized object (prototype)
- Clone it instead of rebuilding from scratch
- Modify only required fields
*/

interface CardClonable {
    Card cloneCard();
}

class Card implements CardClonable {
    public String title;
    public String description;
    public String color;

    // Heavy constructor (called only once ideally)
    public Card(String t, String d, String c) {
        title = t;
        description = d;
        color = c;

        // Simulating heavy UI setup
        setupLayout();
        applyStyles();
    }

    // Copy constructor (used in cloning)
    public Card(Card other) {
        this.title = other.title;
        this.description = other.description;
        this.color = other.color;
    }

    @Override
    public Card cloneCard() {
        return new Card(this); // clean cloning
    }

    // To fastly tweak the properties if needed
    void setTitle(String t) {
        title = t;
    }

    void setDescription(String d) {
        description = d;
    }

    void setColor(String c) {
        color = c;
    }

    void setupLayout() {
        System.out.println("Setting up layout (ONLY ONCE)");
    }

    void applyStyles() {
        System.out.println("Applying styles (ONLY ONCE)");
    }

    void show() {
        System.out.println("Title: " + title + ", Desc: " + description + ", Color: " + color);
    }
}

public class Main {
    public static void main(String[] args) {

        // Create prototype (heavy work happens here)
        Card baseCard = new Card("Base", "Template", "Blue");

        // Clone and modify
        Card c1 = baseCard.cloneCard();
        c1.setTitle("Product 1");

        Card c2 = baseCard.cloneCard();
        c2.setTitle("Product 2");
        c2.setColor("Green");

        Card c3 = baseCard.cloneCard();
        c3.setTitle("Product 3");
        c3.setColor("Black");

        c1.show();
        c2.show();
        c3.show();
    }
}