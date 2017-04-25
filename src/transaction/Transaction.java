package transaction;

import connexion.Connexion;
import connexion.Message;

import java.util.Observable;

/**
 * Created by Gaetan on 07/03/2017.
 * Mother class
 */
public abstract class Transaction extends Observable implements Runnable {

    protected Connexion connexion;

    protected Message message;

    public Message getMessage() {
        return message;
    }

    protected Transaction(Connexion connexion) {
        this.connexion = connexion;
    }

}
