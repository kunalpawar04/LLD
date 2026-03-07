package org.example;

class Computer {

    // Fields are private and final to make the object immutable
    // Once created, the state of Computer cannot be modified
    // Required components
    private final String cpu;
    private final Integer storage;
    private final String display;

    // Optional components
    private final boolean rgbKeyboard;
    private final boolean liquidCooling;
    private final String externalGraphicCard;

    // Private constructor prevents direct object creation
    // Objects can only be created through the Builder
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.storage = builder.storageInGB;
        this.display = builder.display;
        this.rgbKeyboard = builder.rgbKeyboard;
        this.liquidCooling = builder.liquidCooling;
        this.externalGraphicCard = builder.externalGraphicCard;
    }

    // Static nested Builder class responsible for constructing Computer objects
    public static class Builder {

        // Required components
        private String cpu;
        private Integer storageInGB;
        private String display;

        // Optional components
        private boolean rgbKeyboard;
        private boolean liquidCooling;
        private String externalGraphicCard;

        // Builder methods allow step-by-step configuration
        // Each method returns the Builder itself to support method chaining

        public Builder withCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder withStorageInGB(Integer storageInGB) {
            this.storageInGB = storageInGB;
            return this;
        }

        public Builder withDisplay(String display) {
            this.display = display;
            return this;
        }

        public Builder withRgbKeyboard(boolean rgbKeyboard) {
            this.rgbKeyboard = rgbKeyboard;
            return this;
        }

        public Builder withLiquidCooling(boolean liquidCooling) {
            this.liquidCooling = liquidCooling;
            return this;
        }

        public Builder withExternalGpu(String externalGraphicCard) {
            this.externalGraphicCard = externalGraphicCard;
            return this;
        }

        public Computer build() {

            // Centralized validation before object creation
            // Prevents creation of objects in an inconsistent state

            if (cpu == null || cpu.isEmpty())
                throw new IllegalArgumentException("CPU is required to build the PC");

            if (storageInGB == null || storageInGB <= 0)
                throw new IllegalArgumentException("Storage cannot be zero");

            if (display == null || display.isEmpty())
                throw new IllegalArgumentException("Display is required");

            // If validation passes, create the immutable Computer object
            return new Computer(this);
        }
    }

    public void showConfiguration() {
        System.out.println("CPU: " + cpu);
        System.out.println("Storage: " + storage + "GB SSD");
        System.out.println("Display: " + display);

        if (rgbKeyboard)
            System.out.println("RGB Mechanical Keyboard included");

        if (liquidCooling)
            System.out.println("Liquid Cooling System installed");

        if (externalGraphicCard != null && !externalGraphicCard.isEmpty())
            System.out.println("External GPU: " + externalGraphicCard);
    }
}

public class Main {

    public static void main(String[] args) {

        // Example of building a Computer using the Builder pattern
        Computer pc = new Computer.Builder()
                .withCpu("M1 bionic chip")
                .withStorageInGB(250)
                .withDisplay("Liquid Retina")
                .withRgbKeyboard(true)
                .build();

        pc.showConfiguration();

        // This will throw an exception because CPU is missing
        Computer pc2 = new Computer.Builder()
                .withStorageInGB(500)
                .withDisplay("FHD")
                .withLiquidCooling(true)
                .withRgbKeyboard(true)
                .withExternalGpu("Nvidia 900x")
                .build();

        pc2.showConfiguration();
    }
}
