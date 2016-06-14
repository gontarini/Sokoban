package sokoban;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcin and pawel
 */
public class NetworkClient {

    /**
     * name of the server
     */
    private String hostName = "localhost";

    /**
     * remote endpoint connection
     */
    int portNumber = 1234;

    /**
     * socket for the specific client
     */
    protected Socket client;

    /**
     * establishing connection with the server
     */
    public NetworkClient() {
        try {
            System.out.println("Connecting to " + hostName
                    + " on port " + portNumber);
            client = new Socket("192.168.0.2", portNumber);
            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());
//         OutputStream outToServer = client.getOutputStream();
//         DataOutputStream out = new DataOutputStream(outToServer);
//         out.writeUTF("Hello from "
//                      + client.getLocalSocketAddress());
//         InputStream inFromServer = client.getInputStream();
//         DataInputStream in =
//                        new DataInputStream(inFromServer);
//         System.out.println("Server says " + in.readUTF());
//         client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void requestLevels() {
        
            try {
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream  out = new DataOutputStream(outToServer);
                out.writeUTF("levels");
            } catch (IOException ex) {
                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }
}
