package transaction;

import sample.Connexion;
import sample.Message;

import java.util.List;

/**
 * Created by Gaetan on 11/03/2017.
 * List command
 */
public class StatAction extends Transaction {

    public StatAction(Connexion connexion) {
        super(connexion);
    }

    private int nbMails = 0;
    private int sizeMails = 0;

    @Override
    public void run() {
        this.connexion.send(new Message(Command.STAT, ""));
        this.message = this.connexion.receive();
        if (message.getCommand() == Command.OK) {
            if (this.buildInfos(this.message)) {
                setChanged();
                notifyObservers(this);
            }
        }
    }

    private boolean buildInfos(Message message) {
        List<String> args = message.getArgs();
        if (args.size() == 2) {
            nbMails = Integer.parseInt(args.get(0));
            sizeMails = Integer.parseInt(args.get(1));
            return true;
        }
        return false;
    }

    public int getNbMails() {
        return nbMails;
    }

    public int getSizeMails() {
        return sizeMails;
    }
}
