package commandline;

import java.util.Stack;

/**
 * AI Player class, of which there will be up to four per game
 */
public class AIPlayer extends Player {

	/**
	 * This method selects highest attribute of the player's card to play for the
	 * round, using a simple loop.
	 */
	protected int selectAttribute() {

		System.out.println(playerName + " is choosing which attribute to select\r\n");
		int largest = 0; // largest attribute
		int largestPosition = 0; // index of largest attribute
		for (int i = 0; i < 5; i++) {
			if (peekACard().attributes[i] > largest) {
				largest = peekACard().attributes[i];
				largestPosition = i;
			}

		}
		return largestPosition;

	}

	public AIPlayer(String playerName) {
		super(playerName);
		// TODO Auto-generated constructor stub
	}

}