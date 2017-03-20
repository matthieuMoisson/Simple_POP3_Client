package mail;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by p1509413 on 20/03/2017.
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

    private void buildInfo(String fullMessage) {
        String[] lines = fullMessage.split("\r\n\r\n");
        String header = lines[0];
        String content = lines[1];
        System.out.println("----*//--*-");
        System.out.println(header);
        System.out.println("----*//--*-----");

        System.out.println(content);
        System.out.println("----*//--*-");

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
        if (field == "Date: ") {
            this.date = lineSplit;
        } else if (field == "Subject: ") {
            this.subject = lineSplit;
        } else if (field == "To: ") {
            this.receiver = lineSplit;
        } else if (field == "From: ") {
            this.sender = lineSplit;
        }

    }


}
