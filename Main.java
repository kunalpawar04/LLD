package org.example;

/*
 Strategy Pattern:
 - Behavior is defined via interfaces
 - Concrete implementations can be swapped at runtime
 - Avoids large inheritance hierarchies
*/

/* ---------- WALKING STRATEGY ---------- */
interface Walker {
    void walk();
}

class NoWalker implements Walker {
    @Override
    public void walk() {
        System.out.println("Sorry, I can't walk :(");
    }
}

class SlowWalker implements Walker {
    @Override
    public void walk() {
        System.out.println("Hey there, I am a slow walker");
    }
}

class FastWalker implements Walker {
    @Override
    public void walk() {
        System.out.println("Hi, don't have much time to say because I am walking fast ;)");
    }
}

/* ---------- EATING STRATEGY ---------- */
interface Eater {
    void eat();
}

class VegEater implements Eater {
    @Override
    public void eat() {
        System.out.println("I eat Veg food");
    }
}

class NonVegEater implements Eater {
    @Override
    public void eat() {
        System.out.println("I eat Non Veg food");
    }
}

/* ---------- DANCING STRATEGY ---------- */
interface Dancer {
    void dance();
}

class NoDancer implements Dancer {
    @Override
    public void dance() {
        System.out.println("Damn! I can't dance");
    }
}

class BreakDancer implements Dancer {
    @Override
    public void dance() {
        System.out.println("Yo, I am doing break dance");
    }
}

class DiscoDancer implements Dancer {
    @Override
    public void dance() {
        System.out.println("I am a disco dancer!");
    }
}

class ClassicalDancer implements Dancer {
    @Override
    public void dance() {
        System.out.println("Pranam! I am a classical dancer.");
    }
}

/*
 Human is the Context class.
 It does not know *how* walking, eating or dancing is done.
 It simply delegates the behavior to the strategy objects.
*/
class Human {

    // Composition over inheritance
    private final Walker walker;
    private final Eater eater;
    private final Dancer dancer;

    Human(Walker walker, Eater eater, Dancer dancer) {
        this.walker = walker;
        this.eater = eater;
        this.dancer = dancer;
    }

    public void walk() {
        walker.walk();
    }

    public void eat() {
        eater.eat();
    }

    public void dance() {
        dancer.dance();
    }
}

public class Main {
    public static void main(String[] args) {

        Human kunal = new Human(
                new FastWalker(),
                new NonVegEater(),
                new NoDancer()
        );

        System.out.println("Kunal's capabilities - ");
        kunal.walk();
        kunal.dance();
        kunal.eat();

        System.out.println();

        Human didi = new Human(
                new SlowWalker(),
                new VegEater(),
                new ClassicalDancer()
        );

        System.out.println("Didi's capabilities - ");
        didi.walk();
        didi.dance();
        didi.eat();

        System.out.println();

        Human abhishek = new Human(
                new SlowWalker(),
                new VegEater(),
                new NoDancer()
        );

        System.out.println("Abhishek's capabilities - ");
        abhishek.walk();
        abhishek.dance();
        abhishek.eat();
    }
}