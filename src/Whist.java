import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Whist extends CardGame {

    public static Random random;
    private static final String VERSION = "1.0";
    private static int nbPlayers;
    private static int nbStartCards;
    private static int winningScore;
    private static boolean enforceRules;
    private static String[] playerTypes;
    private static Player[] players;
    private static WhistFactory creator;
    private static PlayingCard trumpCard;
    private static boolean showNPCCards;

    private static final Deck deck = new Deck(PlayingCard.Suit.values(), PlayingCard.Rank.values(), "cover");

    /*****************************************************************************************************************/

    /** main method for running game */

    public static void main(String[] args) throws IOException {
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        new Whist();
    }

    /*****************************************************************************************************************/

    /** This method is what defines the class as a facade controller. It handles and executes all the different
        functionality of the game */

    private Whist() throws IOException {

        super(700, 700, 30);
        initGame();

        //randomly pick a trump suit for the start of the game
        PlayingCard.Suit trumpSuit = startGame();
        //randomly assign a starting player for first round
        int startingPlayerID = determineStartingPlayer();
        int gameWinnerID;

        //execute loop until there is a winner
        do {
            //if players do not have any cards left in hand, pick a new trump suit, and deal more cards
            if (players[startingPlayerID].getPlayingHand().getCardsInHand().isEmpty()) {
                trumpCard.removeTrumpActor(this);
                trumpSuit = startGame();
            }
            //instantiate a round
            Round round = creator.getRound(players, trumpSuit, deck);
            //play the round (all players execute their moves), winner = starting player for next round
            startingPlayerID = round.playRound(this, startingPlayerID, enforceRules);
            //if a player wins the entire game, ID of winner is returned
            gameWinnerID = findWinnerID();
        } while (gameWinnerID == -1);

        //display "game over" sprites and acknowledge winner
        gameOver(gameWinnerID);

    }

    /*****************************************************************************************************************/

    /** This method is responsible for reading in the game properties/settings from the external properties file and
        storing the data into the corresponding attributes in this class */

    private void readPropertiesFile() throws IOException {

        Properties whistProperties = new Properties();
        //file to get properties from
        FileReader inStream = new FileReader("smart.properties");

        try { whistProperties.load(inStream); }
        catch (IOException e) { e.printStackTrace(); }
        //extract properties
        nbPlayers = Integer.parseInt(whistProperties.get("Number_of_Players").toString());
        nbStartCards = Integer.parseInt(whistProperties.get("Number_Starting_Cards").toString());
        winningScore = Integer.parseInt(whistProperties.get("Winning_Score").toString());
        enforceRules = Boolean.parseBoolean(whistProperties.get("Enforce_Rules").toString());
        playerTypes = whistProperties.get("Players").toString().split(",");
        showNPCCards = Boolean.parseBoolean(whistProperties.get("Show_NPC_Cards").toString());

    }

    /*****************************************************************************************************************/

    /** This method deals the cards from the deck to the players according to the number of starting cards specified
        in properties file and the random seed => ensures that behviour of player can be reproduced */

    private void dealCards() {

        Hand dealingHand = deck.toHand();
        Hand[] hands = creator.getPlayingHandArray(nbPlayers);
        int cardsInDeck = 52;

        //iterate through total number of players
        for (int i = 0; i < nbPlayers; i++) {
            //instantiate a temporary hand for each player
            hands[i] = creator.getHand(deck);
            //need to store number of cards specified in properties file for each hand
            for (int j = 0; j < nbStartCards; j++) {
                //get a random card from the dealing hand
                Card temp = dealingHand.getCard(random.nextInt(cardsInDeck));
                //check if that card has been dealt already. If so, continue to pick another random card
                while (temp == null) { temp = dealingHand.getCard(random.nextInt(cardsInDeck)); }
                //deal the card and remove from dealing hand
                dealingHand.remove(temp, false);
                hands[i].insert(temp, false);
            }
        }

        for (int i = 0; i < nbPlayers; i++) {
		    hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		    //give cards to player hand
		    players[i].setPlayingHand(hands[i]);
		    //can be used to make cards face downwards for all NPCs
            if (i != 0 && players[i].getPlayerType() != Player.PlayerType.HUMAN_INTERACTIVE && !showNPCCards)
		        players[i].getPlayingHand().getCardsInHand().setVerso(true);
        }

    }

    /*****************************************************************************************************************/

    /** This method initializes the players in the game by using the WhistFactory class as a creator */

    private void initPlayers(WhistFactory creator) {

        //factory object creates an array to store players
        players = creator.getPlayerArray(nbPlayers);
        //iterate through array
        for (int i = 0; i < nbPlayers; i++) {
            //factory object used to create player with specific behaviour determined by player type
            players[i] = creator.getPlayer(i, playerTypes[i]);
            //initialize score of each player and display on screen
            players[i].getPlayerScore().initScore(this);
        }

    }

    /*****************************************************************************************************************/

    /** Starting player of game randomly determined */

    private int determineStartingPlayer() {
        return random.nextInt(nbPlayers);
    }

    /*****************************************************************************************************************/

    /** This method initializes the graphics of the game */

    private void initGraphics() {

        int handWidth = 400;
	    RowLayout[] layouts = new RowLayout[nbPlayers];

	    for (int i = 0; i < nbPlayers; i++) {
	        //set layout of cards on screen
		    layouts[i] = new RowLayout(players[i].getPlayingHand().getPosition(), handWidth);
		    layouts[i].setRotationAngle(90*i);
		    //set each player's playing hand according to layout
		    players[i].getPlayingHand().getCardsInHand().setView(this, layouts[i]);
		    players[i].getPlayingHand().getCardsInHand().setTargetArea(new TargetArea(Round.TRICK_LOCATION));
		    players[i].getPlayingHand().getCardsInHand().draw();
	    }

    }

    /*****************************************************************************************************************/

    /** This method initializes the game */

    private void initGame() throws IOException {
        //instantiate a new random object
        random = new Random();
        //extract information from properties file
        readPropertiesFile();
        setTitle("A Game of Whist - Version " + VERSION);
        setStatusText("Initializing...");
        //get instance of the factory object used to create other game objects
        // (Singleton Concrete Factory Pattern used)
        creator = WhistFactory.getInstance();
        //use factory object to instantiate and initialize players
        initPlayers(creator);
        //instantiate a trump card object
        trumpCard = creator.getTrumpCard();

    }

    /*****************************************************************************************************************/

    /** This is the master method used to start the game. It returns the starting trump suit */

    private PlayingCard.Suit startGame() {
        //deal the cards to players
        dealCards();
        //initialize game graphics
        initGraphics();
        //return starting trump suit
        return trumpCard.pickTrumpSuit(this);

    }

    /*****************************************************************************************************************/

    /** This method displays the game over message on the screen and updates the status text of the game */

    private void gameOver(int winnerID) {
        //display game over sprite
        Location textLocation = new Location(350, 450);
        addActor(new Actor("sprites/gameover.gif"), textLocation);
        //set game status text indicating winner
        setStatusText("Game over. Winner is player: " + winnerID);
        refresh();

    }

    /*****************************************************************************************************************/

    /** This method finds the winning player ID. If no winner yet, a sentinel (-1) is returned */

    private int findWinnerID() {

        //iterate through all the players
        for (int i = 0; i < nbPlayers; i++) {
            //if player score is equal to winning score, return player id
            if (players[i].getPlayerScore().getCurrentScore() == winningScore) {
                return i;
            }
        }

        //no winner yet => return -1
        return -1;

    }

    /*****************************************************************************************************************/

    /** This method returns a random enum value */

    // return random Enum value
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /*****************************************************************************************************************/

}
