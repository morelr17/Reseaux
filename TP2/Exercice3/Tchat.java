import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.lang.String;

public class Tchat {

    protected MulticastSocket socket;
    protected String nickname;


    /**
     * Constructor
     * @param nickname the name we want the message to be sent
     */
    public Tchat(String nickname) throws Exception{
        this.nickname = nickname;
        
        int port = 7654;
        InetAddress dst = InetAddress.getByName("224.0.0.1");
        this.socket = new MulticastSocket(port);
        this.socket.joinGroup(dst);
    }

    /**
     * Run the program
     * Create a thread to receiving the message and another to send them.
     */
    public void run() throws Exception {
        ReceiveUDP receive = new ReceiveUDP(this.socket);
        SendUDP send = new SendUDP(this.socket, this.nickname);
        Thread threadReceive = new Thread(receive);
        Thread threadSend = new Thread(send);

        
        threadReceive.start();
        threadSend.start();
        threadSend.join();
        
        this.socket.close();
    }

    /**
     * Run the tchat program in a shell
     */
    public static void main (String[] args) throws Exception {
        String nickname;
        
        System.out.println("Welcome to the Tchat !");
        if(args.length >= 1 && !args[0].equals("")){
            nickname = args[0];
        }
        else{
            nickname = "Romain";
        }
        Tchat tchat = new Tchat(nickname);
        System.out.println(nickname + " want to send the following message:");
        tchat.run();
    }

}