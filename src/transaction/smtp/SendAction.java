package transaction.smtp;

import connexion.Connexion;
import connexion.Message;
import mail.Mail;
import transaction.Command;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaetan on 11/03/2017.
 * Send a mail
 */
public class SendAction extends Transaction {

    private Mail mail;

    private List<String> recipients = new ArrayList<>();

    public SendAction(Connexion connexion, Mail mail) {
        super(connexion);
        this.mail = mail;
        this.buildRecipients(mail.getReceiver());
    }

    private void buildRecipients(String fullReceiver) {

            String[] receivers = fullReceiver.split(";");
            for (String receiver : receivers) {
                if (receiver.contains("<") && receiver.contains(">")) {
                    this.recipients.add(receiver.split("<")[1].split(">")[0]);
                }else {
                    this.recipients.add(receiver.trim());
                }
            }
    }

    @Override
    public void run() {

        // System.out.println(this.recipients);

        this.connexion.send(new Message(Command.ELHO, String.valueOf(5)));


        this.message = this.connexion.receive();
        if (message.getCommand() == Command.OK) {
            setChanged();
            notifyObservers(this);
        }

    }
}
