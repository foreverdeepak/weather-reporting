package com.mckinsey.wr.platform.kafka;

/**
 * Created by deepakc on 10/04/17.
 */
public class PubsubException extends RuntimeException {

    public PubsubException() {
        super();
    }

    public PubsubException(String message) {
        super(message);
    }

    public PubsubException(String message, Throwable cause) {
        super(message, cause);
    }

    public PubsubException(Throwable cause) {
        super(cause);
    }
}
