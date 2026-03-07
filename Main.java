package org.example;

/*
 BAD DESIGN EXAMPLE (Without Builder Pattern)
 -------------------------------------------------------

 This example intentionally demonstrates a poor design for creating
 complex objects. The Computer class represents a customizable PC.

 A computer may have:
   Required fields:
     - CPU
     - Storage
     - Display

   Optional fields:
     - RGB Keyboard
     - Liquid Cooling
     - External GPU

 Problems with this design:

 1. Telescoping Constructors
    - Multiple constructors are created to support different combinations
      of optional parameters.
    - As optional fields increase, the number of constructors grows rapidly.

 2. Mutable Object (Immutability Violation)
    - Public setters allow modification after object creation.
    - A Computer object can be left in an inconsistent state.

 3. Inconsistent Object State
    - A user may forget to set required fields before calling build().

 4. Scattered Validation
    - Validation logic is delayed until build() is called.

 5. Hard to Read and Maintain
    - Client code becomes confusing because constructors look similar.

 The Builder Pattern solves these issues by:
    - Constructing objects step by step
    - Ensuring required parameters are provided
    - Keeping objects immutable
*/

class Computer {

    // Required components
    private String cpu;
    private Integer storageInGB;
    private String display;

    // Optional components
    private boolean rgbKeyboard;
    private boolean liquidCooling;
    private String externalGraphicCard;

    /*
     Problem 1: Immutability Violation
     ---------------------------------
     These setters allow modification after object creation.
     A user can change important fields anytime.
    */
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setStorageInGB(Integer storageInGB) {
        this.storageInGB = storageInGB;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setRgbKeyboard(boolean rgbKeyboard) {
        this.rgbKeyboard = rgbKeyboard;
    }

    public void setLiquidCooling(boolean liquidCooling) {
        this.liquidCooling = liquidCooling;
    }

    public void setExternalGraphicCard(String externalGraphicCard) {
        this.externalGraphicCard = externalGraphicCard;
    }

    /*
     Problem 2: Telescoping Constructor Pattern
     ------------------------------------------
     As optional parameters grow, constructors multiply.
     This becomes hard to maintain and confusing for developers.
    */

    // Required parameters only
    public Computer(String cpu, Integer storageInGB, String display) {
        this.cpu = cpu;
        this.storageInGB = storageInGB;
        this.display = display;
    }

    // Required + RGB keyboard
    public Computer(String cpu, Integer storageInGB, String display, Boolean rgbKeyboard) {
        this.cpu = cpu;
        this.storageInGB = storageInGB;
        this.display = display;
        this.rgbKeyboard = rgbKeyboard;
    }

    // Required + RGB + Liquid cooling
    public Computer(String cpu, Integer storageInGB, String display, Boolean rgbKeyboard, Boolean liquidCooling) {
        this.cpu = cpu;
        this.storageInGB = storageInGB;
        this.display = display;
        this.rgbKeyboard = rgbKeyboard;
        this.liquidCooling = liquidCooling;
    }

    // Required + External GPU
    public Computer(String cpu, Integer storageInGB, String display, String externalGraphicCard) {
        this.cpu = cpu;
        this.storageInGB = storageInGB;
        this.display = display;
        this.externalGraphicCard = externalGraphicCard;
    }

    /*
     Imagine adding more optional fields:
       - WiFi card
       - Bluetooth
       - Extra SSD
       - Water cooling
       - Custom cabinet

     The number of constructors would explode.
    */


    /*
     Problem 3: Inconsistent Behavior
     --------------------------------
     If a required field is missing or modified incorrectly,
     the object becomes invalid.

     Problem 4: Scattered Validation
     --------------------------------
     Validation happens late during build() instead of during creation.
    */
    public void build() {

        if (cpu == null || cpu.isEmpty())
            throw new IllegalArgumentException("CPU is required to build the PC");

        if (storageInGB == null || storageInGB == 0)
            throw new IllegalArgumentException("Storage cannot be zero");

        if (display == null || display.isEmpty())
            throw new IllegalArgumentException("Display is required");

        System.out.println("Starting PC Build...");
        System.out.println("CPU: " + cpu);
        System.out.println("Storage: " + storageInGB + "GB SSD");
        System.out.println("Display: " + display);

        if (rgbKeyboard)
            System.out.println("RGB Mechanical Keyboard included");

        if (liquidCooling)
            System.out.println("Liquid Cooling System installed");

        if (externalGraphicCard != null && !externalGraphicCard.isEmpty())
            System.out.println("External GPU: " + externalGraphicCard);

        System.out.println("PC Build Completed.");
    }
}

public class Main {

    public static void main(String[] args) {

        /*
         Problem 1: Telescoping Constructors
         -----------------------------------
         Hard to understand what each constructor parameter means.
        */

        Computer officePC =
                new Computer("Intel i5 12400", 512, "24-inch IPS Monitor");

        Computer gamingPC =
                new Computer("AMD Ryzen 7 7800X", 1000, "27-inch 165Hz Gaming Monitor", true);

        Computer workstation =
                new Computer("Intel i9 13900K", 2000, "32-inch 4K Display", true, true);

        /*
         Problem 2: Mutable Object
         -------------------------
         Users can change critical configuration later.
        */

        officePC.setExternalGraphicCard("NVIDIA RTX 3060");
        officePC.setCpu(""); // Accidentally breaking the object

        gamingPC.setStorageInGB(2000); // Unexpected modification
        workstation.setRgbKeyboard(false);

        /*
         Problem 3: Inconsistent Behavior
         --------------------------------
         If important fields are missing or corrupted,
         errors will only appear during build().
        */

        System.out.println("Building Gaming PC...");
        gamingPC.build();

        System.out.println();

        System.out.println("Building Office PC...");
        officePC.build(); // This may fail due to invalid CPU
    }
}