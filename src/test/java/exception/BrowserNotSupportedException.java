package exception;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BrowserNotSupportedException extends RuntimeException {
    private final Logger logger = LogManager.getLogger(BrowserNotSupportedException.class);
    public BrowserNotSupportedException(String error) {
        logger.error(error);
    }
}
