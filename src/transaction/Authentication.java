package transaction;

import sample.Connexion;
import sample.Message;

/**
 * Created by Gaetan on 07/03/2017.
 * Authentication
 */
public class Authentication extends Transaction{

    private String username;
    private String password;
    private String timestamp;
    private final String SHARE_SECRET = "montagne";

    public Authentication(Connexion connexion, String username, String password, String timestamp) {
        super(connexion);
        this.username = username;
        this.password = password;
        this.timestamp = timestamp;
    }

    @Override
    public void run() {
        this.connexion.send(new Message(Command.APOP, username + " " + password));
        this.message = this.connexion.receive();
        setChanged();
        notifyObservers(this);
    }
}
