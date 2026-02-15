package org.example;

import java.util.Scanner;

/*
 Chain of Responsibility Pattern:
 - Passes request along a chain of handlers
 - Each handler decides:
      • Handle the request
      OR
      • Forward it to next handler
 - Decouples sender from receiver
 - Client does not know which handler will process request
*/


/*
 Handler Interface
 All approvers implement this
*/
interface LeaveApprover {

    // Set next handler in chain
    void setNext(LeaveApprover next);

    // Process leave request
    void approveLeave(int days);
}


/*
 Handler 1: Team Lead
 Can approve leave up to 2 days
*/
class TeamLead implements LeaveApprover {

    private LeaveApprover next; // next handler in chain

    @Override
    public void setNext(LeaveApprover next) {
        this.next = next;
    }

    @Override
    public void approveLeave(int days) {

        // Team Lead can approve up to 2 days
        if (days <= 2) {
            System.out.println("Team Lead approved " + days + " days leave");
        }

        // Forward request to next handler
        else {
            System.out.println("Team Lead cannot approve. Forwarding to Manager...");

            if (next != null) {
                next.approveLeave(days);
            }
        }
    }
}


/*
 Handler 2: Manager
 Can approve leave up to 7 days
*/
class Manager implements LeaveApprover {

    private LeaveApprover next;

    @Override
    public void setNext(LeaveApprover next) {
        this.next = next;
    }

    @Override
    public void approveLeave(int days) {

        if (days <= 7) {
            System.out.println("Manager approved " + days + " days leave");
        }

        else {
            System.out.println("Manager cannot approve. Forwarding to HR...");

            if (next != null) {
                next.approveLeave(days);
            }
        }
    }
}


/*
 Handler 3: HR
 Can approve leave up to 14 days
*/
class HR implements LeaveApprover {

    private LeaveApprover next;

    @Override
    public void setNext(LeaveApprover next) {
        this.next = next;
    }

    @Override
    public void approveLeave(int days) {

        if (days <= 14) {
            System.out.println("HR approved " + days + " days leave");
        }

        else {
            System.out.println("HR cannot approve. Forwarding to Leave Panel...");

            if (next != null) {
                next.approveLeave(days);
            }
        }
    }
}


/*
 Handler 4: Leave Panel
 Final authority
*/
class LeavePanel implements LeaveApprover {

    private LeaveApprover next;

    @Override
    public void setNext(LeaveApprover next) {
        this.next = next;
    }

    @Override
    public void approveLeave(int days) {

        if (days <= 31) {
            System.out.println("Leave Panel approved " + days + " days leave");
        }

        else {
            System.out.println("Leave request denied. Too many days.");
        }
    }
}


/*
 Client
 Creates chain and sends request
*/
public class Main {

    public static void main(String[] args) {

        // Create handlers
        LeaveApprover teamLead = new TeamLead();
        LeaveApprover manager = new Manager();
        LeaveApprover hr = new HR();
        LeaveApprover leavePanel = new LeavePanel();


        /*
         Build chain

         TeamLead → Manager → HR → LeavePanel
        */
        teamLead.setNext(manager);
        manager.setNext(hr);
        hr.setNext(leavePanel);


        // Client input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter leave days: ");
        int days = sc.nextInt();


        /*
         Client sends request to first handler

         Client does NOT know who will approve
        */
        teamLead.approveLeave(days);

        sc.close();
    }
}
