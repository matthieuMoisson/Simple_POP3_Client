package mail;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by p1509413 on 20/03/2017.
 * J'ai modifié mon entête
 */
public class Mail {

    private String sender;
    private String receiver;
    private String date;
    private String subject;
    private String content;
    private final List<String> fieldsNames = new ArrayList();

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Mail(String fullMessage) {
        fieldsNames.add("Date: ");
        fieldsNames.add("Subject: ");
        fieldsNames.add("From: ");
        fieldsNames.add("To: ");
        this.buildInfo(fullMessage);
    }

    /**
     * New Mail
     * @param sender sender
     * @param receiver receiver
     * @param subject subject
     * @param content content
     */
    public Mail(String sender, String receiver, String subject, String content) {
        this.sender = "<" + sender + ">";
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss").format(new Date());
    }

    private void buildInfo(String fullMessage) {
        String[] lines = fullMessage.split("\r\n\r\n");
        String header = lines[0];
        String content = lines[1];

        String[] headerFields = header.split("\r\n");
        for (String fieldLine : headerFields) {
            for (String field : fieldsNames) {
                String[] lineSplit = fieldLine.split(field);
                if (lineSplit.length > 1) {
                    this.setField(field, lineSplit[1]);
                }
            }
        }
        this.content = content;

    }

    private void setField(String field, String lineSplit) {
        if (Objects.equals(field, "Date: ")) {
            this.date = lineSplit;
        } else if (Objects.equals(field, "Subject: ")) {
            this.subject = lineSplit;
        } else if (Objects.equals(field, "To: ")) {
            this.receiver = lineSplit;
        } else if (Objects.equals(field, "From: ")) {
            this.sender = lineSplit;
        }

    }


}
