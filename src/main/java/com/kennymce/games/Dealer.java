package com.kennymce.games;

public class Dealer {
    private static final Dealer ourInstance = new Dealer();
    private final Deck deck = new Deck();
    private Game game;
    public static Dealer getInstance() {
        return ourInstance;
    }

    public void setGame(Game game) { //TODO not entirely happy about this as a setter but this a singleton :(
        this.game = game;
        deck.shuffle();
    }

    private void dealToPlayer(Player player){
        Card card = deck.Deal();
        player.takeCard(card);
    }
    public Card DealCard(){
        return deck.Deal();
    }

    public void dealPlayerCards(Players players){
        int numberOfCardsPerPlayer = game.getNumberOfCardsPerPlayer();
        int cardsDealtPerPlayer = 0;
        while(cardsDealtPerPlayer < numberOfCardsPerPlayer){
            //Deal one card to each player
            for (int i=0; i<players.Size(); i++){
                dealToPlayer(players.player(i));
            }
            cardsDealtPerPlayer++;
        }
    }
    public int CardsInDeck(){
        return deck.size();
    }
}
