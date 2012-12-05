package com.ali4z.minecraft.logsim.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogFile {
	private File file;
	
	private Map<Long,List<LogFileData>> data;
	
	private List<Long> timeList;

	public LogFile(String filePath) {
		file = new File(filePath);
		data = new HashMap<Long,List<LogFileData>>();
		timeList = new ArrayList<Long>();
		
		try {
			parseFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			String line = br.readLine();
			
			while (line != null) {
				LogFileData data = new LogFileData(line);
				if ( data.loaded() ) {
					addData(data);
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
	}
	
	private synchronized void addData(LogFileData data) {
		if ( !this.data.containsKey(data.getTime()) ) {
			this.data.put(data.getTime(), new ArrayList<LogFileData>());
			timeList.add(data.getTime());
		}
		
		this.data.get(data.getTime()).add(data);
	}
	
	public synchronized List<LogFileData> getData(Long time, int side) {
		String sside = "CLIENT";
		switch (side) {
			case 1:
				sside = "ISERVER";
				break;
			case 2:
				sside = "SERVER";
				break;
		}
		List<LogFileData> orig = data.get(time);
		List<LogFileData> out = new ArrayList<LogFileData>();
		for ( LogFileData data : orig) {
			if ( data.getSide() == null || data.getSide().equals(sside) )
				out.add(data);
		}
		return out;
	}
	
	public List<Long> getTimes() {
		return timeList;
	}
}
