/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kennymce.games;

public class Card {
    public Suit getSuit() {
        return suit;
    }

    public Pip getValue() {
        return value;
    }

    private final Suit suit;
    private final Pip value;

    public Card(Suit suit, Pip value) {
        this.suit = suit;
        this.value = value;
    }

    public String toString(){
        return value + " of " + suit;
    }

}
