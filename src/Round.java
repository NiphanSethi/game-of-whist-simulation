import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

public class Round {

    private Player[] players;
    private Hand trick;
    private PlayingCard.Suit trumpSuit;
    private RoundInformation roundInformation;

    public static final Location TRICK_LOCATION = new Location(350, 350);
    public static final int TRICK_WIDTH = 40;
    public static final Location HIDE_LOCATION = new Location(-500, - 500);

    public Round(Player[] players, PlayingCard.Suit trumpSuit, Deck deck) {
        this.players = players;
        this.trumpSuit = trumpSuit;
        trick = new Hand(deck);
        roundInformation = new RoundInformation(this);
    }

    /*****************************************************************************************************************/

    /** Getters for all private variables */

    public Hand getTrick() { return trick; }
    public PlayingCard.Suit getTrumpSuit() { return trumpSuit; }

    /*****************************************************************************************************************/

    /** This method is responsible for players executing their moves, checking validity of moves and determining winner
        of round. Once all of these steps have completed, round resets to its initial state (no cards in trick hand)
        and the winner's score is incremented by 1. */

    public int playRound(CardGame cardGame, int startingPlayerID, boolean enforceRules) {

        int i = startingPlayerID, winningPlayerID = i;
        Card winningCard = null;

        do {
            initTrickLayout(cardGame, i);
            //execute player move
            players[i].executeMove(trick, roundInformation);
            checkValidMove(enforceRules, trick, i);
            //update winning card and player
            if (winningCard == null) { winningCard = trick.getFirst(); }
            else {
                winningCard = updateWinningCard(trick, winningCard);
                if (trick.getLast() == winningCard) { winningPlayerID = i; }
            }

            //transfer current winning card to round information object. Players use this object to make decisions
            //about their moves
            roundInformation.setWinningCard(winningCard);
            if (++i == players.length) { i = 0; }

        //iterate through all the players
        } while (i != startingPlayerID);

        resetRound(cardGame, winningPlayerID);

        //return winning player ID
        return winningPlayerID;

    }

    /*****************************************************************************************************************/

    /** This method initializes the trick layout at the centre of the game table */

    private void initTrickLayout(CardGame cardGame, int playerID) {

        //initialize trick position and spread of cards
        int rowWidth = (trick.getNumberOfCards() + 2) * TRICK_WIDTH;
        //draw trick graphics
        trick.setView(cardGame, new RowLayout(TRICK_LOCATION, rowWidth));
        trick.draw();
        //set text status for game state
        updateStatusText(cardGame, players[playerID]);

    }

    /*****************************************************************************************************************/

    /** This method checks whether a move is valid or not, and throws an exception if the game rules are explicitly
        enforced */

    private void checkValidMove(boolean enforceRules, Hand trick, int playerID) {

        Card leadingCard = trick.getFirst();
        Card lastPlayed = trick.getLast();
        PlayingCard.Suit leadingCardSuit = (PlayingCard.Suit) leadingCard.getSuit();
        Hand playerHand = players[playerID].getPlayingHand().getCardsInHand();
        // messages to be printed to console if rules are broken
        String violation = "Follow rule broken by player " + playerID + " attempting to play " + lastPlayed;
        String cheaterMessage = "A cheating player spoiled the game!";

        //check if card played has same suit as leading card of trick and if another suit is played, check if player
        // doesn't have leading card suit in hand
        if (lastPlayed.getSuit() != leadingCardSuit && playerHand.getNumberOfCards() > 0
                && playerHand.getNumberOfCardsWithSuit(leadingCardSuit) > 0) {
            //if found cheating, print message to console
            System.out.println(violation);
            //throw new exception
            if (enforceRules) try { throw new BrokeRuleException((violation)); }
            catch (BrokeRuleException e) {
                    e.printStackTrace();
                    //print a cheater message to console and exit game
                    System.out.println(cheaterMessage);
                    System.exit(0);
            }
        }

    }

    /*****************************************************************************************************************/

    /** This method resets the game table at the end of a round */

    private void resetRound(CardGame cardGame, int winningPlayerID) {

        int resetRoundTime = 600;
        //delay to acknowledge winner
        GameGrid.delay(resetRoundTime);
        //update winning player score and display on screen
        players[winningPlayerID].getPlayerScore().updateScore(cardGame);
        //hide current trick to an offscreen position
        trick.setView(cardGame, new RowLayout(HIDE_LOCATION, 0));
        trick.draw();

    }

    /*****************************************************************************************************************/

    /** This method returns the current winning card based on the trick state */

    private Card updateWinningCard(Hand trick, Card winningCard) {

        //print current winning card information along with card last player played to console
        System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + winningCard.getRankId());
        System.out.println(" played: suit = " + trick.getLast().getSuit() + ", rank = " + trick.getLast().getRankId());
        //check if the card just put down is a winning card
        if (checkNewWinner(winningCard, trick.getLast())) {
            //print message to console
            System.out.println("NEW WINNER");
            //return new winning card
            return trick.getLast();
        } else { return winningCard; }

    }

    /*****************************************************************************************************************/

    /** This method checks whether there is a new winner after a player has executed their move */

    private boolean checkNewWinner(Card winningCard, Card selectedCard) {

        //new winner if selected card has same suit but greater rank than current winning card or trump suit played
        // when current winning card suit not same as trump suit
        return (selectedCard.getSuit() == winningCard.getSuit() && PlayingCard.rankGreater(selectedCard, winningCard) ||
                selectedCard.getSuit() == trumpSuit && winningCard.getSuit() != trumpSuit);

    }

    /*****************************************************************************************************************/

    /** This method updates the status text of the game while a player is making a move. It requires the player type
        as well as the player id in order to set this text. */

    private void updateStatusText(CardGame cardGame, Player player) {

        int thinkingTime = 2000;

        if (player.getPlayerType() == Player.PlayerType.HUMAN_INTERACTIVE) {
            cardGame.setStatusText("Player " + player.getPlayerID() + " double-click on card to follow.");
        } else {
            cardGame.setStatusText("Player " + player.getPlayerID() + " thinking...");
            GameGrid.delay(thinkingTime);
        }

    }

    /*****************************************************************************************************************/


}
