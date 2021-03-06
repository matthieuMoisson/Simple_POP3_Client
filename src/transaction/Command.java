package transaction;

import java.util.HashSet;

/**
 * Created by Gaetan on 05/03/2017.
 * List of commands
 */
public enum Command {

    OK("OK"),
    APOP("APOP"),
    DELE("DELE"),
    RETR("RETR"),
    RSET("RSET"),
    LIST("LIST"),
    STAT("STAT"),
    QUIT("QUIT"),
    DEFAULT(""),
    EXCEPTION("EXCEPTION"),
    ERROR("ERR"),
    ELHO("EHLO"),
    OKSMTP("250"),
    MAIL("MAIL"),
    RCPT("RCPT"),
    RST("RST"),
    DATA("DATA"),
    ERRORSMTP("550"),
    GETMAIL("354"),
    QUITCODE("221");

    private final String text;

    public String getText() {
        return text;
    }

    Command(String text) {
        this.text = text;
    }

    public static HashSet<String> getEnums() {

        HashSet<String> values = new HashSet<>();
        for (Command c : Command.values()) {
            System.out.println("c.name" + c.getText());
            values.add(c.getText());
        }
        return values;
    }
}
