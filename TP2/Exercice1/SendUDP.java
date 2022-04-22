import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.lang.String;

public class SendUDP {
    
    public static void main (String[] args) throws Exception {
	
		InetAddress dst = InetAddress.getByName(args[0]);
		int port = Integer.parseInt(args[1]);
		byte[] array = args[2].getBytes();

		DatagramSocket socket;
		DatagramPacket packet;
		packet = new DatagramPacket(array, array.length, dst, port);
		socket = new DatagramSocket();

		socket.send(packet);

		socket.close(); 
    }
}