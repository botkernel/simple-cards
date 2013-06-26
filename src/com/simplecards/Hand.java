package com.simplecards;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * Represents a hand (set of cards.)
 *
 */
public class Hand {

    protected Card[] _cards;
        
    public Hand(Card[] cards) {
        this._cards = cards;
    }
        
    public Card[] getCards() { return _cards; }

    public void add(Card card) {
        if(_cards == null) {
            _cards = new Card[] { card };
            return;
        }
        Card[] newCards = new Card[_cards.length + 1];
        for(int i = 0; i < _cards.length; i++) {
            newCards[i] = _cards[i];
        }

        newCards[_cards.length] = card;
        _cards = newCards;
    }

    public String toString() {
        String ret = "";
        for(Card card: _cards) {
            ret += card + " ";
        }

        return ret;
    }

    public boolean contains(Card card) {
        for(Card c: _cards) {
            if(c.equals(card)) {
                return true;
            }
        }
        return false;
    }

}

