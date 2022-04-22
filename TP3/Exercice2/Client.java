import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

	Server server;
	Socket client;
	BufferedReader input;

	public Client(Socket socket, Server server) {
		this.client = socket;
		this.server = server;
        
	}

    /**
    *Put the client in Listening Mode 
    */
	public void run() {
		this.addClient(client);
		try {
			this.listen();
		} 
        catch (IOException e) {
			System.out.println("Listen Error");
			try {
				client.close();
			} 
            catch (IOException e1) {
				System.out.println("Client closed error");
			}
		}

	}

    /**
    *the client listen message of Server 
    */
	public void listen() throws IOException {
		
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String message;

		while((message = input.readLine()) != null && !(message.equals("Stop"))){
			this.diffuseMessage(message);
		}
		input.close();
		this.removeClient(client);
    }
		
	

    /**
    *add client in list of server 
    *@param client a client socket 
    */
	public void addClient(Socket client) {
		System.out.println(client + " connected.");
		server.clients.add(client);
	}

    /**
    *remove Client from server 
    *@param client a client socket 
    */
	public void removeClient(Socket client) throws IOException {
		server.clients.remove(client);
		System.out.println(client + " disconnected.");
		client.close();
	}
	
    /**
    *Send one message for all people who is connect to server 
    *@param message a message for online people 
    */
	public void diffuseMessage(String message) {
		for (Socket s : server.clients) {
			if (!s.equals(this.client)) {
				try {
					PrintWriter output = new PrintWriter(s.getOutputStream());
					output.println(message);
					output.flush();
				} catch (IOException e) {
					System.out.println(s + " not reached.");
				}
			}
		}
	}
}