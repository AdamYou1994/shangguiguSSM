package com.allen.crowd.funding.entity;

public class ResultEntity<T> {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	public static final String NO_MESSAGE = "NO_MESSAGE";
	public static final String NO_DATA = "NO_DATA";
	
	private String result;
	private String message;
	private T data;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public ResultEntity() {
	}
	public ResultEntity(String result, String message, T data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}
	public static ResultEntity<String> successWithoutData(){
		return new ResultEntity<String>(SUCCESS,NO_MESSAGE,NO_DATA);
	}
	public static <E> ResultEntity<E> successWithoutData(E data){
		return new ResultEntity<E>(SUCCESS,NO_MESSAGE,data);
	}
	public static <E> ResultEntity<E> failed(E data,String message){
		return new ResultEntity<E>(FAILED,message,data);
	}
}
