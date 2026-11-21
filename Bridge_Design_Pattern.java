
package org.example;

/*
 Bridge Pattern:
 - Separates abstraction from implementation
 - Allows both to vary independently
 - Uses composition instead of inheritance
 - Abstraction delegates operations to implementor
*/


/*
 IMPLEMENTOR INTERFACE
 Defines the interface for device implementations
*/
interface Device {
    void turnOn();
    void turnOff();
    void setVolume(int volume);
}


/*
 CONCRETE IMPLEMENTORS
 Different types of devices
*/
class TV implements Device {

    @Override
    public void turnOn() {
        System.out.println("TV is turned ON");
    }

    @Override
    public void turnOff() {
        System.out.println("TV is turned OFF");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("TV volume set to " + volume);
    }
}

class Radio implements Device {

    @Override
    public void turnOn() {
        System.out.println("Radio is turned ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Radio is turned OFF");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("Radio volume set to " + volume);
    }
}


/*
 ABSTRACTION
 Defines the high-level remote control interface
 Maintains a reference to Device (bridge)
*/
abstract class Remote {

    protected Device device;

    public Remote(Device device) {
        this.device = device;
    }

    abstract void turnOn();
    abstract void turnOff();
}


/*
 REFINED ABSTRACTIONS
 Different types of remote controls
*/
class BasicRemote extends Remote {

    public BasicRemote(Device device) {
        super(device);
    }

    @Override
    public void turnOn() {
        System.out.println("Basic Remote: Turning device ON");
        device.turnOn();
    }

    @Override
    public void turnOff() {
        System.out.println("Basic Remote: Turning device OFF");
        device.turnOff();
    }
}

class AdvancedRemote extends Remote {

    public AdvancedRemote(Device device) {
        super(device);
    }

    @Override
    public void turnOn() {
        System.out.println("Advanced Remote: Turning device ON");
        device.turnOn();
        device.setVolume(50);
    }

    @Override
    public void turnOff() {
        System.out.println("Advanced Remote: Turning device OFF");
        device.turnOff();
    }

    public void mute() {
        System.out.println("Advanced Remote: Muting device");
        device.setVolume(0);
    }
}


/*
 CLIENT
 Combines abstraction and implementation dynamically
*/
public class Main {

    public static void main(String[] args) {

        /*
         Create device implementations
        */
        Device tv = new TV();
        Device radio = new Radio();


        /*
         Bridge combinations

         Any remote can control any compatible device
        */

        Remote basicTVRemote = new BasicRemote(tv);
        Remote advancedRadioRemote = new AdvancedRemote(radio);
        Remote advancedTVRemote = new AdvancedRemote(tv);


        /*
         Client interacts through abstraction
        */

        basicTVRemote.turnOn();
        basicTVRemote.turnOff();

        System.out.println();

        advancedRadioRemote.turnOn();
        ((AdvancedRemote) advancedRadioRemote).mute();
        advancedRadioRemote.turnOff();

        System.out.println();

        advancedTVRemote.turnOn();
        advancedTVRemote.turnOff();
    }
}
