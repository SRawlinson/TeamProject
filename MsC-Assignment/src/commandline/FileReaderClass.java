package commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReaderClass {
	
	ArrayList<Card> deck;
	String[] attributeNames;
	public void getCardsFromFile() {

		FileReader fR = null;
//		Game currentGame = new Game();
		deck = new ArrayList();
		try {
			String fileName = "CatDeck";
			
			String strFilePath = fileName + ".txt";
	        
	        //file object
	        File file = new File(strFilePath);
	        
	        /*
	         * Use getAbsolutePath method of the File class
	         * to get file's absolute path in file system.
	         */
//	        System.out.println( file.getAbsolutePath() );
	        
			String filePath = file.getAbsolutePath();
			
			
			fR = new FileReader(filePath);
			Scanner s = new Scanner(fR);
			String line = s.nextLine();
			attributeNames = line.split(" ");
			String [] newNames = new String[5];
			for (int i = 0; i < 5; i++) {
				newNames[i] = attributeNames[i+1];
			}
			
			attributeNames = newNames;
			
			String names = "";
			
			for (int i = 0; i < attributeNames.length; i ++) {
				names += attributeNames[i] + ", ";
			}
			
			//System.out.println(names);

			while (s.hasNextLine()) {
				line = s.nextLine();
				String[] lineProcess = line.split(" ");
//				Card cardObject = new Card();
				Card cardObject = Card.createCard(lineProcess, attributeNames);
				deck.add(cardObject);
			}

		} catch (FileNotFoundException e) {
			System.out.println("No file found");
			e.printStackTrace();

		} finally {
			try {
				fR.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		

	}
	public ArrayList getDeck() {
		return deck;
	}

	public String[] getAttributeNames(){
		return attributeNames;
	}
}
