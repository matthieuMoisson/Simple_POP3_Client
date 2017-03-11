package logger;

import javafx.scene.control.Label;

/**
 * Created by Gaetan on 11/03/2017.
 */
public class Logger {

    private static Label label;

    public static void setLabel(Label l) {label = l;}

    public static void log(LogMessage logMessage) {
        if (label != null) {
            label.setTextFill(logMessage.getLogType().getColor());
//            this.label.setText(this.label.getText().concat("\n" + logMessage));
            label.setText(logMessage.toString());
        } else {
            System.out.println("label not set");
            System.out.println(logMessage);
        }
    }

    public static void log(String message) {
        log(new LogMessage(message));
    }
}
