package br.com.partner.exceptions;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException(final String message) {
        super(message);
    }

    public BadGatewayException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
