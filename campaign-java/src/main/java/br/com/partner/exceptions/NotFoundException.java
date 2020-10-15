package br.com.partner.exceptions;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2720948316979517800L;

    public NotFoundException(final String message) {
        super(message);
    }

}
