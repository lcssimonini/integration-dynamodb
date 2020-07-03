package br.com.vfs.integrationdynamodb.errors;

public class HostNotFoundException extends RuntimeException {

    public HostNotFoundException(final String message) {
        super(message);
    }
}
