package org.example;

import java.util.HashMap;
import java.util.Map;

/*
 Command Pattern:
 - Encapsulates a request as an object
 - Decouples the invoker from the receiver
 - Allows commands to be parameterized and swapped at runtime
 - Supports undo/redo operations
 - Promotes open/closed principle by adding new commands without changing existing code
*/

/*
 Command interface
 Declares common operations for all commands
 */
interface Command {
    void execute();  // perform the action
    void undo();     // reverse the action
}

/*
 Invoker class
 Responsible for triggering commands
 */
class Remote {

    // Map button number -> Command
    // Using Map interface for flexibility
    private final Map<Integer, Command> commandMap;

    public Remote() {
        this.commandMap = new HashMap<>();
    }

    /*
     Assigns a command to a button
     If the button already has a command, it gets replaced
     */
    public void setCommand(int buttonNumber, Command command) {
        commandMap.put(buttonNumber, command);
    }

    /*
     Executes the command assigned to the button
     */
    public void pressOnButton(int buttonNumber) {
        Command command = commandMap.get(buttonNumber);

        if (command == null) {
            System.out.println("No device is set up for button: " + buttonNumber);
        } else {
            command.execute();
        }
    }

    /*
     Undoes the command assigned to the button
     */
    public void pressOffButton(int buttonNumber) {
        Command command = commandMap.get(buttonNumber);

        if (command == null) {
            System.out.println("No device is set up for button: " + buttonNumber);
        } else {
            command.undo();
        }
    }
}

/*
 Receiver class
 Contains the actual business logic
 */
class Light {
    public void on() {
        System.out.println("Light is ON 🔆");
    }

    public void off() {
        System.out.println("Light is OFF 🌚");
    }
}

/*
 Concrete Command for Light
 */
class LightCommand implements Command {

    private final Light light;

    public LightCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

/*
 Receiver class
 */
class Fan {
    public void on() {
        System.out.println("Fan is ON 💨");
    }

    public void off() {
        System.out.println("Fan is OFF 🥵");
    }
}

/*
 Concrete Command for Fan
 */
class FanCommand implements Command {

    private final Fan fan;

    public FanCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.on();
    }

    @Override
    public void undo() {
        fan.off();
    }
}

/*
 Client class
 Wires everything together
 */
public class Main {
    public static void main(String[] args) {

        Remote remote = new Remote();

        // Light setup
        Light light = new Light();
        Command lightCommand = new LightCommand(light);
        remote.setCommand(1, lightCommand);

        // Fan setup
        Fan fan = new Fan();
        Command fanCommand = new FanCommand(fan);
        remote.setCommand(2, fanCommand);

        // Button presses
        remote.pressOnButton(1);
        remote.pressOffButton(1);
        remote.pressOnButton(2);
        remote.pressOffButton(2);

        // Invalid button
        remote.pressOffButton(99);
    }
}
