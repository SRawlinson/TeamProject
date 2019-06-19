package commandline;

import java.sql.*;

/**
 * The DatabaseInteraction class handles submitting and recalling information
 * from the Database. It is sent the relevant data from the Game class in the
 * first instance, and stores the relevant information as private variables in
 * the latter.
 */
public class DatabaseInteraction {
	private int gamesPlayed;
	private int humanWins;
	private int compWins;
	private int avgDraws;
	private int longestRound;

	// database sign in credentials
	static final String USERNAME = "m_18_2208247s";
	static final String PASSWORD = "2208247s";

	// jdbc driver name and database url
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/m_18_2208247s";
	// tried updating with username in URL as indicated in lecture slides...didn't
	// work

	public DatabaseInteraction() {

	}

	/**
	 * Using the results of each game, a static method is used to update the
	 * database with the relevant data. It uses the individuals scores to determine
	 * the overall winner of the game.
	 * 
	 * @param gameResults
	 */
	public static void insertGameStats(int[] gameResults) {
		// load JDBC Driver

		int humanRoundWins = gameResults[0];
		int aiOneWins = gameResults[1];
		int aiTwoWins = gameResults[2];
		int aiThreeWins = gameResults[3];
		int aiFourWins = gameResults[4];
		int numOfRounds = gameResults[5];
		int drawsNumber = gameResults[6];
		String overallGameWinner = calculateOverallWinner(gameResults);

		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find JDBC Driver");
			e.printStackTrace();
			return;
		}

		System.out.println("Driver found");

		Connection connection = null;
		Statement stmt = null;

		try {
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("Succesfully connected!");
		} catch (SQLException e) {
			System.out.println("Connection Failed");
			e.printStackTrace();
			return;
		}

		if (connection != null) {

			try {
				System.out.println("Inserting info into table...");
				stmt = connection.createStatement();
				// First query
				String insert = "INSERT INTO game(humanRoundWins, aiOneWins, aiTwoWins, aiThreeWins, aiFourWins, numOfRounds, drawsNumber, overallGameWinner) VALUES("
						+ humanRoundWins + "," + aiOneWins + "," + aiTwoWins + "," + aiThreeWins + "," + aiFourWins
						+ "," + numOfRounds + "," + drawsNumber + "," + overallGameWinner + ")";
				stmt.executeUpdate(insert);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * The getGameStats method returns the required information outlined in the
	 * requirements.
	 * 
	 * Number of games is calculated by returning a count of the tuples in the
	 * table. Number of AI wins is calculated by searching the 'overall winner
	 * column for entries not including the word 'human'. This number is subtracted
	 * from the number of games to find the number of human wins. The average number
	 * of draws and largest number of rounds are calculated using SQL queries.
	 */
	public String getGameStats() {
		String gameStats = "";
		try {

			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Game stats - Could not find JDBC Driver");
			e.printStackTrace();
			
		}

		System.out.println("Game stats - Driver found");

		Connection connection = null;
		Statement stmt = null;

		try {
			System.out.println("Game stats - Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("Game stats - Succesfully connected\n");
		} catch (SQLException e) {
			System.out.println("Game stats - Connection Failed");
			e.printStackTrace();
			
		}

		if (connection != null) {

			try {

				Statement statement = connection.createStatement();

				// First Query
				String queryGamesPlayed = "SELECT COUNT(*)humanroundwins FROM game";
				ResultSet rsGamesPlayed = statement.executeQuery(queryGamesPlayed);

				while (rsGamesPlayed.next()) {
					this.gamesPlayed = rsGamesPlayed.getInt(1);
				}

				String queryHumanWinner = "SELECT COUNT(*) overallGameWinner FROM game WHERE overallGameWinner = 'Human'";
				ResultSet rsHumanWinner = statement.executeQuery(queryHumanWinner);
				
				while (rsHumanWinner.next()) {
					this.humanWins = rsHumanWinner.getInt(1);
					this.compWins = gamesPlayed - humanWins;
				}

				// Fourth Query
				String queryAvgDraws = "SELECT AVG(drawsNumber) FROM game";

				ResultSet rsAvgDraws = statement.executeQuery(queryAvgDraws);
				while (rsAvgDraws.next()) {
					this.avgDraws = rsAvgDraws.getInt(1);

				}

				// Fifth Query
				String queryMaxRounds = "SELECT MAX(numOfRounds) FROM game";

				ResultSet rsMaxRounds = statement.executeQuery(queryMaxRounds);
				while (rsMaxRounds.next()) {
					this.longestRound = rsMaxRounds.getInt(1);
				}
				// close all result sets
				rsGamesPlayed.close();

				rsHumanWinner.close();
				rsAvgDraws.close();
				rsMaxRounds.close();
				gameStats = collateStats();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gameStats;
	}
	
	public String collateStats() {
	    String gameStats = "Game Statistics: \n" +
	    "Number of Games: " + getGamesPlayed() + "\n" +
	    "Number of Human Wins: " + getHumanWins() + "\n" +
	    "Number of AI Wins: " + getCompWins() + "\n" +
	    "Average Number of Draws: " + getAvgDraws() + "\n" +
	    "Longest Game: " + getLongestRound() + " rounds.";
	    
	    return gameStats;
	}

	public int getGamesPlayed() {
		return this.gamesPlayed;
	}

	public int getHumanWins() {
		return this.humanWins;
	}

	public int getCompWins() {
		return this.compWins;
	}

	public int getAvgDraws() {
		return this.avgDraws;
	}

	public int getLongestRound() {
		return this.longestRound;
	}

	/**
	 * This method calculates the overall winner of the game through finding the
	 * highest individual round score.
	 * 
	 * @param gameResults
	 * @return winnerResult
	 */
	public static String calculateOverallWinner(int[] gameResults) {
		int winner = 0;
		int winnerResults = 0;
		String winnerResult = "";
		for (int i = 0; i < 5; i++) {
			if (gameResults[i] > winnerResults) {
				winnerResults = gameResults[i];
				winner = i;
			}
		}

		if (winner == 0) {
			winnerResult = "'Human'";
		}
		if (winner == 1) {
			winnerResult = "'AI player One'";
		}
		if (winner == 2) {
			winnerResult = "'AI player Two'";
		}
		if (winner == 3) {
			winnerResult = "'AI player Three'";
		}
		if (winner == 4) {
			winnerResult = "'AI player Four'";
		}
		return winnerResult;
	}
	
	public String onlineTest() {
		String testResult = "Successful Test!";
		return testResult;
	}
}
