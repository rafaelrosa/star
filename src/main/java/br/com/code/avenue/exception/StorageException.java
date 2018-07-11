package br.com.code.avenue.exception;

public class StorageException extends RuntimeException {
	
	private static final long serialVersionUID = 20180709223503L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
