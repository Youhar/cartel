package com.cartel.nfc;

public class  ReaderException extends Exception{
	public static final int AUTH_ERROR=1;
	public static final int READ_ERROR=2;

	public ReaderException(int i){
		super(getErrorMessage(i));
	}
	
	private static String getErrorMessage(int i){
		switch(i){
		case 1 : 
			return "Authenticate Error on Card";
		case 2 :
			return "Read Error on Card";
		case 3:
			return "Validation Error";
		default :
			return "Unknown Reader Error";
		}
	}
}
