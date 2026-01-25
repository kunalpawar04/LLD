package org.example;

/*
 Factory Design Pattern:
 - Encapsulates object creation logic
 - Client does not know which concrete class is instantiated
 - New implementations can be added with minimal changes
*/

/* ---------- BASE NOTIFICATION CONTRACT ---------- */
interface NotificationJob {
    void doNotify();
}

/* ---------- SMS NOTIFICATION FAMILY ---------- */
interface SMSNotificationJob extends NotificationJob {
    // Marker interface for SMS-based notifications
}

class Android16NotificationJob implements SMSNotificationJob {
    @Override
    public void doNotify() {
        System.out.println(
                "Hey there 😄, your notification service is running on Android 16!"
        );
    }
}

class Android10NotificationJob implements SMSNotificationJob {
    @Override
    public void doNotify() {
        System.out.println(
                "Hi, notification service running on older Android 10 🥲"
        );
    }
}

class IOS26NotificationJob implements SMSNotificationJob {
    @Override
    public void doNotify() {
        System.out.println(
                "Welcome to iOS 😈. Please subscribe to use this notification service."
        );
    }
}

class GenericNotificationJob implements SMSNotificationJob {
    @Override
    public void doNotify() {
        System.out.println("Generic SMS Notification service started");
    }
}

/* ---------- EMAIL NOTIFICATION FAMILY ---------- */
interface EmailNotificationJob extends NotificationJob {
    // Marker interface for Email-based notifications
}

class LinuxEmailNotificationJob implements EmailNotificationJob {
    @Override
    public void doNotify() {
        System.out.println(
                "[LOG] Linux Email Notification service started"
        );
    }
}

class MacEmailNotificationJob implements EmailNotificationJob {
    @Override
    public void doNotify() {
        System.out.println(
                "Welcome!😎 macOS Email Notification service is running smoothly"
        );
    }
}

class WindowsEmailNotificationJob implements EmailNotificationJob {
    @Override
    public void doNotify() {
        System.out.println(
                "Windows Email Notification service started (expect surprises 🌚)"
        );
    }
}

/*
 Factory class:
 - Centralizes creation logic
 - Decides which implementation to return
 - Client code stays clean and decoupled
*/
class NotificationFactory {

    public SMSNotificationJob getSMSNotificationJob(String type) {

        if ("android16".equalsIgnoreCase(type)) {
            return new Android16NotificationJob();
        } else if ("android10".equalsIgnoreCase(type)) {
            return new Android10NotificationJob();
        } else if ("ios26".equalsIgnoreCase(type)) {
            return new IOS26NotificationJob();
        }

        // Default fallback
        return new GenericNotificationJob();
    }

    public EmailNotificationJob getEmailNotificationJob(String os) {

        if ("linux".equalsIgnoreCase(os)) {
            return new LinuxEmailNotificationJob();
        } else if ("mac".equalsIgnoreCase(os)) {
            return new MacEmailNotificationJob();
        }

        // Default fallback
        return new WindowsEmailNotificationJob();
    }
}

public class Main {
    public static void main(String[] args) {

        NotificationFactory factory = new NotificationFactory();

        SMSNotificationJob android16Sms =
                factory.getSMSNotificationJob("android16");
        android16Sms.doNotify();
        System.out.println();

        SMSNotificationJob iosSms =
                factory.getSMSNotificationJob("ios26");
        iosSms.doNotify();
        System.out.println();

        SMSNotificationJob genericSms =
                factory.getSMSNotificationJob("Realme 3");
        genericSms.doNotify();
        System.out.println();

        EmailNotificationJob linuxEmail =
                factory.getEmailNotificationJob("linux");
        linuxEmail.doNotify();
        System.out.println();

        EmailNotificationJob windowsEmail =
                factory.getEmailNotificationJob("windows");
        windowsEmail.doNotify();
    }
}
