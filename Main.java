package org.example;

import java.util.Scanner;

/*
 Adapter Pattern:
 - Converts one interface into another that the client expects
 - Allows incompatible classes to work together
 - Client remains unaware of the conversion logic
*/

/*
 Target Interface
 The client works only with dollars
 */
interface DollarAmount {
    void printInDollars();
}

/*
 Adaptee
 Existing class that represents money in Rupees
 */
class MoneyInRupee {

    private final double amount;

    public MoneyInRupee(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

/*
 Adapter
 Bridges Rupee-based money to Dollar-based interface
 */
class RupeeToDollarAdapter implements DollarAmount {

    private final MoneyInRupee rupeeMoney;

    // Keeping conversion rate as constant improves readability and maintainability
    private static final double CONVERSION_RATE = 80.0;

    public RupeeToDollarAdapter(MoneyInRupee rupeeMoney) {
        this.rupeeMoney = rupeeMoney;
    }

    @Override
    public void printInDollars() {
        double dollars = rupeeMoney.getAmount() / CONVERSION_RATE;
        System.out.println("Converted ₹" + rupeeMoney.getAmount() + " to $" + dollars);
    }
}

/*
 Client
 Depends only on the DollarAmount interface, not on Rupees
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print(
                "Welcome to the International Bank.\n" +
                        "Enter amount in Rupees: "
        );

        double amount = sc.nextDouble();
        sc.nextLine(); // consume leftover newline

        // Adaptee
        MoneyInRupee rupeeMoney = new MoneyInRupee(amount);

        // Adapter hides the conversion logic from the client
        DollarAmount dollarAmount = new RupeeToDollarAdapter(rupeeMoney);

        System.out.println(
                "\nWelcome to the American Bank.\n" +
                        "We accept payments only in Dollars.\n" +
                        "Press Enter to submit your amount."
        );

        sc.nextLine(); // wait for user input

        // Client uses DollarAmount without knowing about Rupees
        dollarAmount.printInDollars();

        sc.close();
    }
}