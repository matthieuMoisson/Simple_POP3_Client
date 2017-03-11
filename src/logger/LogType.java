package logger;

import javafx.scene.paint.Color;

/**
 * Created by Gaetan on 11/03/2017.
 * Logger Statut
 */
public enum LogType {
    SUCCESS,
    ERROR,
    WARNING,
    INFO;

    public Color getColor() {
        switch (this) {
            case SUCCESS:
                return Color.GREEN;
            case ERROR:
                return Color.RED;
            case WARNING:
                return Color.ORANGE;
            case INFO:
            default:
                return Color.ALICEBLUE;
        }
    }
}
