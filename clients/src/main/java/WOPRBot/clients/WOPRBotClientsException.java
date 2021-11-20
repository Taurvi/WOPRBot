package WOPRBot.clients;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WOPRBotClientsException extends RuntimeException {
    public WOPRBotClientsException(String message) {
        super(message);
        log.error(message);
    }

    public WOPRBotClientsException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
