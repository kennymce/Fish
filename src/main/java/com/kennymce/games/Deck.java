package com.kennymce.games;

import java.util.Collections;
import java.util.ListIterator;

public class Deck extends Cards{

    public Deck() {
        super();
        for (Suit s : Suit.values())
            for (Pip v : Pip.values())
                super.addCard(new Card(s, v));
    }
    public ListIterator<Card> iterator(){
        return super.iterator();
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
}
