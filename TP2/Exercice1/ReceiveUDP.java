import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.lang.String;

public class ReceiveUDP {

    public static void main (String[] args) throws Exception {
		
		DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));
		DatagramPacket packet = new DatagramPacket(new byte[512], 512);

		socket.receive(packet);

		System.out.println("Packet receive by :" + packet.getAddress() + "\nport: " + packet.getPort() + "\nsize: " + packet.getLength());

		byte array[] = packet.getData();
		String message = new String(array);
		System.out.println("Message: " + message);
		
		socket.close();
	}
}