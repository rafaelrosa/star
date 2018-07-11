package br.com.code.avenue.constants;

public class Messages {
	public static final String UPLOAD_OK = "Movie script successfully received";
	public static final String UPLOAD_ALREADY_DONE = "Movie script already received";
	public static final String UNEXPECTED_ERR = "Unexpected error";
	public static final String NOT_IMPLEMENTED = "Not implemented";
	
	public static String settingNotFound(long id) {
		return "Movie setting with id "+id+" not found";
	}
	
	public static String characterNotFound(long id) {
		return "Movie character with id "+id+" not found";
	}
}
