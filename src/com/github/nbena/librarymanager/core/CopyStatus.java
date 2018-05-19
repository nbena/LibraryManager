package com.github.nbena.librarymanager.core;

public enum CopyStatus {
	
	FREE,
	RESERVED;
	
	public String toString(){
		return super.toString().toLowerCase();
	}
	
	public static CopyStatus from(String arg0){
		return CopyStatus.valueOf(arg0.toUpperCase());
	}

}
