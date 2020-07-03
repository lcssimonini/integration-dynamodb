package br.com.vfs.integrationdynamodb.errors;

public class ConflictHostException extends RuntimeException {

    public ConflictHostException(final String message) {
        super(message);
    }
}
