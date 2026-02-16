package org.example;

/*
 Bridge Pattern:
 - Separates abstraction from implementation
 - Allows both to vary independently
 - Uses composition instead of inheritance
 - Abstraction delegates work to implementor
*/


/*
 IMPLEMENTOR Interface
 Defines interface for implementation classes
*/
interface Application {
    void run();
}


/*
 CONCRETE IMPLEMENTORS
 Different types of applications
*/
class Browser implements Application {

    @Override
    public void run() {
        System.out.println("Running Browser application");
    }
}

class TextEditor implements Application {

    @Override
    public void run() {
        System.out.println("Running Text Editor application");
    }
}


/*
 ABSTRACTION Interface
 Defines high-level control interface
*/
interface OS {
    void boot();
    void launchApplication();
}


/*
 REFINED ABSTRACTION: MacOS
 Has reference to Application (bridge)
*/
class MacOS implements OS {

    // Bridge reference
    private Application application;

    public MacOS(Application application) {
        this.application = application;
    }

    @Override
    public void boot() {
        System.out.println("MacOS is booting...");
    }

    @Override
    public void launchApplication() {

        System.out.println("MacOS launching application:");
        application.run(); // Delegation happens here
    }
}


/*
 REFINED ABSTRACTION: Linux
*/
class Linux implements OS {

    private Application application;

    public Linux(Application application) {
        this.application = application;
    }

    @Override
    public void boot() {
        System.out.println("Linux is booting...");
    }

    @Override
    public void launchApplication() {

        System.out.println("Linux launching application:");
        application.run();
    }
}


/*
 REFINED ABSTRACTION: Windows
*/
class Windows implements OS {

    private Application application;

    public Windows(Application application) {
        this.application = application;
    }

    @Override
    public void boot() {
        System.out.println("Windows is booting...");
    }

    @Override
    public void launchApplication() {

        System.out.println("Windows launching application:");
        application.run();
    }
}


/*
 CLIENT
 Combines abstraction and implementation dynamically
*/
public class Main {

    public static void main(String[] args) {

        /*
         Create implementations
        */
        Application browser = new Browser();
        Application textEditor = new TextEditor();


        /*
         Bridge combinations

         Any OS can run any Application
        */

        OS mac = new MacOS(browser);
        OS linux = new Linux(textEditor);
        OS windows = new Windows(browser);


        /*
         Client uses abstraction only
        */

        mac.boot();
        mac.launchApplication();

        System.out.println();

        linux.boot();
        linux.launchApplication();

        System.out.println();

        windows.boot();
        windows.launchApplication();
    }
}