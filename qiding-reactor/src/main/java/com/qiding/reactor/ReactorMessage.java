package com.qiding.reactor;



public class ReactorMessage implements UserMessage {
	private String message;
	private Integer object;


	public ReactorMessage(String message, Integer object) {
		this.message = message;
		this.object = object;
	}

	public ReactorMessage() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getObject() {
		return object;
	}

	public void setObject(Integer object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "ReactorMessage{" +
			"message='" + message + '\'' +
			", object='" + object + '\'' +
			'}';
	}

	@Override
	public void toLocalString() {
		System.out.println("message"+toString());;
	}
}
