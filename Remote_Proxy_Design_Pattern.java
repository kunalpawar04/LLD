package org.example;

/*
 Remote Proxy Pattern:
 - Provides a local representation of an object located on a remote server
 - Hides network communication details from the client
 - Client interacts as if the object is local
 - Proxy handles connection, communication, and disconnection
*/


/*
 Subject Interface
 Both Real Object (Remote Server) and Proxy implement this
 Client interacts only with this interface
*/
interface Data {
    void fetchData();
}


/*
 Real Object (Remote Object)
 This object exists on a remote server
 Proxy communicates with this object over network
*/
class RemoteServerData implements Data {

    public RemoteServerData() {
        System.out.println("Remote Server: Initializing remote server resources...");
    }

    @Override
    public void fetchData() {
        System.out.println("Remote Server: Fetching private data from remote server...");
    }
}


/*
 Proxy Class (Remote Proxy)
 This acts as a local representative of the remote server
 It manages network communication
*/
class RemoteServerProxy implements Data {

    // Reference to remote object
    private RemoteServerData remoteServerData;

    @Override
    public void fetchData() {

        /*
         Step 1: Establish connection to remote server
        */
        System.out.println("Proxy: Connecting to remote server...");


        /*
         Step 2: Create remote object if not already created
         (Simulates obtaining remote reference)
        */
        if (remoteServerData == null) {
            remoteServerData = new RemoteServerData();
        }


        /*
         Step 3: Forward request to remote server
        */
        System.out.println("Proxy: Sending request to remote server...");
        remoteServerData.fetchData();


        /*
         Step 4: Close connection
        */
        System.out.println("Proxy: Disconnecting from remote server...");
    }
}


/*
 Client
 Client does NOT know:
 - Whether object is local or remote
 - How connection is established
 - How communication happens
*/
public class Main {

    public static void main(String[] args) {

        /*
         Client interacts with proxy like a normal object
         Client unaware of remote communication
        */
        Data data = new RemoteServerProxy();

        /*
         Proxy handles all remote communication
        */
        data.fetchData();
    }
}
