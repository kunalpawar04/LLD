package org.example;

// Represents the final product that will be created using the Builder
class Pizza {

    // Required components of a Pizza
    // Fields are private and final to ensure immutability
    // Once the Pizza object is created, its state cannot change
    private final String dough;
    private final String sauce;
    private final String cheese;

    // Optional components
    private final String toppings;
    private final boolean thinCrust;
    private final boolean extraCheese;

    // Private constructor ensures objects can only be created through the Builder
    private Pizza(Builder builder) {
        this.dough = builder.dough;
        this.sauce = builder.sauce;
        this.cheese = builder.cheese;
        this.toppings = builder.toppings;
        this.thinCrust = builder.thinCrust;
        this.extraCheese = builder.extraCheese;
    }

    // Static nested Builder class responsible for constructing Pizza objects
    public static class Builder {

        // Required fields
        private String dough;
        private String sauce;
        private String cheese;

        // Optional fields
        private String toppings;
        private boolean thinCrust;
        private boolean extraCheese;

        // Builder methods allow step-by-step configuration of the object
        // Each method returns the Builder itself to support method chaining

        public Builder withDough(String dough) {
            this.dough = dough;
            return this;
        }

        public Builder withSauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public Builder withCheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        public Builder withToppings(String toppings) {
            this.toppings = toppings;
            return this;
        }

        public Builder withThinCrust(boolean thinCrust) {
            this.thinCrust = thinCrust;
            return this;
        }

        public Builder withExtraCheese(boolean extraCheese) {
            this.extraCheese = extraCheese;
            return this;
        }

        // Final step of object creation
        // Validates required fields before constructing the Pizza
        public Pizza build() {

            if (dough == null || dough.isEmpty())
                throw new IllegalArgumentException("Pizza can't be created without dough");

            if (cheese == null || cheese.isEmpty())
                throw new IllegalArgumentException("Pizza can't be created without cheese");

            if (sauce == null || sauce.isEmpty())
                throw new IllegalArgumentException("Pizza can't be created without sauce");

            // If validation passes, create immutable Pizza object
            return new Pizza(this);
        }
    }

    // Helper method to display the pizza configuration
    void showDetails() {
        System.out.println("Pizza Details:");
        System.out.println("Dough: " + dough);
        System.out.println("Sauce: " + sauce);
        System.out.println("Cheese: " + cheese);

        if (toppings != null && !toppings.isEmpty())
            System.out.println("Toppings: " + toppings);

        if (extraCheese)
            System.out.println("Extra cheese included");

        if (thinCrust)
            System.out.println("Thin crust included");
    }
}

// Director class defines predefined recipes for building different pizzas
// The Director knows the construction process but not the internal details
class PizzaDirector {

    // Creates a predefined Veg Pizza configuration
    public static Pizza vegPizzaBuilder() {
        return new Pizza.Builder()
                .withDough("Refined flour base")
                .withSauce("Tomato sauce")
                .withToppings("Olives & Onions")
                .withCheese("Plain cheese")
                .build();
    }

    // Creates a predefined Chicken Pizza configuration
    public static Pizza chickenPizzaBuilder() {
        return new Pizza.Builder()
                .withDough("Refined flour base")
                .withSauce("Hot Korean sauce")
                .withToppings("Chicken balls")
                .withCheese("Plain cheese")
                .build();
    }

    // Creates a more complex pizza with additional options
    public static Pizza deluxePizzaBuilder() {
        return new Pizza.Builder()
                .withDough("Wheat base")
                .withSauce("Peri Peri sauce")
                .withCheese("Mozzarella cheese")
                .withToppings("Pepperoni, Tomatoes & Olives")
                .withThinCrust(true)
                .withExtraCheese(true)
                .build();
    }
}

public class Main {

    public static void main(String[] args) {

        // Client does not directly build the Pizza
        // Instead it asks the Director for predefined configurations

        Pizza vegPizza = PizzaDirector.vegPizzaBuilder();
        vegPizza.showDetails();
        System.out.println();

        Pizza chickenPizza = PizzaDirector.chickenPizzaBuilder();
        chickenPizza.showDetails();
        System.out.println();

        Pizza deluxePizza = PizzaDirector.deluxePizzaBuilder();
        deluxePizza.showDetails();
        System.out.println();
    }
}
