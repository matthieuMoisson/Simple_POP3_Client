package transaction.smtp;

import connexion.Message;
import connexion.SmtpConnexion;
import mail.Mail;
import transaction.Command;
import transaction.Transaction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gaetan on 11/03/2017.
 * Send a mail
 */
public class SendAction extends Transaction {

    private Mail mail;

    private List<String> recipients = new ArrayList<>();

    private List<String> recipientsValid = new ArrayList<>();

    public SendAction(Mail mail) throws IOException {
        super(SmtpConnexion.getInstance());
        this.connexion.receive();
        this.mail = mail;
        this.buildRecipients(mail.getReceiver());
    }

    private void buildRecipients(String fullReceiver) {
        String[] receivers = fullReceiver.split(";");
        for (String receiver : receivers) {
            if (receiver.contains("<") && receiver.contains(">")) {
                this.recipients.add("<" + receiver.split("<")[1].split(">")[0] +">");
            }else {
                this.recipients.add("<" +receiver.trim() + ">");
            }
        }
    }

    @Override
    public void run() {

        // System.out.println(this.recipients);

        // ------------ Envoi ELHO
        this.connexion.send(new Message(Command.ELHO, "polytech.ipc"));
        this.message = this.connexion.receive();
        System.out.println(this.message.getCommand());
        if (this.message.getCommand() != Command.OKSMTP) {
            System.out.println("Error after ELHO: " + this.message.getArgComplet());
            return;
        }

        // ------------- Envoi MAIL FROM
        this.connexion.send(new Message("MAIL FROM " + mail.getSender()));
        this.message = this.connexion.receive();

        // ------------- Envoi RCPT TO
        for (String recipient : recipients) {
            this.connexion.send(new Message("RCPT TO " + recipient));
            this.message = this.connexion.receive();
            if(message.getCommand() != Command.ERRORSMTP) {
                recipientsValid.add(recipient);
            }
        }

        if(!recipientsValid.isEmpty())
        {
            // ------------- Envoi DATA
            this.connexion.send(new Message(Command.DATA));
            this.message = this.connexion.receive();
            if(message.getCommand() == Command.GETMAIL) {
                // ------------- Envoi Mail content

                this.connexion.send(new Message(this.buildMail()));

                this.message = this.connexion.receive();
                if(message.getCommand() == Command.OKSMTP)
                    this.connexion.send(new Message(Command.QUIT));
                else
                    this.sendReset();
            }
            else
            {
                this.sendReset();
            }
        } else{
            this.sendReset();
        }
        if (message.getCommand() == Command.OKSMTP) {
            setChanged();
            notifyObservers(this);
        }
    }

    private String buildMail(){
        StringBuilder mail = new StringBuilder("");
        mail.append("Date: ").append(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss").format(new Date())).append("\r\n");
        mail.append("Subject: ").append(this.mail.getSubject()).append("\r\n");
        mail.append("From: ").append(this.mail.getSender()).append("\r\n");
        mail.append("To: ");
        for (int i = 0; i < this.recipientsValid.size(); i++) {
            mail.append(this.recipientsValid.get(i));
            if(i != this.recipientsValid.size()-1){
                mail.append(", ");
            }else{
                mail.append("\r\n");
            }
        }
        mail.append("\r\n");
        mail.append(this.mail.getContent());
        return mail.toString();
    }

    private void sendReset() {
        this.connexion.send(new Message(Command.RST));
    }
}
