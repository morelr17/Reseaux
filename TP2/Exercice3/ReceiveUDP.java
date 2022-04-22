import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.lang.String;
import java.lang.Runnable;
import java.io.IOException;

public class ReceiveUDP implements Runnable{

    protected MulticastSocket socket;
    protected int port;
    protected InetAddress dst;

    /**
     * Constructor
     * @param socket the socket we want to read 
     */
    public ReceiveUDP(MulticastSocket socket) throws Exception {
        this.port = 7654;
        this.dst = InetAddress.getByName("224.0.0.1");
        this.socket = socket;
    }

    @Override
    /**
     * Receive a message from the socket while the socket is opened
     */
    public void run () {
        DatagramPacket packet; 
        byte[] msg;
        String string;
        while(!this.socket.isClosed()) {
            packet = new DatagramPacket(new byte[512], 512);

            try {
                this.socket.receive(packet);

                if(!packet.getAddress().equals(this.dst)) {
                    System.out.println("packet received by: " + packet.getAddress() + "\nport: " + packet.getPort() + "\nsize: " + packet.getLength());
                
                    msg = packet.getData();
                    string = new String(msg);
                    
                    System.out.println("Message:" + string);
                }
            } catch(IOException e) {
                if(!this.socket.isClosed()) {
                    System.out.println("Tchat(ReceiveUDP): problem encountered when we send a message");
                }
            }
        }
    }

}