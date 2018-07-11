package br.com.code.avenue.exception;

public class StorageFileNotFoundException extends StorageException {
	
	private static final long serialVersionUID = 20180709223622L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
