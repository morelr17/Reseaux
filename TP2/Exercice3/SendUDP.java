import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.lang.String;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class SendUDP implements Runnable {

    protected BufferedReader in;
    protected MulticastSocket socket;
    protected InetAddress dst;
    protected int port;
    protected String nickname;

    /**
     * Constructor
     * @param socket the socket we want to read
     * @param nickname the name we want the message to be sent with
     */
    public SendUDP(MulticastSocket socket, String nickname) throws Exception {
        this.nickname = nickname;
        this.in = new BufferedReader(new InputStreamReader(System.in)); 
        this.socket = socket;
        this.port = 7654;
        this.dst = InetAddress.getByName("224.0.0.1");
    }

    /**
     * Wait for an input message and send it.
     */
    public void run () {
        DatagramPacket packet;
        String string = null;
        try {
            while(!(string = this.in.readLine()).equals("/part") && !(string == null)) {
            byte[] msg = string.getBytes();
            packet = new DatagramPacket(msg, msg.length, dst, port);
            socket.setTimeToLive(1);
            socket.send(packet);
            }	
        } catch (IOException e) {
            System.out.println("Tchat(SendUDP): a problem is encountered when we send a message");
        }
        System.out.println("Close the Tchat program.");
    }
    
}