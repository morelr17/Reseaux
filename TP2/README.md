# Rapport pour le TP2

---
## Protocole UDP

### Exercice 1

Nous avons dans un premier temps crée un fichier Makefile dans le dossier Exercice1 comme ceci:

```
JFLAGS = -g
JC = javac
OPT = -O3
#OPT = -g
WARN = -Wall

all: ReceiveUDP SendUDP

ReceiveUDP: ReceiveUDP.java
	$(JC) $^

SendUDP: SendUDP.java
	$(JC) $^

clean: *.class 
	$(RM) $^

doc: *.java 
	javadoc -d $@ $^
```

Ensuite, nous avons crée les classes ReceiveUDP et SendUDP comme ceci:

ReceiveUDP:

```
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
```

SendUDP:
```
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
```

Et finalement, pour tester, nous avons lancé les commandes suivantes:

Premier terminal:
```
PS C:\Users\nawfe\licence\l3\RSX\tp\2021-rsx-1-t-ps-groupe-miage-1\TP2\Exercice1>  java ReceiveUDP 1500
```

Second terminal:
```
PS C:\Users\nawfe\licence\l3\RSX\TP\2021-rsx-1-t-ps-groupe-miage-1\TP2\Exercice1> java SendUDP localhost 1500 "you are welcome"
```

Dans le premier terminal, nous avons bien reçu le message:

```
PS C:\Users\nawfe\licence\l3\RSX\tp\2021-rsx-1-t-ps-groupe-miage-1\TP2\Exercice1> java ReceiveUDP 1500
Packet receive by :/127.0.0.1
port: 59310
size: 15
Message: you are welcome
```

---
## Multicast UDP

### Exercice 2

Nous avons crée le même makefile que pour l'exercice 1

Ensuite nous avons modifié les codes comme ceci:

ReceiveUDP:

```
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
```

SendUDP:

```
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.lang.String;

public class SendUDP {
    
    public static void main (String[] args) throws Exception {

        DatagramPacket packet;
        InetAddress dst = InetAddress.getByName("224.0.0.1");
        int port = 7654;
        byte[] array = args[0].getBytes();

        packet = new DatagramPacket(array, array.length, dst, port);

        MulticastSocket socket = new MulticastSocket();
        socket.setTimeToLive(1);
        socket.send(packet);
        socket.close(); 
    }
}
```

Et finalement, nous avons testé de la manière suivante:

Premier terminal :
```
PS C:\Users\nawfe\licence\l3\RSX\tp\2021-rsx-1-t-ps-groupe-miage-1\TP2\Exercice2> java ReceiveUDP
```

Second terminal: 
```
PS C:\Users\nawfe\licence\l3\RSX\TP\2021-rsx-1-t-ps-groupe-miage-1\TP2\Exercice2> java SendUDP "you are welcome"
```

Et nous avons bien reçu le message dans le premier terminal: 
```
PS C:\Users\nawfe\licence\l3\RSX\tp\2021-rsx-1-t-ps-groupe-miage-1\TP2\Exercice2> java ReceiveUDP
Packet receive by : /192.168.137.12
port: 54000
size: 15
Message: you are welcome  
```



---
## Protocole UDP

### Exercice 3

Dans un premier temps nous avons redéfini les classes SendUDP et ReceiveUDP de la façon suivante:

ReceiveUDP:

```
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
                    System.out.println(string);
                    System.out.print("> ");
                }
            } catch(IOException e) {
                if(!this.socket.isClosed()) {
                    System.out.println("Tchat(ReceiveUDP): problem encountered when we send a message");
                }
            }
        }
    }

}
```

SendUDP:

```
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
            byte[] msg = (this.nickname + " > " + string).getBytes();
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
```

Ensuite nous avons crée la classe Tchat qui va permettre de créer le tchat:

Tchat:

```
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
        tchat.run();
    }

}
```

Pour compiler le tout nous lançons la commande suivante:

```
make Tchat
```

Ensuite quand nous lançons la commande: 

```
java Tchat Nawfel
```

Nous obtenons alors le tchat suivant:

```
Welcome to the Tchat !
Romain want to send the following message:
Bonjour a Tous ! 
packet received by: /192.168.1.22
port: 7654
size: 17
Message:Bonjour a Tous ! 
Je suis en binôme avec Nawfel Senoussi.
packet received by: /192.168.1.22
port: 7654
size: 39
Message:Je suis en bin?me avec Nawfel Senoussi.
```




