package io.writer;

import io.IOConfig;

import java.io.FileWriter;
import java.io.IOException;

import dataObjects.CarParkData;
import dataObjects.DriverData;

public class DataPrinter {

	private FileWriter fw;

	private int lastTime = Integer.MIN_VALUE;

	private StringBuilder buffer;

	public DataPrinter(String path) throws IOException {
		fw = new FileWriter(path);
	}

	public void printDriverData(int time, DriverData data) throws IOException {
		if (time > lastTime) {
			if (buffer != null){
				fw.write(buffer.toString());
				fw.flush();
			}
			buffer = new StringBuilder();
			lastTime = time;
		}

		buffer.append(time);
		buffer.append(IOConfig.DELIMITER);
		buffer.append(data.toString());
		buffer.append("\n");
	}
	
	public void printCarParkData(int time, CarParkData data) throws IOException {
		if (time > lastTime) {
			if (buffer != null){
				fw.write(buffer.toString());
				fw.flush();
			}
			buffer = new StringBuilder();
			lastTime = time;
		}

		buffer.append(time);
		buffer.append(IOConfig.DELIMITER);
		buffer.append(data.toString());
		buffer.append("\n");
	}
}
