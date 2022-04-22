import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.lang.String;

public class ReceiveUDP {

    public static void main (String[] args) throws Exception {

        DatagramPacket packet; 
        MulticastSocket socket;
        socket = new MulticastSocket(7654);
        packet = new DatagramPacket(new byte[512], 512);

        socket.joinGroup(InetAddress.getByName("224.0.0.1"));
        socket.receive(packet);

        System.out.println("Packet receive by : " + packet.getAddress() + "\nport: " + packet.getPort() + "\nsize: " + packet.getLength());

        byte array[] = packet.getData();
        String message = new String(array);
        System.out.println("Message: " + message);

        socket.close();
    }
}