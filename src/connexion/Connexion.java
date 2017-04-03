package connexion;

import sample.Settings;
import transaction.Command;

import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Gaetan on 06/03/2017.
 * Connexion
 */
public abstract class Connexion {

    private final InputStream is;
    private final OutputStream os;

    protected String currentUsername = "";

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    protected static Connexion connexion;

    protected Connexion() throws IOException {
        SSLServerSocketFactory serverFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SocketFactory factory= SSLSocketFactory.getDefault() ;
        SSLSocket clientSocket = (SSLSocket) factory.createSocket(Settings.getIpServer(), Settings.getPort());
        clientSocket.setEnabledCipherSuites(serverFactory.getSupportedCipherSuites());
        is = clientSocket.getInputStream();
        os = clientSocket.getOutputStream();
    }

    public String getTimetamp(Message m)
    {
        List<String> args = m.getArgs();
        for (String s: args ) {
            if(s.charAt(0) == '<')
                return s;
        }
        return null;
    }

    /**
     * Send a message over TCP
     *
     * @param message Message to sent
     */
    public void send(Message message) {
        try {
            os.write(message.getBytes());
            System.out.println("Message Sent : " + message);
        } catch (IOException e) {
            System.out.println("Could not write " + message);
            e.printStackTrace();
        }
    }

    /**
     * Receive a message over TCP
     *
     * @return the message received
     */
    public Message receive() {
        byte b[] = new byte[Message.BUFFER_MAX_SIZE];
        try {
            if (is.read(b) != -1) {
                Message message = new Message(b);
                System.out.println("Message received : " + message);
                return message;
            }
        } catch (IOException e) {
            System.out.println("Could not read from input stream");
            e.printStackTrace();
        }
        return new Message(Command.EXCEPTION);
    }

    public void close() {
        try {
            this.is.close();
            this.os.close();
            connexion = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
