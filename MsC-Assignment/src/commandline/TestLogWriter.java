package commandline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

public class TestLogWriter {
	/**
	 * Test log class. Takes the log information which is scattered around the other classes and nested within if statements.
	 * This is then written to a new file called "Lot.txt"
	 */
	
	FileWriter logWriter;
	BufferedWriter thisBufWriter; 
	String fileLogInput = "";
	
	public TestLogWriter(String log) {
		this.fileLogInput = log; 
	}

	public void WriteLogFile() {
		try {
			File testLog = new File("Log.txt");
			logWriter = new FileWriter(testLog);
			BufferedWriter thisBufWriter = new BufferedWriter(logWriter);
			thisBufWriter.write(fileLogInput);
			thisBufWriter.newLine();
			thisBufWriter.close(); 
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}