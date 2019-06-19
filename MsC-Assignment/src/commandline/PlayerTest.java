package commandline;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class PlayerTest {
	Player testPlayer;
	Card testCard;
	
/*	@BeforeClass
	void createPlayer() {
		int[] testArray = new int[] {2, 4, 2, 1, 8};
		String[] namesArray = new String[] {"Size", "Rarity", "Temper", "Cuteness", "Mischief"};

		testPlayer = new AIPlayer("testPlayer"); 
		testCard = new Card("Abyssinian", testArray, namesArray);

	}*/

	@Test
	void setScoreTest() {	
		testPlayer = new AIPlayer("testPlayer"); 

		testPlayer.setScore(5);
		
		assertEquals(5, testPlayer.getScore());
	}
	
	@Test
	void getPlayerNameTest() {
		testPlayer = new AIPlayer("testPlayer"); 

		assertEquals("testPlayer", testPlayer.getPlayerName());
	}
	
	@Test
	void winsRoundTest() {
		testPlayer = new AIPlayer("testPlayer"); 

		testPlayer.winsRound();
		assertEquals(1, testPlayer.getScore());
	}
	
	@Test
	void setPlayerNameTest() {
		testPlayer = new AIPlayer("testPlayer"); 

		testPlayer.setPlayerName("newName");
		assertEquals("newName", testPlayer.getPlayerName());
	}
	
	@Test
	void isEmptyTest() {
		testPlayer = new AIPlayer("testPlayer"); 

		testPlayer.deckEmptyCheck();
		
		assertEquals(testPlayer.hasCards, false);
	}
	
	@Test
	void deckCheck() {
		int[] testArray = new int[] {2, 4, 2, 1, 8};
		String[] namesArray = new String[] {"Size", "Rarity", "Temper", "Cuteness", "Mischief"};

		testPlayer = new AIPlayer("testPlayer"); 
		testCard = new Card("Abyssinian", testArray, namesArray);
		testPlayer.pushToDeck(testCard);
		testPlayer.deckEmptyCheck();
		
		assertEquals(testPlayer.hasCards, true);
	}
	
	@Test 
	void popCardTest() {
		
		String testName = "testName";
		int[] testIntArray = {1,2,3,4,5};
		String[] testAttributes = {"a","b","c","e","f"};
		
		Card testCard = new Card(testName, testIntArray, testAttributes);
		
		Queue<Card> testDeck = new LinkedList<>();
		
		
		testDeck.add(testCard); 
		
		testDeck.remove(testCard);
		
		
		assertEquals (true, testDeck.isEmpty());
		
		
		
	}
	
	@Test 
	void peekCardTest() {
		
		String testName = "testName";
		int[] testIntArray = {1,2,3,4,5};
		String[] testAttributes = {"a","b","c","e","f"};
		
		 
		
		Card testCard = new Card(testName, testIntArray, testAttributes);
		
		Queue<Card> testDeck = new LinkedList<>();
		
		
		testDeck.add(testCard); 
		
		
		
		
		assertEquals(testCard, testDeck.peek());		
		
	}
	
	
}
