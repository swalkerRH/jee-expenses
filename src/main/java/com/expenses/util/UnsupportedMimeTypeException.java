package com.expenses.util;

public class UnsupportedMimeTypeException extends Exception {
	public UnsupportedMimeTypeException(String e){
		super(e);
	}
	
	
	public UnsupportedMimeTypeException(String s, Throwable e){
		super(s, e);
	}
}
