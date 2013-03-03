package com.caldabeast.pcd;

public class PCDUnallowedException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * The cause of the error that threw this exception. Useful for debugging.
	 */
	public final String errorCause;
	public PCDUnallowedException(String errorCause){
		this.errorCause = errorCause;
		System.out.println("[PCD] An ERROR has occured that will likely produce unexpected code result.");
		System.out.println("[PCD] Error Cause: " + errorCause);
	}
	
	@Override
	public void printStackTrace(){
		System.out.println(errorCause);
	}
	
}
