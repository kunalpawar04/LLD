package org.example;

/*
 Template Method Design Pattern:
 ---------------------------------------
 - Defines the skeleton of an algorithm in a base class
 - Lets subclasses implement individual steps of the algorithm
 - The overall flow is fixed and cannot be changed by subclasses
 - Uses a final method (template method) to enforce execution order
 - Promotes code reuse and consistent behavior across implementations

 In this example:
 - TransactionFlow defines the fixed transaction pipeline
 - UPIPayment provides concrete implementations for each step
 - Subclasses can customize steps but cannot alter the transaction flow
*/

abstract class TransactionFlow {

    // Step 1: Validate whether the transaction can proceed
    // Subclasses decide how validation is done
    public abstract void validateBalance();

    // Step 2: Deduct money from sender's account
    public abstract void deductAccount();

    // Step 3: Credit money to receiver's account
    public abstract void creditAccount();

    // Step 4: Notify sender and receiver about transaction status
    public abstract void notifyParties();

    // Template Method:
    // - Marked final so subclasses cannot change the execution order
    // - Defines the fixed pipeline for a transaction
    public final void transactionPipeFlow() {
        validateBalance();
        deductAccount();
        creditAccount();
        notifyParties();
    }
}

// Concrete implementation of TransactionFlow for UPI payments
class UPIPayment extends TransactionFlow {

    // Now even if we change the sequence of overriding this method
    // The execution flow will remain same

    @Override
    public void creditAccount() {
        System.out.println("Step 3: Credit balance to receiver if sender has enough balance");
    }

    @Override
    public void notifyParties() {
        System.out.println("Step 4: Notify transaction status to involved parties :)");
    }

    @Override
    public void validateBalance() {
        System.out.println("Step 1: Check if sender has enough balance");
    }

    @Override
    public void deductAccount() {
        System.out.println("Step 2: Deduct balance if account has sufficient funds");
    }
}

public class Main {

    public static void main(String[] args) {

        // Client code interacts only with the template method
        // It does not control or know the internal steps
        UPIPayment upi = new UPIPayment();
        upi.transactionPipeFlow();
    }
}