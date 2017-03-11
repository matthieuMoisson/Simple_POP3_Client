package logger;

/**
 * Created by Gaetan on 11/03/2017.
 * LogMessage Message
 */
public class LogMessage {

    private LogType logType = LogType.ERROR;

    private String message = "";

    public LogMessage(LogType logType, String message) {
        this.logType = logType;
        this.message = message;
    }

    public LogMessage(String message) {
        this.message = message;
    }

    public LogType getLogType() {
        return logType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
