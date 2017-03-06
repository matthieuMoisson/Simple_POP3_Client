package sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Gaetan on 06/03/2017.
 * Connexion
 */
public class Connexion {

    private final InputStream is;
    private final OutputStream os;

    public Connexion() throws IOException {
        Socket clientSocket = new Socket(Settings.getIpServer(), Settings.getPort());
        is = clientSocket.getInputStream();
        os = clientSocket.getOutputStream();

    }

    public Message authentication(String username, String password) {
        send(new Message(Command.APOP, username + " " + password));
        return receive();
    }

    /**
     * Send a message over TCP
     *
     * @param message Message to sent
     */
    private void send(Message message) {
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
    private Message receive() {
        byte b[] = new byte[Message.BUFFER_MAX_SIZE];
        try {
            if (is.read(b) != -1) {
                Message message = new Message(b);
                System.out.println("Message received : " + message);
                return message;
            }
        } catch (IOException e) {
            System.out.printf("Could not read from input stream");
            e.printStackTrace();
        }
        return new Message(Command.EXCEPTION);
    }

}
