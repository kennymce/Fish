package com.kennymce.games;

import java.io.File;
import java.util.Observable;

public class Game extends Observable {

    private final File configFile = new File("E:\\dev\\Fish\\fishConfig.json");
    private final File playerInfoFile = new File("E:\\\\dev\\\\Fish\\\\playerinfo.json");

    private final ConfigHelper configHelper = new ConfigHelper(configFile);
    private final PlayerInfoHelper playerInfoHelper = new PlayerInfoHelper(playerInfoFile);

    private int numberOfPlayers;
    private int numberOfCardsPerPlayer;
    private int turnNumber;
    private Players playersOut = new Players();
    private FishLogger fishLogger = new FishLogger() ;


    Dealer dealer = new Dealer();

    Players players;

    public Game() {
        setupGame();
        setupPlayers();
        dealCards();
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfCardsPerPlayer() {
        return numberOfCardsPerPlayer;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void incrementTurnNumber(){
        turnNumber++;
    }

    private void setupGame() {
        numberOfPlayers = configHelper.getNumberOfPlayers();
        numberOfCardsPerPlayer = configHelper.getNumberOfCardsPerPlayer();
    }

    private void setupPlayers(){
        players = new Players();

        for (int i=0; i < numberOfPlayers; i++){
            Player player = new Player(
                    playerInfoHelper.getPlayerInfo(i).getGender(),
                    playerInfoHelper.getPlayerInfo(i).getName(),
                    playerInfoHelper.getPlayerInfo(i).getType()
            );
            players.AddPlayer(player);
        }
        //Now tell the players about each other by registering them with each other
        for (Player p:players) {
            for (Player pInner:players) {
                //do not register any player with themselves
                if (!pInner.equals(p)){
                    p.registerPlayer(pInner);
                }
            }
        }
    }

    private void dealCards(){
        dealer.setGame(this);
        dealer.dealPlayerCards(this.players);
    }

    public void notifyTurn(TurnResult turnResult){
        setChanged();
        notifyObservers(turnResult);
    }

    //TODO game shouldn't play itself!
    public void PlayGame(){

        try {
            while(playersOut.Size()<players.Size()-1){
                for (Player player:players) {
                    if (!playersOut.contains(player)){
                        TurnResult turnResult = player.takeYourTurn();
                        if (turnResult != null){
                            this.notifyTurn(turnResult);

                            //ask a the player as per the turn result
                            Player playerToAsk = turnResult.getPlayer();
                            Cards cardsFromThePlayer = playerToAsk.ask(turnResult,player);
                            //all the other players are listening to the turn
                            for (Player listeningPlayer: this.players){
                                listeningPlayer.Listen(turnResult, player);
                            }
                            if (cardsFromThePlayer.size()>0){
                                //add the cards from this player to the asking player
                                String takingCards;
                                for (Card card: cardsFromThePlayer){
                                    takingCards = turnResult.getAskingPlayer().getName().toString() +
                                            " takes " + card.getValue() + " of " + card.getSuit() +
                                            " from " + turnResult.getPlayer();
                                    fishLogger.LogMessage(takingCards);
                                    player.takeCard(card);
                                }
                            }
                            else{
                                //The player said 'fish'
                                fishLogger.LogMessage(playerToAsk.getName().toString() + " says Fish");
                                Card card = dealer.DealCard();
                                if (card == null){
                                    //No cards left so take a card randomly from somebody else
                                    turnResult = player.takeYourTurn();
                                    //TODO we need a re-factor here because now a player can effectively have >1 turn
                                }

                                if (card != null){
                                    player.takeCard(card);
                                    fishLogger.LogMessage("Player "+player.getName().toString()+" draws "+
                                            card.getValue()+" of "+card.getSuit());
                                }
                            }
                        }
                        //TODO Take a card from another player when it's still your turn and the deck is empty
                        //is this player out of cards?
                        if (player.NumberOfCards()==0){
                            //Cannot remove from the players collection whilst iterating over it
                            //so we add to another collection
                            //TODO when should a player be removed from the game?
                            RemovePlayerFromGame(player);
                        }
                    }
                }
                fishLogger.LogMessage("*********Turn "+this.turnNumber+" Finished**********");
                this.turnNumber++;
            }
            fishLogger.LogMessage("*********Game Finished**********");
            Player winner = this.Winner();
            if (!(winner==null)){
                fishLogger.LogMessage(winner.getName().toString() + " is the winner.");
                fishLogger.LogMessage(winner.getName().toString() + " has " + winner.getBooksList());
            }
            fishLogger.LogMessage("Here's what everyone has....");
            for (Player p:players) {
                fishLogger.LogMessage(p.getName().toString() + " has " + p.getBooksList());
            }
            fishLogger.LogMessage("There are " + dealer.CardsInDeck() + " cards in the deck.");
            fishLogger.LogMessage("All Cards: ");
            fishLogger.LogMessage(players.ShowAllPlayersAllCards());

        } catch (Exception e) {
            fishLogger.LogError("Exception in game:",e);
        }
    }

    private Player Winner(){
        //TODO there can be more than one winner - i.e. same number of books
        Player player = null;
        int highest = 0;

        for (Player p:players) {
            int books;
            books = p.getBooks();
            if (books > highest){
                player = p;
                highest = books;
            }
        }
        return player;
    }

    private void RemovePlayerFromGame(Player player){
        String outOfGame;
        outOfGame = "++++++++++++++" + player.getName().toString() + " is out of the game ++++++++++++++";
        fishLogger.LogMessage(outOfGame);
        playersOut.AddPlayer(player);
        //Remove the playersOut from players and then the remaining
        //players learn that player is out
        for (Player playerToTell: this.players){
            if (!playerToTell.equals(player))
                playerToTell.learnOfPlayerOut(player);
        }
    }

//DEBUG METHODS
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < numberOfPlayers; i++){
            sb.append(players.player(i).getName().toString()).append(", ");
            sb.append(players.player(i).getType()).append(", ");
            sb.append(players.player(i).getGender()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
