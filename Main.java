package org.example;

import java.util.Scanner;

/*
 State Pattern:
 - Allows an object to change its behavior when its internal state changes
 - The object appears to change its class
 - Eliminates large conditional statements (if-else / switch)
*/

/*
 State Interface
 Defines all possible actions that can be performed on the vending machine
 Each concrete state will implement this behavior differently
 */
interface VendingState {
    void insertCoin();
    void selectItem();
    void dispenseItem();
    void refill();
}

/*
 Context Class (VendingMachine)
 - Maintains the current state
 - Delegates behavior to the current state
 - Holds all possible state instances
 */
class VendingMachine {

    // All possible states of the machine
    private VendingState noCoinState;
    private VendingState hasCoinState;
    private VendingState dispenseState;
    private VendingState soldOutState;

    // Current state of the machine
    private VendingState currentState;

    public VendingMachine() {
        // Initialize all states and pass current machine reference
        noCoinState = new NoCoinState(this);
        hasCoinState = new HasCoinState(this);
        dispenseState = new DispenseState(this);
        soldOutState = new SoldOutState(this);

        // Initial state (can be changed based on inventory)
        currentState = noCoinState;
    }

    // Method to update current state
    public void setState(VendingState state) {
        this.currentState = state;
    }

    // Getter methods to allow states to transition
    public VendingState getNoCoinState() { return noCoinState; }
    public VendingState getHasCoinState() { return hasCoinState; }
    public VendingState getDispenseState() { return dispenseState; }
    public VendingState getSoldOutState() { return soldOutState; }

    // Delegate actions to current state
    public void insertCoin() {
        currentState.insertCoin();
    }

    public void selectItem() {
        currentState.selectItem();
    }

    public void dispenseItem() {
        currentState.dispenseItem();
    }

    public void refill() {
        currentState.refill();
    }
}

/*
 Concrete State: NoCoinState
 - Machine is waiting for coin insertion
 */
class NoCoinState implements VendingState {

    private VendingMachine machine;

    public NoCoinState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertCoin() {
        System.out.println("Coin inserted");
        // Transition to HasCoinState
        machine.setState(machine.getHasCoinState());
    }

    @Override
    public void selectItem() {
        System.out.println("Please insert a coin first!");
    }

    @Override
    public void dispenseItem() {
        System.out.println("Insert coin and select item first!");
    }

    @Override
    public void refill() {
        System.out.println("Machine refilled successfully");
    }
}

/*
 Concrete State: HasCoinState
 - Coin has been inserted, waiting for item selection
 */
class HasCoinState implements VendingState {

    private VendingMachine machine;

    public HasCoinState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectItem() {
        System.out.println("Item selected");
        // Transition to DispenseState
        machine.setState(machine.getDispenseState());
    }

    @Override
    public void insertCoin() {
        System.out.println("Coin already inserted");
    }

    @Override
    public void dispenseItem() {
        System.out.println("Select item first");
    }

    @Override
    public void refill() {
        System.out.println("Complete current transaction first");
    }
}

/*
 Concrete State: DispenseState
 - Responsible for dispensing the item
 */
class DispenseState implements VendingState {

    private VendingMachine machine;

    public DispenseState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void dispenseItem() {
        System.out.println("Dispensing item...");
        // After dispensing, go back to NoCoinState
        machine.setState(machine.getNoCoinState());
    }

    @Override
    public void insertCoin() {
        System.out.println("Wait! Dispensing in progress");
    }

    @Override
    public void selectItem() {
        System.out.println("Item already selected");
    }

    @Override
    public void refill() {
        System.out.println("Cannot refill during dispensing");
    }
}

/*
 Concrete State: SoldOutState
 - Machine has no items available
 */
class SoldOutState implements VendingState {

    private VendingMachine machine;

    public SoldOutState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertCoin() {
        System.out.println("Machine is sold out. Cannot insert coin.");
    }

    @Override
    public void selectItem() {
        System.out.println("No items available");
    }

    @Override
    public void dispenseItem() {
        System.out.println("Nothing to dispense");
    }

    @Override
    public void refill() {
        System.out.println("Machine refilled");
        // After refill, machine becomes usable again
        machine.setState(machine.getNoCoinState());
    }
}

/*
 Client
 - Interacts only with VendingMachine (context)
 - Does not know about internal state transitions
 */
public class Main {

    public static void main(String[] args) {

        VendingMachine vendingMachine = new VendingMachine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Vending Machine =====");
            System.out.println("Press 1 to insert coin");
            System.out.println("Press 2 to select item");
            System.out.println("Press 3 to dispense item");
            System.out.println("Press 4 to refill machine");
            System.out.println("Press any other key to exit");

            System.out.print("Enter your choice: ");

            int input = scanner.nextInt();

            if (input == 1) {
                System.out.println("Action: Insert Coin");
                vendingMachine.insertCoin();

            } else if (input == 2) {
                System.out.println("Action: Select Item");
                vendingMachine.selectItem();

            } else if (input == 3) {
                System.out.println("Action: Dispense Item");
                vendingMachine.dispenseItem();

            } else if (input == 4) {
                System.out.println("Action: Refill Machine");
                vendingMachine.refill();

            } else {
                System.out.println("Invalid input! Exiting...");
                scanner.close();
                break;
            }
        }
    }
}