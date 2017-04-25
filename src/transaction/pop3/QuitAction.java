package transaction.pop3;

import connexion.Connexion;
import connexion.Message;
import transaction.Command;
import transaction.Transaction;

/**
 * Created by Gaetan on 11/03/2017.
 * List command
 */
public class QuitAction extends Transaction {

    public QuitAction(Connexion connexion) {
        super(connexion);
    }

    private boolean connexionClosed = false;

    @Override
    public void run() {
        this.connexion.send(new Message(Command.QUIT, ""));
        this.message = this.connexion.receive();
        if (message.getCommand() == Command.OK) {
            connexionClosed = true;
            setChanged();
            notifyObservers(this);
        }
    }

    public boolean isConnexionClosed() {
        return connexionClosed;
    }
}
