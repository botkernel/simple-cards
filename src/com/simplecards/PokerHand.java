package com.simplecards;

import java.util.*;

/**
 *
 * Represents a poker hand (set of cards.)
 *
 */
public class PokerHand extends Hand {

    //
    // Payoffs
    //
    // Royal Flush     250 * bet
    // Straight Flush  50  * bet
    // Four of a kind  25  * bet
    // Full House      9   * bet
    // Flush           6   * bet
    // Straight        4   * bet
    // Three of a kind 3   * bet
    // Two Pair        2   * bet
    // Jacks or Better 1   * bet
    //
    // User loses a credit when it is entered in the machine to start game.
    // User is returned (if they have a winning hand) x*bet.
    //
    // Notes
    // http://www.worktheodds.com/university/videopoker.php
    //
    // It is important to note that all Video Poker payoffs are on a “for” 
    // and not a “to” basis. For example, a pair of Jacks pays 1 for 1 on 
    // a Jacks or Better Video Poker machine. 
    // This means that your original bet is returned, not doubled — 
    // in other words, you break even.
    //

    public PokerHand() {
        super();
    }

    public PokerHand(Card[] cards) {
        super(cards);
    }

    public String toString() {
        String ret = super.toString();

        //
        // Determine winning hand
        //

        return ret;
    }

    public boolean isStraight() {

        List<PokerCard> sorted = new ArrayList<PokerCard>();
        
        for(Card card: _cards) {
            sorted.add((PokerCard)card);
        }

        Collections.sort(sorted, new CardComparator());

        PokerCard[] cards = (PokerCard[])sorted.toArray(new PokerCard[0]);
    
        if(isStraightWalker(cards)) {
            return true;
        }

        //
        // Check for ace as high
        //
        if( cards[0].getFace().equals(BaseEngine.ACE) ) {
            cards[0].setValue(BaseEngine.FACES.length);
            Collections.sort(sorted, new CardComparator());
        
            if(isStraightWalker(cards)) {
                return true;
            }
        }

        return false;
    }

    private boolean isStraightWalker(PokerCard[] cards) {
        int prev = -1;
        for(int i = 0; i < cards.length; i++) {
            if(i == 0) {
                prev = cards[i].getValue();
            } else {
                int current = cards[i].getValue();
                if(current - prev != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private class CardComparator implements Comparator<PokerCard> {

        public int compare(PokerCard o1, PokerCard o2) {
            
            if(o1.getValue() < o2.getValue()) {
                return -1;
            }
            if(o1.getValue() > o2.getValue()) {
                return 1;
            }
            return 0;
        }

    } 

}

