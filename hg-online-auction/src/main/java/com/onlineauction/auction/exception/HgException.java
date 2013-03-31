package com.onlineauction.auction.exception;

/**
 * Custom HG Auction Exception that we can change later on to format a nice little servlet message.
 * 
 * @author hamo
 *
 */
@SuppressWarnings("serial")
public class HgException extends Exception {
	
	private final String errorMsg;
	
	public HgException (final String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	public String toString() {
		return ("HgException: " + this.errorMsg);
	}
}
