package com.expenses.util;

public class OverwriteImageException extends Exception {

	public OverwriteImageException(String e){
		super(e);
	}
	
	
	public OverwriteImageException(String s, Throwable e){
		super(s, e);
	}
}
