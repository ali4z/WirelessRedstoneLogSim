package com.ali4z.minecraft.logsim.business;

import com.ali4z.minecraft.logsim.data.DataPool;
import com.ali4z.minecraft.logsim.data.LogFileData;

public class LogFileDataParser {

	public static void parseData(DataPool pool, LogFileData data) {
		parseRedstoneEther(pool,data);
	}
	
	private static void parseRedstoneEther(DataPool pool, LogFileData data) {
		try {
			if ( data.getTheClass().equals("RedstoneEther") ) {
				if ( data.getTheMethod().contains("addTransmitter") ) {
					String[] params = extractParameters(data.getTheMethod());
					pool.addTX(
							Integer.parseInt(params[1].trim()), 
							Integer.parseInt(params[2].trim()), 
							Integer.parseInt(params[3].trim()), 
							params[4].trim(),
							data.getTime()
					);
				} else if ( data.getTheMethod().contains("remTransmitter") ) {
					String[] params = extractParameters(data.getTheMethod());
					pool.remTX(
							Integer.parseInt(params[1].trim()), 
							Integer.parseInt(params[2].trim()), 
							Integer.parseInt(params[3].trim()), 
							params[4].trim()
					);
				} else if ( data.getTheMethod().contains("setTransmitterState") ) {
					String[] params = extractParameters(data.getTheMethod());
					pool.setTXState(
							Integer.parseInt(params[1].trim()), 
							Integer.parseInt(params[2].trim()), 
							Integer.parseInt(params[3].trim()), 
							params[4].trim(),
							Boolean.parseBoolean(params[5].trim()),
							data.getTime()
					);
				} else if ( data.getTheMethod().contains("addReceiver") ) {
					String[] params = extractParameters(data.getTheMethod());
					pool.addRX(
							Integer.parseInt(params[1].trim()), 
							Integer.parseInt(params[2].trim()), 
							Integer.parseInt(params[3].trim()), 
							params[4].trim(),
							data.getTime()
					);
				} else if ( data.getTheMethod().contains("remReceiver") ) {
					String[] params = extractParameters(data.getTheMethod());
					pool.remRX(
							Integer.parseInt(params[1].trim()), 
							Integer.parseInt(params[2].trim()), 
							Integer.parseInt(params[3].trim()), 
							params[4].trim()
					);
				}
			} else 	if (data.getTheClass().equals("RedstoneEtherFrequency")) {
				if ( data.getTheMethod().contains("not loaded. Removing") ) {
					if ( data.getTheMethod().contains("getState") ) {
						String[] methodParts = data.getTheMethod().split(":");
						String freq = methodParts[2];
						freq = freq.replaceAll("[^\\d]", "");
						String coords = methodParts[3];
						String[] coorArr = extractParameters(coords);
						pool.remTX(
								Integer.parseInt(coorArr[0].trim()), 
								Integer.parseInt(coorArr[1].trim()), 
								Integer.parseInt(coorArr[2].trim()), 
								freq
						);
					} else if ( data.getTheMethod().contains("updateReceivers") ) {
						String[] methodParts = data.getTheMethod().split(":");
						String freq = methodParts[2];
						freq = freq.replaceAll("[^\\d]", "");
						String coords = methodParts[3];
						String[] coorArr = extractParameters(coords);
						pool.remRX(
								Integer.parseInt(coorArr[0].trim()), 
								Integer.parseInt(coorArr[1].trim()), 
								Integer.parseInt(coorArr[2].trim()), 
								freq
						);
					}
				}
			} 
		} catch ( ArrayIndexOutOfBoundsException e ) {
			e.printStackTrace();
			System.out.println(data.toString());
		}
	}
	
	public static String[] extractParameters(String line) {
		return line.substring(
				1+line.indexOf("("),
				line.indexOf(")")
		).split(",");
	}
}
