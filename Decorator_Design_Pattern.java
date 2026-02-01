package org.example;

/**
 * Component interface
 *
 * This is the common interface for:
 * 1. The base object (Mobile)
 * 2. All decorators
 *
 * The client will always interact using this interface,
 * which allows us to add features dynamically.
 */
interface Feature {
    String showFeatures();
}

/**
 * Concrete Component
 *
 * This is the base object that can be decorated.
 * It provides the default behavior.
 */
class Mobile implements Feature {
    @Override
    public String showFeatures() {
        return "Calling";
    }
}

/**
 * Base Decorator
 *
 * - Implements the same interface as the component
 * - Holds a reference to another Feature object
 *
 * This is the key to the Decorator pattern:
 * decorators wrap objects of the same type.
 */
abstract class FeatureDecorator implements Feature {
    protected Feature feature;

    public FeatureDecorator(Feature feature) {
        this.feature = feature;
    }
}

/**
 * Concrete Decorator
 *
 * Adds Foldable capability to an existing Feature object
 * without modifying the original class.
 */
class FoldableFeature extends FeatureDecorator {
    public FoldableFeature(Feature feature) {
        super(feature);
    }

    @Override
    public String showFeatures() {
        // Delegate to the wrapped object and add extra behavior
        return feature.showFeatures() + " + Foldable";
    }
}

/**
 * Concrete Decorator
 *
 * Adds Fast Charging capability.
 */
class FastChargingFeature extends FeatureDecorator {
    public FastChargingFeature(Feature feature) {
        super(feature);
    }

    @Override
    public String showFeatures() {
        return feature.showFeatures() + " + Fast Charging";
    }
}

/**
 * Concrete Decorator
 *
 * Adds Titanium Build capability.
 */
class TitaniumBuildFeature extends FeatureDecorator {
    public TitaniumBuildFeature(Feature feature) {
        super(feature);
    }

    @Override
    public String showFeatures() {
        return feature.showFeatures() + " + Titanium Build";
    }
}

public class Main {
    public static void main(String[] args) {

        /*
         * Step 1: Create a base object
         * At this point, the mobile has only basic functionality.
         */
        Feature onePlusFeatures = new Mobile();
        System.out.println(onePlusFeatures.showFeatures());

        /*
         * Step 2: Add Foldable feature dynamically
         * The original Mobile object is not changed.
         */
        onePlusFeatures = new FoldableFeature(onePlusFeatures);
        System.out.println(onePlusFeatures.showFeatures());

        /*
         * Step 3: Add Titanium build feature
         * Decorators can be stacked in any order.
         */
        onePlusFeatures = new TitaniumBuildFeature(onePlusFeatures);
        System.out.println(onePlusFeatures.showFeatures());

        /*
         * Step 4: Add Fast Charging feature
         * Final object is a chain of decorators wrapping the base Mobile.
         */
        onePlusFeatures = new FastChargingFeature(onePlusFeatures);
        System.out.println(onePlusFeatures.showFeatures());
    }
}
