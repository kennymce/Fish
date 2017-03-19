package com.kennymce.games;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class Cards implements Iterable<Card>{
    protected LinkedList<Card> cards = new LinkedList<>();

    public ListIterator<Card> iterator(){
        return cards.listIterator();
    }

    public Cards() {
        //Not really needed as you get one by default
    }

    public void addCard(Card c){
        cards.add(c);
    }
    public Card Deal(){
        return cards.poll();
    }
    public int size(){
        return cards.size();
    }
    public boolean contains(Card card){
        return cards.contains(card);
    }
    public void remove(Card theCard){
        cards.remove(theCard);
    }
    public Card randomCard(){
        int index = 0;
        try {
            Random randomGenerator = new Random();
            index = randomGenerator.nextInt(cards.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return cards.get(index);
        }
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    //DEBUG METHODS
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (Card c:cards){
            sb.append(c.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
