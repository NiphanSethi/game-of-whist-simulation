import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;

public class WhistFactory {

    //store an instance of this class as an attribute (singleton pattern used)
    public static WhistFactory instance;

    /*****************************************************************************************************************/

    /** This method instantiates and returns a player according to the specified ID and playerType */

    public Player getPlayer(int playerID, String playerType) {
        Player.PlayerType playerTypeEnum = Player.PlayerType.valueOf(playerType);
        return new Player(playerID, playerTypeEnum);
    }

    /*****************************************************************************************************************/

    /** This method instantiates an array of players according to a specified size */

    public Player[] getPlayerArray(int nbPlayers) { return new Player[nbPlayers]; }

    /*****************************************************************************************************************/

    /** This method instantiates a playing hand array. It is used to deal cards out from deck before distributing to
        players */

    public Hand[] getPlayingHandArray(int nbPlayers) { return new Hand[nbPlayers]; }

    /*****************************************************************************************************************/

    /** This method instantiates a new hand from deck of cards */

    public Hand getHand(Deck deck) { return new Hand(deck); }

    /*****************************************************************************************************************/

    /** This method instantiates a round for a game of Whist. It requires information regarding the players involved,
        the trump suit and the deck of cards used */

    public Round getRound(Player[] players, PlayingCard.Suit trumpSuit, Deck deck) {
        return new Round(players, trumpSuit, deck);
    }

    /*****************************************************************************************************************/

    public PlayingCard getTrumpCard() { return new PlayingCard(); }

    /*****************************************************************************************************************/

    /** This method instantiates an object of type WhistFactory if one is not already instantiated (Singleton Pattern
        used */

    public static WhistFactory getInstance() {
        if (instance == null) {
            instance = new WhistFactory();
        }
        return instance;
    }

    /*****************************************************************************************************************/

}
