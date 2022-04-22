import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static int PORT = 2021;

    public static void main(String[] args) {

        // Création du serveur
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
        }
        catch (IOException e) {
            System.out.println("SocketServeur Error");
        }

        while (true) {
            // Création de la socket client et acceptation de cette socket
            Socket client = null;
            try {
                client = server.accept();
            }
            catch (IOException e) {
                System.out.println("Error With Server and Socket");
            }

            // Envoi du message par le serveur
            try {
                new PrintStream(client.getOutputStream()).println("Bienvenue sur mon serveur et au revoir\n");
            } 
            catch (IOException e) {
                System.out.println("Error PrintStream");
            }

            // Fermeture de la socket
            try {
                client.close();
            }
            catch (IOException e) {
                System.out.println("Error closing socket");
            }
        }
    }
}