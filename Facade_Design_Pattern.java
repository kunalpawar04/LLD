package org.example;

/*
 Facade Pattern:
 - Provides a simplified, unified interface to a complex subsystem
 - Hides internal complexity and coordinates multiple classes internally
 - Client interacts with only one facade class, not the subsystem classes
*/

/*
 This class represents one low-level subsystem.
 Responsibility: Verify that the user is allowed to use the network.
 The client should NOT call this directly.
 */
class NetworkAuthentication {

    // Authenticates the user with the mobile network
    public void authenticate() {
        System.out.println("Authenticating user on network 🛜");
    }
}

/*
 Another subsystem class.
 Responsibility: Find the nearest mobile tower for signal connection.
 */
class NearestTower {

    // Finds the closest available mobile tower
    public void findTower() {
        System.out.println("Finding nearest mobile tower 🗼");
    }
}

/*
 Subsystem responsible for routing.
 Responsibility: Decide the path the call will take across towers.
 */
class CallRouting {

    // Routes the call through multiple towers if needed
    public void routeCall() {
        System.out.println("Routing call through multiple towers 🗼... 🗼... 🗼");
    }
}

/*
 Subsystem for voice communication.
 Responsibility: Establish an actual voice channel once routing is done.
 */
class VoiceChannel {

    // Sets up the voice channel for the call
    public void establish() {
        System.out.println("Establishing voice channel 📲");
    }
}

/*
 * FACADE CLASS

 This class hides the complexity of all subsystems.
 The client interacts ONLY with this class.

 Internally, it coordinates:
 - Authentication
 - Tower selection
 - Call routing
 - Voice channel setup
 */
class CallFacade {

    /*
     Subsystem objects are created and managed by the Facade.
     Marked as final because:
     - They should not change once initialized
     - Facade fully owns them
     */
    private final NetworkAuthentication auth = new NetworkAuthentication();
    private final NearestTower tower = new NearestTower();
    private final CallRouting routing = new CallRouting();
    private final VoiceChannel voice = new VoiceChannel();

    /*
     This is the simplified method exposed to the client.
     One method internally triggers a complex sequence of operations.
     */
    public void makeCall() {
        System.out.println("Starting call ☎️");

        // Step 1: Authenticate user on network
        auth.authenticate();

        // Step 2: Find nearest available mobile tower
        tower.findTower();

        // Step 3: Route call through required towers
        routing.routeCall();

        // Step 4: Establish voice channel
        voice.establish();

        System.out.println("Call connected successfully 🗣️");
    }
}

/*
 Client code.
 The client does NOT know about authentication, routing, or towers.
 It only knows about the Facade.
 */
public class Main {

    public static void main(String[] args) {

        // Client interacts with Facade only
        CallFacade call = new CallFacade();

        // Single method call hides all complexity
        call.makeCall();
    }
}
