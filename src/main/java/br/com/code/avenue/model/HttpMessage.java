package br.com.code.avenue.model;

public class HttpMessage { 
	private String message;
	
	public HttpMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HttpMessage [message=").append(message).append("]");
		return builder.toString();
	}
	
	public String toJSON() {
		return "{\"message\": \""+message+"\"}";		
	}
}
