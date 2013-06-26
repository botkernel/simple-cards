package com.simplecards;

/**
 *
 */
public class Card {

    protected String _face;
    protected String _suit;

    public Card(String str) {
        this(   str.substring(0, str.length() - 1), 
                str.substring(str.length() - 1)         );
    }

    public Card(String face, String suit) {
        this._face = face;
        this._suit = suit;
    }

    public String getFace() { return _face; }
    public String getSuit() { return _suit; }
    
    public boolean equals(Card card) {
        return  this._face.equals(card.getFace()) && 
                this._suit.equals(card.getSuit());
    }

    public String toString() { return getFace() + getSuit(); }

}

