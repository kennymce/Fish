package com.kennymce.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private final PlayerInfo.Gender gender;
    private final PlayerInfo.Name name;
    private final PlayerInfo.PlayerType type;
    private final Cards cards = new Cards();

    public boolean isGotABook() {
        return gotABook;
    }

    private boolean gotABook = false;

    public int getBooks() {
        return books;
    }

    private int books = 0;

    private String booksList;
    public String getBooksList() { return booksList; }

    private Players playersInPlay = new Players();
    private Map<Pip, Integer> pips = new HashMap<Pip, Integer>();
    private Map<Pip, Player> whoHasWhat = new HashMap<>();

    private final FishLogger fishLogger = new FishLogger();

    public Player(PlayerInfo.Gender gender, PlayerInfo.Name name, PlayerInfo.PlayerType type) {
        this.gender = gender;
        this.name = name;
        this.type = type;
    }
    public Map<Pip, Integer> getPips() {
        return pips;
    }

    public PlayerInfo.Name getName() {
        return name;
    }

    public PlayerInfo.Gender getGender() {
        return gender;
    }

    public PlayerInfo.PlayerType getType() {
        return type;
    }

    public void registerPlayer(Player player){
            playersInPlay.AddPlayer(player);
    }

    public void deRegisterPlayer(Player player){
        playersInPlay.RemovePlayer(player);
    }

    public void takeCard(Card card){
        cards.addCard(card);
        //add it to the pips HashMap
        Pip key = card.getValue();
        if (!pips.containsKey(key)){
            pips.put(key,  1);
        }
        else{
            pips.put(key, pips.get(key) + 1);
        }
        this.checkCards();
    }

    public void checkCards(){
        //Go through your cards and see if you have a book
        //if you have a book, increment books, keeping track of the ones to remove
        Cards cardsToRemove = new Cards();
        String newBook= null;
        for(Card cardToCheck: cards){
            //is this card present in the pips hashmap?
            if (pips.get(cardToCheck.getValue())==4){
                newBook = cardToCheck.getValue().toString();
                cardsToRemove.addCard(cardToCheck);
            }
        }
        if (newBook != null){
            if (booksList==null){
                booksList = newBook;
            }else
            {
                StringBuilder sb = new StringBuilder(booksList).append(",").append(newBook);
                booksList = sb.toString();
            }
            AddBook();
            whoHasWhat.remove(newBook);
        }
        //Now remove all the cards in cardsToRemove from playerCards
        for (Card cardToRemove : cardsToRemove){
            if (cards.contains(cardToRemove)){
                cards.remove(cardToRemove);
                pips.remove(cardToRemove.getValue());
            }
        }
    }

    private void AddBook() {
        books++;
        gotABook = true;
    }

    public TurnResult takeYourTurn(){
        //Check your cards and if you have two of any Pip then
        //return the Pip and a random player number so that the
        //controller can ask one of the other players for that Pip
        //if you don't have any cards left (e.g. if someone just took your last card)
        //then you don't get a turn
        TurnResult theResult = null;
        gotABook = false;
        try {
            if (cards.size()>0){
                String logMessage;
                logMessage = "################### " + "Player "+ this.getName().toString()+"'s Turn ################### ";
                fishLogger.LogMessage(logMessage);
                logMessage ="Player "+ this.getName().toString() + " has " + cards.size() + " cards.";
                fishLogger.LogMessage(logMessage);
                //Shuffle your cards first
                cards.shuffle();
                Pip lastPip = null;

                //Go through your PIPs and remember whether anyone has one of yours
                for(Pip key: pips.keySet()){
                    int value = pips.get(key);
                    if (value > 1){
                        theResult = ChoosePlayer(key);
                        break;
                    }
                    else {
                        //just remember this PIP so we can use this if none are > 1
                        lastPip = key;
                    }
                }
                if (theResult == null){
                    //Ask a player for a PIP (currently, the lastPip that was checked)
                    Player randomPlayer = playersInPlay.RandomPlayer();
                    theResult = new TurnResult(randomPlayer, lastPip,this);
                }
            }
        } catch (Exception e) {
            fishLogger.LogMessage(e.getMessage());
        }finally {
            return theResult;
        }
    }

    public Cards ask(TurnResult theTurnResult, Player askingPlayer){
        //Player is asked for a Pip.
        //Return those cards for that Pip or null if none
        Cards returnCards = new Cards();
        Cards removeCards = new Cards();
        for (Card card: cards){
            if (theTurnResult.getPip() == card.getValue()){
                returnCards.addCard(card);
                fishLogger.LogMessage("Player "+this.getName().toString()+" giving up "
                        + card.getValue().name());
                //Add the card to a collection of cards to remove from playerCards
                removeCards.addCard(card);
            }
        } for (Card card: removeCards){
            cards.remove(card);
        }

        //Finally, remember that you were asked for this
        whoHasWhat.put(theTurnResult.getPip(), askingPlayer);
        //and REALLY finally, remove this from your pips
        pips.remove(theTurnResult.getPip());
        return returnCards;
    }

    private TurnResult ChoosePlayer(Pip pip){
        //Return a TurnResult which is: Player, PIP, AskingPlayer
        Player player;
        TurnResult turnResult;
        if (whoHasWhat.containsKey(pip)){
            player = whoHasWhat.get(pip);
        }else{ //nobody's got any of this PIP - just ask anyone
            player = playersInPlay.RandomPlayer();
        }
        turnResult = new TurnResult(player, pip, this);
        return turnResult;
    }

    public void Listen(TurnResult turnResult, Player askingPlayer){
        //Take note of the turnResult, only if it's not you that's asking
        //and also if you're not the player being asked
        if (!turnResult.getPlayer().equals(this) &&
                !askingPlayer.equals(this)){
            String listenString;
            listenString = this.getName().toString() + " hears "  + askingPlayer.getName().toString() +
                    " asking " + turnResult.getPlayer().getName().toString() +
                    " for " + turnResult.getPip().toString();
            fishLogger.LogMessage(listenString);
            whoHasWhat.put(turnResult.getPip(), askingPlayer);
        }
    }

    public void learnOfPlayerOut(Player player){
        //Remove all knowledge of this player from your whoHasWhat hash map
        //Get a list of the Keys to delete
        ArrayList<Pip> keysToDelete = new ArrayList<>();
        for (Map.Entry<Pip, Player> entry : whoHasWhat.entrySet()) {
            Pip key = entry.getKey();
            Player p = entry.getValue();
            if (p.equals(player)){
                keysToDelete.add(key);
            }
        }
        for (Pip pipToRemove: keysToDelete){
            whoHasWhat.remove(pipToRemove);
        }
        this.deRegisterPlayer(player);
        fishLogger.LogMessage("Player "+this.getName().toString()+" learns that Player "
                +player.getName().toString()+" is out.");
    }

    public int NumberOfCards(){
        return cards.size();
    }

    public TurnResult takeRandomCardFromAnyPlayer(){
        Player theUnluckyPlayer = playersInPlay.RandomPlayer();
        //TODO this is causing a null exception in the logging - may need to rethink the null PIP idea
        TurnResult turnResult = new TurnResult(theUnluckyPlayer,null,this);
        return turnResult;
    }

    //DEBUG METHODS
    public String toString(){
        return this.getName() + ", " + this.getGender() + ", " + this.getType();
    }
    public String cardsToString(){
        return cards.toString();
    }
}
