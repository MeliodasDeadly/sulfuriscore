package fr.sulfuris.dev.handlers.logger;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class authLogFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        if (record == null || record.getMessage() == null) {
            return true;
        }
        if (logger.isSensitiveCommand(record.getMessage())) {
            String playerName = record.getMessage().split(" ")[0];
            record.setMessage(playerName + " as envoyé une commande sensible! " + record.getMessage());
        }
        return true;
    }
}
