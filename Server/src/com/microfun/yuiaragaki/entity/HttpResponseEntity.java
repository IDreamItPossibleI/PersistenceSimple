package com.microfun.yuiaragaki.entity;

public class HttpResponseEntity<T> {
	
	public static final int RESULT_OK = 0;
	public static final int RESULT_ERROR = 1;
	
	private int resultCode;
	private String resultMessage;
	private T data;
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}
