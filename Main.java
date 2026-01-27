package org.example;

class Singleton {
    public static volatile Singleton singleton;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if(singleton == null) { // First check with no lock
            synchronized (Singleton.class) {
                if(singleton == null) { // Second check with lock
                    singleton = new Singleton();
                }
            }
        }

        return singleton;
    }
}

public class Main {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        System.out.println(s1.equals(s2));
    }
}
