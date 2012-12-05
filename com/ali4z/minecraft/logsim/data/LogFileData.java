package com.ali4z.minecraft.logsim.data;

public class LogFileData {

	private Long time;
	private String level;
	private String side;
	private String theClass;
	private String theMethod;
	private String trace;
	
	public LogFileData(String logLine) {
		String[] lineArr = logLine.split(":");
		if ( lineArr.length >= 6 ) {
			time = Long.parseLong(lineArr[0].trim());
			level = lineArr[1].trim();
			int next = 3;
			if ( 
					lineArr[2].equals("CLIENT") ||
					lineArr[2].equals("SERVER") ||
					lineArr[2].equals("ISERVER")
			) {
				side = lineArr[2].trim();
				next = 3;
			} else {
				side = "CLIENT";
				next = 2;
			}
			theClass = lineArr[next].trim();
			theMethod = "";
			for ( int i = next+1; i < lineArr.length-1; i++ ) {
				theMethod += lineArr[i]+":";
			}
			theMethod = theMethod.substring(0, theMethod.length()-1).trim();
			trace = lineArr[lineArr.length-1].trim();
		} else if ( lineArr.length == 5 ) {
			time = Long.parseLong(lineArr[0].trim());
			level = lineArr[1].trim();
			side = "CLIENT";
			theClass = lineArr[2].trim();
			theMethod = lineArr[3].trim();
			trace = lineArr[4].trim();
		}
	}
	
	public boolean loaded() {
		return time != null && level != null && theClass != null && theMethod != null && trace != null;
	}
	
	public Long getTime() {
		return time;
	}
	public String getLevel() {
		return level;
	}
	public String getSide() {
		return side;
	}
	public String getTheClass() {
		return theClass;
	}
	public String getTheMethod() {
		return theMethod;
	}
	public String getTrace() {
		return trace;
	}

	public String toString() {
		return time+":"+side+":"+theClass+":"+theMethod+":"+trace;
	}
}
