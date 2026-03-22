package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
 Flyweight Design Pattern:
 ---------------------------------------
 - Used to reduce memory usage when many similar objects exist
 - Splits object state into:
     1. Intrinsic (shared, heavy, constant)
     2. Extrinsic (dynamic, provided at runtime)

 In this example:
 - TreeIntrinsic = shared tree data (species, texture, color)
 - TreeExtrinsic = position-related data (latitude, longitude, inclination)
 - TreeFactory = ensures reuse of TreeIntrinsic objects
 - Map (client) = provides extrinsic data during rendering
*/

// Flyweight class (shared object)
class TreeIntrinsic {

    // Intrinsic (shared) state
    private final String species;
    private final String texture;
    private final String color;

    public TreeIntrinsic(String species, String texture, String color) {
        this.species = species;
        this.texture = texture;
        this.color = color;
    }

    // Operation that uses both intrinsic and extrinsic data
    public void render(int latitude, int longitude, int inclination) {
        System.out.println(
                "Rendering " + species +
                        " at (" + latitude + ", " + longitude + ")" +
                        " with inclination " + inclination +
                        " | Texture: " + texture +
                        " | Color: " + color
        );
    }
}

// Flyweight Factory (manages shared objects)
class TreeFactory {

    // Cache to store already created flyweights
    private static final Map<String, TreeIntrinsic> treeCache = new HashMap<>();

    public static TreeIntrinsic getTree(String species, String texture, String color) {

        String key = species + "-" + texture + "-" + color;

        // Reuse existing object if present
        if (!treeCache.containsKey(key)) {
            treeCache.put(key, new TreeIntrinsic(species, texture, color));
        }

        return treeCache.get(key);
    }
}

// Client class (Game Map)
class GTAViceCityMap {

    void renderForest() {

        int minLatitude = 10, maxLatitude = 4000;
        int minLongitude = -5, maxLongitude = 6078;
        int minInclination = 0, maxInclination = 30;

        Random rand = new Random();

        // Only ONE intrinsic object will be reused n times (5000 in this case)
        TreeIntrinsic treeFlyweight =
                TreeFactory.getTree("Normal Tree", "Rough", "Dark Green");

        for (int i = 0; i < 5000; i++) {

            // Extrinsic data (varies for each tree)
            int latitude = rand.nextInt(maxLatitude - minLatitude + 1) + minLatitude;
            int longitude = rand.nextInt(maxLongitude - minLongitude + 1) + minLongitude;
            int inclination = rand.nextInt(maxInclination - minInclination + 1) + minInclination;

            // Pass extrinsic data during method call (not stored inside object)
            treeFlyweight.render(latitude, longitude, inclination);
        }
    }
}

public class Main {

    public static void main(String[] args) {

        // Client triggers rendering
        GTAViceCityMap map = new GTAViceCityMap();
        map.renderForest();
    }
}
