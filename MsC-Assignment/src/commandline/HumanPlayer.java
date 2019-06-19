package commandline;

import java.util.Scanner;
import java.util.Stack;

/** 
 * Class for the Human Player. Only one will exist per game and they will always be held in the first position
 * of the ArrayList of players, no matter how many AI players there are. 
 */
public class HumanPlayer extends Player{

	
	private Stack<Card> playerDeck = new Stack<>();
	
	
	

	public HumanPlayer(String playerName) {
		super(playerName);
		// TODO Auto-generated constructor stub
	}




	@Override
	
	/**
	 * This method displays card taken as a parameter and asks the user to input an attribute
	 * between 1 and 5, based on the displayed results. It then returns selected attribute.
	 * 
	 * It uses a while loop in the same format as that found in TopTrumpsCLIApplication to gather the input
	 * from the user. 
	 * 
	 * 
	 */
	protected int selectAttribute() {

		
		System.out.println("Please select an attribute from the " + peekACard().getName() + "\n");
		System.out.println(peekACard());		
		while(true) {
			try {
				int selection = askUserForInputSelection();
				selection --;
				return selection;
				} catch (WrongInputException e) {
					System.out.println("User must enter a number between 1 and 5!");
				}
			}
		
	
	}

	/**
	 * This is the method utilising the code from TopTrumpsCLIAppliaction. With different acceptance criteria, 
	 * if follows the same pattern of throwing WrongInputExceptions in the event of an incorrect entry and/or
	 * a NumberFormatException. 
	 * 
	 * We felt that it would be better to have the user choose from numbers 1-5, rather than 0-4, as that would 
	 * be more intuitive for the average user. 
	 */
	public static int askUserForInputSelection() throws WrongInputException{

		Scanner scanner = new Scanner(System.in);
		String inputString = scanner.next();
		String[] quit = new String[] {"q","Q"};  // player is able to quit when either q or Q is pressed

		int input = 0;
		if (inputString.contains(quit[0]) || inputString.contains(quit[1])) {
			System.out.println("Quitting...");
			System.exit(0);
		}
		
		try {
			input = Integer.parseInt(inputString);
		} catch(NumberFormatException e) {
			throw new WrongInputException();
		}
		if (input > 0 && input < 6) {
			return input;
		} else {
			throw new WrongInputException();
		}
		
		
	}
}