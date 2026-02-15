package org.example;

/*
 Protection Proxy Pattern:
 - Controls access to a real object
 - Checks authorization before allowing access
 - Only authorized users can perform sensitive operations
 - Client is unaware of the access control logic
*/


/*
 Subject Interface
 Both Real Object and Proxy implement this
*/
interface FileAccess {
    void readFile();
}


/*
 Real Object
 This is the sensitive object that must be protected
*/
class RealFile implements FileAccess {

    @Override
    public void readFile() {
        System.out.println("Reading sensitive file content...");
    }
}


/*
 Proxy Class (Protection Proxy)
 This controls access to RealFile
*/
class FileProxy implements FileAccess {

    private RealFile realFile;
    private String role; // user's role

    public FileProxy(String role) {
        this.role = role;
    }

    @Override
    public void readFile() {

        /*
         Check if user is authorized
         Only ADMIN can access the file
        */
        if (role.equals("ADMIN")) {

            /*
             Create real object only when access is allowed
            */
            if (realFile == null) {
                realFile = new RealFile();
            }

            /*
             Forward request to real object
            */
            realFile.readFile();

        } else {

            /*
             Access denied
             Real object is never created
            */
            System.out.println("Access Denied: You are not authorized");
        }
    }
}


/*
 Client
 Client does not know whether it's using proxy or real object
*/
public class Main {

    public static void main(String[] args) {

        // Unauthorized user
        FileAccess userFile = new FileProxy("USER");

        System.out.println("USER trying to read file:");
        userFile.readFile();


        System.out.println();


        // Authorized user
        FileAccess adminFile = new FileProxy("ADMIN");

        System.out.println("ADMIN trying to read file:");
        adminFile.readFile();
    }
}
