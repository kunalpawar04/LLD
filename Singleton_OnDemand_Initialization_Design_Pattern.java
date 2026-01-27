package org.example;

class Singleton {

    // Private constructor prevents external instantiation
    private Singleton() {
    }

    /*
     * This static inner class is NOT loaded when Singleton is loaded.
     * It is loaded only when getInstance() is called for the first time.
     *
     * JVM guarantees that class loading is thread-safe,
     * so no explicit synchronization or locks are required.
     *
     * This gives us:
     * - Lazy initialization
     * - Thread safety
     * - Better performance (no synchronization overhead)
     */
    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }

    // Returns the singleton instance
    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }
}

public class Main {
    public static void main(String[] args) {

        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        // Both references point to the same instance
        System.out.println(s1.equals(s2)); // true
    }
}
