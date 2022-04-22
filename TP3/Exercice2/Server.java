import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public final int port = 2021;
	private ServerSocket server;
	public ArrayList<Socket> clients;

	/**
    * Create Server with ServerSocket
    */
    public void create() throws IOException {
		server = new ServerSocket(port);
		System.out.println("Bienvenue sur le Serveur");
		clients = new ArrayList<Socket>();
		this.acceptClients();
	}

    /**
    *Accept all Client on server and lauch the thread 
    */
	public void acceptClients() throws IOException {
		while (true) {
			Client c = new Client(server.accept(), this );
			c.start();
		}
	}

   
	public static void main(String[] args) {
		Server s = new Server();
		try {
			s.create();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
	}

}