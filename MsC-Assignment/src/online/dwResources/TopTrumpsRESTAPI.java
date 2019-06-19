package online.dwResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import commandline.DatabaseInteraction;
import commandline.FileReaderClass;
import commandline.NewGame;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
	NewGame theGame;
	FileReaderClass fr;
	boolean writeGameLogsToFile = false;
	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------
		fr = new FileReaderClass();
		fr.getCardsFromFile();
		//writeGameLogsToFile = false;
	
		
	}
	
	
	/**
	 * This is the method which initialises the game, shuffling the deck and allocating cards. 
	 * 
	 * It returns the integer value of the player in the arraylist theGame.players who is to play first. 
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	@GET
	@Path("/startAndSelect")
	public String startAndSelectFirstPlayer() throws IOException{
		theGame = new NewGame(fr.getDeck(), fr.getAttributeNames(), writeGameLogsToFile);
		int firstPlayerIndex = theGame.startAndSelectFirstPlayer();
		
		String firstPlayerIndexAsJSON = oWriter.writeValueAsString(firstPlayerIndex);
		
		return firstPlayerIndexAsJSON;
	}
	
	/**
	 * This method returns the attributes and their values associated with the top card in the human player's 
	 * deck. 
	 */
	@GET
	@Path("/getCard")
	public String getCardInfo() throws IOException {
		String cardAsJSON = "";
		if (theGame.getPlayers().get(0).checkCards()) {
		String cardToAPI = theGame.getPlayerCardForAPI(0);
				
		cardAsJSON = oWriter.writeValueAsString(cardToAPI);
		System.out.println(cardToAPI);
		System.out.println(cardAsJSON);
		
		} else if (!theGame.getPlayers().get(0).checkCards()){
			String noCards = "You have no cards left!";
			cardAsJSON = oWriter.writeValueAsString(noCards);
		}
		return cardAsJSON;
		
	}
	/**
	 * The following four methods return the attributes and values associated with the AI Player's top card. 
	 * 
	 * As explained in the GameScreen ftl file, we had difficulty implementing methods which took arguments and 
	 * so relied upon hard coded methods such as these to display every piece of necessary information rather than 
	 * use more general methods as we had initially hoped.  
	 */
	@GET
	@Path("/getAI1Card")
	public String getAI1Card() throws IOException {
		String cardAsJSON = "";
		if (theGame.getPlayers().get(1).checkCards()) {
		String cardToAPI = theGame.getPlayerCardForAPI(1);
				
		cardAsJSON = oWriter.writeValueAsString(cardToAPI);
		System.out.println(cardToAPI);
		System.out.println(cardAsJSON);
		
		} else if (!theGame.getPlayers().get(1).checkCards()){
			String noCards = "AI Player 1 is out of the Game!";
			cardAsJSON = oWriter.writeValueAsString(noCards);
		}
		return cardAsJSON;		
	}
	
	@GET
	@Path("/getAI2Card")
	public String getAI2Card() throws IOException {
		String cardAsJSON = "";
		if (theGame.getPlayers().get(2).checkCards()) {
		String cardToAPI = theGame.getPlayerCardForAPI(2);
				
		cardAsJSON = oWriter.writeValueAsString(cardToAPI);
		System.out.println(cardToAPI);
		System.out.println(cardAsJSON);
		
		} else if (!theGame.getPlayers().get(2).checkCards()){
			String noCards = "AI Player 2 is out of the Game!";
			cardAsJSON = oWriter.writeValueAsString(noCards);
		}
		return cardAsJSON;		
	}
	
	@GET
	@Path("/getAI3Card")
	public String getAI3Card() throws IOException {
		String cardAsJSON = "";
		if (theGame.getPlayers().get(3).checkCards()) {
		String cardToAPI = theGame.getPlayerCardForAPI(3);
				
		cardAsJSON = oWriter.writeValueAsString(cardToAPI);
		System.out.println(cardToAPI);
		System.out.println(cardAsJSON);
		
		} else if (!theGame.getPlayers().get(3).checkCards()){
			String noCards = "AI Player 3 is out of the Game!";
			cardAsJSON = oWriter.writeValueAsString(noCards);
		}
		return cardAsJSON;		
	}
	
	@GET
	@Path("/getAI4Card")
	public String getAI4Card() throws IOException {
		String cardAsJSON = "";
		if (theGame.getPlayers().get(4).checkCards()) {
		String cardToAPI = theGame.getPlayerCardForAPI(4);
				
		cardAsJSON = oWriter.writeValueAsString(cardToAPI);
		System.out.println(cardToAPI);
		System.out.println(cardAsJSON);
		
		} else if (!theGame.getPlayers().get(4).checkCards()){
			String noCards = "AI Player 4 is out of the Game!";
			cardAsJSON = oWriter.writeValueAsString(noCards);
		}
		return cardAsJSON;		
	}
	@GET
	@Path("/getSelector")
	public String getSelector() throws IOException {
		
		String selectorString = theGame.getSelector();
		
		return selectorString;
		
	}

	
	/**
	 * This method returns the index of the attribute chosen by the AI player when it is their turn 
	 * to play. 
	 * @param selector
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/chosenAttribute")
	public String getChosenAttribute() throws IOException{
		String attribute = theGame.returnChosenAttribute();
		
		String attributeAsJSON = oWriter.writeValueAsString(attribute);
		
		return attributeAsJSON;
	}
	

	@GET
	@Path("/size")
	/**
	 * The following five methods each indicate to the game object what the user has selected when it their turn 
	 * to play. Similar to the methods regarding the AI cards and scores, a method had ot be written for each selection
	 * as we had difficulty writing more general ones. 
	 * @return
	 * @throws IOException
	 */
	public String choseSize() throws IOException {
		theGame.setSelector(0);
		int attributeValue = theGame.getPlayers().get(0).getAnAttribute(0);
		theGame.setChosenAttribute(0);
		String message = "You have chosen Size, with a value of " + attributeValue + "from " + 
				theGame.getPlayers().get(0).peekACard().getName() ;
		
		String messageAsJSON = oWriter.writeValueAsString(message);
		
		return messageAsJSON;
	}
	
	@GET
	@Path("/rarity")
	public String choseRarity() throws IOException {
		theGame.setSelector(0);

		int attributeValue = theGame.getPlayers().get(0).getAnAttribute(1);
		theGame.setChosenAttribute(1);
		String message = "You have chosen Rarity, with a value of " + attributeValue + "from " + 
				theGame.getPlayers().get(0).peekACard().getName() ;
		
		String messageAsJSON = oWriter.writeValueAsString(message);
		
		return messageAsJSON;
	}
	
	@GET
	@Path("/temper")
	public String choseTemper() throws IOException {
		theGame.setSelector(0);

		int attributeValue = theGame.getPlayers().get(0).getAnAttribute(2);
		theGame.setChosenAttribute(2);
		String message = "You have chosen Good Temper, with a value of " + attributeValue + "from " + 
				theGame.getPlayers().get(0).peekACard().getName() ;
		
		String messageAsJSON = oWriter.writeValueAsString(message);
		
		return messageAsJSON;
	}
	
	@GET
	@Path("/cute")
	public String choseCuteness() throws IOException {
		theGame.setSelector(0);

		int attributeValue = theGame.getPlayers().get(0).getAnAttribute(3);
		theGame.setChosenAttribute(3);
		String message = "You have chosen Cuteness, with a value of " + attributeValue + "from " + 
				theGame.getPlayers().get(0).peekACard().getName() ;
		
		String messageAsJSON = oWriter.writeValueAsString(message);
		
		return messageAsJSON;
	}
	
	@GET
	@Path("/mischief")
	public String choseMischief() throws IOException {
		theGame.setSelector(0);

		int attributeValue = theGame.getPlayers().get(0).getAnAttribute(4);
		theGame.setChosenAttribute(4);
		String message = "You have chosen Mischief Rating, with a value of " + attributeValue + "from " + 
				theGame.getPlayers().get(0).peekACard().getName() ;
		
		String messageAsJSON = oWriter.writeValueAsString(message);
		
		return messageAsJSON;
	}
	
	/**
	 * This method calls the condensed version of checking for a winner. 
	 * 
	 * NOTE: This does not account for draws, and has to be used in conjunction with the checkForDraws method.
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/findWinner")
	public String findWinnerOfRound() throws IOException{
		String winnerMessage = theGame.findWinnerOfRound();
		
		String winnerAsJSON = oWriter.writeValueAsString(winnerMessage);
		
		return winnerAsJSON;
	}
	
	
	/**
	 * This method checks for draws. The card allocation is updated accordingly via the 
	 * drawDecisions method, and so the return value is used to print if there was a draw or not. 
	 * 
	 * If there was not, the value of the findWinnerOfRound method can be used to declare the winner.
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/checkForDraws")
	public String wasThereADraw() throws IOException{
		String draw = theGame.drawDecisions();
		
		String drawAsJSON = oWriter.writeValueAsString(draw);
		
		return drawAsJSON;
	}
	
	
	/**
	 * This returns true if there is an overall winner for the game. It is used to exit the loop at the 
	 * end of a game.  
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/winnerCheck")
	public String checkOverallWinner() throws IOException {
		boolean overallWinner = theGame.checkForOutRightWinner();
		
		String winnerMessage = "";
		
		if (overallWinner == true) {
			winnerMessage = "winner";
		
		} else if (overallWinner == false) {
			winnerMessage = "noWinner";
		}
		
		String winnerASJSON = oWriter.writeValueAsString(winnerMessage);
		
		return winnerASJSON;
	}
	
	/**
	 * This method allows us to call the endGameMethod from the API, creating and setting the gameStats array, as well as 
	 * creating the final scores String. This string is returned for ease of writing it to the webpage.  
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/endGame")
	public String endOfGame() throws IOException {
		theGame.endGameMethod();
		String finalMessage = theGame.finalScores;
		
		String finalsAsJSON = oWriter.writeValueAsString(finalMessage);
		
		return finalsAsJSON;
	}
	/**
	 * This method returns the number of the round currently in play. 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/getRoundNum")
	public String getRoundNum() throws IOException {
		return theGame.getRounds();
	}
	
	/**
	 * This method is for printing the game statistics from the database to the statistics screen. Although it is 
	 * non-functional, we have been unable to find the problem and so have included it to show our process. 
	 * @return
	 * @throws JsonProcessingException
	 */
	@GET
	@Path("/printGameStats")
	public String printGameStatsOnline() throws JsonProcessingException {
		String results = "";
		DatabaseInteraction db = new DatabaseInteraction();
		
		results = db.getGameStats();		
		String resultsAsJSON = oWriter.writeValueAsString(results);
		
		return resultsAsJSON;
	}
	
	/**
	 * These methods return the size of the player deck for each player, used for displaying the current state of the 
	 * game. 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/getCardCount")
	public String getCardCount() throws IOException{
		String count = theGame.getCardCount(0);
		String countAsJSON = oWriter.writeValueAsString(count);
		return countAsJSON;
	}
	
	@GET
	@Path("/getCardCountForAI1")
	public String getCardCountForAI1() throws IOException {
		String count = theGame.getCardCount(1);
		String countAsJSON = oWriter.writeValueAsString(count);
		return countAsJSON;
	}
	
	@GET
	@Path("/getCardCountForAI2")
	public String getCardCountForAI2() throws IOException {
		String count = theGame.getCardCount(2);
		String countAsJSON = oWriter.writeValueAsString(count);
		return countAsJSON;
	}
	
	@GET
	@Path("/getCardCountForAI3")
	public String getCardCountForAI3() throws IOException {
		String count = theGame.getCardCount(3);
		String countAsJSON = oWriter.writeValueAsString(count);
		return countAsJSON;
	}
	
	@GET
	@Path("/getCardCountForAI4")
	public String getCardCountForAI4() throws IOException {
		String count = theGame.getCardCount(4);
		String countAsJSON = oWriter.writeValueAsString(count);
		return countAsJSON;
	}
	/**
	 * This method displays the size of the common pile in the event of a draw or even successive draws. 
	 */
	@GET
	@Path("/getCommonPile")
	public String getCommonPileCount() throws IOException {
		String count = theGame.getCommonPileCount();
		String countAsJSON = oWriter.writeValueAsString(count);
		return countAsJSON;
	}

	
}
