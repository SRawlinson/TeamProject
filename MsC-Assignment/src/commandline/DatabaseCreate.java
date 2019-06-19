package commandline;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreate {
	
	                //database sign in credentials
	                static final String USERNAME = "m_18_2208247s";
	                static final String PASSWORD = "2208247s";
	                
	                //jdbc driver name and database url
	                static final String JDBC_DRIVER = "org.postgresql.Driver";
	                static final String DB_URL = "jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/m_18_2208247s";
	                
	                
	        public static void main (String[] args){
	        
	            try{
	            Class.forName(JDBC_DRIVER);
	            } catch (ClassNotFoundException e){
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
	                System.out.println("Succesfully connected to database");
	                } catch(SQLException e){
	                System.out.println("Connection Failed");
	                e.printStackTrace();
	                return;
	                }
	                
	                if (connection != null){
	                
	                try{
	                System.out.println("Creating a table in database...");
	                stmt = connection.createStatement();
	                
	                String createGameTable = "CREATE TABLE game" +
	                                        "(humanRoundWins INTEGER, " +
	                                        "aiOneWins INTEGER, " +
	                                        "aiTwoWins INTEGER, " +
	                                        "aiThreeWins INTEGER, " +
	                                        "aiFourWins INTEGER, " +
	                                        "numOfRounds INTEGER, " +
	                                        "drawsNumber INTEGER, " +
	                                        "overallGameWinner VARCHAR(65) )"; 
	                
	                stmt.executeUpdate(createGameTable);
	                System.out.println("Table succesfully created");
	                }catch (SQLException e) {
	                e.printStackTrace();
	                }
	        }
	}
}
