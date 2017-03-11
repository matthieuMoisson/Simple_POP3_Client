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

    public Authentication(Connexion connexion, String username, String password) {
        super(connexion);
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        this.connexion.send(new Message(Command.APOP, username + " " + password));
        this.message = this.connexion.receive();
        setChanged();
        notifyObservers(this);
    }
}
